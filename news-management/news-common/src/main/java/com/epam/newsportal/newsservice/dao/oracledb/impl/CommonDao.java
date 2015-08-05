package com.epam.newsportal.newsservice.dao.oracledb.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class CommonDao {
	
	public static final Logger logger = Logger.getLogger(CommonDao.class);
	
	protected void closeDaoResources(DataSource dataSource, Connection connection, Statement statement, ResultSet resultSet) {
		if (resultSet != null) {
			try{
				resultSet.close();
			} catch (SQLException e) {
				logger.error("Cannot close SQL resultset", e);
			}
			
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				logger.error("Cannot close SQL statement", e);
			}
		}
		DataSourceUtils.releaseConnection(connection, dataSource);
	}
}