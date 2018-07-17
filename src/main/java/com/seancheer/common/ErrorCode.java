package com.seancheer.common;

import org.json.JSONObject;

public enum ErrorCode {

	/*
	 * 输入的key正确，校验成功！
	 */
	LOGIN_SUCCESS(100, "验证成功！"),
	
	POST_SUCCESS(100,"发表成功！"),

	/*
	 * 在godEntrance页面尝试登入博客编辑界面的时候输入的key不正确
	 */
	INVALID_KEY(101, "错误的密码，请重试！"),

	FAVOR_SUCCESS(102, "点赞成功！"),

	UNKOWN_ERROR(103, "未知错误！"),
	
	USER_NOT_EXISTS(104,"用户不存在！"),
	
	INTERNAL_ERROR(105,"内部错误！"),
	
	PARAMETER_ERROR(106,"参数错误！"),
	
	SERVER_BUSY(108,"服务器忙，请稍后重试！");

	private int code;

	private String msg;

	private ErrorCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return toJson().toString();
	}

	public JSONObject toJson() {
		JSONObject object = new JSONObject();
		object.put("code", code);
		object.put("msg", msg);
		return object;
	}

	/**
	 * 通过code来查找对应得ErrorCode对象
	 * 
	 * @param code
	 * @return
	 */
	public static ErrorCode getErrorCodeByCode(int code) {
		if (code < 0) {
			throw new IllegalArgumentException("Invalide code. Code must be greater than 0!");
		}

		ErrorCode[] allErrorCodes = ErrorCode.values();
		for (ErrorCode errorCode : allErrorCodes) {
			if (code == errorCode.getCode()) {
				return errorCode;
			}
		}

		return null;
	}
}
