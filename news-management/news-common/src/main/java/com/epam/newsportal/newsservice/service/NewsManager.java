package com.epam.newsportal.newsservice.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.epam.newsportal.newsservice.controller.SearchCriteria;
import com.epam.newsportal.newsservice.entity.dto.CommentDTO;
import com.epam.newsportal.newsservice.entity.dto.NewsDTO;
import com.epam.newsportal.newsservice.entity.dto.UserDTO;
import com.epam.newsportal.newsservice.exception.DaoException;
import com.epam.newsportal.newsservice.exception.ServiceException;

@Transactional(rollbackFor = ServiceException.class, propagation = Propagation.REQUIRED)
public class NewsManager implements INewsManager {

	public static final Logger logger = Logger.getLogger(NewsManager.class);

	private NewsService newsService;
	private AuthorService authorService;
	private TagService tagService;
	private CommentService commentService;
	private UserService userService;

	public NewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	public void setAuthorService(AuthorService authorService) {
		this.authorService = authorService;
	}

	public void setTagService(TagService tagService) {
		this.tagService = tagService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public long addNews(NewsDTO newsDTO) throws ServiceException {
		long newsId;
		try{
			newsId = newsService.insertNews(newsDTO);
			if (newsDTO.getAuthor().getAuthorId() != 0) {
				authorService.addAuthorToNews(newsDTO.getAuthor().getAuthorId(), newsId);
			}
			if(newsDTO.getTagIdList() != null){
				tagService.insertTagListForNews(newsDTO.getTagIdList(), newsId);
			}
		} catch (DaoException e){
			logger.info(e);
			throw new ServiceException("Error inserting news");
		} catch (Exception e) {
			logger.info(e);
			throw new ServiceException("Error inserting news");
		}
		return newsId;
	}
	
	@Override
	public void editNews(NewsDTO newsDTO) throws ServiceException {
		try{
			newsService.updateNews(newsDTO);
			if (newsDTO.getAuthor().getAuthorId() != 0) {
				authorService.addAuthorToNews(newsDTO.getAuthor().getAuthorId(), newsDTO.getNewsId());
			}
			if (newsDTO.getTagIdList() != null) {
				tagService.insertTagListForNews(newsDTO.getTagIdList(), newsDTO.getNewsId());
			}
		} catch (DaoException e) {
			logger.info(e);
			throw new ServiceException("Error updating news id = " + newsDTO.getNewsId());
		}
	}
	
	@Override
	public void deleteNews(long newsId) throws ServiceException {
		try{
			commentService.deleteCommentByNews(newsId);
			authorService.deleteNewsAuthorXref(newsId);
			tagService.deleteNewsTagXref(newsId);
			newsService.deleteNews(newsId);
		} catch (DaoException e) {
			logger.info(e);
			throw new ServiceException("Error deleting news id = " + newsId);
		}
	}
	
	@Override
	public NewsDTO viewSingleNews(long newsId) throws ServiceException {
		NewsDTO newsDTO;
		try {
			newsDTO = newsService.getNewsById(newsId);
			if (newsDTO != null) {
				newsDTO.setAuthor(authorService.getAuthorByNews(newsId));
				newsDTO.setTags(tagService.getTagByNews(newsId));
				newsDTO.setComments(commentService.getCommentByNews(newsId));
				newsDTO.buildTagIdList(newsDTO.getTags());
			}
		} catch (DaoException e) {
			logger.info(e);
			throw new ServiceException("Cannot get news id = " + newsId);
		}
		return newsDTO;
	}
	
	@Override
	public void addNewsAuthor(long authorId, long newsId) throws ServiceException {
		try{
			authorService.addAuthorToNews(authorId, newsId);
		} catch (DaoException e) {
			logger.info(e);
			throw new ServiceException("Cannot add author id = " + authorId + " for news id = " + newsId);
		}
	}
	
	@Override
	public List<NewsDTO> newsSearchResult(SearchCriteria criteria) throws ServiceException {
		List<NewsDTO> newsDTOlist;
		try{
			newsDTOlist = newsService.getNewsSearchResult(criteria);
			if(newsDTOlist != null){
				for(NewsDTO newsDTO : newsDTOlist){
					newsDTO.setAuthor(authorService.getAuthorByNews(newsDTO.getNewsId()));
					newsDTO.setTags(tagService.getTagByNews(newsDTO.getNewsId()));
					newsDTO.setComments(commentService.getCommentByNews(newsDTO.getNewsId()));
					newsDTO.buildTagIdList(newsDTO.getTags());
				}
			}
		} catch (DaoException e) {
			logger.info(e);
			throw new ServiceException("Cannot get news list");
		}
		return newsDTOlist;
	}
	
	@Override
	public void addTagsForNewsMessage(List<Long> tagIdlist, long newsId) throws ServiceException {
		try {
			tagService.insertTagListForNews(tagIdlist, newsId);
		} catch (DaoException e) {
			logger.info(e);
			throw new ServiceException("Cannot insert tags for news id = " + newsId);
		}
	}
	
	@Override
	public void addCommentForNews(CommentDTO commentDTO) throws ServiceException {
		try{
			commentService.insertComment(commentDTO);
		} catch (DaoException e){
			logger.info(e);
			throw new ServiceException("Cannot insert comment for news id = " + commentDTO.getNewsId());
		}
	}
	
	@Override
	public void deleteComment(long commentId) throws ServiceException {
		try{
			commentService.deleteComment(commentId);
		} catch (DaoException e){
			logger.info(e);
			throw new ServiceException("Cannot delete comment id = " + commentId);
		}
	}
	
	@Override
	public int countAllNews() throws ServiceException {
		try {
			return newsService.getNewsCount();
		} catch (DaoException e){
			logger.info(e);
			throw new ServiceException("Cannot get news count");
		}
	}

	@Override
	public UserDTO getUserByName(String name) throws ServiceException {
		try{
			return userService.getUserByName(name);
		} catch (DaoException e){
			logger.error(e);
			throw new ServiceException("Cannot get user name = " + name);
		}
	}
}