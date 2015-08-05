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

import com.epam.newsportal.newsservice.entity.Tag;
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

@DatabaseSetup(value = "/TagDaoTest.xml", type = DatabaseOperation.INSERT)
@DatabaseTearDown(value = "/TagDaoTest.xml", type = DatabaseOperation.DELETE_ALL)
public class TagDaoTest {
	
	@Autowired
	private TagDao tagDao;
	
	@Test
	public void getListTest() throws Exception {
		List<Tag> tags = tagDao.getList(); 
		Assert.assertEquals(5, tags.size());
	}
	
	@Test
	public void getByIdTest() throws Exception {
		Tag tag = tagDao.getById(3);
		Assert.assertEquals(tag.getTagName(), "Tag three");
	}
	
	@Test
	public void insertTest() throws Exception {
		Tag tag = tagDao.getById(2L);
		tag.setTagName("Inserted");
		long tagId = tagDao.insert(tag);
		Assert.assertEquals(tagDao.getById(tagId).getTagName(), "Inserted");
	}
	
	@Test
	public void updateTest() throws Exception {
		Tag tag = tagDao.getById(3L);
		tag.setTagName("Updated");
		tagDao.update(tag);
		Assert.assertEquals(tagDao.getById(3L).getTagName(), "Updated");
	}
	
	@Test
	public void deleteTest() throws Exception {
		tagDao.delete(5L);
		Assert.assertNull(tagDao.getById(5L));
	}
	
	@Test
	
	public void getTagByNewsTest() throws Exception {
		List<Tag> tags = tagDao.getTagByNews(1L);
		Assert.assertEquals(1, tags.size());
	}
}
