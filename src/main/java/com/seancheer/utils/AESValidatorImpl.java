package com.seancheer.utils;

import com.seancheer.common.BlogConfigImpl;
import com.seancheer.common.BlogConstants;
import com.seancheer.common.IBlogConfig;
import com.seancheer.exception.BlogBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * 验证用户输入的key是否正确，
 * 通过RSA的方式进行解析
 * @author seancheer
 * @date 2018年3月8日
 */
public class AESValidatorImpl implements IGodValidator{
	private static final Logger logger = LoggerFactory.getLogger(AESValidatorImpl.class);

	private static final String KEY = "hellohelloo";

	private static final boolean DEBUG_MODE = true;

	private String blogPassword;

	private IBlogConfig blogConfig = BlogConfigImpl.getInstance();

	/**
	 * 默认的初始化方法，读取正确的secretkey
	 */
	public void init()
	{
		blogPassword = blogConfig.getValue(BlogConstants.BLOG_GOD_PASSWORD);
		if (StringUtils.isEmpty(blogPassword))
		{
			String msg = "Blog encrypt password can not be empty! Please check!";
			logger.error(msg);
			throw new RuntimeException(msg);
		}

        try {
            blogPassword = AESHelper.decrypt(blogPassword);
        } catch (BlogBaseException e) {
		    throw new RuntimeException(e);
        }

        if (StringUtils.isEmpty(blogPassword))
        {
            String msg = "Blog decrypt password can not be empty! Please check!";
            logger.error(msg);
            throw new RuntimeException(msg);
        }
    }

    /**
     * 验证用户提交的key是否正确
     * @param userKey
     * @return
     */
	@Override
	public boolean validate(String userKey) {

	    if (DEBUG_MODE)
        {
            logger.warn("Current mode is DEBUG_MODE.");
            return KEY.equals(userKey);
        }

	    if (StringUtils.isEmpty(userKey))
        {
            logger.debug("User submit a empty key!");
            return false;
        }

        try {
            return blogPassword.equals(AESHelper.decrypt(userKey));
        } catch (BlogBaseException e) {
	        logger.info("Decrypting user key failed!");
	        return false;
        }
	}
}
