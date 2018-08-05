package com.seancheer.common;

import org.springframework.web.servlet.ModelAndView;

/**
 * 提供一些基本的方法
 * 
 * @author seancheer
 * @date 2018年3月24日
 */
public class BaseOperation {

	/**
	 * 重定向到500页面
	 * 
	 * @return
	 */
	protected ModelAndView redirect500View() {
		ModelAndView mAndView = new ModelAndView("redirect:/error/500");
		return mAndView;
	}

	/**
	 * 重定向到400页面
	 * 
	 * @return
	 */
	protected ModelAndView redirect404View() {
		ModelAndView mAndView = new ModelAndView("redirect:/error/404");
		return mAndView;
	}

	/**
	 * 重定向到502页面
	 * 
	 * @return
	 */
	protected ModelAndView redirect502View() {
		ModelAndView mAndView = new ModelAndView("redirect:/error/502");
		return mAndView;
	}
	
}
