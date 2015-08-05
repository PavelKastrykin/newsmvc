package com.epam.newsportal.newsservice;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.epam.newsportal.newsservice.service.INewsManager;

public class EntryPoint {
	
	public static void main(String args[]) throws Exception {
		ApplicationContext context =  new ClassPathXmlApplicationContext("classpath:applicationContext.xml"); 
		INewsManager manager = (INewsManager)context.getBean("newsManager");
		manager.addNewsAuthor(8L, 3L);
	}
}
