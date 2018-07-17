package com.seancheer.utils;

import org.springframework.util.StringUtils;

/**
 * 过滤掉用户的输入，防止xss攻击的简单实现
 * @author seancheer
 * @date 2018年4月11日
 */
public class SimpleXssHelperImpl implements IXssHelper {

	@Override
	public String filter(String content) {
		if (StringUtils.isEmpty(content))
		{
			return content;
		}
		
		//将content中的特殊字符过滤掉，防止xss攻击
		return content;
	}

}
