package com.seancheer.session;

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
		//检查map中是否有已经过期的session，然后remove掉
	}
}
