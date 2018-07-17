package com.seancheer.utils;

import java.util.UUID;

/**
 * token生成器
 * @author seancheer
 * @date 2018年3月20日
 */
public class TokenGenerator {

	/**
	 * 生成uuid，去除掉中间的-
	 * @return
	 */
	public static String genUUIDToken()
	{
		return UUID.randomUUID().toString().toLowerCase().replaceAll("-", "");
	}
	
}
