package com.seancheer.backupservice;

import com.seancheer.dao.entity.Passage;

/**
 * 继续博客备份的接口
 *
 * @author seancheer
 * @date 2018年6月9日
 */
public interface IBlogBackup {

    //指定backup的方式，目前仅支持email一种方式
    static final String BLOG_BACKUP_METHOD = "blog.backup.method";

    //默认采用email的备份方式
    static final BakcupMethod DEFEAULT_BACKUP_METHOD = BakcupMethod.valueOf("email");

    //需要备份的地址
    static final String BLOG_BACKUP_EMAIL_ADDRESS = "blog.backup.emailaddress";

    //用来备份的邮件服务器地址
    static final String BLOG_BACKUP_MAIL_HOST = "blog.backup.mail.host";

    //发送人的邮件地址
    static final String BACKUP_MAIL_SENDER_ADDRESS = "blog.backup.mail.sender.address";

    //收件人的邮件凭据
    static final String BACKUP_MAIL_SENDER_PWD = "blog.backup.mail.sender.pwd";

    static final String BACKUP_LOCAL_PATH = "blog.backup.local.path";

    /**
     * 立即进行备份
     * @param p
     * @return
     */
    boolean backupImmediatly(Passage p);

    /**
     * 获取备份的目的地
     * @return
     */
    String getBakDestination();
}
