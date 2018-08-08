package com.seancheer.service;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.seancheer.common.BlogConstants;
import com.seancheer.common.BlogCode;
import com.seancheer.controller.vo.NeedGodReq;
import com.seancheer.dao.entity.User;
import com.seancheer.dao.interfaces.UserDao;
import com.seancheer.exception.BlogBaseException;
import com.seancheer.exception.UserNotExistsException;
import com.seancheer.service.interfaces.IGodService;
import com.seancheer.session.SessionManager;
import com.seancheer.utils.CookieGenerator;
import com.seancheer.utils.IGodValidator;
import com.seancheer.utils.TokenGenerator;

/**
 * 是否能够进入到博客编辑界面的实现类
 * 
 * @author seancheer
 * @date 2018年3月7日
 */
public class GodServiceImpl implements IGodService {

	private static final Logger logger = LoggerFactory.getLogger(GodServiceImpl.class);

	private static final String blogListUri = "/blogList";

	private static final String adminUserName = BlogConstants.ADMIN_USER_NAME;

	@Autowired
	private IGodValidator godValidator;

	@Autowired
	private SessionManager sessionManager;

	@Autowired
	private UserDao userDao;

	/**
	 * 验证用户输入的key是否正确
	 */
	@Override
	public String godEntrance(final NeedGodReq req, final HttpServletResponse response, final HttpSession session) {
		if (godValidator.validate(req.getKey())) {
			logger.info("GodEntrance invalidating success!");
			response.setStatus(HttpStatus.OK.value());

			try {
				genSessionAndMakeCookie(response, session);

			} catch (UserNotExistsException e) {
				return BlogCode.USER_NOT_EXISTS.toJson().toString();
			} catch (BlogBaseException e) {
				logger.error("GenSessionAndMakeCookie failed!", e);
				return BlogCode.INTERNAL_ERROR.toJson().toString();
			}

			JSONObject succObject = BlogCode.SUCCESS.toJson();
			succObject.put(BlogConstants.REDIRECT, blogListUri);
			return succObject.toString();
		}

		response.setStatus(HttpStatus.FORBIDDEN.value());
		return BlogCode.INVALID_KEY.toString();
	}

	/**
	 * 生成session并设置cookie
	 * 
	 * @param response
	 * @throws BlogBaseException
	 */
	private void genSessionAndMakeCookie(final HttpServletResponse response, final HttpSession session)
			throws BlogBaseException {
		User user = userDao.queryRecordByName(adminUserName);
		if (null == user) {
			logger.info("User[{}] does not exists!", adminUserName);
			throw new UserNotExistsException("User does not exists!");
		}

		String token = TokenGenerator.genUUIDToken();
		response.addCookie(
				CookieGenerator.genCookie("userId", String.valueOf(user.getId()), BlogConstants.TEN_DAY_IN_SECONDS));
		response.addCookie(CookieGenerator.genCookie("token", token, BlogConstants.TEN_DAY_IN_SECONDS));
		response.addCookie(CookieGenerator.genCookie("sId", session.getId(), BlogConstants.TEN_DAY_IN_SECONDS));

		session.setAttribute("userId", user.getId());
		session.setAttribute("token", token);
		session.setAttribute("expiredAt", System.currentTimeMillis() + BlogConstants.TEN_DAY_IN_SECONDS * 1000);
		sessionManager.putSession(String.valueOf(user.getId()), session);
	}
}
