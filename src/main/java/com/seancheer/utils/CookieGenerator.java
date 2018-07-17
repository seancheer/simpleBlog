package com.seancheer.utils;

import javax.servlet.http.Cookie;

import com.seancheer.common.BlogConfigImpl;

/**
 * 生成cookie的类
 * @author seancheer
 * @date 2018年3月20日
 */
public class CookieGenerator {

	/**
	 * 生成cookie，根据传入的超时时间生成
	 * @param key
	 * @param value
	 * @param timeout 单位为s
	 * @return
	 */
	public static Cookie genCookie(String key, String value, int timeout)
	{
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(timeout);
		//cookie.setDomain(BlogConfig.DOMAIN_NAME);
		return cookie; 
	}
}
