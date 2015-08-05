package com.epam.newsportal.newsservice.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.epam.newsportal.newsservice.dao.ICommentDao;
import com.epam.newsportal.newsservice.entity.Comment;
import com.epam.newsportal.newsservice.entity.dto.CommentDTO;
import com.epam.newsportal.newsservice.exception.DaoException;

public class CommentService {

	public static final Logger logger = Logger.getLogger(CommentService.class);
	
	private ICommentDao commentDao;

	public ICommentDao getCommentDao() {
		return commentDao;
	}

	public void setCommentDao(ICommentDao commentDao) {
		this.commentDao = commentDao;
	}
	
	public CommentDTO getCommentById(long commentId) throws DaoException {
		Comment comment = commentDao.getById(commentId);
		CommentDTO commentDTO = new CommentDTO();
		commentDTO.buildCommentToDTO(comment);
		return commentDTO;
	}
	
	public long insertComment(CommentDTO commentDTO) throws DaoException {
		Comment comment = commentDTO.buildDTOtoComment();
		return commentDao.insert(comment);
	}
	
	public void updateComment(CommentDTO commentDTO) throws DaoException {
		Comment comment = commentDTO.buildDTOtoComment();
		commentDao.update(comment);
	}
	
	public void deleteComment(long commentId) throws DaoException {
		commentDao.delete(commentId);
	}

	public List<CommentDTO> getCommentByNews (long newsId) throws DaoException {
		
		List<Comment> comments = commentDao.getCommentByNews(newsId);
		List<CommentDTO> commentsDTO = new ArrayList<>();
		if (!comments.isEmpty()) {
			for(Comment comment : comments){
				CommentDTO commentDTO = new CommentDTO();
				commentDTO.buildCommentToDTO(comment);
				commentsDTO.add(commentDTO);
			}
		}
		return commentsDTO;
	}
	
	public void deleteCommentByNews(long newsId) throws DaoException {
		commentDao.deleteCommentByNews(newsId);
	}
}
