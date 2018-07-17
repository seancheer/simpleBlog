package com.seancheer.service.interfaces;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import com.seancheer.exception.BlogBaseException;

/**
 * 博客相关的service类，包含创建blog，修改和删除blog等
 * 
 * @author seancheer
 * @date 2018年4月13日
 */
public interface IBlogService {

	String getAllCategoies(HttpServletResponse response);

	ModelAndView getBlogList(HttpServletResponse response, Long page, String categoryIds) throws BlogBaseException;

	ModelAndView getBlog(HttpServletResponse response, HttpSession session, Integer blogId) throws BlogBaseException;
}
