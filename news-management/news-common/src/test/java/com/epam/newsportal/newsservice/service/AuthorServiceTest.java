package com.epam.newsportal.newsservice.service;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.epam.newsportal.newsservice.dao.IAuthorDao;
import com.epam.newsportal.newsservice.entity.Author;
import com.epam.newsportal.newsservice.entity.dto.AuthorDTO;

public class AuthorServiceTest {
	
	@Mock
	private IAuthorDao authorDao;
	
	@Mock
	private Connection connection;
	
	@Mock
	private DataSource dataSource;

	@Mock
	private ResultSet resultSet;
	
	@Mock
	private Author author;
	
	@Mock
	private List<Author> authorList;
	
	@Mock
	private AuthorDTO authorDTO;
	
	private AuthorService authorService;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	
		authorService = new AuthorService();
		ReflectionTestUtils.setField(authorService, "authorDao", authorDao);
		
		when(authorDao.getByNewsId(anyLong())).thenReturn(author);
		when(authorDao.getById(anyLong())).thenReturn(author);
	}
	
	@Test
	public void testAllNoExceptions() throws Exception {
		authorService.addAuthorToNews(1L, 1L);
		authorService.getAuthorByNews(1L);
		authorService.getAuthorList();
		authorService.getAuthorById(1L);
		authorService.insertAuthor(authorDTO);
		authorService.updateAuthor(authorDTO);
		authorService.deleteAuthor(1L);
		authorService.deleteNewsAuthorXref(1L);
	}
	
	@Test
	public void getAuthorListTest() throws Exception {
		when(authorDao.getList()).thenReturn(authorList);
		verify(authorDTO, times(authorList.size())).buildAuthorToDTO((Author)anyObject());
	}
	
	@Test
	public void getAuthorByNewsTest() throws Exception {	
		when(authorDao.getByNewsId(12L)).thenReturn(author);
		Assert.assertEquals(authorService.getAuthorByNews(12L).getAuthorName(), author.getAuthorName()); 
	}
	
	@Test
	public void getAuthorByIdTest() throws Exception {
		authorService.getAuthorById(1L);
		verify(authorDao, times(1)).getById(1L);
	}
	
	
	@Test
	public void insertAuthorTest() throws Exception {
		authorService.insertAuthor(authorDTO);
		verify(authorDao, times(1)).insert((Author)anyObject());
	}
	
	@Test
	public void updateAuthorTest() throws Exception {
		authorService.updateAuthor(authorDTO);
		verify(authorDao, times(1)).update((Author)anyObject());
	}
	
	@Test
	public void deleteAuthorTest() throws Exception {
		authorService.deleteAuthor(anyLong());
		verify(authorDao, times(1)).delete(anyLong());
	}
	
	@Test
	public void addAuthorToNewsTest() throws Exception {
		authorService.addAuthorToNews(anyLong(), anyLong());
		verify(authorDao, times(1)).addAuthorToNews(anyLong(), anyLong());
	}
	
}
