package com.seancheer.utils.springmvc;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
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

    /**
     * 自定义返回页面，避免默认页面返回
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @return
     */
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex instanceof HttpRequestMethodNotSupportedException)
        {
            response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
            return new ModelAndView("redirect:/error/405");
        }

        return super.doResolveException(request, response, handler, ex);
    }
}
