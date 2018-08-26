package com.seancheer.utils.springmvc;

import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 当springmvc出现映射错误的时候，由该实现方法进行处理
 * @author: seancheer
 * @date: 2018/8/26
 **/
@Order(value = 9999999)
public class CustomMappingExceptionResovler extends DefaultHandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        return super.resolveException(request, response, handler, ex);
    }
}
