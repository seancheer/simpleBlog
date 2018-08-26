package com.seancheer.utils.springmvc;

import org.springframework.web.servlet.DispatcherServlet;

/**
 * 定制化的dispatcherservlet
 *
 * @author: seancheer
 * @date: 2018/8/26
 **/
public class BlogDispatcherServlet extends DispatcherServlet {
    public BlogDispatcherServlet() {
        super();
        //设置只使用自定义的exceptionResovler
        setDetectAllHandlerExceptionResolvers(false);
    }
}
