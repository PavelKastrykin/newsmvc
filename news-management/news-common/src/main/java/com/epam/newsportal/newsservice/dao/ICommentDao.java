package com.epam.newsportal.newsservice.dao;

import java.util.List;

import com.epam.newsportal.newsservice.entity.Comment;
import com.epam.newsportal.newsservice.exception.DaoException;

public interface ICommentDao extends IEntityDao<Comment> {
	
	List<Comment> getCommentByNews(long newsId) throws DaoException;

	void deleteCommentByNews(long newsId) throws DaoException;

}
