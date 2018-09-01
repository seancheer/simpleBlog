package com.seancheer.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.seancheer.common.BlogConstants;
import com.seancheer.service.interfaces.IGodService;
import com.seancheer.session.SessionManager;
import com.seancheer.utils.CookieEntity;
import com.seancheer.utils.CookieHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 检查cookie的interceptor，如果cookie过期，
 * 那么让该用户重新登录，同时根据cookie
 *
 * @author seancheer
 * @date 2018年4月12日
 */
public class CookieInterceptor implements AsyncHandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(CookieInterceptor.class);

    @Autowired
    private IGodService godService;

    @Autowired
    private SessionManager sessionManager;

    /**
     * 检查是否有session，如果不存在session，那么直接返回true
     * 如果存在session，那么，检查session是否为god，不是god，无用的session，直接返回true，是god，检查是否过期
     * 如果过期，设置expired字段，以便后续进行处理，如果未过期，返回true
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        Cookie[] cookies = request.getCookies();
        if (null == cookies || 0 == cookies.length) {
            return true;
        }


        String userId = null;
        CookieEntity cookieEntity = null;
        try {
            cookieEntity = CookieHelper.getEntityFromCookie(cookies);
            if (null == cookieEntity)
            {
                logger.debug("Can not find cookie info!");
                return true;
            }

            userId = cookieEntity.getUserId();
            logger.debug("The userid in cache is:" + userId);
            boolean isGod = godService.isGod(Integer.parseInt(userId));
            boolean validCookie = sessionManager.invalidateCookie(cookieEntity);

            //设置对应的标志位，以供后续进行处理
            if (isGod && validCookie)
            {
                request.setAttribute(BlogConstants.COOKIE_IS_GOD, true);
                request.setAttribute(BlogConstants.COOKIE_IS_EXPIRED, false);
                request.setAttribute(BlogConstants.COOKIE_ENTITY, cookieEntity);
            }
        } catch (NumberFormatException e) {
            logger.info("Invalid session. userId:" + userId);
            return true;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

    }

}
