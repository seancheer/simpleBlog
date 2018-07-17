package com.seancheer.exception;

import com.seancheer.common.ErrorCode;

/**
 * 博客系统的基本异常类
 * @author seancheer
 * @date 2018年2月23日
 */
public class BlogBaseException extends Exception {

	/**
	 * id
	 */
	private static final long serialVersionUID = 8211557141763645479L;
	
	private String message;
	
	private Throwable cause;
	
	private ErrorCode errorCode;
	
	public BlogBaseException()
	{
		;
	}
	
	public BlogBaseException(ErrorCode errorCode)
	{
		this.errorCode = errorCode;
		this.message = errorCode.getMsg();
	}
	
	public BlogBaseException(String msg)
	{
		this.message = msg;
	}
	
	public BlogBaseException(Throwable cause)
	{
		this.message = "";
		this.cause = cause;
	}
	
	public BlogBaseException(String msg,Throwable cause)
	{
		this.message = msg;
		this.cause = cause;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Throwable getCause() {
		return cause;
	}

	public void setCause(Throwable cause) {
		this.cause = cause;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

}
