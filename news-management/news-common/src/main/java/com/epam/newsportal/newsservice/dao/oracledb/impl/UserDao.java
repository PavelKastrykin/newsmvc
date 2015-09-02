package com.epam.newsportal.newsservice.dao.oracledb.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.epam.newsportal.newsservice.dao.IUserDao;
import com.epam.newsportal.newsservice.entity.RoleName;
import com.epam.newsportal.newsservice.entity.User;
import com.epam.newsportal.newsservice.exception.DaoException;

public class UserDao extends CommonDao implements IUserDao {
	
	public static final Logger logger = Logger.getLogger(UserDao.class);
	
	private static final String ORACLE_USER_GET_BY_NAME = "SELECT U.USER_ID, U.USER_NAME, U.LOGIN, U.PASSWORD, UR.ROLE_NAME "
			+ "FROM USERS U JOIN USER_ROLES UR ON U.USER_ID = UR.USER_ID WHERE USER_NAME = ?";
	
	private static final String USER_USER_ID = "USER_ID";
	private static final String USER_USER_NAME = "USER_NAME";
	private static final String USER_LOGIN = "LOGIN";
	private static final String USER_PASSWORD = "PASSWORD";
	private static final String USER_ROLE_NAME = "ROLE_NAME";
	
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public User getUserByName(String name) throws DaoException {
		User user = new User();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try{
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection.prepareStatement(ORACLE_USER_GET_BY_NAME);
			statement.setString(1, name);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				user.setUserId(resultSet.getLong(USER_USER_ID));
				user.setLogin(resultSet.getString(USER_LOGIN));
				user.setUserName(resultSet.getString(USER_USER_NAME));
				user.setPassword(resultSet.getString(USER_PASSWORD));
				user.setRoleName(RoleName.valueOf(resultSet.getString(USER_ROLE_NAME).toUpperCase()));
			}
			
		} catch (SQLException e) {
			logger.error(e);
			throw new DaoException("Cannot get user name = " + name);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Unhandled exception occurred while getting user name = " + name);
		} finally {
			closeDaoResources(dataSource, connection, statement, resultSet);
		}
		
		return user;
	}

}
