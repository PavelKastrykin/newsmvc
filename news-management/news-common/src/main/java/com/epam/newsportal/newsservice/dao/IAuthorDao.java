package com.epam.newsportal.newsservice.dao;

import java.util.List;

import com.epam.newsportal.newsservice.entity.Author;
import com.epam.newsportal.newsservice.exception.DaoException;

public interface IAuthorDao extends IEntityDao<Author> {

	Author getByNewsId(long newsId) throws DaoException;

	List<Author> getList() throws DaoException;
	
	void addAuthorToNews(long authorId, long newsId) throws DaoException;

	void deleteNewsAuthorXref(long newsId) throws DaoException;

}
