package com.seancheer.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.List;

/**
 * cookie的实体
 *
 * @author: seancheer
 * @date: 2018/9/1
 **/
public class CookieEntity {

    private static final List<String> entityKeys = Arrays.asList("sId", "userId", "token");

    private String userId;

    private String sessionId;

    private String token;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * builder
     */
    public static class CookieEntityBuilder {
        private CookieEntity entity;

        public CookieEntityBuilder()
        {
            entity = new CookieEntity();
        }


        /**
         * 设置cookie里面的值
         * @param cookie
         * @return
         */
        public CookieEntityBuilder setCookie(Cookie cookie)
        {
            if (null == cookie)
            {
                return this;
            }

            String name = cookie.getName();
            if (StringUtils.isEmpty(name) || !entityKeys.contains(name))
            {
                return this;
            }

            if ("userId".equals(name))
            {
                entity.setUserId(cookie.getValue());
            }else if("sId".equals(name))
            {
                entity.setSessionId(cookie.getValue());
            }
            else
            {
                entity.setToken(cookie.getValue());
            }

            return this;
        }

        /**
         * 最终进行build
         * @return
         */
        public CookieEntity build()
        {
            return entity;
        }
    }
}
