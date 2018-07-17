package com.seancheer.dao.interfaces;

import java.util.List;

import com.seancheer.dao.entity.Passage;
import com.seancheer.exception.BlogBaseException;

/**
 * 操作passage表的类
 * @author seancheer
 * @date 2018年2月26日
 */
public interface BlogDao extends BaseDao<Passage> {

	public List<Passage> queryRecordByTitle(String title) throws BlogBaseException;

	public List<Passage> queryRecordByTitleIgnoreDelation(String title) throws BlogBaseException;
	
	public Passage queryRecordById(Integer id) throws BlogBaseException;
	
	public Passage queryRecordByIdIgnoreDelation(Integer id) throws BlogBaseException;
	
	public void deleteRecordFake(Passage rec) throws BlogBaseException;
	
	public long getRowCount() throws BlogBaseException;
	
	public List<Passage> queryLimitRecByCategoryId(Long page, List<String> categoryIds, List<Long> rowCount) throws BlogBaseException;
}
