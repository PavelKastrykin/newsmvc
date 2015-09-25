package com.epam.newsportal.newsservice.dao.oracledb.impleclipselink;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.epam.newsportal.newsservice.dao.IAuthorDao;
import com.epam.newsportal.newsservice.entity.dto.AuthorDTO;
import com.epam.newsportal.newsservice.exception.DaoException;

public class AuthorDaoEL implements IAuthorDao {

	public static final Logger logger = Logger.getLogger(AuthorDaoEL.class);
	
	@PersistenceContext
    private EntityManager em;
	
	@Override
	public AuthorDTO getById(Long id) throws DaoException {
		try {
			return em.find(AuthorDTO.class, id);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Cannot get news id = " + id);
		}
	}

	@Override
	public Long insert(AuthorDTO item) throws DaoException {
		try {
			em.persist(item);
			em.flush();
			return item.getAuthorId();
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Cannot insert new author");
		}
		
	}

	@Override
	public void update(AuthorDTO item) throws DaoException {
		try {
			em.merge(item);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Cannot update author id = " + item.getAuthorId());
		}
	}

	@Override
	public void delete(Long id) throws DaoException {
		try {
			AuthorDTO author = em.find(AuthorDTO.class, id);
			author.setExpired(new Timestamp(new java.util.Date().getTime()));
			em.merge(author);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Cannot set author id = " + id + " expired");
		}
		
	}

	@Override
	public List<AuthorDTO> getList() throws DaoException  {
		try {
			Query query = em.createQuery("select a from AuthorDTO a where a.expired is null"); 
			return (List<AuthorDTO>)query.getResultList();
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Cannot get author list ");
		}
		
	}
}
