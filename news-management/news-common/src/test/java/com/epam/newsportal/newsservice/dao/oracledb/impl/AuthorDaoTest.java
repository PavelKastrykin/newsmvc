package com.epam.newsportal.newsservice.dao.oracledb.impl;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.epam.newsportal.newsservice.entity.Author;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/testConfiguration.xml"})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })

@DatabaseSetup(value = "/AuthorDaoTest.xml", type = DatabaseOperation.INSERT)
@DatabaseTearDown(value = "/AuthorDaoTest.xml", type = DatabaseOperation.DELETE_ALL)
public class AuthorDaoTest {
	
	@Autowired
	private AuthorDao authorDao;
	
	@Test
	public void insertTest() throws Exception {
		
		Author author = new Author();
		author.setAuthorName("Author Six");
		long id = authorDao.insert(author);
	    Assert.assertNotNull(authorDao.getById(id));
	    Assert.assertNull(authorDao.getById(id).getExpired());
	}
	
	@Test
	public void getListTest() throws Exception {
		
		List<Author> authors = authorDao.getList();
		Assert.assertEquals(5, authors.size());
	}
	
	@Test
	public void getByIdTest() throws Exception {
		Author author = authorDao.getById(1L);
		Assert.assertEquals("First Author", author.getAuthorName());
		
	}
	
	@Test
	public void updateTest() throws Exception {
		Author author = new Author();
		author.setAuthorId(1L);
		author.setAuthorName("Updated name");
		authorDao.update(author);
		Assert.assertEquals("Updated name", authorDao.getById(1L).getAuthorName());
	}
	
	@Test
	public void deleteTest() throws Exception {
		authorDao.delete(5L);
		Assert.assertNotNull(authorDao.getById(5L).getExpired());
	}
	
	@Test
	public void getAuthorByNewsTest() throws Exception {
		Author author = authorDao.getByNewsId(1L);
		Assert.assertEquals(author.getAuthorName(), "First Author");
	}
	
	@Test
	public void addAuthorToNewsTest() throws Exception {
		authorDao.addAuthorToNews(1L, 2L);
		Assert.assertEquals(authorDao.getByNewsId(2L).getAuthorId(), 1L);
	}
}
