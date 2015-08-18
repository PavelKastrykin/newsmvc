package com.epam.newsportal.newsservice.dao;

import java.util.List;

import com.epam.newsportal.newsservice.controller.SearchCriteria;
import com.epam.newsportal.newsservice.entity.News;
import com.epam.newsportal.newsservice.exception.DaoException;

public interface INewsDao extends IEntityDao<News> {
	
	List<News> getNewsByAuthor(long authorId) throws DaoException;

	List<News> getNewsByTag(long tagId) throws DaoException;
	
	int newsCount() throws DaoException;
	
	List<News> getSearchResult(SearchCriteria criteria) throws DaoException;
}
