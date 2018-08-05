package com.seancheer.exception;

import com.seancheer.common.BlogCode;

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
	
	private BlogCode blogCode;
	
	public BlogBaseException()
	{
		;
	}
	
	public BlogBaseException(BlogCode blogCode)
	{
		this.blogCode = blogCode;
		this.message = blogCode.getMsg();
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

	public BlogCode getBlogCode() {
		return blogCode;
	}

	public void setBlogCode(BlogCode blogCode) {
		this.blogCode = blogCode;
	}

}
