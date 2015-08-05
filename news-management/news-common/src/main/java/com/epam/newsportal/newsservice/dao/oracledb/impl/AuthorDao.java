package com.epam.newsportal.newsservice.dao.oracledb.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.epam.newsportal.newsservice.dao.IAuthorDao;
import com.epam.newsportal.newsservice.entity.Author;
import com.epam.newsportal.newsservice.exception.DaoException;

public class AuthorDao extends CommonDao implements IAuthorDao {

	public static final Logger logger = Logger.getLogger(AuthorDao.class);
	
	private static final String ORACLE_AUTHORS_GET_LIST = "SELECT AUTHOR_ID, AUTHOR_NAME, "
			+ "EXPIRED FROM AUTHORS WHERE EXPIRED IS NULL";
	private static final String ORACLE_AUTHORS_GET_BY_ID = "SELECT AUTHOR_ID, AUTHOR_NAME, "
			+ "EXPIRED FROM AUTHORS WHERE AUTHOR_ID = ?";
	private static final String ORACLE_AUTHORS_INSERT_AUTHOR = "INSERT INTO AUTHORS "
			+ "(AUTHOR_ID, AUTHOR_NAME, EXPIRED) VALUES (AUTHORS_SEQ.NEXTVAL, ?, ?)";
	private static final String ORACLE_AUTHORS_UPDATE_AUTHOR = "UPDATE AUTHORS SET "
			+ "AUTHOR_NAME = ?, EXPIRED = ? WHERE AUTHOR_ID = ?";
	private static final String ORACLE_AUTHORS_DELETE_AUTHOR = "UPDATE AUTHORS SET "
			+ "EXPIRED = ? WHERE AUTHOR_ID = ?";
	private static final String ORACLE_AUTHORS_GET_BY_NEWS = "SELECT AUTHOR_ID, AUTHOR_NAME, "
			+ "EXPIRED FROM AUTHORS WHERE AUTHOR_ID IN (SELECT AUTHOR_ID "
			+ "FROM NEWS_AUTHORS WHERE NEWS_AUTHORS.NEWS_ID = ?)";
	private static final String ORACLE_AUTHORS_DELETE_NEWS_XREF = "DELETE FROM NEWS_AUTHORS WHERE "
			+ "NEWS_ID = ?";
	private static final String ORACLE_AUTHORS_INSERT_NEWS_XREF = "INSERT INTO NEWS_AUTHORS (NEWS_ID, "
			+ "AUTHOR_ID) VALUES(?, ?)";
	
	private static final String AUTHORS_AUTHOR_ID = "AUTHOR_ID";
	private static final String AUTHORS_AUTHOR_NAME = "AUTHOR_NAME";
	private static final String AUTHORS_EXPIRED = "EXPIRED";
	
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<Author> getList() throws DaoException {

		List<Author> authorList = new ArrayList<>();
		Author author;
		Statement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try {
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(ORACLE_AUTHORS_GET_LIST);
			while (resultSet.next()) {
				author = buildAuthor(resultSet);
				authorList.add(author);
			}
		} catch (SQLException e) {
			logger.error(e);
			throw new DaoException("Cannot get authors list");
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Unhandled exception occurred while getting authors list");
		} finally {
			closeDaoResources(dataSource, connection, statement, resultSet);
		}
		return authorList;
	}

	@Override
	public Author getById(long authorId) throws DaoException {
		Author author = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection.prepareStatement(ORACLE_AUTHORS_GET_BY_ID);
			statement.setLong(1, authorId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				author = buildAuthor(resultSet);
			}
		} catch (SQLException e) {
			logger.error(e);
			throw new DaoException("Cannot get author, id = " + authorId);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Unhandled exception occurred while getting author, id = " + authorId);
		} finally {
			closeDaoResources(dataSource, connection, statement, resultSet);
		}
		return author;
	}

	@Override
	public long insert(Author item) throws DaoException {
		PreparedStatement statement = null;
		Connection connection = null;;
		long newAuthorId = 0;
		ResultSet resultSet = null;
		try {
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection.prepareStatement(ORACLE_AUTHORS_INSERT_AUTHOR, new String[]{AUTHORS_AUTHOR_ID});
			statement.setString(1, item.getAuthorName());
			statement.setTimestamp(2, item.getExpired());
			statement.executeUpdate();
			resultSet = statement.getGeneratedKeys();
			if (resultSet != null && resultSet.next()) {
				newAuthorId = resultSet.getLong(1);
			}
		} catch (SQLException e) {
			logger.error(e);
			throw new DaoException("Cannot insert author name = " + item.getAuthorName());
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Unhandled exception occurred while inserting author name = " + item.getAuthorName());
		} finally {
			closeDaoResources(dataSource, connection, statement, resultSet);
		}
		return newAuthorId;
	}

	@Override
	public void update(Author item) throws DaoException {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection.prepareStatement(ORACLE_AUTHORS_UPDATE_AUTHOR);
			statement.setString(1, item.getAuthorName());
			statement.setTimestamp(2, item.getExpired());
			statement.setLong(3, item.getAuthorId());
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e);
			throw new DaoException("Cannot update author, id = " + item.getAuthorId());
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Unhandled exception occurred while updating author, id = " + item.getAuthorId());
		} finally {
			closeDaoResources(dataSource, connection, statement, null);
		}
	}

	@Override
	public void delete(long authorId) throws DaoException {
		PreparedStatement statement = null;
		Connection connection = null;
		Date date = new Date();  
		try {
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection.prepareStatement(ORACLE_AUTHORS_DELETE_AUTHOR);
			statement.setTimestamp(1, new Timestamp(date.getTime()));
			statement.setLong(2, authorId);
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e);
			throw new DaoException("Cannot delete author, id = " + authorId);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Unhandled exception occurred while deleting author, id = " + authorId);
		} finally {
			closeDaoResources(dataSource, connection, statement, null);
		}
	}

	@Override
	public Author getByNewsId(long newsId) throws DaoException {
		Author author = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection.prepareStatement(ORACLE_AUTHORS_GET_BY_NEWS);
			statement.setLong(1, newsId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				author = buildAuthor(resultSet);
			}
		} catch (SQLException e) {
			logger.error(e);
			throw new DaoException("Cannot get author for news_id = " + newsId);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Unhandled exception occurred while getting author for news_id = " + newsId);
		} finally {
			closeDaoResources(dataSource, connection, statement, resultSet);
		}
		return author;
	}
	
	@Override
	public void addAuthorToNews(long authorId, long newsId) throws DaoException{
		PreparedStatement statement = null;
		Connection connection = null;
		try{
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection.prepareStatement(ORACLE_AUTHORS_DELETE_NEWS_XREF);
			statement.setLong(1, newsId);
			statement.executeUpdate();
			if (statement != null) {
				statement.close();
			}
			statement = connection.prepareStatement(ORACLE_AUTHORS_INSERT_NEWS_XREF);
			statement.setLong(1, newsId);
			statement.setLong(2, authorId);
			statement.executeUpdate();
			
		} catch (SQLException e){
			logger.error(e);
			throw new DaoException("Cannot insert author for news_id = " + newsId);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Unhandled exception occurred while inserting author for news_id = " + newsId);
		} finally {
			closeDaoResources(dataSource, connection, statement, null);
		}
	}
	
	@Override
	public void deleteNewsAuthorXref(long newsId) throws DaoException {
		PreparedStatement statement = null;
		Connection connection = null;
		try{
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection.prepareStatement(ORACLE_AUTHORS_DELETE_NEWS_XREF);
			statement.setLong(1, newsId);
			statement.executeUpdate();
		} catch (SQLException e){
			logger.error(e);
			throw new DaoException("Cannot delete author x-ref for news_id = " + newsId);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Unhandled exception occurred while deleting author x-ref for news_id = " + newsId);
		} finally {
			closeDaoResources(dataSource, connection, statement, null);
		}
	}
	
	private Author buildAuthor(ResultSet rs) throws SQLException {
		Author author = new Author();
		author.setAuthorId(rs.getLong(AUTHORS_AUTHOR_ID));
		author.setAuthorName(rs.getString(AUTHORS_AUTHOR_NAME));
		author.setExpired(rs.getTimestamp(AUTHORS_EXPIRED));
		return author;
	}
}
