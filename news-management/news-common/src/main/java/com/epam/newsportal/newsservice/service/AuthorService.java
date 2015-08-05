package com.epam.newsportal.newsservice.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.epam.newsportal.newsservice.dao.IAuthorDao;
import com.epam.newsportal.newsservice.entity.Author;
import com.epam.newsportal.newsservice.entity.dto.AuthorDTO;
import com.epam.newsportal.newsservice.exception.DaoException;

public class AuthorService {

	public static final Logger logger = Logger.getLogger(AuthorService.class);

	private IAuthorDao authorDao;

	public void setAuthorDao(IAuthorDao authorDao) {
		this.authorDao = authorDao;
	}

	public List<AuthorDTO> getAuthorList() throws DaoException {
		List<Author> authors = authorDao.getList();
		List<AuthorDTO> authorsDTO = new ArrayList<>();
		for(Author author : authors){
			AuthorDTO authorDTO = new AuthorDTO();
			authorDTO.buildAuthorToDTO(author);
			authorsDTO.add(authorDTO);
		}
		return authorsDTO;
	}
	
	public AuthorDTO getAuthorById(long authorId) throws DaoException {
		Author author = authorDao.getById(authorId);
		AuthorDTO authorDTO = new AuthorDTO();
		authorDTO.buildAuthorToDTO(author);
		return authorDTO;
	}
	
	public long insertAuthor(AuthorDTO authorDTO) throws DaoException {
		Author author = authorDTO.buildDTOtoAuthor();
		return authorDao.insert(author);
	}
	
	public void updateAuthor(AuthorDTO authorDTO) throws DaoException {
		Author author = authorDTO.buildDTOtoAuthor();
		authorDao.update(author);
	}
	
	public void deleteAuthor(long authorId) throws DaoException {
		authorDao.delete(authorId);
	}
	
	public AuthorDTO getAuthorByNews(long newsId) throws DaoException {
		Author author = authorDao.getByNewsId(newsId);
		AuthorDTO authorDTO = new AuthorDTO();
		authorDTO.buildAuthorToDTO(author);
		return authorDTO;
	}
	
	public void addAuthorToNews(Long authorId, long newsId) throws DaoException {
		authorDao.addAuthorToNews(authorId, newsId);
	}
	
	public void deleteNewsAuthorXref(long newsId) throws DaoException {
		authorDao.deleteNewsAuthorXref(newsId);
	}
	
}
