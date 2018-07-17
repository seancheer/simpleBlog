package com.seancheer.dao.interfaces;

import java.util.List;

import com.seancheer.dao.entity.Category1;
import com.seancheer.exception.BlogBaseException;

public interface Category1Dao extends BaseDao<Category1> {
	
	public List<Category1> queryAllRecord() throws BlogBaseException;
	
	public List<Category1> queryAllRecordIgnoreDelation() throws BlogBaseException;
	
	public List<Category1> queryRecByName(String name) throws BlogBaseException;
	
	public List<Category1> queryRecByNameIgnoreDelation(String name) throws BlogBaseException;
	
	public List<Category1> queryRecById(int id) throws BlogBaseException;

	public List<Category1> queryRecByIdIgnoreDelation(int id) throws BlogBaseException;
}
