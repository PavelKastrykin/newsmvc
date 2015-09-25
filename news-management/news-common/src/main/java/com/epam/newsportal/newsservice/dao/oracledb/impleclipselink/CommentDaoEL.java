package com.epam.newsportal.newsservice.dao.oracledb.impleclipselink;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.epam.newsportal.newsservice.dao.ICommentDao;
import com.epam.newsportal.newsservice.entity.dto.CommentDTO;
import com.epam.newsportal.newsservice.exception.DaoException;

public class CommentDaoEL implements ICommentDao {
	
	public static final Logger logger = Logger.getLogger(CommentDaoEL.class);
	
	@PersistenceContext
    private EntityManager em;
	
	@Override
	public CommentDTO getById(Long id) throws DaoException {
		try {
			return em.find(CommentDTO.class, id);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Cannot get comment id = " + id);
		}
	}

	@Override
	public Long insert(CommentDTO item) throws DaoException {
		try {
			em.persist(item);
			em.flush();
			return item.getCommentId();
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Cannot insert comment");
		}
	}

	@Override
	public void update(CommentDTO item) throws DaoException {
		try {
			em.merge(item);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Cannot update comment id = " + item.getCommentId());
		}
	}

	@Override
	public void delete(Long id) throws DaoException {
		try {
			CommentDTO comment = em.find(CommentDTO.class, id);
			em.remove(comment);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("Cannot delete comment id = " + id);
		}
	}
}
