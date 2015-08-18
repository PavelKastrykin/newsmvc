package com.epam.newsportal.newsservice.dao.oracledb.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.epam.newsportal.newsservice.dao.ICommentDao;
import com.epam.newsportal.newsservice.entity.Comment;
import com.epam.newsportal.newsservice.exception.DaoException;

public class CommentDao extends CommonDao implements ICommentDao {

	public static final Logger logger = Logger.getLogger(CommentDao.class);
	
	private static final String ORACLE_COMMENTS_GET_BY_ID = "SELECT COMMENT_ID, NEWS_ID, COMMENT_TEXT, "
			+ "CREATION_DATE FROM COMMENTS WHERE COMMENT_ID = ?";
	private static final String ORACLE_COMMENTS_INSERT_COMMENT = "INSERT INTO COMMENTS "
			+ "(COMMENT_ID, NEWS_ID, COMMENT_TEXT, CREATION_DATE) "
			+ "VALUES (COMMENTS_SEQ.NEXTVAL, ?, ?, ?)";
	private static final String ORACLE_COMMENTS_UPDATE_COMMENT = "UPDATE COMMENTS SET "
			+ "NEWS_ID = ?, COMMENT_TEXT = ?, CREATION_DATE = ? WHERE COMMENT_ID = ?";
	private static final String ORACLE_COMMENTS_DELETE_COMMENT = "DELETE FROM COMMENTS "
			+ "WHERE COMMENT_ID = ?";
	private static final String ORACLE_COMMENTS_GET_LIST_BY_NEWS = "SELECT COMMENT_ID, "
			+ "NEWS_ID, COMMENT_TEXT, CREATION_DATE FROM COMMENTS  WHERE NEWS_ID = ? ORDER BY COMMENT_ID";
	private static final String ORACLE_COMMENTS_DELETE_COMMENT_BY_NEWS_ID = "DELETE FROM COMMENTS "
			+ "WHERE NEWS_ID = ?";
	
	private static final String COMMENTS_COMMENT_ID = "COMMENT_ID";
	private static final String COMMENTS_NEWS_ID = "NEWS_ID";
	private static final String COMMENTS_COMMENT_TEXT = "COMMENT_TEXT";
	private static final String COMMENTS_CREATION_DATE = "CREATION_DATE";
	
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Comment getById(long commentId) throws DaoException {
		Comment comment = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection.prepareStatement(ORACLE_COMMENTS_GET_BY_ID);
			statement.setLong(1, commentId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				comment = buildComment(resultSet);
			}
		} catch (SQLException e) {
			logger.error(e);
			throw new DaoException("Cannot get comment, id = " + commentId);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Unhandled exception occurred while getting comment, id = " + commentId);
		} finally {
			closeDaoResources(dataSource, connection, statement, resultSet);
		}
		return comment;
	}

	@Override
	public long insert(Comment item) throws DaoException {
		PreparedStatement statement = null;
		long newCommentId = 0;
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection.prepareStatement(ORACLE_COMMENTS_INSERT_COMMENT, new String[]{COMMENTS_COMMENT_ID});
			statement.setLong(1, item.getNewsId());
			statement.setString(2, item.getCommentText());
			statement.setTimestamp(3, item.getCreationDate());
			statement.executeUpdate();
			resultSet = statement.getGeneratedKeys();
			if (resultSet != null && resultSet.next()) {
				newCommentId = resultSet.getLong(1);
			}
		} catch (SQLException e) {
			logger.error(e);
			throw new DaoException("Cannot insert comment text = " + item.getCommentText());
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Unhandled exception occurred while inserting comment text = " + item.getCommentText());
		} finally {
			closeDaoResources(dataSource, connection, statement, resultSet);
		}
		return newCommentId;
	}

	@Override
	public void update(Comment item) throws DaoException {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection.prepareStatement(ORACLE_COMMENTS_UPDATE_COMMENT);
			statement.setLong(1, item.getNewsId());
			statement.setString(2, item.getCommentText());
			statement.setTimestamp(3, item.getCreationDate());
			statement.setLong(4, item.getCommentId());
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e);
			throw new DaoException("Cannot update comment, id = " + item.getCommentId());
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Unhandled exception occurred while updating comment, id = " + item.getCommentId());
		} finally {
			closeDaoResources(dataSource, connection, statement, null);
		}
	}

	@Override
	public void delete(long commentId) throws DaoException {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection.prepareStatement(ORACLE_COMMENTS_DELETE_COMMENT);
			statement.setLong(1, commentId);
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e);
			throw new DaoException("Cannot delete comment, id = " + commentId);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Unhandled exception occurred while deleting comment, id = " + commentId);
		} finally {
			closeDaoResources(dataSource, connection, statement, null);
		}
	}

	@Override
	public List<Comment> getCommentByNews(long newsId) throws DaoException {
		Comment comment;
		List<Comment> commentList = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection.prepareStatement(ORACLE_COMMENTS_GET_LIST_BY_NEWS);
			statement.setLong(1, newsId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				comment = buildComment(resultSet);
				commentList.add(comment);
			}
		} catch (SQLException e) {
			logger.error(e);
			throw new DaoException("Cannot get comments by news, id = " + newsId);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Unhandled exception occurred while getting comments by news, id = " + newsId);
		} finally {
			closeDaoResources(dataSource, connection, statement, resultSet);
		}
		return commentList;
	}

	@Override
	public void deleteCommentByNews(long newsId) throws DaoException {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection.prepareStatement(ORACLE_COMMENTS_DELETE_COMMENT_BY_NEWS_ID);
			statement.setLong(1, newsId);
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e);
			throw new DaoException("Cannot delete comments by news, id = " + newsId);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Unhandled exception occurred deleting comments by news, id = " + newsId);
		} finally {
			closeDaoResources(dataSource, connection, statement, null);
		}
	}

	private Comment buildComment(ResultSet rs) throws SQLException {
		Comment comment = new Comment();
		comment.setCommentId(rs.getLong(COMMENTS_COMMENT_ID));
		comment.setNewsId(rs.getLong(COMMENTS_NEWS_ID));
		comment.setCommentText(rs.getString(COMMENTS_COMMENT_TEXT));
		comment.setCreationDate(rs.getTimestamp(COMMENTS_CREATION_DATE));
		return comment;
	}
}
