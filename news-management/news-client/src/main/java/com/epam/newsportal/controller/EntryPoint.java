//package com.epam.newsportal.controller;
//
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import com.epam.newsportal.newsservice.controller.SearchCriteria;
//import com.epam.newsportal.newsservice.service.INewsManager;
//
//public class EntryPoint {
//	
//	public static void main (String[] args) throws Exception {
//		SearchCriteria criteria = new SearchCriteria();
//		ApplicationContext context =  new ClassPathXmlApplicationContext("classpath:applicationContext.xml"); 
//		INewsManager manager = (INewsManager)context.getBean("newsManager");
//		manager.countAllNews();
//		System.out.println();
//	}
//}
