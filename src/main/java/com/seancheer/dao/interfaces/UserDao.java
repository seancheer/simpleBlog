package com.seancheer.dao.interfaces;

import com.seancheer.dao.entity.User;
import com.seancheer.exception.BlogBaseException;

/**
 * 操作user表的基本类
 * 
 * @author seancheer
 * @date 2018年2月23日
 */
public interface UserDao extends BaseDao<User> {

	public User queryRecordByName(String name) throws BlogBaseException;

	public User queryRecordByNameIgnoreDelation(String name) throws BlogBaseException;
	
}
