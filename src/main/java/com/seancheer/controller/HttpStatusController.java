package com.seancheer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * http状态码专用的controller
 * @author seancheer
 * @date 2018年4月14日
 */
@Controller
public class HttpStatusController extends BaseController{
	/**
	 * 500
	 * 
	 * @param key
	 */
	@RequestMapping(value = "/error/500", method = RequestMethod.GET)
	public void customInternalError() {
		;
	}
	
	
	/**
	 * 502
	 * 
	 * @param key
	 */
	@RequestMapping(value = "/error/502", method = RequestMethod.GET)
	public void customBadGateway() {
		;
	}
	
	
	
	/**
	 * 404
	 * 
	 * @param key
	 */
	@RequestMapping(value = "/error/404", method = RequestMethod.GET)
	public void customNotFound() {
		;
	}
}
