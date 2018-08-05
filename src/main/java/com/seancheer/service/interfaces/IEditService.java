package com.seancheer.service.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 编辑博客的逻辑，包括增加，修改，删除，查询等
 * 
 * @author seancheer
 * @date 2018年4月11日
 */
public interface IEditService {

	static final int MIN_BLOG_CONTENT_LENGTH = 20;

	/**
	 * 创建博客
	 * 
	 * @param request
	 * @param response
	 * @param postForm 表单
	 * @return
	 */
	JSONObject createNewBlog(HttpServletRequest request, HttpServletResponse response, Map<String,String> postForm);

	/**
	 * 通过博客id查询博客
	 * 
	 * @param blogId
	 * @param response
	 * @return
	 */
	JSONObject queryBlog(String blogId, HttpServletResponse response);

	/**
	 * 通过博客id删除博客
	 * 
	 * @param blogId
	 * @param response
	 * @return
	 */
	JSONObject deleteBlog(String blogId, HttpServletResponse response);
	
	/**
	 * 编辑blog页面
	 * @param response
	 * @param session
	 * @param blogId
	 * @return
	 */
	ModelAndView editBlog(HttpServletResponse response, HttpSession session,Integer blogId);

	/**
	 * 负责更新blog的接口
	 * @param request
	 * @param response
	 * @param blogId
	 * @param postForm 表单
	 * @return
	 */
	JSONObject updateBlog(HttpServletRequest request, HttpServletResponse response, Integer blogId, Map<String,String> postForm);

}
