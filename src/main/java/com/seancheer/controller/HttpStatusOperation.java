package com.seancheer.controller;

import com.seancheer.common.BaseOperation;
import com.seancheer.common.UrlConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * http状态码专用的controller
 * @author seancheer
 * @date 2018年4月14日
 */
@Controller
public class HttpStatusOperation extends BaseOperation {
	/**
	 * 500
	 */
	@RequestMapping(value = UrlConstants.ERROR_500_PAGE, method = RequestMethod.GET)
	public void customInternalError() {
		;
	}
	
	
	/**
	 * 502
	 */
	@RequestMapping(value = UrlConstants.ERROR_502_PAGE, method = RequestMethod.GET)
	public void customBadGateway() {
		;
	}
	
	
	
	/**
	 * 404
	 */
	@RequestMapping(value = UrlConstants.ERROR_404_PAGE, method = RequestMethod.GET)
	public void customNotFound() {
		;
	}


	/**
	 * 405
	 */
	@RequestMapping(value = UrlConstants.ERROR_405_PAGE, method = RequestMethod.GET)
	public void notAllowed() {
		;
	}
}
