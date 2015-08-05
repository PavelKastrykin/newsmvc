package com.epam.newsportal.newsservice.dao;

import com.epam.newsportal.newsservice.exception.DaoException;

public interface IEntityDao<T> {

	T getById(long id) throws DaoException;

	long insert(T item) throws DaoException;
	
	void update(T item) throws DaoException;

	void delete(long id) throws DaoException;

}
