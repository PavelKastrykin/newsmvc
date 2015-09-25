package com.epam.newsportal.newsservice.dao.oracledb.implhibernate;

import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.epam.newsportal.newsservice.dao.IAuthorDao;
import com.epam.newsportal.newsservice.entity.dto.AuthorDTO;
import com.epam.newsportal.newsservice.exception.DaoException;

public class AuthorDao implements IAuthorDao {

	public static final Logger logger = Logger.getLogger(AuthorDao.class);
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public List<AuthorDTO> getList() throws DaoException {
		try {
			Session session = sessionFactory.getCurrentSession();
			List<AuthorDTO> authors = (List<AuthorDTO>)session.createQuery("from AuthorDTO a where a.expired = null order by authorName").list();
			return authors;
		} catch (HibernateException e) {
			logger.error(e);
			throw new DaoException("Cannot get author list");
		}
	}
	
	@Override
	public AuthorDTO getById(Long authorId) throws DaoException {
		try {
			Session session = sessionFactory.getCurrentSession();
			AuthorDTO author = (AuthorDTO)session.createQuery("from AuthorDTO where authorId = (:authorId)")
					.setLong("authorId", authorId).uniqueResult();
			return author;
		} catch (HibernateException e) {
			logger.error(e);
			throw new DaoException("Cannot get author id = " + authorId);
		}
	}

	@Override
	public Long insert(AuthorDTO item) throws DaoException {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(item);
			return item.getAuthorId();
		} catch (HibernateException e) {
			logger.error(e);
			throw new DaoException("Cannot insert new author");
		}
	}

	@Override
	public void delete(Long authorId) throws DaoException {
		try {
			Session session = sessionFactory.getCurrentSession();
			AuthorDTO author = (AuthorDTO)session.createQuery("from AuthorDTO where authorId = (:authorId)")
					.setLong("authorId", authorId).uniqueResult();
			author.setExpired(new Timestamp(new java.util.Date().getTime()));
			session.update(author);
		} catch (HibernateException e) {
			logger.error(e);
			throw new DaoException("Cannot set author id = " + authorId + " expired");
		}
	}

	@Override
	public void update(AuthorDTO item) throws DaoException {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.update(item);
		} catch (HibernateException e) {
			logger.error(e);
			throw new DaoException("Cannot update author id = " + item.getAuthorId());
		}
	}
}
