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

import com.epam.newsportal.newsservice.entity.Comment;
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

@DatabaseSetup(value = "/CommentDaoTest.xml", type = DatabaseOperation.INSERT)
@DatabaseTearDown(value = "/CommentDaoTest.xml", type = DatabaseOperation.DELETE_ALL)
public class CommentDaoTest {
	
	@Autowired
	private CommentDao commentDao;
	
	@Test
	public void getByIdTest() throws Exception {
		Comment comment = commentDao.getById(5L);
		Assert.assertEquals(5, comment.getCommentId());
	}
	
	@Test
	public void insertTest() throws Exception {
		Comment comment = commentDao.getById(1L);
		comment.setCommentText("Inserted");
		long id = commentDao.insert(comment);
		comment = commentDao.getById(id);
		Assert.assertEquals("Inserted", comment.getCommentText());
	}
	
	@Test
	public void updateTest() throws Exception {
		Comment comment = commentDao.getById(1L);
		comment.setCommentText("Updated");
		commentDao.update(comment);
		comment = commentDao.getById(1L);
		Assert.assertEquals("Updated", comment.getCommentText());
	}
	
	@Test
	public void deleteTest() throws Exception {
		commentDao.delete(5L);
		Assert.assertNull(commentDao.getById(5L));
	}
	
	@Test
	public void getCommentByNewsTest() throws Exception {
		List<Comment> comments = commentDao.getCommentByNews(2L);
		Assert.assertEquals(3, comments.size());
	}
	
	@Test
	public void deleteCommentByNewsTest() throws Exception {
		commentDao.deleteCommentByNews(2L);
		Assert.assertNull(commentDao.getById(3L));
	}
}
