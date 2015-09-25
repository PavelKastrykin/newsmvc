package com.epam.newsportal.newsservice.dao.oracledb.implhibernate;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.epam.newsportal.newsservice.controller.SearchCriteria;
import com.epam.newsportal.newsservice.dao.INewsDao;
import com.epam.newsportal.newsservice.entity.dto.NewsDTO;
import com.epam.newsportal.newsservice.exception.DaoException;

public class NewsDao implements INewsDao {

	public static final Logger logger = Logger.getLogger(NewsDao.class);
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public NewsDTO getById(Long newsId) throws DaoException {
		try {
			Session session = sessionFactory.getCurrentSession();
			NewsDTO news = (NewsDTO)session.createQuery("from NewsDTO where newsId = (:newsId)")
						.setLong("newsId", newsId).uniqueResult();
			return news;
		} catch (HibernateException e) {
			logger.error(e);
			throw new DaoException("Cannot get news id = " + newsId);
		}
	}
	
	@Override
	public Long insert(NewsDTO item) throws DaoException {
		try {
			Session session = sessionFactory.getCurrentSession();
			item.setCreationDate(new Timestamp(new java.util.Date().getTime()));
			item.setModificationDate(new Date(new java.util.Date().getTime()));
			session.save(item);
			return item.getNewsId();
		} catch (HibernateException e) {
			logger.error(e);
			throw new DaoException("Cannot insert new news");
		}
	}

	@Override
	public void update(NewsDTO item) throws DaoException { 
		try {
			Session session = sessionFactory.getCurrentSession();
			item.setModificationDate(new Date(new java.util.Date().getTime()));
			session.update(item);
		} catch (HibernateException e) {
			logger.error(e);
			throw new DaoException("Cannot update news id = " + item.getNewsId());
		}
	}

	@Override
	public void delete(Long newsId) throws DaoException {
		try {
			Session session = sessionFactory.getCurrentSession();
			NewsDTO news = (NewsDTO)session.createQuery("from NewsDTO where newsId = (:newsId)")
					.setLong("newsId", newsId).uniqueResult();
			session.delete(news);
		} catch (HibernateException e) {
			logger.error(e);
			throw new DaoException("Cannot delete news id = " + newsId);
		}
	}

	@Override
	public List<NewsDTO> getSearchResult(SearchCriteria criteria) throws DaoException {
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("select distinct n from NewsDTO n left join n.tags tags left join n.authors auts ");
			
			if (criteria.getAuthorId() != 0 & criteria.getTagIdList() == null){
				builder.append("where auts.authorId = " + criteria.getAuthorId());
			} else if (criteria.getAuthorId() == 0 & criteria.getTagIdList() != null) {
				builder.append("where tags.tagId in ( ");
				for(int i = 0; i < criteria.getTagIdList().size(); i++){
					builder.append(criteria.getTagIdList().get(i));
					if (i != criteria.getTagIdList().size() - 1) {
						builder.append(", ");
					}
				}
				builder.append(") ");
			} else if (criteria.getAuthorId() != 0 & criteria.getTagIdList() != null){
				builder.append("where auts.authorId = " + criteria.getAuthorId());
				builder.append(" and ");
				builder.append("tags.tagId in ( ");
				for(int i = 0; i < criteria.getTagIdList().size(); i++){
					builder.append(criteria.getTagIdList().get(i));
					if (i != criteria.getTagIdList().size() - 1) {
						builder.append(", ");
					}
				}
				builder.append(") ");
			}
			builder.append("order by size(n.comments) desc ");
			String query = builder.toString();
			Session session = sessionFactory.getCurrentSession();
			Query queryList = session.createQuery(query);
			criteria.setSearchSize(queryList.list().size());
			queryList.setFirstResult(criteria.getStartWith());
			queryList.setMaxResults(criteria.getNewsCount());
			List<NewsDTO> newsList = queryList.list();

			if (criteria.getCurrentNewsId() != 0){
				for (int i = 0; i < newsList.size(); i++){
					if (newsList.get(i).getNewsId() == (criteria.getCurrentNewsId())){
						if (i != 0){
							criteria.setPrevNewsId(newsList.get(i - 1).getNewsId());
						}
						if (i < (newsList.size() - 1)){
							criteria.setNextNewsId(newsList.get(i + 1).getNewsId());
						}
						break;
					}
				}
			}		
			return newsList;
		} catch (HibernateException e) {
			logger.error(e);
			throw new DaoException("Cannot get news list");
		}
	}
}
