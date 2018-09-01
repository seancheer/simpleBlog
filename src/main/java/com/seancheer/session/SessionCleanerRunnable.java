package com.seancheer.session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpSession;

public class SessionCleanerRunnable implements Runnable {

	private SessionManager manager;

	public SessionCleanerRunnable(SessionManager manager) {
		this.manager = manager;
	}

	@Override
	public void run() {
		doRun();
	}

	private void doRun() {
		ConcurrentMap<String, HttpSession> map = manager.getSessionMap();
		if (null == map || map.isEmpty())
		{
			return;
		}
		
		long currentMillis = System.currentTimeMillis();
        List<String>  expiredUserIds = new ArrayList<>();

		//检查map中是否有已经过期的session，然后remove掉
        for (Map.Entry<String, HttpSession> entry : map.entrySet()) {
            String userId = entry.getKey();
            HttpSession session = entry.getValue();
            long expiredTime = (long)session.getAttribute("expiredAt");

            if (currentMillis >= expiredTime)
            {
                expiredUserIds.add(userId);
            }
        }

        for (String userId:expiredUserIds) {
            manager.removeSessionByUserId(userId);
        }
	}
}
