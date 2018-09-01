package com.seancheer.common;

import org.springframework.util.StringUtils;

public class BlogConstants {

    //blog title所能接受的最大长度
    public static final int MAX_BLOGTITLE_LENGTH = 64;

    //浏览器使用window.location.replace进行跳转
    public static final String REDIRECT = "redirect";

    //浏览器使用window.location.href进行跳转
    public static final String HREF = "href";

    public static final String DEFAULT_ENCODING = "utf-8";

    public static final int ONE_DAY_IN_SECONDS = 60 * 60 * 24;

    public static final int ONE_DAY_IN_MILISECONDS = ONE_DAY_IN_SECONDS * 1000;

    public static final int ONE_MONTH_IN_SECONDS = ONE_DAY_IN_SECONDS * 30;

    public static final int TEN_DAY_IN_SECONDS = ONE_DAY_IN_SECONDS * 10;

    public static final String KEY_BLOG_TITLE = "blogTitle";

    public static final String KEY_BLOG_CONTENT = "blogContent";

    public static final String KEY_BLOG_CATEGORY_0 = "select0";

    public static final String KEY_BLOG_CATEGORY_1 = "select1";

    public static final String ADMIN_USER_NAME = "god";

    public static final String CATEGORY_SEPERATOR = ",";

    public static final String BLOG_AES_KEY = "blog.aes.key";

    public static final String BLOG_GOD_PASSWORD = "blog.god.password";

    public static final String BLOG_BACKUP_DIR = "blog.backup.dir";

    public static final String MAIL_HOST = "mail.host";

    public static final String BLOG_MAIL_HOST = "blog.mail.host";

    public static final String MAIL_AUTH = "mail.smtp.auth";

    //后台备份的时间间隔，默认为1天运行一次.
    public static final String BACKUP_INTERVAL_MS = "backup.interval.ms";

    //至少一分钟
    public static final int MIN_BACKUP_INTERVAL_MS = 1000 * 60;

    //最大10天
    public static final int MAX_BACKUP_INTERVAL_MS = ONE_DAY_IN_MILISECONDS * 10;

    //中国的时区
    public static final String CHINA_TIME_ZONE = "GMT+8:00";

    public static final String COOKIE_IS_EXPIRED = "cookie_is_expired";

    //是否为管理员类型的session
    public static final String COOKIE_IS_GOD = "cookie_is_god";

    //从cookie中读取的entity
    public static final String COOKIE_ENTITY = "cookie_entity";

    public static String BACKUP_LOCAL_PATH = "/home/seancheer/";

    static {
        String platform = System.getProperty("os.name").toLowerCase();
        //如果是windows系统，那么使用c盘的tmp的目录，方便调试
        if (platform.contains("windows")) {
            BACKUP_LOCAL_PATH = "c:/Temp/";
        }
    }

}
