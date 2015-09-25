package com.epam.newsportal.newsservice.service;

import org.apache.log4j.Logger;

import com.epam.newsportal.newsservice.dao.IUserDao;
import com.epam.newsportal.newsservice.entity.dto.RoleDTO;
import com.epam.newsportal.newsservice.entity.dto.UserDTO;
import com.epam.newsportal.newsservice.exception.DaoException;

public class UserService {
	
	public static final Logger logger = Logger.getLogger(UserService.class);
	
	private IUserDao userDao;
	
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	
	public UserDTO getUserByName(String userName) throws DaoException {
		UserDTO user = userDao.getUserDTObyName(userName);
		RoleDTO role = user.getRoleDTO().get(0);
		return user;
	}
}
