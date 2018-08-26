package com.seancheer.backupservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seancheer.common.BlogConfigImpl;
import com.seancheer.common.BlogConstants;
import com.seancheer.common.IBlogConfig;
import com.seancheer.dao.entity.Passage;
import com.seancheer.exception.BlogBaseException;
import com.seancheer.utils.AESHelper;
import com.seancheer.utils.EmailUtils;
import com.seancheer.utils.FileUtils;
import com.seancheer.utils.springmvc.BlogApplicationContext;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.internal.StandardServiceRegistryImpl;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.util.StringUtils;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 简单的备份实现类，包含以下备份策略
 * 1 立即备份，发表博客的时候进行备份，此时的备份只是保存在本地
 * 2 定时备份，定时备份，打包，并发送邮件到指定地址，发送成功删除对应文件
 *
 * @author: seancheer
 * @date: 2018/6/12
 **/

public class SimpleBlogBackupImpl implements IBlogBackup {

    private static final Logger logger = LoggerFactory.getLogger(SimpleBlogBackupImpl.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    //截取的文件名的长度
    private static final int TITLE_PREFIX_LENGTH = 5;

    //立即备份的最少的file个数
    private static final int IMMEDIATLY_FILE_NUM = 5;

    //存档的file个数
    private static final int BACKUP_ARCHIVE_FILE_NUM = 3;

    //默认凌晨3点开始备份
    private static final int BACKUP_TIME_HOUR = 3;

    private String mailHost;

    private String targetAddress;

    private String senderAddress;

    private String senderPwd;

    private String immediatlyPath;

    private String archivePath;

    private IBlogConfig blogConfig = BlogConfigImpl.getInstance();

    /**
     * 构造方法，负责初始化一些必要的变量
     */
    public SimpleBlogBackupImpl() {
        init();

        int backupInterval = blogConfig.getInt(BlogConstants.BACKUP_INTERVAL_MS, BlogConstants.ONE_DAY_IN_MILISECONDS,
                BlogConstants.MIN_BACKUP_INTERVAL_MS, BlogConstants.MAX_BACKUP_INTERVAL_MS);
        logger.info("Backup at fixed time, backupInterval:{}ms", backupInterval);
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                backupAtFixedTime();
            }
        }, calcDelayMSInChina(), backupInterval, TimeUnit.MILLISECONDS);
    }


    /**
     * 初始化相关的变量
     */
    private void init() {
        mailHost = blogConfig.getValue(BLOG_BACKUP_MAIL_HOST);
        targetAddress = blogConfig.getValue(BLOG_BACKUP_EMAIL_ADDRESS);
        senderAddress = blogConfig.getValue(BACKUP_MAIL_SENDER_ADDRESS);
        senderPwd = blogConfig.getValue(BACKUP_MAIL_SENDER_PWD);
        logger.info("Backup info. mailHost:{} targetAddress:{} senderAddress:{}", mailHost, targetAddress, senderAddress);
        if (StringUtils.isEmpty(mailHost) || StringUtils.isEmpty(targetAddress) || StringUtils.isEmpty(senderAddress)
                || StringUtils.isEmpty(senderPwd)) {
            logger.error("mailHost or address is empty!");
            throw new RuntimeException("mailHost or address is empty!");
        }

        String localPath = blogConfig.getValue(BACKUP_LOCAL_PATH);
        logger.info("Backup local path:" + localPath);
        if (StringUtils.isEmpty(localPath)) {
            logger.warn("Backup local path is empty! Use default instead!");
            localPath = DEFAULT_LOCAL_PATH;
        }

        immediatlyPath = localPath + BAKCUP_IMMEDIATLY_DIR;
        archivePath = localPath + BAKCUP_ARCHIVE_DIR;
        createPathIfNotExits(immediatlyPath);
        createPathIfNotExits(archivePath);
    }


    /**
     * 立即进行备份，准备做一个简单的备份，将passage对象
     * 转换为json，放在对象目录下。
     *
     * @param p
     * @return
     */
    @Override
    public boolean backupImmediatly(Passage p) {
        if (null == p) {
            throw new IllegalArgumentException("Invalid passage!");
        }

        String fileName = String.format("%s/%s-%s.json", immediatlyPath, p.getTitle().substring(0, TITLE_PREFIX_LENGTH),
                System.currentTimeMillis());
        File file = new File(fileName);

        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            MAPPER.writeValue(out, p);
        } catch (IOException e) {
            return false;
        } finally {
            closeOutputStream(out);
        }

        //开启一个线程，检查是否有必要清理早期的json文件，由于该任务需要的事情很少，所以简单开启一个线程即可
        new Thread(new Runnable() {
            @Override
            public void run() {
                cleanImmediatlyPath();
            }
        }).start();
        return true;
    }

    /**
     * 触发后台任务，此处调试的时候会用到
     *
     * @return
     */
    @Override
    public boolean triggerBgTask() {
        backupAtFixedTime();
        return true;
    }

    /**
     * 长期备份，作为一个定时任务，该任务做以下事情：
     * 1 把数据库中的sql导出来，打包发送给特定的email地址，该操作需要在凌晨来做
     * 2 发送完成后，保存最近三次的压缩包，多余的进行删除。
     */
    private void backupAtFixedTime() {
        //step 1: export sql，如果需要导出数据，那么需要使用mysqldump功能
        //String filePath = exportDDLSqlToPath();
        //把immediately目录下面的json全部打包，然后发送到邮箱

        String filePath = null;
        try {
            //目前就先只对immediatly下面的文件进行打包即可
            filePath = zipImmediatlyFile();
            //step 2: send newest file to some address
            sendArchiveFile(filePath);
        } catch (BlogBaseException e) {
            logger.error("Sending archive failed! filePath:" + filePath, e);
        }
        //step 3: clean some archive files
        cleanArchive();
        cleanArchive(".zip");
    }

    /**
     * 导出数据库中的表结构到特定目录
     *
     * @return 文件路径
     */
    private String exportDDLSqlToPath() {
        //Metadata metadata = new Meta
        Metadata metadata = getMetadata();
        String filePath = null;

        try {
            SchemaExport export = new SchemaExport();
            export.setDelimiter(";");
            export.setFormat(true);
            export.setHaltOnError(true);

            Calendar calendar = Calendar.getInstance();
            long nowMili = calendar.getTimeInMillis();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String formatDate = df.format(calendar.getTime());
            //文件格式为formatDate-timeInMili.sql
            filePath = String.format("%s/%s-%s.sql", archivePath, formatDate, nowMili);
            export.setOutputFile(filePath);
            export.createOnly(EnumSet.of(TargetType.SCRIPT), metadata);
        } finally {
            ServiceRegistry serviceRegistry = ((MetadataImplementor) metadata).getMetadataBuildingOptions().getServiceRegistry();
            ((StandardServiceRegistryImpl) serviceRegistry).destroy();
        }

        return filePath;
    }

    /**
     * 将即时备份目录下面的文件全部打包
     *
     * @return
     */
    private String zipImmediatlyFile() throws BlogBaseException {
        Calendar calendar = Calendar.getInstance();
        long nowMili = calendar.getTimeInMillis();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String formatDate = df.format(calendar.getTime());
        //将文件压缩到archivePath下
        String zipFilePath = String.format("%s/%s-%s.zip", archivePath, formatDate, nowMili);
        File[] files = new File(immediatlyPath).listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".json");
            }
        });

        logger.info("File in immediatlyPath size:" + files.length);
        FileUtils.zipFiles(zipFilePath, files);
        return zipFilePath;
    }

    /**
     * 发送指定文件到特定路径
     * @param filePath
     */
    public void sendArchiveFile(String filePath) throws BlogBaseException {
        String from = senderAddress;
        String decryptPwd = AESHelper.decrypt(senderPwd);
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        InternetAddress toAddress = null;
        try {
            toAddress = new InternetAddress(targetAddress);
        } catch (AddressException e) {
            throw new BlogBaseException("Address is invalid:" + targetAddress, e);
        }

        logger.info("Starting sending the email! from:{} to:{} filePath:{}", from, targetAddress, filePath);
        EmailUtils.sendMsg(from, decryptPwd, fileName, toAddress, mailHost, fileName, filePath);
        logger.info("Sending email success~");
    }

    /**
     * 清理一些存档文件
     * 存档文件格式为formatDate-timeInMili.sql
     */
    private void cleanArchive() {
      cleanArchive(".sql");
    }

    /**
     * 对archive文件夹进行清理
     * @param fileSuffix 指定的文件后缀名
     */
    private void cleanArchive(String fileSuffix)
    {
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(fileSuffix);
            }
        };
        cleanDirByFilter(archivePath, filter, BACKUP_ARCHIVE_FILE_NUM, fileSuffix);
    }

    /**
     * 获取备份的目的地
     *
     * @return
     */
    @Override
    public String getBakDestination() {
        return immediatlyPath;
    }


    /**
     * 如果对应文件夹不存在，那么创建
     *
     * @param path path
     */
    private void createPathIfNotExits(String path) {
        if (StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException("Path can not be empty!");
        }

        File file = new File(path);
        if (!file.exists() && !file.mkdirs()) {
            String msg = String.format("Creating dir failed! path:" + path);
            logger.error(msg);
            throw new RuntimeException(msg);
        }

    }

    /**
     * 检查immediately文件夹是否需要进行清理
     */
    private void cleanImmediatlyPath() {
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".json");
            }
        };
        cleanDirByFilter(immediatlyPath, filter, IMMEDIATLY_FILE_NUM, ".json");
    }


    /**
     * 关闭outputstream
     *
     * @param out
     */
    private void closeOutputStream(OutputStream out) {
        if (null == out) {
            return;
        }

        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            logger.error("Closing outputstream failed!", e);
            throw new RuntimeException(e);
        }
    }


    /**
     * 计算当前时间到备份时间的时间间隔，单位为ms
     * 依据的标准为中国标准时间
     *
     * @return
     */
    private long calcDelayMSInChina() {
        TimeZone chinaZone = TimeZone.getTimeZone(BlogConstants.CHINA_TIME_ZONE);
        Calendar calendar = Calendar.getInstance();
        int nowHour = calendar.get(Calendar.HOUR_OF_DAY);
        long nowTimeMili = calendar.getTime().getTime() - calendar.getTimeZone().getRawOffset() + chinaZone.getRawOffset();
        //设置为凌晨3点
        calendar.set(Calendar.HOUR_OF_DAY, BACKUP_TIME_HOUR);

        //说明此时时间已经过了凌晨3点，那么，需要将day增加1
        if (nowHour > BACKUP_TIME_HOUR) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        long backTimeMili = calendar.getTime().getTime() - calendar.getTimeZone().getRawOffset() + chinaZone.getRawOffset();
        assert backTimeMili > nowTimeMili;
        long delay = backTimeMili - nowTimeMili;
        logger.info("nowHour:{} nowTimemili:{} backTimeMili:{} delay:{}", nowHour, nowTimeMili, backTimeMili, delay);
        return delay;
    }

    /**
     * 根据filter，扫描path下面的所有文件，
     *
     * @param path
     * @param filter
     * @param atLeastNum
     * @param suffix     文件的后缀名，用来截取文件名中的时间戳
     */
    private void cleanDirByFilter(String path, FilenameFilter filter, int atLeastNum, String suffix) {
        File file = new File(path);
        File[] files = file.listFiles(filter);

        if (files == null) {
            logger.info("Can not list files with *{}!", suffix);
            return;
        }

        if (files.length <= atLeastNum) {
            return;
        }

        Map<String, File> fileMap = new HashMap<>();
        List<String> timeList = new ArrayList<>();
        for (File f : files) {
            String fileName = f.getName();
            logger.debug("This file name:" + fileName);
            String[] splitNames = fileName.split("-");

            //取最后一个部分
            String name = splitNames[splitNames.length - 1];
            int dotIndex = name.indexOf(suffix);
            //把时间戳部分取出来
            String key = name.substring(0, dotIndex);
            logger.debug("Timestamp in this file:{}ms fileName:{}", key, fileName);
            fileMap.put(key, f);
            timeList.add(key);
        }

        Collections.sort(timeList);
        //取5个后面进行删除
        List<String> deletedList = timeList.subList(atLeastNum, timeList.size());
        logger.info("We will delete files with these timestamp:[{}]", deletedList);

        for (String item : deletedList) {
            File deletedFile = fileMap.get(item);
            if (!deletedFile.delete()) {
                logger.error("Deleting file failed! fileName:" + deletedFile.getName());
            }
        }
    }

    /**
     * 通过sessionfactory获取到实际的Metadata
     *
     * @return
     */
    private Metadata getMetadata() {
        LocalSessionFactoryBean sessionFactoryBean = BlogApplicationContext.getContext().getBean(LocalSessionFactoryBean.class);
//        Properties properties = new Properties();
//        properties.put("hibernate.hbm2ddl.auto", "create");
//        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
//        properties.put("hibernate.id.new_generator_mappings", "true");
//        properties.put("hibernate.connection.driver_class", "org.hsqldb.jdbc.JDBCDriver");
//        properties.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/my_blog?useUnicode=true&amp;characterEncoding=UTF-8");
//        properties.put("hibernate.connection.username","root");
//        properties.put("hibernate.connection.password","143900");
//        StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().applySettings(properties).build();
//        MetadataSources sources = new MetadataSources(standardRegistry);
        Field configField = null;
        try {
            configField = sessionFactoryBean.getClass().getDeclaredField("configuration");
            configField.setAccessible(true);
            Configuration config = (Configuration) configField.get(sessionFactoryBean);
            Field sourceField = Configuration.class.getDeclaredField("metadataSources");
            sourceField.setAccessible(true);
            MetadataSources sources = (MetadataSources) sourceField.get(config);
            return sources.getMetadataBuilder(config.getStandardServiceRegistryBuilder().build()).build();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.error("Reflecting failed!", e);
            throw new RuntimeException(e);
        }
    }

    public String getArchivePath() {
        return archivePath;
    }
}
