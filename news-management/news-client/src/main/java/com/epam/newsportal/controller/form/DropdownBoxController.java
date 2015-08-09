//package com.epam.newsportal.controller.form;
//
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//import javax.inject.Inject;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.validation.BindException;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.SimpleFormController;
//
//import com.epam.newsportal.newsservice.controller.SearchCriteria;
//import com.epam.newsportal.newsservice.entity.dto.AuthorDTO;
//import com.epam.newsportal.newsservice.entity.dto.TagDTO;
//import com.epam.newsportal.newsservice.service.AuthorService;
//import com.epam.newsportal.newsservice.service.TagService;
//
//public class DropdownBoxController extends SimpleFormController {
//	
//	@Inject
//	private AuthorService authorService;
//	
//	@Inject
//	private TagService tagService;
//	
//	public DropdownBoxController() {
//		setCommandClass(SearchCriteria.class);
//		setCommandName("searchForm");
//	}
//	
//	@Override
//	protected Object formBackingObject(HttpServletRequest request) throws Exception {
//		SearchCriteria criteria = new SearchCriteria();
//		return criteria;
//	}
//	
//	@Override
//	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) 
//			throws Exception {
//		
//		SearchCriteria criteria = (SearchCriteria)command;
//		return new ModelAndView("newsIndex", "criteria", criteria);
//	}
//	
//	
//	
//	protected Map referenceData(HttpServletRequest request) throws Exception {
//		
//		Map referenceData = new HashMap<>();
//		Map<Long, String> authorMap = new LinkedHashMap<>();
//		for(AuthorDTO author : authorService.getAuthorList()){
//			authorMap.put(author.getAuthorId(), author.getAuthorName());
//		}
//		referenceData.put("authorList", authorMap);
//		
//		Map<Long, String> tagMap = new LinkedHashMap<>();
//		for(TagDTO tag : tagService.getTagList()){
//			tagMap.put(tag.getTagId(), tag.getTagName());
//		}
//		referenceData.put("tagList", tagMap);
//		return referenceData;
//	}
//
//}
