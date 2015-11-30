package com.epam.newsportal.newsservice.dao.oracledb.impleclipselink;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.epam.newsportal.newsservice.controller.SearchCriteria;
import com.epam.newsportal.newsservice.dao.INewsDao;
import com.epam.newsportal.newsservice.entity.dto.NewsDTO;
import com.epam.newsportal.newsservice.exception.DaoException;

public class NewsDaoEL implements INewsDao {

	private static final Logger logger = Logger.getLogger(NewsDaoEL.class);
	
	@PersistenceContext
    private EntityManager em;
	
	@Override
	public NewsDTO getById(Long id) throws DaoException {
		try {
			return em.find(NewsDTO.class, id);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Cannot get news id = " + id);
		}
	}

	@Override
	public Long insert(NewsDTO item) throws DaoException {
		try {
			item.setCreationDate(new Timestamp(new java.util.Date().getTime()));
			item.setModificationDate(new Date(new java.util.Date().getTime()));
			em.persist(item);
			em.flush();
			return item.getNewsId();
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Cannot insert news");
		}
	}

	@Override
	public void update(NewsDTO item) throws DaoException {
		try {
			item.setModificationDate(new Date(new java.util.Date().getTime()));
			em.merge(item);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Cannot update news id = " + item.getNewsId());
		}
	}

	@Override
	public void delete(Long id) throws DaoException {
		try {
			NewsDTO news = em.find(NewsDTO.class, id);
			em.remove(news);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Cannot delete news id = " + id);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NewsDTO> getSearchResult(SearchCriteria criteria) throws DaoException {
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("select distinct n from NewsDTO n left join n.tags tags left join n.authors auts ");
			
			if (criteria.getAuthorId() != 0 & criteria.getTagIdList() == null){
				builder.append("where auts.authorId = ").append(criteria.getAuthorId());
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
				builder.append("where auts.authorId = ").append(criteria.getAuthorId());
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
			builder.append(" order by size(n.comments) desc ");
			String query = builder.toString();
			Query queryList = em.createQuery(query);
			criteria.setSearchSize(queryList.getResultList().size());
			queryList.setFirstResult(criteria.getStartWith());
			queryList.setMaxResults(criteria.getNewsCount());
			
			List<NewsDTO> newsList = queryList.getResultList();

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
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Cannot get news list");
		}
	}
}
