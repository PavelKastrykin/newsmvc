package com.epam.newsportal.newsservice.dao.oracledb.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.epam.newsportal.newsservice.dao.ITagDao;
import com.epam.newsportal.newsservice.entity.Tag;
import com.epam.newsportal.newsservice.exception.DaoException;

public class TagDao extends CommonDao implements ITagDao {

	public static final Logger logger = Logger.getLogger(TagDao.class);
	
	private static final String ORACLE_TAGS_GET_LIST =  "SELECT TAG_ID, TAG_NAME FROM TAGS";
	private static final String ORACLE_TAGS_GET_BY_ID = "SELECT TAG_ID, TAG_NAME FROM TAGS "
			+ "WHERE TAG_ID = ?";
	private static final String ORACLE_TAGS_INSERT_TAG = "INSERT INTO TAGS "
			+ "(TAG_ID, TAG_NAME) VALUES (TAGS_SEQ.NEXTVAL, ?)";
	private static final String ORACLE_TAGS_UPDATE_TAG = "UPDATE TAGS SET "
			+ "TAG_NAME = ? WHERE TAG_ID = ?";
	private static final String ORACLE_TAGS_DELETE_TAG = "DELETE FROM TAGS "
			+ "WHERE TAG_ID = ?";
	private static final String ORACLE_TAGS_GET_LIST_FOR_NEWS = "SELECT TAG_ID, TAG_NAME FROM "
			+ "TAGS WHERE TAG_ID IN (SELECT TAG_ID FROM NEWS_TAGS "
			+ "WHERE NEWS_TAGS.NEWS_ID = ?)";
	private static final String ORACLE_TAGS_DELETE_SINGLE_XREF = "DELETE FROM NEWS_TAGS WHERE "
			+ "NEWS_ID = ? AND TAG_ID = ?";
	private static final String ORACLE_TAGS_DELETE_NEWS_XREF = "DELETE FROM NEWS_TAGS WHERE "
			+ "NEWS_ID = ?";
	private static final String ORACLE_TAGS_INSERT_NEWS_XREF = "INSERT INTO NEWS_TAGS (NEWS_ID, "
			+ "TAG_ID) VAawerLUES(?, ?)";
	
	private static final String TAGS_TAG_ID = "TAG_ID";
	private static final String TAGS_TAG_NAME = "TAG_NAME";
	
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<Tag> getList() throws DaoException {
		List<Tag> tagList = new ArrayList<>();
		Tag tag;
		Statement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(ORACLE_TAGS_GET_LIST);
			while (resultSet.next()) {
				tag = buildTag(resultSet);
				tagList.add(tag);
			}
		} catch (SQLException e) {
			logger.error(e);
			throw new DaoException("Cannot get tag list");
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Unhandled exception occurred while getting tag list");
		} finally {
			closeDaoResources(dataSource, connection, statement, resultSet);
		}
		return tagList;
	}

	@Override
	public Tag getById(long tagId) throws DaoException {
		Tag tag = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection.prepareStatement(ORACLE_TAGS_GET_BY_ID);
			statement.setLong(1, tagId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				tag = buildTag(resultSet);
			}
		} catch (SQLException e) {
			logger.error(e);
			throw new DaoException("Cannot get tag id = " + tagId);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Unhandled exception occurred while getting tag id = " + tagId);
		} finally {
			closeDaoResources(dataSource, connection, statement, resultSet);
		}
		return tag;
	}

	@Override
	public long insert(Tag item) throws DaoException {
		PreparedStatement statement = null;
		long newTagId = 0;
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection.prepareStatement(ORACLE_TAGS_INSERT_TAG, new String[]{TAGS_TAG_ID});
			statement.setString(1, item.getTagName());
			statement.executeUpdate();
			resultSet = statement.getGeneratedKeys();
			if (resultSet != null && resultSet.next()) {
				newTagId = resultSet.getLong(1);
			}
		} catch (SQLException e) {
			logger.error(e);
			throw new DaoException("Cannot insert tag name = " + item.getTagName());
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Unhandled exception occurred while inserting tag name = " + item.getTagName());
		} finally {
			closeDaoResources(dataSource, connection, statement, resultSet);
		}
		return newTagId;
	}

	@Override
	public void update(Tag item) throws DaoException {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection.prepareStatement(ORACLE_TAGS_UPDATE_TAG);
			statement.setString(1, item.getTagName());
			statement.setLong(2, item.getTagId());
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e);
			throw new DaoException("Cannot update tag id = " + item.getTagId());
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Unhandled exception occurred while updating tag id = " + item.getTagId());
		} finally {
			closeDaoResources(dataSource, connection, statement, null);
		}
	}

	@Override
	public void delete(long tagId) throws DaoException {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection.prepareStatement(ORACLE_TAGS_DELETE_TAG);
			statement.setLong(1, tagId);
			statement.executeQuery();
		} catch (SQLException e) {
			logger.error(e);
			throw new DaoException("Cannot delete tag id = " + tagId);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Unhandled exception occurred while deleting tag id = " + tagId);
		} finally {
			closeDaoResources(dataSource, connection, statement, null);
		}
	}

	@Override
	public List<Tag> getTagByNews(long newsId) throws DaoException {
		Tag tag;
		List<Tag> tagList = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection.prepareStatement(ORACLE_TAGS_GET_LIST_FOR_NEWS);
			statement.setLong(1, newsId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				tag = buildTag(resultSet);
				tagList.add(tag);
			}
		} catch (SQLException e) {
			logger.error(e);
			throw new DaoException("Cannot get tag list by news id = " + newsId);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Unhandled exception occurred while getting tag list by news id = " + newsId);
		} finally {
			closeDaoResources(dataSource, connection, statement, resultSet);
		}
		return tagList;
	}

	@Override
	public void insertTagListForNews(List<Tag> tagList, long newsId) throws DaoException {
		PreparedStatement statement = null;
		Connection connection = null;
		try{
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection.prepareStatement(ORACLE_TAGS_INSERT_NEWS_XREF);
			for(Tag tag : tagList){
				statement.setLong(1, newsId);
				statement.setLong(2, tag.getTagId());
				statement.addBatch();
			}
			statement.executeBatch();
		} catch (SQLException e) {
			logger.error(e);
			throw new DaoException("Cannot insert tag list by news id = " + newsId);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Unhandled exception occurred while inserting tag list by news id = " + newsId);
		} finally {
			closeDaoResources(dataSource, connection, statement, null);
		}
	}
	
	@Override
	public void deleteTagXref(long newsId, long tagId) throws DaoException {
		PreparedStatement statement = null;
		Connection connection = null;
		try{
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection.prepareStatement(ORACLE_TAGS_DELETE_SINGLE_XREF);
			statement.setLong(1, newsId);
			statement.setLong(2, tagId);
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e);
			throw new DaoException("Cannot delete tag xref by news id = " + newsId);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Unhandled exception occurred while deleting tag xref by news id = " + newsId);
		} finally {
			closeDaoResources(dataSource, connection, statement, null);
		}
	}
	
	@Override
	public void deleteNewsTagXref(long newsId) throws DaoException {
		PreparedStatement statement = null;
		Connection connection = null;
		try{
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection.prepareStatement(ORACLE_TAGS_DELETE_NEWS_XREF);
			statement.setLong(1, newsId);
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e);
			throw new DaoException("Cannot delete tags by news id = " + newsId);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Unhandled exception occurred while deleting tags by news id = " + newsId);
		} finally {
			closeDaoResources(dataSource, connection, statement, null);
		}
	}
	
	private Tag buildTag(ResultSet rs) throws SQLException {
		Tag tag = new Tag();
		tag.setTagId(rs.getLong(TAGS_TAG_ID));
		tag.setTagName(rs.getString(TAGS_TAG_NAME));
		return tag;
	}

	

	

	
}
