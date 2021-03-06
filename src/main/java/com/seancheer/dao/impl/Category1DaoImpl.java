package com.seancheer.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.seancheer.dao.entity.Category1;
import com.seancheer.dao.interfaces.Category1Dao;
import com.seancheer.exception.BlogBaseException;

public class Category1DaoImpl extends BaseDaoImpl<Category1> implements Category1Dao {

	private static final Logger logger = LoggerFactory.getLogger(Category1DaoImpl.class);

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public Category1 updateRecord(Category1 rec) throws BlogBaseException {
		try {
			super.updateRecord(rec);
		} catch (HibernateException e) {
			throw new BlogBaseException("Updating record failed in Category1DaoImpl.", e);
		}

		return rec;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public Category1 insertRecord(Category1 rec) throws BlogBaseException {
		try {
			super.insertRecord(rec);
		} catch (HibernateException e) {
			throw new BlogBaseException("Inserting record failed in Category1DaoImpl.", e);
		}

		return rec;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public void deleteRecord(Category1 rec) throws BlogBaseException {
		try {
			super.deleteRecord(rec);
		} catch (HibernateException e) {
			throw new BlogBaseException("Deleting record failed in Category1DaoImpl.", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
	public List<Category1> queryAllRecord() throws BlogBaseException {
		return queryAllRecord(false);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
	public List<Category1> queryAllRecordIgnoreDelation() throws BlogBaseException {
		return queryAllRecord(true);
	}

	/**
	 * 查询当前数据库中的所有记录
	 * 
	 * @param includeDel
	 * @return
	 * @throws BlogBaseException
	 */
	@SuppressWarnings("unchecked")
	private List<Category1> queryAllRecord(boolean includeDel) throws BlogBaseException {
		Query query = getCurrentSession().createQuery("from Category1" + (includeDel ? "" : " where isDel = :isDel"));

		if (!includeDel) {
			query.setParameter("isDel", (byte)0);
		}

		try {
			return query.list();
		} catch (HibernateException e) {
			throw new BlogBaseException("Querying all record failed in Category1DaoImpl.", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
	public List<Category1> queryRecByName(String name) throws BlogBaseException {
		if (StringUtils.isEmpty(name)) {
			String cause = "Invalid name. Please check. name:" + name;
			logger.error(cause);
			throw new IllegalArgumentException(cause);
		}

		return queryRecByName(name, false);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
	public List<Category1> queryRecByNameIgnoreDelation(String name) throws BlogBaseException {
		if (StringUtils.isEmpty(name)) {
			String cause = "Invalid name. Please check. name:" + name;
			logger.error(cause);
			throw new IllegalArgumentException(cause);
		}

		return queryRecByName(name, true);
	}

	/**
	 * 通过name查询对应得category
	 * 
	 * @param name
	 * @param includeDel
	 *            是否需要包含已经被删除的
	 * @return
	 * @throws BlogBaseException
	 */
	@SuppressWarnings("unchecked")
	private List<Category1> queryRecByName(String name, boolean includeDel) throws BlogBaseException {
		Query query = getCurrentSession()
				.createQuery("from Category1 where name like :name " + (includeDel ? "" : " and isDel = :isDel"));
		query.setParameter("name", name);

		if (!includeDel) {
			query.setParameter("isDel", (byte)0);
		}

		try {
			return query.list();
		} catch (HibernateException e) {
			throw new BlogBaseException("Querying all record by name failed in Category1DaoImpl.", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
	public List<Category1> queryRecById(int id) throws BlogBaseException {
		if (StringUtils.isEmpty(id)) {
			String cause = "Invalid id. Please check. id:" + id;
			logger.error(cause);
			throw new IllegalArgumentException(cause);
		}

		return queryRecById(id, false);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
	public List<Category1> queryRecByIdIgnoreDelation(int id) throws BlogBaseException {
		if (StringUtils.isEmpty(id)) {
			String cause = "Invalid id. Please check. id:" + id;
			logger.error(cause);
			throw new IllegalArgumentException(cause);
		}

		return queryRecById(id, true);
	}

	/**
	 * 通过name查询对应得category
	 * 
	 * @param name
	 * @param includeDel
	 *            是否需要包含已经被删除的
	 * @return
	 * @throws BlogBaseException
	 */
	@SuppressWarnings("unchecked")
	private List<Category1> queryRecById(int id, boolean includeDel) throws BlogBaseException {
		Query query = getCurrentSession()
				.createQuery("from Category1 where id = :id " + (includeDel ? "" : " and isDel = :isDel"));
		query.setParameter("id", (byte)id);

		if (!includeDel) {
			query.setParameter("isDel", (byte)0);
		}

		try {
			return query.list();
		} catch (HibernateException e) {
			throw new BlogBaseException("Querying all record by name failed in Category1DaoImpl.", e);
		}
	}
}
