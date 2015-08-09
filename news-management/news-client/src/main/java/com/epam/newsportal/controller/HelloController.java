package com.epam.newsportal.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.epam.newsportal.newsservice.service.INewsManager;

@Controller
public class HelloController {

	@Inject
	private INewsManager newsManager;
	
	@RequestMapping(value = { "/"}, method = RequestMethod.GET)
	public ModelAndView welcomePage() throws Exception {

		ModelAndView model = new ModelAndView();
		model.addObject("newsListShort", newsManager.viewListNews(0, 0));
		model.setViewName("newsIndex");		
		return model;
	}
	
	@RequestMapping(value = { "/news**" }, method = RequestMethod.GET)
	public ModelAndView anotherWelcomePage(@RequestParam("id") long id) throws Exception {

		ModelAndView model = new ModelAndView();
		model.addObject("singleNews", newsManager.viewSingleNews(id));
		model.setViewName("singleNews");
		return model;
	}
//	
//	@RequestMapping(value = "/back", method = RequestMethod.POST)
//	public ModelAndView rateHandler(HttpServletRequest request) {
//		ModelAndView model = new ModelAndView();
//		String referer = request.getHeader("Referer");
//		model.setViewName("redirect:"+ referer);
//	    return model;
//	}
	
}