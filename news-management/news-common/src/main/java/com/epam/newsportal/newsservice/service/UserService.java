package com.epam.newsportal.newsservice.service;

import org.apache.log4j.Logger;

import com.epam.newsportal.newsservice.dao.IUserDao;
import com.epam.newsportal.newsservice.entity.User;
import com.epam.newsportal.newsservice.entity.dto.UserDTO;
import com.epam.newsportal.newsservice.exception.DaoException;

public class UserService {
	
	public static final Logger logger = Logger.getLogger(UserService.class);
	
	private IUserDao userDao;
	
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	
	public UserDTO getUserByName(String name) throws DaoException {
		User user = userDao.getUserByName(name);
		UserDTO userDTO = new UserDTO();
		userDTO.buildUserToDTO(user);
		return userDTO;
	}
}
