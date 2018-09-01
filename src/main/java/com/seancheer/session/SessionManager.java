package com.seancheer.session;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import com.seancheer.utils.CookieEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * 保存session的类，包含以下功能： 1 启动清理session的线程 2 管理session，加入和删除
 * 
 * @author seancheer
 * @date 2018年3月10日
 */
public class SessionManager {

	private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);
	
	private static final long INITIAL_DELAY = 0;
	
	private static final long DELAY_IN_SECONDS = 10;
	
	private ConcurrentMap<String, HttpSession> sessionMap;

	/**
	 * 初始化方法，在xml中有定义
	 */
	public void init() {
		sessionMap = new ConcurrentHashMap<String, HttpSession>();
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		service.scheduleWithFixedDelay(new SessionCleanerRunnable(this), INITIAL_DELAY, DELAY_IN_SECONDS, TimeUnit.SECONDS);
		logger.info("Innitial SessionCleaner success~~~~~");
	}

	/**
	 * 通过userid来获取对应的session
	 * @param userId
	 * @return
	 */
	public HttpSession getSessionByUserId(String userId) {
		if (StringUtils.isEmpty(userId)) {
			String msg = "Userid is invalid! Please check! userId:" + userId;
			logger.warn(msg);
			throw new IllegalArgumentException(msg);
		}
		
		return sessionMap.get(userId);
	}

    /**
     * 验证用户传入的cookie在系统上是否已经有记录
     * @param cookieEntity
     * @return
     */
	public boolean invalidateCookie(CookieEntity cookieEntity)
    {
        if (null == cookieEntity)
        {
            return false;
        }
        String userId = cookieEntity.getUserId();
        String token = cookieEntity.getToken();
        String sId = cookieEntity.getSessionId();
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(token) || StringUtils.isEmpty(sId))
        {
            return false;
        }

        //通过cookie中的sessionid来获取对应的session
        HttpSession session = sessionMap.get(userId);
        if (null == session)
        {
            return false;
        }

        String userIdInCache = String.valueOf(session.getAttribute("userId"));
        String tokenInCache = String.valueOf(session.getAttribute("token"));
        return userId.equals(userIdInCache) && token.equals(tokenInCache) && sId.equals(session.getId());
    }
	
	/**
	 * 加入新的session，如果已经存在，那么直接进行覆盖。
	 * @param userId
	 * @param session
	 */
	public void putSession(String userId,HttpSession session)
	{
		sessionMap.put(userId, session);
	}

	/**
	 * 通过userid删除对应得session
	 * @param userId
	 */
	public HttpSession removeSessionByUserId(String userId)
	{
		if (StringUtils.isEmpty(userId)) {
			String msg = "Userid is invalid! Please check! userId:" + userId;
			logger.warn(msg);
			throw new IllegalArgumentException(msg);
		}
		
		return sessionMap.remove(userId);
	}

	public ConcurrentMap<String, HttpSession> getSessionMap() {
		return sessionMap;
	}

}
