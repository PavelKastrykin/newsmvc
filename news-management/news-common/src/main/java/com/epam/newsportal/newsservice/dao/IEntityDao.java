package com.epam.newsportal.newsservice.dao;

import com.epam.newsportal.newsservice.exception.DaoException;

public interface IEntityDao<T> {

	T getById(Long id) throws DaoException;

	Long insert(T item) throws DaoException;
	
	void update(T item) throws DaoException;

	void delete(Long id) throws DaoException;

}
