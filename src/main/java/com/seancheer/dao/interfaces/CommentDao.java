package com.seancheer.dao.interfaces;

import java.util.List;

import com.seancheer.dao.entity.Comment;
import com.seancheer.exception.BlogBaseException;

/**
 * Comment表的相关操作
 * 
 * @author seancheer
 * @date 2018年2月27日
 */
public interface CommentDao extends BaseDao<Comment> {

	public List<Comment> queryByPassageId(Integer id) throws BlogBaseException;

	public List<Comment> queryByPassageIdIgnoreDelation(Integer id) throws BlogBaseException;

	public List<Comment> queryByUserId(Integer userId) throws BlogBaseException;

	public List<Comment> queryByUserIdIgnoreDelation(Integer userId) throws BlogBaseException;
}
