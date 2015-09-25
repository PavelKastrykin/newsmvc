package com.epam.newsportal.newsservice.dao.oracledb.implhibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.epam.newsportal.newsservice.dao.ITagDao;
import com.epam.newsportal.newsservice.entity.dto.TagDTO;
import com.epam.newsportal.newsservice.exception.DaoException;

public class TagDao implements ITagDao {

	public static final Logger logger = Logger.getLogger(TagDao.class);
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public List<TagDTO> getList() throws DaoException {
		try {
			Session session = sessionFactory.getCurrentSession();
			List<TagDTO> tags = (List<TagDTO>)session.createQuery("from TagDTO order by tagName").list();
			return tags;
		} catch (HibernateException e) {
			logger.error(e);
			throw new DaoException("Cannot get tag list");
		}
	}

	@Override
	public TagDTO getById(Long tagId) throws DaoException {
		try {
			Session session = sessionFactory.getCurrentSession();
			TagDTO tag = (TagDTO)session.createQuery("from TagDTO where tagId = (:tagId)")
					.setLong("tagId", tagId).uniqueResult();
			return tag;
		} catch (HibernateException e) {
			logger.error(e);
			throw new DaoException("Cannot get tag id = " + tagId);
		}
	}

	@Override
	public Long insert(TagDTO item) throws DaoException {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(item);
			return item.getTagId();
		} catch (HibernateException e) {
			logger.error(e);
			throw new DaoException("Cannot insert new tag");
		} 
	}

	@Override
	public void update(TagDTO item) throws DaoException {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.update(item);
		} catch (HibernateException e) {
			logger.error(e);
			throw new DaoException("Cannot update tag id = " + item.getTagId());
		}
	}

	@Override
	public void delete(Long tagId) throws DaoException {
		try {
			Session session = sessionFactory.getCurrentSession();
			TagDTO tag = (TagDTO)session.createQuery("from TagDTO where tagId = (:tagId)")
					.setLong("tagId", tagId).uniqueResult();
			session.delete(tag);
		} catch (HibernateException e) {
			logger.error(e);
			throw new DaoException("Cannot delete tag id = " + tagId);
		}
	}

	@Override
	public void insertTagListForNews(List<Long> tagIdList, Long newsId) throws DaoException {
	}
}
