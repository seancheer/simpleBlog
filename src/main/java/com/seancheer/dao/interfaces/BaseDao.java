package com.seancheer.dao.interfaces;

import com.seancheer.exception.BlogBaseException;

/**
 * dao类的基础接口
 * @author seancheer
 * @date 2018年2月11日
 */
public interface BaseDao<T> {

	T updateRecord(T rec) throws BlogBaseException;
	
	T insertRecord(T rec) throws BlogBaseException;;
	
	void deleteRecord(T rec) throws BlogBaseException;;
	
}
