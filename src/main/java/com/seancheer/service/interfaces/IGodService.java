package com.seancheer.service.interfaces;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.seancheer.controller.vo.NeedGodReq;

/**
 * 进入博客编辑界面的service实现类
 * @author seancheer
 * @date 2018年3月7日
 */
public interface IGodService {

	/**
	 * 检验用户输入的key是否正确
	 * @param req
	 * @return
	 */
	String godEntrance(final NeedGodReq req, final HttpServletResponse response,final HttpSession session);

	/**
	 * 判断是否为god用户
	 * @param userId
	 * @return
	 */
	boolean isGod(Integer userId);
}
