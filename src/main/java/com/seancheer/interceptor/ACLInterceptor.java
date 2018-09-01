package com.seancheer.interceptor;

import com.seancheer.acl.IPathAccessController;
import com.seancheer.common.BlogConstants;
import com.seancheer.utils.CookieEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限控制interceptor
 *
 * @author seancheer
 * @date 2018年4月12日
 */
public class ACLInterceptor implements AsyncHandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(ACLInterceptor.class);

    @Autowired
    private IPathAccessController pathAccessController;

    /**
     * 对于god来讲，可以访问所有路径；
     * 普通用户，需要对其访问的路径进行权限检查
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //如果来自于god，那么可以访问所有路径
        if (isGodFromRequest(request))
        {
            return true;
        }

        CookieEntity entity = (CookieEntity)request.getAttribute(BlogConstants.COOKIE_ENTITY);
        String userId = "";
        if (null != entity)
        {
            userId = entity.getUserId();
        }
        String uri = request.getRequestURI();
        logger.debug("uri:" + uri);

        //检查是否有权限访问该路径
        if (!pathAccessController.canAccess(convertUserId(userId),uri))
        {
            response.sendRedirect("/error/404");
            return false;
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


    /**
     * 从request中判断是否为god
     * @param request
     * @return
     */
    private boolean isGodFromRequest(HttpServletRequest request) {
        Boolean isGod = (Boolean) request.getAttribute(BlogConstants.COOKIE_IS_GOD);
        Boolean isExpired = (Boolean) request.getAttribute(BlogConstants.COOKIE_IS_EXPIRED);

        if (isGod == null || isExpired == null)
        {
            return false;
        }

        return isGod && !isExpired;
    }

    /**
     * 将string类型的userid转换为Integer
     * @param userId
     * @return
     */
    private Integer convertUserId(String userId)
    {
        if (StringUtils.isEmpty(userId))
        {
            return null;
        }

        try
        {
            return Integer.parseInt(userId);
        }catch(NumberFormatException e)
        {
            return null;
        }
    }
}
