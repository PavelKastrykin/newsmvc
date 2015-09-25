package com.epam.newsportal.newsservice.dao;

import java.util.List;

import com.epam.newsportal.newsservice.controller.SearchCriteria;
import com.epam.newsportal.newsservice.entity.dto.NewsDTO;
import com.epam.newsportal.newsservice.exception.DaoException;

public interface INewsDao extends IEntityDao<NewsDTO> {
	
	List<NewsDTO> getSearchResult(SearchCriteria criteria) throws DaoException;
}
