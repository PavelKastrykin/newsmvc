package com.epam.newsportal.newsservice.dao.oracledb.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;

import com.epam.newsportal.newsservice.controller.SearchCriteria;
import com.epam.newsportal.newsservice.dao.INewsDao;
import com.epam.newsportal.newsservice.entity.News;
import com.epam.newsportal.newsservice.exception.DaoException;

public class NewsDao extends CommonDao implements INewsDao {

//	public static final Logger logger = Logger.getLogger(NewsDao.class);
	
	private static final int DEFAULT_COUNT = 1000000;
	
	private static final String ORACLE_NEWS_GET_BY_ID = "SELECT NEWS_ID, TITLE, SHORT_TEXT, "
			+ "FULL_TEXT, CREATION_DATE, MODIFICATION_DATE FROM NEWS "
			+ "WHERE NEWS_ID = ?";
	private static final String ORACLE_NEWS_INSERT_NEWS = "INSERT INTO NEWS "
			+ "(NEWS_ID, TITLE, SHORT_TEXT, FULL_TEXT, CREATION_DATE, MODIFICATION_DATE) "
			+ "VALUES (NEWS_SEQ.NEXTVAL, ?, ?, ?, ?, ?)";
	private static final String ORACLE_NEWS_UPDATE_NEWS = "UPDATE NEWS SET "
			+ "TITLE = ?, SHORT_TEXT = ?, FULL_TEXT = ?, CREATION_DATE = ?, MODIFICATION_DATE = ? "
			+ "WHERE NEWS_ID = ?"; 
	private static final String ORACLE_NEWS_DELETE_NEWS = "DELETE FROM NEWS "
			+ "WHERE NEWS_ID = ?";
	private static final String ORACLE_NEWS_GET_LIST_BY_AUTHOR = "SELECT NEWS_ID, TITLE, "
			+ "SHORT_TEXT, FULL_TEXT, CREATION_DATE, MODIFICATION_DATE FROM NEWS "
			+ "WHERE NEWS_ID IN (SELECT NEWS_ID FROM NEWS_AUTHORS WHERE "
			+ "NEWS_AUTHORS.AUTHOR_ID = ?)";
	private static final String ORACLE_NEWS_GET_LIST_BY_TAG = "SELECT NEWS_ID, TITLE, "
			+ "SHORT_TEXT, FULL_TEXT, CREATION_DATE, MODIFICATION_DATE FROM NEWS "
			+ "WHERE NEWS_ID IN (SELECT NEWS_ID FROM NEWS_TAGS WHERE "
			+ "NEWS_TAGS.TAG_ID = ?)";
	private static final String ORACLE_NEWS_COUNT = "SELECT COUNT(*) AS QUANTITY FROM NEWS";
	
	private static final String ORACLE_SEARCH_BEGIN = "SELECT N.NEWS_ID, N.TITLE, N.SHORT_TEXT, N.FULL_TEXT, "
			+ "N.CREATION_DATE, N.MODIFICATION_DATE, COUNT(DISTINCT CO.COMMENT_ID) QTY FROM NEWS N "
			+ "LEFT JOIN COMMENTS CO ON N.NEWS_ID = CO.NEWS_ID "
			+ "LEFT JOIN NEWS_AUTHORS NA ON N.NEWS_ID = NA.NEWS_ID  "
			+ "LEFT JOIN NEWS_TAGS NT ON N.NEWS_ID = NT.NEWS_ID ";
	private static final String ORACLE_SEARCH_ADD_AUTHOR = "WHERE NA.AUTHOR_ID = ";
	private static final String ORACLE_SEARCH_ADD_TAGS = "NT.TAG_ID IN ( ";
	private static final String ORACLE_SEARCH_END = "GROUP BY N.NEWS_ID, N.TITLE, N.SHORT_TEXT, N.FULL_TEXT, "
			+ "N.CREATION_DATE, N.MODIFICATION_DATE ORDER BY QTY DESC, N.MODIFICATION_DATE DESC";
	
	private static final String ORACLE_SEARCH_ROWS_BEGIN_APPEND = "SELECT NEWS_ID, TITLE, SHORT_TEXT, FULL_TEXT, "
			+ "CREATION_DATE, MODIFICATION_DATE	FROM (SELECT A.NEWS_ID, A.TITLE, A.SHORT_TEXT, A.FULL_TEXT, "
			+ "A.CREATION_DATE, A.MODIFICATION_DATE, ROWNUM RNUM FROM (";
	private static final String ORACLE_SEARCH_ROWS_END_FIRST_APPEND = ") A WHERE ROWNUM <= ";
	private static final String ORACLE_SEARCH_ROWS_END_SECOND_APPEND = " ) WHERE RNUM  > ";
	
	private static final String ORACLE_SEARCH_LEAD_LAG_BEGIN = "SELECT L.NEWS_ID, L.PREV_ID, L.NEXT_ID FROM "
			+ "(SELECT NEWS_ID, LAG(NEWS_ID) OVER(ORDER BY NEWS_ID) PREV_ID, LEAD(NEWS_ID) OVER(ORDER BY NEWS_ID) NEXT_ID "
			+ "FROM (";
	private static final String ORACLE_SEARCH_LEAD_LAG_END = ")) L WHERE NEWS_ID = ";

	private static final String NEWS_NEWS_ID = "NEWS_ID";
	private static final String NEWS_TITLE = "TITLE";
	private static final String NEWS_SHORT_TEXT = "SHORT_TEXT";
	private static final String NEWS_FULL_TEXT = "FULL_TEXT";
	private static final String NEWS_CREATION_DATE = "CREATION_DATE";
	private static final String NEWS_MODIFICATION_DATE = "MODIFICATION_DATE";
	private static final String TABLE_QUANTITY = "QUANTITY";
	private static final String PREV_ID = "PREV_ID";
	private static final String NEXT_ID = "NEXT_ID";
	
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public News getById(long newsId) throws DaoException {
		News news = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection.prepareStatement(ORACLE_NEWS_GET_BY_ID);
			statement.setLong(1, newsId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				news = buildNews(resultSet);
			}
		} catch (SQLException e) {
			logger.error(e);
			throw new DaoException("Cannot get news id = " + newsId);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Unhandled exception occurred while getting news id = " + newsId);
		} finally {
			closeDaoResources(dataSource, connection, statement, resultSet);
		}
		return news;
	}

	@Override
	public long insert(News item) throws DaoException {
		PreparedStatement statement = null;
		long newNewsId = 0;
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection.prepareStatement(ORACLE_NEWS_INSERT_NEWS, new String[]{NEWS_NEWS_ID});
			statement.setString(1, item.getTitle());
			statement.setString(2, item.getShortText());
			statement.setString(3, item.getFullText());
			statement.setTimestamp(4, item.getCreationDate());
			statement.setDate(5, item.getModificationDate());
			statement.executeUpdate();
			resultSet = statement.getGeneratedKeys();
			if (resultSet != null && resultSet.next()) {
				newNewsId = resultSet.getLong(1);
			}
		} catch (SQLException e) {
			logger.error(e);
			throw new DaoException("Cannot insert news title = " + item.getTitle());
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Unhandled exception occurred while inserting news title = " + item.getTitle());
		} finally {
			closeDaoResources(dataSource, connection, statement, resultSet);
		}
		return newNewsId;
	}

	@Override
	public void update(News item) throws DaoException {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection.prepareStatement(ORACLE_NEWS_UPDATE_NEWS);
			statement.setString(1, item.getTitle());
			statement.setString(2, item.getShortText());
			statement.setString(3, item.getFullText());
			statement.setTimestamp(4, item.getCreationDate());
			statement.setDate(5, item.getModificationDate());
			statement.setLong(6, item.getNewsId());
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e);
			throw new DaoException("Cannot update news id = " + item.getNewsId());
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Unhandled exception occurred while updating news id = " + item.getNewsId());
		} finally {
			closeDaoResources(dataSource, connection, statement, null);
		}
	}

	@Override
	public void delete(long newsId) throws DaoException {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection.prepareStatement(ORACLE_NEWS_DELETE_NEWS);
			statement.setLong(1, newsId);
			statement.executeQuery();
		} catch (SQLException e) {
			logger.error(e);
			throw new DaoException("Cannot delete news id = " + newsId);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Unhandled exception occurred while deleting news id = " + newsId);
		} finally {
			closeDaoResources(dataSource, connection, statement, null);
		}
	}

	@Override
	public List<News> getNewsByAuthor(long authorId) throws DaoException {
		News news;
		List<News> newsList = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection.prepareStatement(ORACLE_NEWS_GET_LIST_BY_AUTHOR);
			statement.setLong(1, authorId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				news = buildNews(resultSet);
				newsList.add(news);
			}
		} catch (SQLException e) {
			logger.error(e);
			throw new DaoException("Cannot get news list by author id = " + authorId);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Unhandled exception occurred while getting news list by author id = " + authorId);
		} finally {
			closeDaoResources(dataSource, connection, statement, resultSet);
		}
		return newsList;
	}

	@Override
	public List<News> getNewsByTag(long tagId) throws DaoException {
		News news;
		List<News> newsList = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection.prepareStatement(ORACLE_NEWS_GET_LIST_BY_TAG);
			statement.setLong(1, tagId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				news = buildNews(resultSet);
				newsList.add(news);
			}
		} catch (SQLException e) {
			logger.error(e);
			throw new DaoException("Cannot get news list by tag id = " + tagId);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Unhandled exception occurred while getting news list by tag id = " + tagId);
		} finally {
			closeDaoResources(dataSource, connection, statement, resultSet);
		}
		return newsList;
	}
	
	@Override
	public int newsCount() throws DaoException {
		int newsCount = 0;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try{
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection.prepareStatement(ORACLE_NEWS_COUNT);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				newsCount = resultSet.getInt(TABLE_QUANTITY);
			}
		} catch (SQLException e) {
			logger.error(e);
			throw new DaoException("Cannot get news count");
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Unhandled exception occurred while getting news count");
		} finally {
			closeDaoResources(dataSource, connection, statement, resultSet);
		}
		return newsCount;
	}
	
	@Override
	public List<News> getSearchResult(SearchCriteria criteria) throws DaoException {
		
		List<News> newsList = new ArrayList<>();
		Statement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		StringBuilder builder = new StringBuilder();
		//Starting generate query without row number
		builder.append(ORACLE_SEARCH_BEGIN);
		if (criteria.getNewsCount() == 0) {
			criteria.setNewsCount(DEFAULT_COUNT);
		}
		if (criteria.getAuthorId() == 0 & criteria.getTagIdList() == null) {
			builder.append(ORACLE_SEARCH_END);
		}
		else if (criteria.getAuthorId() != 0 & criteria.getTagIdList() == null) {
			builder.append(ORACLE_SEARCH_ADD_AUTHOR).append(criteria.getAuthorId()).append(" ").append(ORACLE_SEARCH_END);
		}
		else if (criteria.getAuthorId() == 0 & criteria.getTagIdList() != null) {
			builder.append("WHERE ").append(ORACLE_SEARCH_ADD_TAGS);
			for(int i = 0; i < criteria.getTagIdList().size(); i++){
				builder.append(criteria.getTagIdList().get(i));
				if (i != criteria.getTagIdList().size() - 1) {
					builder.append(", ");
				}
			}
			builder.append(") ").append(ORACLE_SEARCH_END);
		}
		
		else {
			builder.append(ORACLE_SEARCH_ADD_AUTHOR).append(criteria.getAuthorId()).append(" ")
			.append("AND ").append(ORACLE_SEARCH_ADD_TAGS);
			for(int i = 0; i < criteria.getTagIdList().size(); i++){
				builder.append(criteria.getTagIdList().get(i));
				if (i != criteria.getTagIdList().size() - 1) {
					builder.append(", ");
				}
			}
			builder.append(")").append(ORACLE_SEARCH_END);
		}
		String simpleQuery = builder.toString();
		//At this moment query without row number is generated - simpleQuery
		//needed to get search size for criteria
		
		try{
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(simpleQuery);
			int count = 0;
			while(resultSet.next()){
				count++;
			}
			criteria.setSearchSize(count);

//			resultSet.last();
//			criteria.setSearchSize(resultSet.getRow());
			
			resultSet.close();
			statement.close();
			
			//Getting previous and next news_id for current
			
			statement = connection.createStatement();
			if(criteria.getCurrentNewsId() != 0){
				statement = connection.createStatement();
				String queryForNewsId = ORACLE_SEARCH_LEAD_LAG_BEGIN + simpleQuery + ORACLE_SEARCH_LEAD_LAG_END 
						+ criteria.getCurrentNewsId();
				resultSet = statement.executeQuery(queryForNewsId);
				if(resultSet.next()){
					if(resultSet.getLong(PREV_ID) != 0){
						criteria.setPrevNewsId(resultSet.getLong(PREV_ID));
					}
					if(resultSet.getLong(NEXT_ID) != 0){
						criteria.setNextNewsId(resultSet.getLong(NEXT_ID));
					}
				}
				resultSet.close();
				statement.close();
			}
			//Finally, criteria is modified
			//At last return necessary list of news
			
			StringBuilder finalQueryBuilder = new StringBuilder();
			finalQueryBuilder.append(ORACLE_SEARCH_ROWS_BEGIN_APPEND).append(simpleQuery)
			.append(ORACLE_SEARCH_ROWS_END_FIRST_APPEND).append(criteria.getStartWith() + criteria.getNewsCount())
			.append(ORACLE_SEARCH_ROWS_END_SECOND_APPEND).append(criteria.getStartWith());
			
			statement = connection.createStatement();
			resultSet = statement.executeQuery(finalQueryBuilder.toString());
			News news;
			while (resultSet.next()) {
				news = buildNews(resultSet);
				newsList.add(news);
			}
		} catch (SQLException e) {
			logger.error(e);
			throw new DaoException("Cannot get news search list");
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Unhandled exception occurred while getting news search list");
		} finally {
			closeDaoResources(dataSource, connection, statement, resultSet);
		}
		return newsList;
	}

	private News buildNews(ResultSet rs) throws SQLException {
		News news = new News();
		news.setNewsId(rs.getLong(NEWS_NEWS_ID));
		news.setTitle(rs.getString(NEWS_TITLE));
		news.setShortText(rs.getString(NEWS_SHORT_TEXT));
		news.setFullText(rs.getString(NEWS_FULL_TEXT));
		news.setCreationDate(rs.getTimestamp(NEWS_CREATION_DATE));
		news.setModificationDate(rs.getDate(NEWS_MODIFICATION_DATE));
		return news;
	}
}
