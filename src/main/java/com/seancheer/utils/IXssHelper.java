package com.seancheer.utils;

/**
 * 过滤掉用户的输入，防止xss攻击
 * @author seancheer
 * @date 2018年4月11日
 */
public interface IXssHelper {

	String filter(String content);
}
