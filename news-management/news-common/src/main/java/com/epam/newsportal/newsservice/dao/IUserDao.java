package com.epam.newsportal.newsservice.dao;

import com.epam.newsportal.newsservice.entity.User;
import com.epam.newsportal.newsservice.exception.DaoException;

public interface IUserDao {
	User getUserByName(String name) throws DaoException;
}
