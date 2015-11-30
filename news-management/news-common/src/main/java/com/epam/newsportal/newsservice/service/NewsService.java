package com.epam.newsportal.newsservice.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.newsportal.newsservice.controller.SearchCriteria;
import com.epam.newsportal.newsservice.dao.IAuthorDao;
import com.epam.newsportal.newsservice.dao.INewsDao;
import com.epam.newsportal.newsservice.dao.ITagDao;
import com.epam.newsportal.newsservice.entity.dto.AuthorDTO;
import com.epam.newsportal.newsservice.entity.dto.NewsDTO;
import com.epam.newsportal.newsservice.entity.dto.TagDTO;
import com.epam.newsportal.newsservice.exception.DaoException;


@Service
@Transactional
public class NewsService {

	public static final Logger logger = Logger.getLogger(NewsService.class);

	@Autowired
	@Qualifier("newsDao")
	private INewsDao newsDao;
	
	@Autowired
	@Qualifier("authorDao")
	private IAuthorDao authorDao;
	
	@Autowired
	@Qualifier("tagDao")
	private ITagDao tagDao;
	
	public void setNewsDao(INewsDao newsDao) {
		this.newsDao = newsDao;
	}

	public void setAuthorDao(IAuthorDao authorDao) {
		this.authorDao = authorDao;
	}

	public void setTagDao(ITagDao tagDao) {
		this.tagDao = tagDao;
	}

	public NewsDTO getNewsById(Long newsId) throws DaoException {
		NewsDTO news = newsDao.getById(newsId);
		lazyInit(news);
		buildNewsToFronEnd(news);
		return news;
	}
	
	public long insertNews(NewsDTO newsDTO) throws DaoException {
		buildNewsToDataBase(newsDTO);
		return newsDao.insert(newsDTO);
	}
	
	public void updateNews(NewsDTO newsDTO) throws DaoException {
		buildNewsToDataBase(newsDTO);
		newsDao.update(newsDTO);
	}

	public void deleteNews(Long newsId) throws DaoException {
		newsDao.delete(newsId);
	}

	public List<NewsDTO> getNewsSearchResult(SearchCriteria criteria) throws DaoException {
		List<NewsDTO> newsList = newsDao.getSearchResult(criteria);
		for(NewsDTO news : newsList) {
			lazyInit(news);
			buildNewsToFronEnd(news);
		}
		return newsList;
	}
	
	private void lazyInit(NewsDTO news) {
		news.getAuthors().size();
		news.getComments().size();
		news.getTags().size();
	}

	private void buildNewsToDataBase(NewsDTO news) throws DaoException {
		if(news.getAuthor().getAuthorId() != null){
			news.getAuthors().clear();
			news.getAuthors().add(authorDao.getById(news.getAuthor().getAuthorId()));
		}
		if((news.getTagIdList() != null) && (news.getTagIdList().size() > 0)){
			for(Long tagId : news.getTagIdList()){
				news.getTags().add(tagDao.getById(tagId));
			}
		}
	}
	
	@SuppressWarnings("LoopStatementThatDoesntLoop")
	private void buildNewsToFronEnd(NewsDTO news){
		if(news.getAuthors().size() > 0){
			for(AuthorDTO author : news.getAuthors()){
				news.setAuthor(author);
				break;
			}
		}
		if((news.getTags() != null) && (news.getTags().size() > 0)){
			for(TagDTO tag : news.getTags()){
				news.getTagIdList().add(tag.getTagId());
			}
		}
	}
}
