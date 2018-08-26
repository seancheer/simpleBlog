package com.seancheer.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.seancheer.dao.interfaces.BaseDao;
import com.seancheer.exception.BlogBaseException;

/**
 * dao类的基本实现方法，包含了sessionfactory的封装
 * 
 * @author seancheer
 * @date 2018年2月12日
 */
public class BaseDaoImpl<T> implements BaseDao<T> {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public T updateRecord(T rec) throws BlogBaseException {
		getCurrentSession().update(rec);
		return rec;
	}

	@Override
	public T insertRecord(T rec) throws BlogBaseException {
		getCurrentSession().save(rec);
		return rec;
	}

	@Override
	public void deleteRecord(T rec) throws BlogBaseException {
		getCurrentSession().delete(rec);
		return;
	}

	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

}
