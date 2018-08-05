package com.seancheer.exception;

import com.seancheer.common.BlogCode;

/**
 * 解析config文件失败
 * 
 * @author seancheer
 * @date 2018年5月13日
 */
public class ParseConfigException extends BlogBaseException {

	/**
	 * id
	 */
	private static final long serialVersionUID = -7416288244325929519L;

	public ParseConfigException() {
		;
	}

	public ParseConfigException(BlogCode blogCode) {
		super(blogCode);
	}

	public ParseConfigException(String msg) {
		super(msg);
	}

	public ParseConfigException(Throwable cause) {
		super(cause);
	}

	public ParseConfigException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
