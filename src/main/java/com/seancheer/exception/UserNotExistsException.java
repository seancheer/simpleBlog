package com.seancheer.exception;

import com.seancheer.common.BlogCode;

/**
 * 用户不存在异常
 * @author seancheer
 * @date 2018年3月19日
 */
public class UserNotExistsException extends BlogBaseException {

	/**
	 * id
	 */
	private static final long serialVersionUID = -7573455215996120609L;

	public UserNotExistsException() {
		;
	}

	public UserNotExistsException(BlogCode blogCode) {
		super(blogCode);
	}

	public UserNotExistsException(String msg) {
		super(msg);
	}

	public UserNotExistsException(Throwable cause) {
		super(cause);
	}

	public UserNotExistsException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
