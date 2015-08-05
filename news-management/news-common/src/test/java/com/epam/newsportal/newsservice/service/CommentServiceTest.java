package com.epam.newsportal.newsservice.service;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.epam.newsportal.newsservice.dao.ICommentDao;
import com.epam.newsportal.newsservice.entity.Comment;
import com.epam.newsportal.newsservice.entity.dto.CommentDTO;

public class CommentServiceTest {
	
	@Mock
	private ICommentDao commentDao;
	
	private CommentService commentService;
	
	@Mock
	private Comment comment;
	
	@Mock
	private CommentDTO commentDTO;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		commentService = new CommentService();
		ReflectionTestUtils.setField(commentService, "commentDao", commentDao);
		
		when(commentDao.getById(anyLong())).thenReturn(comment);
	}
	
	@Test
	public void testAllNoExceptions() throws Exception {
		commentService.getCommentById(1L);
		commentService.insertComment(commentDTO);
		commentService.updateComment(commentDTO);
		commentService.deleteComment(1L);
		commentService.getCommentByNews (1L);
		commentService.deleteCommentByNews(1L);
	}
	
	
	@Test
	public void getCommentByIdTest() throws Exception {
		commentService.getCommentById(anyLong());
		verify(commentDao, times(1)).getById(anyLong());
	}
	
	@Test
	public void insertCommentTest() throws Exception {
		commentService.insertComment(commentDTO);
		verify(commentDao, times(1)).insert((Comment)anyObject());
	}
	
	@Test
	public void updateCommentTest() throws Exception {
		commentService.updateComment(commentDTO);
		verify(commentDao, times(1)).update((Comment)anyObject());
	}
	
	@Test
	public void deleteCommentTest() throws Exception {
		commentService.deleteComment(anyLong());
		verify(commentDao, times(1)).delete(anyLong());
	}
	
	@Test
	public void getCommentByNewsTest() throws Exception {
		commentService.getCommentByNews(anyLong());
		verify(commentDao, times(1)).getCommentByNews(anyLong());
	}
	
	@Test
	public void deleteCommentByNewsTest() throws Exception {
		commentService.deleteCommentByNews(anyLong());
		verify(commentDao, times(1)).deleteCommentByNews(anyLong());
	}
}
