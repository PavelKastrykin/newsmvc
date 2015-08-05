package com.epam.newsportal.newsservice.dao.oracledb.impl;

import java.util.ArrayList;
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

import com.epam.newsportal.newsservice.controller.SearchCriteria;
import com.epam.newsportal.newsservice.entity.News;
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

@DatabaseSetup(value = "/NewsDaoTest.xml", type = DatabaseOperation.INSERT)
@DatabaseTearDown(value = "/NewsDaoTest.xml", type = DatabaseOperation.DELETE_ALL)
public class NewsDaoTest {
	
	@Autowired
	private NewsDao newsDao;
	
	@Test
	public void getByIdTest() throws Exception {
		News news = newsDao.getById(5L);
		Assert.assertEquals("Title five", news.getTitle());
	}
	
	@Test
	public void insertTest() throws Exception {
		News news = newsDao.getById(1L);
		news.setTitle("Inserted");
		long id = newsDao.insert(news);
		news = newsDao.getById(id);
		Assert.assertEquals(news.getTitle(), "Inserted");
	}
	
	@Test
	public void updateTest() throws Exception {
		News news = newsDao.getById(5L);
		news.setTitle("Updated");
		newsDao.update(news);
		news = newsDao.getById(5L);
		Assert.assertEquals("Updated", news.getTitle());
	}
	
	@Test
	public void deleteTest() throws Exception {
		newsDao.delete(4L);
		Assert.assertNull(newsDao.getById(4L));
	}
	
	@Test
	public void getNewsByAuthorTest() throws Exception {
		List<News> news = newsDao.getNewsByAuthor(1L);
		Assert.assertEquals(2, news.size());
	}
	
	@Test
	public void getNewsByTagTest() throws Exception {
		List<News> news = newsDao.getNewsByTag(1L);
		Assert.assertEquals(2, news.size());
	}
	
	@Test
	public void newsCountTest() throws Exception {
		long count = newsDao.newsCount();
		Assert.assertEquals(5L, count);
		News news = newsDao.getById(1L);
		newsDao.insert(news);
		count = newsDao.newsCount();
		Assert.assertEquals(6L, count);
	}
	
	@Test
	public void getSearchResultTest() throws Exception {
		SearchCriteria criteria = new SearchCriteria();
		criteria.setAuthorId(1L);
		List<News> newsList = new ArrayList<>();
		newsList = newsDao.getSearchResult(criteria);
		Assert.assertEquals(2, newsList.size());
		criteria.setAuthorId(0);
	}
}
