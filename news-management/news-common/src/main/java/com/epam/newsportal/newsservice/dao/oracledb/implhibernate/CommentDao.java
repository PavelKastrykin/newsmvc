package com.epam.newsportal.newsservice.dao.oracledb.implhibernate;

import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.epam.newsportal.newsservice.dao.ICommentDao;
import com.epam.newsportal.newsservice.entity.dto.CommentDTO;
import com.epam.newsportal.newsservice.exception.DaoException;

public class CommentDao implements ICommentDao {

	private static final Logger logger = Logger.getLogger(CommentDao.class);
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public CommentDTO getById(Long commentId) throws DaoException {
		try {
			Session session = sessionFactory.getCurrentSession();
			return (CommentDTO)session.createQuery("from CommentDTO where commentId = (:commentId)")
					.setLong("commentId", commentId).uniqueResult();
		} catch (HibernateException e) {
			logger.error(e);
			throw new DaoException("Cannot get comment id = " + commentId);
		}
	}

	@Override
	public Long insert(CommentDTO item) throws DaoException {
		try {
			Session session = sessionFactory.getCurrentSession();
			item.setCreationDate(new Timestamp(new java.util.Date().getTime()));
			session.save(item);
			return item.getCommentId();
		} catch (HibernateException e) {
			logger.error(e);
			throw new DaoException("Cannot insert new comment for news id = " + item.getNews().getNewsId());
		} 
	}

	@Override
	public void delete(Long commentId) throws DaoException {
		try {
			Session session = sessionFactory.getCurrentSession();
			CommentDTO comment = (CommentDTO)session.createQuery("from CommentDTO where commentId = (:commentId)")
					.setLong("commentId", commentId).uniqueResult();
			session.delete(comment);
		} catch (HibernateException e) {
			logger.error(e);
			throw new DaoException("Cannot delete comment id = " + commentId);
		}
	}

	@Override
	public void update(CommentDTO item) throws DaoException {
	}
}
