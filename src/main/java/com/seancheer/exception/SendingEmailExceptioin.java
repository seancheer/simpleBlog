package com.seancheer.exception;

/**
 * 发送email出现异常
 * @author: seancheer
 * @date: 2018/8/9
 **/
public class SendingEmailExceptioin extends BlogBaseException{

    /**
     * 默认构造方法
     */
    public SendingEmailExceptioin()
    {
        super();
    }

    /**
     * 带cause的构造方法
     * @param cause
     */
    public SendingEmailExceptioin(Throwable cause)
    {
        super(cause);
    }

    /**
     * 带cause的构造方法
     * @param cause
     * @param msg
     */
    public SendingEmailExceptioin(Throwable cause, String msg)
    {
        super(msg, cause);
    }

}
