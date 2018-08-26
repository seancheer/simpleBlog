package com.seancheer.backupservice;

import com.seancheer.common.BlogConstants;
import com.seancheer.dao.entity.Passage;

/**
 * 继续博客备份的接口
 *
 * @author seancheer
 * @date 2018年6月9日
 */
public interface IBlogBackup {

    //需要备份的地址
    static final String BLOG_BACKUP_EMAIL_ADDRESS = "blog.backup.emailaddress";

    //用来备份的邮件服务器地址
    static final String BLOG_BACKUP_MAIL_HOST = "blog.backup.mail.host";

    //发送人的邮件地址
    static final String BACKUP_MAIL_SENDER_ADDRESS = "blog.backup.mail.sender.address";

    //收件人的邮件凭据
    static final String BACKUP_MAIL_SENDER_PWD = "blog.backup.mail.sender.pwd";

    //本地备份地址
    static final String BACKUP_LOCAL_PATH = "blog.backup.local.path";

    //立即备份的文件夹，绝对路径为local_path/immediatly
    static final String BAKCUP_IMMEDIATLY_DIR = "immediatly";

    //存档的文件夹，真正的路径为local_path/archive
    static final String BAKCUP_ARCHIVE_DIR = "archive";

    //默认的备份地址
    static String DEFAULT_LOCAL_PATH = BlogConstants.BACKUP_LOCAL_PATH;

    /**
     * 立即进行备份
     * @param p
     * @return
     */
    boolean backupImmediatly(Passage p);

    /**
     * 触发后台任务
     * @return
     */
    boolean triggerBgTask();

    /**
     * 获取备份的目的地
     * @return
     */
    String getBakDestination();
}
