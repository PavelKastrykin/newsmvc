package com.epam.newsportal;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.epam.newsportal.newsservice.service.INewsManager;

@Controller
public class HelloController {

	private INewsManager newsManager;
	
	@Inject
	public HelloController(INewsManager newsManager) {
		this.newsManager = newsManager;
	}
	
	@RequestMapping(value = { "/", "/welcome**" }, method = RequestMethod.GET)
	public ModelAndView welcomePage() throws Exception {

		ModelAndView model = new ModelAndView();
		model.addObject("newsListShort", ""/*newsManager.viewListNews(0, 0)*/ );
		model.setViewName("hello");
		return model;

	}
	
	@RequestMapping(value = { "/example"}, method = RequestMethod.GET)
	public ModelAndView anotherWelcomePage() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "Another Spring Security Hello World");
		model.addObject("message", "Another This is welcome page!");
		model.setViewName("another");
		return model;

	}

}