package com.seancheer.dao.impl;

import com.seancheer.dao.entity.User;
import com.seancheer.dao.interfaces.UserDao;
import com.seancheer.exception.BlogBaseException;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 对user表进行操作的基本类
 */
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	/**
	 * 更新一条记录
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public User updateRecord(User rec) throws BlogBaseException {
		try {
			super.updateRecord(rec);
		} catch (HibernateException e) {
			throw new BlogBaseException("Updating record failed in userDao.", e);
		}

		return rec;
	}

	/**
	 * 插入一条新的记录
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public User insertRecord(User rec) throws BlogBaseException {
		try {
			super.insertRecord(rec);
		} catch (HibernateException e) {
			throw new BlogBaseException("Inserting record failed in userDao.", e);
		}
		return rec;
	}

	/**
	 * 删除一条记录
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public void deleteRecord(User rec) throws BlogBaseException {
		try {
			super.deleteRecord(rec);
		} catch (HibernateException e) {
			throw new BlogBaseException("Deleting record failed in userDao.", e);
		}
		return;
	}

	/**
	 * 通过用户名字查询一条记录
	 * 
	 * @param name
	 *            用户名
	 * @return
	 * @throws BlogBaseException
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
	@Override
	public User queryRecordByName(String name) throws BlogBaseException {
		if (StringUtils.isEmpty(name)) {
			String cause = String.format("Invalid name[{}]. Please check.", name);
			logger.info(cause);
			throw new IllegalArgumentException(cause);
		}

		return queryRecordByName(name, false);
	}

	/**
	 * 通过用户名字查询一条记录，忽略是否被删除
	 * 
	 * @param name
	 *            用户名
	 * @return
	 * @throws BlogBaseException
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
	@Override
	public User queryRecordByNameIgnoreDelation(String name) throws BlogBaseException {
		if (StringUtils.isEmpty(name)) {
			String cause = String.format("Invalid name[{}]. Please check.", name);
			logger.info(cause);
			throw new IllegalArgumentException(cause);
		}

		return queryRecordByName(name, true);
	}

	/**
	 * 根据名称查询记录
	 * @param name
	 * @param includeDel
	 * @return
	 * @throws BlogBaseException
	 */
	@SuppressWarnings("rawtypes")
	private User queryRecordByName(String name, boolean includeDel) throws BlogBaseException {
		Query query = getCurrentSession()
				.createQuery("from User where name = :name" + (includeDel ? "" : " and isDel = :isDel"));
		query.setParameter("name", name);

		if (!includeDel) {
			query.setParameter("isDel", (byte)0);
		}

		List result = null;

		try {
			result = query.list();
		} catch (HibernateException e) {
			throw new BlogBaseException("Query user failed in userDao. name:" + name, e);
		}

		return result.isEmpty() ? null : (User) result.get(0);
	}
}
