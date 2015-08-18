package com.epam.newsportal.newsservice.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.epam.newsportal.newsservice.controller.SearchCriteria;
import com.epam.newsportal.newsservice.dao.INewsDao;
import com.epam.newsportal.newsservice.entity.News;
import com.epam.newsportal.newsservice.entity.dto.NewsDTO;
import com.epam.newsportal.newsservice.exception.DaoException;

public class NewsService {

	public static final Logger logger = Logger.getLogger(NewsService.class);

	private INewsDao newsDao;

	public void setNewsDao(INewsDao newsDao) {
		this.newsDao = newsDao;
	}

	public NewsDTO getNewsById(long newsId) throws DaoException {
		News news = newsDao.getById(newsId);
		NewsDTO newsDTO = new NewsDTO();
		newsDTO.buildNewsToDTO(news);
		return newsDTO;
	}
	
	public long insertNews(NewsDTO newsDTO) throws DaoException {
		News news = newsDTO.buildDTOtoNews();
		return newsDao.insert(news);
	}
	
	public void updateNews(NewsDTO newsDTO) throws DaoException {
		News news = newsDTO.buildDTOtoNews();
		newsDao.update(news);
	}

	public void deleteNews(long newsId) throws DaoException {
		newsDao.delete(newsId);
	}
	
	public List<NewsDTO> getNewsByAuthor(long authorId) throws DaoException {
		List<News> newsList = newsDao.getNewsByAuthor(authorId);
		return buildNewsList(newsList);
	}
	
	public List<NewsDTO> getNewsByTag(long tagId) throws DaoException {
		List<News> newsList = newsDao.getNewsByTag(tagId);
		return buildNewsList(newsList);
	}
	
	public int getNewsCount() throws DaoException {
		return newsDao.newsCount();
	}
	
	public List<NewsDTO> getNewsSearchResult(SearchCriteria criteria) throws DaoException {
		List<News> newsList = newsDao.getSearchResult(criteria);
		return buildNewsList(newsList);
	}
	
	private List<NewsDTO> buildNewsList(List<News> newsList) {
		List<NewsDTO> newsDTOlist = new ArrayList<>();
		if (!newsList.isEmpty()) {
			for(News news : newsList){
				NewsDTO newsDTO = new NewsDTO();
				newsDTO.buildNewsToDTO(news);
				newsDTOlist.add(newsDTO);
			}
		}
		return newsDTOlist;
	}
}
