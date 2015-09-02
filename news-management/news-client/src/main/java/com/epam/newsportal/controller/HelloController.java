package com.epam.newsportal.controller;

import java.sql.Timestamp;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.epam.newsportal.newsservice.controller.SearchCriteria;
import com.epam.newsportal.newsservice.entity.dto.AuthorDTO;
import com.epam.newsportal.newsservice.entity.dto.CommentDTO;
import com.epam.newsportal.newsservice.entity.dto.TagDTO;
import com.epam.newsportal.newsservice.service.AuthorService;
import com.epam.newsportal.newsservice.service.INewsManager;
import com.epam.newsportal.newsservice.service.TagService;

@Controller
@SessionAttributes("criteria")
public class HelloController {
	
	private static final int NEWS_QTY_DISPLAYED = 7;
	@Inject 
	private INewsManager newsManager;
	
	@Inject
	private AuthorService authorService;
	
	@Inject
	private TagService tagService;
	
	@RequestMapping(value = { "/", "/view**"}, method = RequestMethod.GET)
	public ModelAndView welcomePage(ModelAndView model, @RequestParam(value="page", required=false, defaultValue = "1") int pageNumber) throws Exception {
		SearchCriteria criteria = new SearchCriteria();
		criteria.setNewsCount(NEWS_QTY_DISPLAYED);
		criteria.setStartWith((pageNumber - 1)* NEWS_QTY_DISPLAYED);
		model.addObject("criteria", criteria);
		
		model.addObject("newsListShort", newsManager.newsSearchResult(criteria));
		model.addObject("authorList", authorService.getAuthorList());
		model.addObject("tagList", tagService.getTagList());		
		model.setViewName("newsIndex");		
		return model;
	}
	
	@RequestMapping(value = { "/news/{newsId}" }, method = RequestMethod.GET)
	public ModelAndView singleNewsPage(ModelAndView model, @PathVariable long newsId, 
			@ModelAttribute("criteria") SearchCriteria criteria, 
			@Valid @ModelAttribute("commentDTO") CommentDTO commentDTO, BindingResult bindingResult) throws Exception {
		criteria.setCurrentNewsId(newsId);
		newsManager.newsSearchResult(criteria);
		
		model.addObject("singleNews", newsManager.viewSingleNews(newsId));		
		model.addObject("commentDTO", commentDTO);
		model.addObject("criteria", criteria);
		model.setViewName("singleNews");
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView filterNews(ModelAndView model, @ModelAttribute("criteria") SearchCriteria criteria) throws Exception {
		criteria.setStartWith(0);
		model.addObject("newsListShort", newsManager.newsSearchResult(criteria));
		List<AuthorDTO> authors = authorService.getAuthorList();
		model.addObject("authorList", authors);
		List<TagDTO> tagList = tagService.getTagList();
		model.addObject("tagList", tagList);
		//model.addObject("criteria", criteria);
		model.setViewName("newsIndex");		

	    return model;
	}
	
	@RequestMapping(value = "/reset", method = RequestMethod.GET)
	public ModelAndView resetFilterForm(ModelAndView model, @ModelAttribute("criteria") SearchCriteria criteria)
			throws Exception {
		model.addObject("newsListShort", newsManager.newsSearchResult(criteria));
		model.addObject("authorList", authorService.getAuthorList());
		model.addObject("tagList", tagService.getTagList());		
		criteria.setAuthorId(0);
		criteria.setTagIdList(null);
		model.addObject("criteria", criteria);
		model.setViewName("newsIndex");		
		return model;
	}
	
	@RequestMapping(value = {"/addComment"}, method = RequestMethod.POST)
	public RedirectView postComment(@Valid @ModelAttribute("commentDTO") CommentDTO commentDTO,	
			BindingResult bindingResult, @RequestParam("newsId") long newsId, 
			RedirectAttributes redirectAttributes) throws Exception {
		if (bindingResult.hasErrors()) {
		    redirectAttributes.addFlashAttribute("commentDTO", commentDTO);
			
			return new RedirectView("/news-client/news/" + String.valueOf(newsId));
		}
		commentDTO.setCreationDate(new Timestamp(new java.util.Date().getTime()));
		commentDTO.setNewsId(newsId);
		newsManager.addCommentForNews(commentDTO);
		return new RedirectView("/news/" + String.valueOf(newsId), true);
	}
	
	@RequestMapping(value={"/home"}, method = RequestMethod.GET)
	public RedirectView returnHome(@ModelAttribute("criteria") SearchCriteria criteria){
		criteria.setAuthorId(0L);
		criteria.setCurrentNewsId(0L);
		criteria.setTagIdList(null);
		criteria.setStartWith(0);
		return new RedirectView("/news-client");
	}
	
	@RequestMapping(value={"/back"}, method = RequestMethod.GET)
	public ModelAndView returnBack(ModelAndView model, @ModelAttribute("criteria") SearchCriteria criteria) throws Exception{
		
		model.addObject("criteria", criteria);
		model.addObject("newsListShort", newsManager.newsSearchResult(criteria));
		model.addObject("authorList", authorService.getAuthorList());
		model.addObject("tagList", tagService.getTagList());		
		model.setViewName("newsIndex");		
		return model;
	}
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleServiseException(Exception ex) {

		ModelAndView model = new ModelAndView();
		model.addObject("exception", ex);
		model.setViewName("errorPage");
		return model;
	}
}