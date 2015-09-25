package com.epam.newsportal.newsservice.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.newsportal.newsservice.dao.ICommentDao;
import com.epam.newsportal.newsservice.entity.dto.CommentDTO;
import com.epam.newsportal.newsservice.exception.DaoException;

@Service
@Transactional
public class CommentService {

	public static final Logger logger = Logger.getLogger(CommentService.class);
	
	@Autowired
	@Qualifier("commentDao")
	private ICommentDao commentDao;

	public void setCommentDao(ICommentDao commentDao) {
		this.commentDao = commentDao;
	}
	
	public CommentDTO getCommentById(Long commentId) throws DaoException {
		return commentDao.getById(commentId);
	}
	
	public Long insertComment(CommentDTO commentDTO) throws DaoException {
		return commentDao.insert(commentDTO);
	}
	
	public void deleteComment(Long commentId) throws DaoException {
		commentDao.delete(commentId);
	}
}
