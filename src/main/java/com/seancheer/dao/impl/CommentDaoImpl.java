package com.seancheer.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.seancheer.dao.entity.Comment;
import com.seancheer.dao.interfaces.CommentDao;
import com.seancheer.exception.BlogBaseException;

/**
 * Comment表的相关操作
 * 
 * @author seancheer
 * @date 2018年2月27日
 */
public class CommentDaoImpl extends BaseDaoImpl<Comment> implements CommentDao {

	private static final Logger logger = LoggerFactory.getLogger(CommentDaoImpl.class);

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public Comment updateRecord(Comment rec) throws BlogBaseException {

		try {
			super.updateRecord(rec);
		} catch (HibernateException e) {
			throw new BlogBaseException("Updating record failed in CommentDaoImpl.", e);
		}

		return rec;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public Comment insertRecord(Comment rec) throws BlogBaseException {
		try {
			super.insertRecord(rec);
		} catch (HibernateException e) {
			throw new BlogBaseException("Inserting record failed in CommentDaoImpl.", e);
		}

		return rec;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public void deleteRecord(Comment rec) throws BlogBaseException {
		try {
			super.deleteRecord(rec);
		} catch (HibernateException e) {
			throw new BlogBaseException("Deleting record failed in CommentDaoImpl.", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
	public List<Comment> queryByPassageId(Integer id) throws BlogBaseException {
		if (id < 0) {
			String cause = String.format("Invalid id. Please check! passageId:%d", id);
			logger.error(cause);
			throw new BlogBaseException(cause);
		}

		return queryByPassageId(id, false);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
	public List<Comment> queryByPassageIdIgnoreDelation(Integer id) throws BlogBaseException {
		if (id < 0) {
			String cause = String.format("Invalid id. Please check! passageId:%d", id);
			logger.error(cause);
			throw new BlogBaseException(cause);
		}

		return queryByPassageId(id, true);
	}

	/**
	 * 通过passage的id来查询其对应得comment
	 * 
	 * @param id
	 * @param includeDel
	 * @return
	 * @throws BlogBaseException
	 */
	@SuppressWarnings("unchecked")
	private List<Comment> queryByPassageId(Integer id, boolean includeDel) throws BlogBaseException {
		Query query = getCurrentSession()
				.createQuery("from Comment where passageId = :passageId" + (includeDel ? "" : " and isDel = :isDel"));
		query.setParameter("passageId", id);

		if (!includeDel) {
			query.setParameter("isDel", 0);
		}

		try {
			return query.list();
		} catch (HibernateException e) {
			throw new BlogBaseException("Query comment failed by passageId in commentDao. passageId:" + id, e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
	public List<Comment> queryByUserId(Integer userId) throws BlogBaseException {
		if (userId < 0) {
			String cause = String.format("Invalid id. Please check! userId:%d", userId);
			logger.error(cause);
			throw new BlogBaseException(cause);
		}

		return queryByUserId(userId, true);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
	public List<Comment> queryByUserIdIgnoreDelation(Integer userId) throws BlogBaseException {
		if (userId < 0) {
			String cause = String.format("Invalid id. Please check! userId:%d", userId);
			logger.error(cause);
			throw new BlogBaseException(cause);
		}

		return queryByUserId(userId, true);
	}

	/**
	 * 通过User的id来查询其对应得comment
	 * 
	 * @param id
	 * @param includeDel
	 * @return
	 * @throws BlogBaseException
	 */
	@SuppressWarnings("unchecked")
	private List<Comment> queryByUserId(Integer userId, boolean includeDel) throws BlogBaseException {
		Query query = getCurrentSession()
				.createQuery("from Comment where userId = :userId" + (includeDel ? "" : " and isDel = :isDel"));
		query.setParameter("userId", userId);

		if (!includeDel) {
			query.setParameter("isDel", 0);
		}

		try {
			return query.list();
		} catch (HibernateException e) {
			throw new BlogBaseException("Query comment failed by passageId in commentDao. userId:" + userId, e);
		}
	}
}
