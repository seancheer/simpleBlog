package com.seancheer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seancheer.common.ErrorCode;
import com.seancheer.controller.vo.NeedGodReq;
import com.seancheer.service.interfaces.IGodService;

/**
 * 进入编辑中心的controller
 * 
 * @author seancheer
 * @date 2018年3月2日
 */
@Controller
public class GodController {

	private static final Logger logger = LoggerFactory.getLogger(GodController.class);

	@Autowired
	private IGodService godService;

	@RequestMapping(value = "/needGod", method = RequestMethod.GET)
	public void needGod(HttpServletRequest request) {
		logger.info("Need god....");
	}

	/**
	 * 判断key是否正确
	 * 
	 * @param key
	 */
	@RequestMapping(value = "/godEntrance", method = RequestMethod.POST)
	@ResponseBody
	public String godEntrance(@Valid NeedGodReq needGodReq, HttpSession session, HttpServletResponse response) {
		if (StringUtils.isEmpty(needGodReq.getKey())) {
			logger.info("Invalid key. Verifing failed! key:" + needGodReq.getKey());
			response.setStatus(HttpStatus.FORBIDDEN.value());
			return ErrorCode.INVALID_KEY.toString();
		}

		return godService.godEntrance(needGodReq, response, session);

	}
}