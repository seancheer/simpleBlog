package com.seancheer.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.seancheer.common.BaseOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.seancheer.exception.BlogBaseException;
import com.seancheer.service.interfaces.IBlogService;

/**
 * passage相关的展示，只负责查询相关的功能
 * 
 * @author seancheer
 * @date 2018年3月2日
 */
@Controller
public class BlogController extends BaseOperation {

	private static final Logger logger = LoggerFactory.getLogger(BlogController.class);

	@Autowired
	private IBlogService blogService;

    /**
     * 获取所有分类的controller
     * @param response
     * @param session
     * @return
     */
	@RequestMapping(value = "/getAllCategoies", method = RequestMethod.GET)
	@ResponseBody
	public String getAllCategoies(HttpServletResponse response, HttpSession session) {
		return blogService.getAllCategoies(response);
	}

	/**
	 * 获取博客列表
	 * @param response
	 * @param session
	 * @param page
	 * @param categoryIds
	 * @return
	 */
	@RequestMapping(value = "/blogList", method = RequestMethod.GET)
	public ModelAndView blogList(HttpServletResponse response, HttpSession session,
			@RequestParam(value = "page", required = false) Long page,
			@RequestParam(value = "categoryId", required = false) String categoryIds) {
		try {
			ModelAndView view = blogService.getBlogList(response, page, categoryIds);
			//TODO 以后需要根据session里面的信息来决定是否为god
			view.addObject("isGod", true);
			return view;
		} catch (BlogBaseException e) {
			logger.error("GetBlogList failed!", e);
			return redirect500View();
		}
	}

	/**
	 * 获取博客详情页
	 * @param response
	 * @param session
	 * @param blogId
	 * @return
	 */
	@RequestMapping(value = "/blog", method = RequestMethod.GET)
	public ModelAndView blogDetail(HttpServletResponse response, HttpSession session,
			@RequestParam(value = "blogId", required = false) Integer blogId) {
		if (null == blogId || blogId < 0)
		{
			return redirect404View();
		}
		try {
			return blogService.getBlog(response, session, blogId);
		} catch (BlogBaseException e) {
			logger.error("Geting blog failed!",e);
			return redirect500View();
		}
	}
}
