package com.seancheer.utils.springmvc;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 获取applicationContext对象，通过该对象可以获取对应的bean，
 * 当在工程中使用自己管理的类的时候，该context会非常有用
 * @author: seancheer
 * @date: 2018/8/24
 **/
public class BlogApplicationContext implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getContext()
    {
        return context;
    }
}
