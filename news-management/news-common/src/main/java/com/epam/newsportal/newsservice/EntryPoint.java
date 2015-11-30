package com.epam.newsportal.newsservice;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.epam.newsportal.newsservice.controller.SearchCriteria;
import com.epam.newsportal.newsservice.entity.dto.NewsDTO;
import com.epam.newsportal.newsservice.service.AuthorService;
import com.epam.newsportal.newsservice.service.CommentService;
import com.epam.newsportal.newsservice.service.NewsService;
import com.epam.newsportal.newsservice.service.TagService;

class EntryPoint {
	
	public static void main(String args[]) throws Exception {
		ApplicationContext context =  new ClassPathXmlApplicationContext("classpath:applicationContext.xml"); 
		NewsService newsService  = (NewsService)context.getBean("newsService");
		AuthorService authorService = (AuthorService)context.getBean("authorService");
		CommentService commentService = (CommentService)context.getBean("commentService");
		TagService tagService = (TagService)context.getBean("tagService");
		
		SearchCriteria criteria = new SearchCriteria();
		List<NewsDTO> news = newsService.getNewsSearchResult(criteria);
		System.out.println();
	}
}
