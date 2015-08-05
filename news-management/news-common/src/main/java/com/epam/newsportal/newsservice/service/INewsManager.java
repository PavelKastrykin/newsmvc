package com.epam.newsportal.newsservice.service;

import java.util.List;

import com.epam.newsportal.newsservice.controller.SearchCriteria;
import com.epam.newsportal.newsservice.entity.dto.CommentDTO;
import com.epam.newsportal.newsservice.entity.dto.NewsDTO;
import com.epam.newsportal.newsservice.entity.dto.TagDTO;
import com.epam.newsportal.newsservice.exception.ServiceException;

public interface INewsManager {
	
	public void addNews(NewsDTO newsDTO) throws ServiceException;
	
	public void editNews(NewsDTO newsDTO) throws ServiceException;
	
	public void deleteNews(long newsId) throws ServiceException;
	
	public List<NewsDTO> viewListNews(int startWith, int quantity) throws ServiceException;
	
	public NewsDTO viewSingleNews(long newsId) throws ServiceException;
	
	public void addNewsAuthor(long authorId, long newsId) throws ServiceException;
	
	public List<NewsDTO> newsSearchResult(SearchCriteria criteria) throws ServiceException;
	
	public void addTagsForNewsMessage(List<TagDTO> tagDTOlist, long newsId) throws ServiceException;
	
	public void addCommentForNews(CommentDTO commentDTO) throws ServiceException;
	
	public void deleteComment(long commentId) throws ServiceException;
	
	public long countAllNews() throws ServiceException;
}
