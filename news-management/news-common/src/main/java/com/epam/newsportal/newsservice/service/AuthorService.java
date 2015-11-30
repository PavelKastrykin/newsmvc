package com.epam.newsportal.newsservice.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.newsportal.newsservice.dao.IAuthorDao;
import com.epam.newsportal.newsservice.dao.INewsDao;
import com.epam.newsportal.newsservice.entity.dto.AuthorDTO;
import com.epam.newsportal.newsservice.exception.DaoException;

@Service
@Transactional
public class AuthorService {

	public static final Logger logger = Logger.getLogger(AuthorService.class);

	@Autowired
	@Qualifier("authorDao")
	private IAuthorDao authorDao;
	
	@Autowired
	@Qualifier("newsDao")
	private INewsDao newsDao;
	
	public void setNewsDao(INewsDao newsDao) {
		this.newsDao = newsDao;
	}
	public void setAuthorDao(IAuthorDao authorDao) {
		this.authorDao = authorDao;
	}

	public List<AuthorDTO> getAuthorList() throws DaoException {
		return authorDao.getList();
	}

	public void deleteAuthor(Long authorId) throws DaoException {
		authorDao.delete(authorId);
	}

	public Long insertAuthor(AuthorDTO item) throws DaoException {
		authorDao.insert(item);
		return item.getAuthorId();
	}
	
	public void updateAuthor(AuthorDTO item) throws DaoException {
		authorDao.update(item);
	}
}
