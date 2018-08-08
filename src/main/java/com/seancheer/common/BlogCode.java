package com.seancheer.common;

import org.json.JSONObject;

/**
 * 一些成功和错误码相关
 * @author: seancheer
 * @date: 2018/8/5
 **/
public enum BlogCode {

    //成功相关的标志码，如果这里修改，那么前端相应的代码也需要修改
    SUCCESS(1000, "成功！"),

    //失败相关的错误码
    FAILED(100, "操作失败！"),
    /*
     * 在godEntrance页面尝试登入博客编辑界面的时候输入的key不正确
     */
    INVALID_KEY(101, "错误的密码，请重试！"),

    UNKOWN_ERROR(103, "未知错误！"),

    USER_NOT_EXISTS(104, "用户不存在！"),

    INTERNAL_ERROR(105, "内部错误！"),

    PARAMETER_ERROR(106, "参数错误！"),

    INVALID_BLOG_TITLE(107, "标题过长！"),

    SERVER_BUSY(108, "服务器忙，请稍后重试！"),

    UNAUTHORIZED(109, "无相关权限！"),

    NOT_FOUND(110, "无法找到对应资源！");

    private int code;

    private String msg;

    private BlogCode(int code, String msg) {
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
    public static BlogCode getErrorCodeByCode(int code) {
        if (code < 0) {
            throw new IllegalArgumentException("Invalide code. Code must be greater than 0!");
        }

        BlogCode[] allBlogCodes = BlogCode.values();
        for (BlogCode blogCode : allBlogCodes) {
            if (code == blogCode.getCode()) {
                return blogCode;
            }
        }

        return null;
    }
}
