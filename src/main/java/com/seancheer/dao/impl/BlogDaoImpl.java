package com.seancheer.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.seancheer.common.BlogConfigImpl;
import com.seancheer.dao.entity.Passage;
import com.seancheer.dao.interfaces.BlogDao;
import com.seancheer.exception.BlogBaseException;

/**
 * 操作Passage表的类
 * 
 * @author seancheer
 * @date 2018年2月26日
 */
public class BlogDaoImpl extends BaseDaoImpl<Passage> implements BlogDao {

	private static final Logger logger = LoggerFactory.getLogger(BlogDaoImpl.class);

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public Passage updateRecord(Passage rec) throws BlogBaseException {

		try {
			super.updateRecord(rec);
		} catch (HibernateException e) {
			throw new BlogBaseException("Updating record failed in PassageDaoImpl.", e);
		}

		return rec;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public Passage insertRecord(Passage rec) throws BlogBaseException {
		try {
			super.insertRecord(rec);
		} catch (HibernateException e) {
			throw new BlogBaseException("Inserting record failed in PassageDaoImpl.", e);
		}
		return rec;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public void deleteRecord(Passage rec) throws BlogBaseException {
		try {
			super.deleteRecord(rec);
		} catch (HibernateException e) {
			throw new BlogBaseException("Deleting record failed in PassageDaoImpl.", e);
		}
	}

	/**
	 * 删除某条记录，这里是fakeDelete，并不真正删除。
	 */
	@Override
	public void deleteRecordFake(Passage rec) throws BlogBaseException {
		rec.setIsDel((byte)1);
		updateRecord(rec);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
	public List<Passage> queryRecordByTitle(String title) throws BlogBaseException {
		if (StringUtils.isEmpty(title)) {
			String cause = String.format("Invalid title. Please check. Title:%s", title);
			logger.error(cause);
			throw new IllegalArgumentException(cause);
		}

		return queryRecordByTitle(title, false);
	}

	@Override
	public List<Passage> queryRecordByTitleIgnoreDelation(String title) throws BlogBaseException {
		if (StringUtils.isEmpty(title)) {
			String cause = String.format("Invalid title. Please check. Title:%s", title);
			logger.error(cause);
			throw new IllegalArgumentException(cause);
		}

		return queryRecordByTitle(title, true);
	}

	/**
	 * 通过title查询符合条件的passage
	 * 
	 * @param title
	 * @param includeDel
	 *            是否需要忽略isdel
	 * @return
	 * @throws BlogBaseException
	 */
	@SuppressWarnings("unchecked")
	private List<Passage> queryRecordByTitle(String title, boolean includeDel) throws BlogBaseException {
		Query query = getCurrentSession()
				.createQuery("from Passage where title like :title" + (includeDel ? "" : " and isDel = :isDel"));
		query.setParameter("name", title);

		if (!includeDel) {
			query.setParameter("isDel", (byte)0);
		}

		try {
			return query.list();
		} catch (HibernateException e) {
			throw new BlogBaseException("Query passage failed in passageDao. title:" + title, e);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
	public Passage queryRecordById(Integer id) throws BlogBaseException {
		if (id < 0) {
			String cause = String.format("Invalid id. Please check. id:%d", id);
			logger.error(cause);
			throw new IllegalArgumentException(cause);
		}

		return queryRecordById(id, false);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
	public Passage queryRecordByIdIgnoreDelation(Integer id) throws BlogBaseException {
		if (id < 0) {
			String cause = String.format("Invalid id. Please check. id:%d", id);
			logger.error(cause);
			throw new IllegalArgumentException(cause);
		}

		return queryRecordById(id, true);
	}

	/**
	 * 通过id来查找对应得passage
	 * 
	 * @param id
	 * @param includeDel
	 *            是否需要忽略isdel字段
	 * @return
	 * @throws BlogBaseException
	 */
	@SuppressWarnings("rawtypes")
	private Passage queryRecordById(Integer id, boolean includeDel) throws BlogBaseException {
		Query query = getCurrentSession()
				.createQuery("from Passage where id = :id" + (includeDel ? "" : " and isDel = :isDel"));
		query.setParameter("id", id);

		if (!includeDel) {
			query.setParameter("isDel", (byte)0);
		}

		List result = null;

		try {
			result = query.list();
		} catch (HibernateException e) {
			throw new BlogBaseException("Query passage failed in passageDao. id:" + id, e);
		}

		return result.isEmpty() ? null : (Passage) result.get(0);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
	public long getRowCount() throws BlogBaseException {
		try {
			Criteria criteria = getCurrentSession().createCriteria(Passage.class);
			criteria.setProjection(Projections.rowCount());
			criteria.add(Restrictions.eq("isDel", 0));
			return (long) criteria.uniqueResult();
		} catch (HibernateException e) {
			throw new BlogBaseException("GetRowCount failed in passageDao.", e);
		}
	}

	/**
	 * 根据当前的page查询特定的blog 需要注意的是，如果说categoryIds里面的值为空，那么将不适用category这个参数
	 * 
	 * @param page
	 * @param categoryIds
	 * @param rowCount 在categoryIds的限制条件下，查询到的个数
	 * @return
	 * @throws BlogBaseException
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
	public List<Passage> queryLimitRecByCategoryId(Long page, List<String> categoryIds, List<Long> rowCount)
			throws BlogBaseException {
		page = (null == page || page < 1L) ? 1L : page;
		StringBuilder sqlBuilder = new StringBuilder("from Passage where isDel = 0 ");
		String categoryKey = null;

		// 准备查询当前限制条件下的个数
		Criteria criteria = getCurrentSession().createCriteria(Passage.class);
		criteria.setProjection(Projections.rowCount());
		criteria.add(Restrictions.eq("isDel", (byte)0));

		// 修改为传入categoryId=1,2，第一位表示category的编号，第二位表示具体的id
		if (null != categoryIds && categoryIds.size() == 2) {
			categoryKey = "category" + categoryIds.get(0) + "Id";
			sqlBuilder.append(" and ").append(categoryKey).append(" = :").append(categoryKey).append(" ");
		}

		int firstResult = (int) ((page - 1) * BlogConfigImpl.DEFAULT_SUM_PER_PAGE);
		// hql无法使用limit，所以这里使用内置的方法来实现limit的效果
		Query query = getCurrentSession().createQuery(sqlBuilder.toString()).setFirstResult(firstResult)
				.setMaxResults(BlogConfigImpl.DEFAULT_SUM_PER_PAGE);
		if (null != categoryKey) {
			query.setParameter(categoryKey, categoryIds.get(1));
			criteria.add(Restrictions.eq(categoryKey, Byte.parseByte(categoryIds.get(1))));
		}

		try {
			rowCount.add((Long)criteria.uniqueResult());
			return query.list();
		} catch (HibernateException e) {
			logger.error("queryLimitRecByCategoryId failed!", e);
			throw new BlogBaseException(e);
		}
	}
}
