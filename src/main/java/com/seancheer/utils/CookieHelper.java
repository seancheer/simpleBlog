package com.seancheer.utils;

import javax.servlet.http.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * cookie相关的操作类
 * @author seancheer
 * @date 2018年4月12日
 */
public class CookieHelper {

	private static final Logger logger = LoggerFactory.getLogger(CookieHelper.class);

	/**
	 * 根据key获取对应得cookie对象
	 * @param cookies
	 * @param key
	 * @return
	 */
	public static Cookie getCookieByKey(Cookie[] cookies,String key)
	{
		if (null == cookies || StringUtils.isEmpty(key))
		{
			logger.warn("Invalid parameter!");
			return null;
		}
		
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(key))
			{
				return cookie;
			}
		}

		return null;
	}

    /**
     * 从cookie中获取当前系统的cookie entity
     * @param cookies
     * @return
     */
	public static CookieEntity getEntityFromCookie(Cookie[] cookies)
    {
        if (null == cookies || cookies.length == 0)
        {
            logger.warn("Invalid parameter!");
            return null;
        }

        CookieEntity.CookieEntityBuilder cookieBuilder = new CookieEntity.CookieEntityBuilder();
        for (Cookie cookie : cookies) {
            cookieBuilder.setCookie(cookie);
        }

        return cookieBuilder.build();
    }
}
