package com.seancheer.dao.interfaces;

import java.util.List;

import com.seancheer.dao.entity.Category2;
import com.seancheer.exception.BlogBaseException;

public interface Category2Dao extends BaseDao<Category2> {
	
	public List<Category2> queryAllRecord() throws BlogBaseException;

	public List<Category2> queryAllRecordIgnoreDelation() throws BlogBaseException;

	public List<Category2> queryRecByName(String name) throws BlogBaseException;

	public List<Category2> queryRecByNameIgnoreDelation(String name) throws BlogBaseException;
	
	public List<Category2> queryRecByParentId(Integer id) throws BlogBaseException;
	
	public List<Category2> queryRecByParentIdIgnoreDelation(Integer id) throws BlogBaseException;
	
	public List<Category2> queryRecById(int id) throws BlogBaseException;

	public List<Category2> queryRecByIdIgnoreDelation(int id) throws BlogBaseException;
}
