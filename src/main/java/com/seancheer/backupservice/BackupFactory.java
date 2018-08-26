package com.seancheer.backupservice;

import com.seancheer.common.BlogConfigImpl;
import com.seancheer.common.IBlogConfig;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 产生备份对象的工厂类
 *
 * @author: seancheer
 * @date: 2018/8/12
 **/
public class BackupFactory {
    //指定backup的方式，目前仅支持email一种方式
    private static final String BLOG_BACKUP_METHOD = "blog.backup.method";

    //默认采用email的备份方式
    private static final BackupMethod DEFAULT_BACKUP_METHOD = BackupMethod.valueOf("EMAIL");

    private static BackupMethod currentMethod = DEFAULT_BACKUP_METHOD;

    private static final Map<BackupMethod, IBlogBackup> allBackups = new HashMap<>();

    /**
     * 获取当成生效的backup method策略
     */
    static {
        IBlogConfig config = BlogConfigImpl.getInstance();
        String method = config.getValue(BLOG_BACKUP_METHOD);
        if (!StringUtils.isEmpty(method)) {
            BackupMethod m = BackupMethod.valueOf(method);
            if (m != null) {
                currentMethod = m;
            }
        }

        //初始化当前所有的backups
        initBackups();
    }

    /**
     * 初始化当前所有可用的backups
     */
    private static void initBackups() {
        allBackups.put(DEFAULT_BACKUP_METHOD, new SimpleBlogBackupImpl());
    }

    /**
     * 获取当前生效的backup策略
     *
     * @return
     */
    public static IBlogBackup getCurrentBackup() {
        return getBackup(DEFAULT_BACKUP_METHOD);
    }


    /**
     * 根据method获取对应的backup
     *
     * @return
     */
    public static IBlogBackup getBackup(BackupMethod method) {
        return allBackups.get(currentMethod);
    }
}
