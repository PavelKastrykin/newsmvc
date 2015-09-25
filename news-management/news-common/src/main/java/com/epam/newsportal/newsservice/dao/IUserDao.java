package com.epam.newsportal.newsservice.dao;

import com.epam.newsportal.newsservice.entity.dto.UserDTO;
import com.epam.newsportal.newsservice.exception.DaoException;

public interface IUserDao {
	UserDTO getUserDTObyName(String userName) throws DaoException;
}
