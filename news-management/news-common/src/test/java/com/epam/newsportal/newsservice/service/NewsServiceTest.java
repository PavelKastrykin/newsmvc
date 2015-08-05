package com.epam.newsportal.newsservice.service;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.epam.newsportal.newsservice.controller.SearchCriteria;
import com.epam.newsportal.newsservice.dao.INewsDao;
import com.epam.newsportal.newsservice.entity.News;
import com.epam.newsportal.newsservice.entity.dto.NewsDTO;

public class NewsServiceTest {

	@Mock
	private INewsDao newsDao;

	@Mock
	private News news;
	
	@Mock
	private NewsDTO newsDTO;
	
	@Mock
	private SearchCriteria criteria;

	private NewsService newsService;
	
	private List<NewsDTO> newsDTOlist;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);

		newsService = new NewsService();
		ReflectionTestUtils.setField(newsService, "newsDao", newsDao);

		when(newsDao.getById(anyLong())).thenReturn(news);
		
		newsDTOlist = new ArrayList<>();
		newsDTOlist.add(newsDTO);
		newsDTOlist.add(newsDTO);
		newsDTOlist.add(newsDTO);
		newsDTOlist.add(newsDTO);
		newsDTOlist.add(newsDTO);
	}

	@Test
	public void testAllNoExceptions() throws Exception {
		newsService.getNewsById(anyLong());
		newsService.insertNews(newsDTO);
		newsService.updateNews(newsDTO);
		newsService.deleteNews(anyLong());
		newsService.getNewsByAuthor(anyLong());
		newsService.getNewsCount();
		newsService.getNewsSearchResult(criteria);
	}

}
