package com.seancheer.common;

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

}
