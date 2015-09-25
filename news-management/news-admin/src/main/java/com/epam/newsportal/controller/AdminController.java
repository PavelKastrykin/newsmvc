package com.epam.newsportal.controller;

import java.sql.Timestamp;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import com.epam.newsportal.newsservice.entity.dto.NewsDTO;
import com.epam.newsportal.newsservice.entity.dto.TagDTO;
import com.epam.newsportal.newsservice.service.AuthorService;
import com.epam.newsportal.newsservice.service.CommentService;
import com.epam.newsportal.newsservice.service.NewsService;
import com.epam.newsportal.newsservice.service.TagService;

@Controller
@SessionAttributes("criteria")
public class AdminController {
	private static final String LOCK_ERROR_MESSAGE = "Error message";
	private static final int NEWS_QTY_DISPLAYED = 7;
	
	@Autowired
	@Qualifier("newsService")
	private NewsService newsService;
	
	@Autowired
	@Qualifier("authorService")
	private AuthorService authorService;
	
	@Autowired
	@Qualifier("tagService")
	private TagService tagService;
	
	@Autowired
	@Qualifier("commentService")
	private CommentService commentService;
	
	@RequestMapping(value = {"/", "/welcome**"}, method = RequestMethod.GET)
	public ModelAndView welcomePage(ModelAndView model) throws Exception {
		SearchCriteria criteria = new SearchCriteria();
		criteria.setNewsCount(NEWS_QTY_DISPLAYED);
		model.addObject("criteria", criteria);
		
		model.addObject("newsListShort", newsService.getNewsSearchResult(criteria)); 	
		model.addObject("authorList", authorService.getAuthorList());
		model.addObject("tagList", tagService.getTagList());
		model.setViewName("newsIndex");		
		return model;
	}
	
	@RequestMapping(value = "/view**", method = RequestMethod.GET)
	public ModelAndView viewFilteredList(ModelAndView model, @ModelAttribute("criteria") SearchCriteria criteria, 
			@RequestParam(value="page", required=false, defaultValue = "1") int pageNumber)	throws Exception {
		
		criteria.setStartWith((pageNumber - 1)* NEWS_QTY_DISPLAYED);
		criteria.setNewsCount(NEWS_QTY_DISPLAYED);
		model.addObject("criteria", criteria);
		
		model.addObject("newsListShort", newsService.getNewsSearchResult(criteria));
		model.addObject("authorList", authorService.getAuthorList());
		model.addObject("tagList", tagService.getTagList());		
		model.setViewName("newsIndex");		
		return model;
	}
	
	@RequestMapping(value = "/reset", method = RequestMethod.GET)
	public ModelAndView resetFilterForm(ModelAndView model, @ModelAttribute("criteria") SearchCriteria criteria)
			throws Exception {
		model.addObject("newsListShort", newsService.getNewsSearchResult(criteria));
		model.addObject("authorList", authorService.getAuthorList());
		model.addObject("tagList", tagService.getTagList());		
		criteria.setAuthorId(0L);
		criteria.setTagIdList(null);
		model.addObject("criteria", criteria);
		model.setViewName("newsIndex");		
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView filterNews(ModelAndView model, @ModelAttribute("criteria") SearchCriteria criteria) throws Exception {
		criteria.setStartWith(0);
		model.addObject("newsListShort", newsService.getNewsSearchResult(criteria));
		List<AuthorDTO> authors = authorService.getAuthorList();
		model.addObject("authorList", authors);
		List<TagDTO> tagList = tagService.getTagList();
		model.addObject("tagList", tagList);
		//model.addObject("criteria", criteria);
		model.setViewName("newsIndex");		

	    return model;
	}
	
	@RequestMapping(value = "/deleteNews", method = RequestMethod.POST)
	public RedirectView deleteNews(ModelAndView model, @ModelAttribute("criteria") SearchCriteria criteria) throws Exception {
		if(criteria.getDeleteNewsList() != null){
			for(Long newsId : criteria.getDeleteNewsList()){
				if(newsId != null){
					newsService.deleteNews(newsId);
				}
			}
		}
		return new RedirectView("/news-admin/welcome");
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(
		@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}
		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");
		return model;
	}
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

	  ModelAndView model = new ModelAndView();
		
	  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	  if (!(auth instanceof AnonymousAuthenticationToken)) {
		UserDetails userDetail = (UserDetails) auth.getPrincipal();	
		model.addObject("username", userDetail.getUsername());
	  }
		
	  model.setViewName("403");
	  return model;
	}
	
	@RequestMapping(value = "/addNews", method = RequestMethod.GET)
	public ModelAndView createNews(ModelAndView model, @Valid @ModelAttribute("newsDTO") NewsDTO newsDTO, 
			BindingResult bindingResult) throws Exception {
		if(bindingResult.hasErrors()){
			model.addObject("newsDTO", newsDTO);
		} else {
			model.addObject("newsDTO", new NewsDTO());
		}
		model.addObject("authorList", authorService.getAuthorList());
		model.addObject("tagList", tagService.getTagList());
		model.setViewName("addNews");
		return model;
	}
	
	@RequestMapping(value = "/newsAdded")
	public RedirectView addNews(ModelAndView model, @Valid @ModelAttribute("newsDTO") NewsDTO newsDTO, 
			BindingResult bindingResult, RedirectAttributes redirectAttributes) throws Exception {
		if(bindingResult.hasErrors()){
			redirectAttributes.addFlashAttribute("newsDTO", newsDTO);
			return new RedirectView("/news-admin/addNews");
		}
		long newsId = newsService.insertNews(newsDTO);
		return new RedirectView("/news-admin/view");
	}
	
	@RequestMapping(value = "/edit/{newsId}", method = RequestMethod.GET)
	public ModelAndView editNews(ModelAndView model, @PathVariable long newsId, 
			@Valid @ModelAttribute("newsDTO") NewsDTO newsDTO, BindingResult bindingResultNews, @Valid @ModelAttribute("commentDTO")
			CommentDTO commentDTO, BindingResult bindingResultComment, @ModelAttribute("lockError") String lockError) throws Exception {
		
		if(bindingResultNews.hasErrors() || bindingResultComment.hasErrors()){
			model.addObject("newsDTO", newsDTO);
			model.addObject("commentDTO", commentDTO);
		} else {
			if(newsDTO.getTitle() != null){
				model.addObject("newDTO", newsDTO);
			} else {
				model.addObject("newsDTO", newsService.getNewsById(newsId));
			}
			model.addObject("commentDTO", new CommentDTO());
		}
		
		model.addObject("authorList", authorService.getAuthorList());
		model.addObject("tagList", tagService.getTagList());
		model.addObject("lockError", lockError);
		model.setViewName("newsEdit");
		return model;
	}
	
	@RequestMapping(value = "/newsEdited", method = RequestMethod.POST)
	public RedirectView confirmNewsEdit(ModelAndView model, @Valid @ModelAttribute("newsDTO") NewsDTO newsDTO,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) throws Exception {
		if(bindingResult.hasErrors()){
			redirectAttributes.addFlashAttribute("newsDTO", newsDTO);
			redirectAttributes.addFlashAttribute("commentDTO", new CommentDTO());
			return new RedirectView("/news-admin/edit/" + newsDTO.getNewsId());
		}
		try{
			newsService.updateNews(newsDTO);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("newsDTO", newsDTO);
			redirectAttributes.addFlashAttribute("commentDTO", new CommentDTO());
			redirectAttributes.addFlashAttribute("lockError", LOCK_ERROR_MESSAGE);
			return new RedirectView("/news-admin/edit/" + newsDTO.getNewsId());
		} 
		return new RedirectView("/news-admin/edit/" + newsDTO.getNewsId());
		
	}
	
	@RequestMapping(value = {"/addComment"}, method = RequestMethod.POST)
	public RedirectView postComment(@Valid @ModelAttribute("commentDTO") CommentDTO commentDTO, 
			BindingResult bindingResult, @RequestParam("newsId") long newsId, 
			RedirectAttributes redirectAttributes) throws Exception {
		if (bindingResult.hasErrors()) {
		    redirectAttributes.addFlashAttribute("commentDTO", commentDTO);
		    redirectAttributes.addFlashAttribute("newsDTO", newsService.getNewsById(newsId));
			return new RedirectView("/news-admin/edit/" + String.valueOf(newsId));
		}
		commentDTO.setCreationDate(new Timestamp(new java.util.Date().getTime()));
		commentDTO.setNews(newsService.getNewsById(newsId));
		commentService.insertComment(commentDTO);
		return new RedirectView("/edit/" + String.valueOf(newsId), true);
	}
	
	@RequestMapping(value = "/addUpdateAuthors", method = RequestMethod.GET)
	public ModelAndView addUpdateAuthorsList(ModelAndView model, @Valid @ModelAttribute("authorDTO") AuthorDTO authorDTO,
			BindingResult bindingResult) throws Exception {
		if(bindingResult.hasErrors()){
			model.addObject("authorDTO", authorDTO);
		} else {
			model.addObject("authorDTO", new AuthorDTO());
		}
		model.addObject("authorList", authorService.getAuthorList());
		model.setViewName("addUpdateAuthors");
		return model;
	}
	
	@RequestMapping(value = "/addUpdateTags", method = RequestMethod.GET)
	public ModelAndView addUpdateTagsList(ModelAndView model, @Valid @ModelAttribute("tagDTO") TagDTO tagDTO, 
			BindingResult bindingResult) throws Exception {
		if(bindingResult.hasErrors()){
			model.addObject("tagDTO", tagDTO);
		} else {
			model.addObject("tagDTO", new TagDTO());
		}
		model.addObject("tagList", tagService.getTagList());
		model.setViewName("addUpdateTags");
		return model;
	}
	
	@RequestMapping(value = "/expire**", method = RequestMethod.GET)
	public ModelAndView expireAuthor(ModelAndView model, @RequestParam("authorId") long authorId) throws Exception {
		authorService.deleteAuthor(authorId);
		model.addObject("authorList", authorService.getAuthorList());
		model.addObject("authorDTO", new AuthorDTO());
		model.setViewName("addUpdateAuthors");
		return model;
	}
	
	@RequestMapping(value = "/authorUpdate", method = RequestMethod.POST)
	public RedirectView updateAuthor(ModelAndView model, @Valid @ModelAttribute("authorDTO") AuthorDTO authorDTO,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) throws Exception {
		if(bindingResult.hasErrors()){
			redirectAttributes.addFlashAttribute("authorDTO", authorDTO);
			return new RedirectView("/news-admin/addUpdateAuthors");
		}
		authorService.updateAuthor(authorDTO);
		return new RedirectView("/news-admin/addUpdateAuthors");
	}
	
	@RequestMapping(value = "/tagUpdate", method = RequestMethod.POST)
	public RedirectView updateTag(ModelAndView model, @Valid @ModelAttribute("tagDTO") TagDTO tagDTO, 
			BindingResult bindingResult, RedirectAttributes redirectAttributes) throws Exception {
		if(bindingResult.hasErrors()){
			redirectAttributes.addFlashAttribute("tagDTO", tagDTO);
			return new RedirectView("/news-admin/addUpdateTags");
		}
		tagService.updateTag(tagDTO);
		return new RedirectView("/news-admin/addUpdateTags");
	}
	
	@RequestMapping(value = "/authorAdd", method = RequestMethod.POST)
	public RedirectView addAuthor(ModelAndView model, @Valid @ModelAttribute("authorDTO") AuthorDTO authorDTO, 
			BindingResult bindingResult, RedirectAttributes redirectAttributes) throws Exception {
		if(bindingResult.hasErrors()){
			redirectAttributes.addFlashAttribute("authorDTO", authorDTO);
			return new RedirectView("/news-admin/addUpdateAuthors");
		}
		authorService.insertAuthor(authorDTO);
		return new RedirectView("/news-admin/addUpdateAuthors");
	}
	
	@RequestMapping(value = "/deleteTag**", method = RequestMethod.GET)
	public ModelAndView deleteTag(ModelAndView model, @RequestParam("tagId") long tagId) throws Exception {
		tagService.deleteTag(tagId);
		model.addObject("tagList", tagService.getTagList());
		model.addObject("tagDTO", new TagDTO());
		model.setViewName("addUpdateTags");
		return model;
	}
	
	@RequestMapping(value = "/tagAdd", method = RequestMethod.POST)
	public RedirectView addTag(ModelAndView model, @Valid @ModelAttribute("tagDTO") TagDTO tagDTO, 
			BindingResult bindingResult, RedirectAttributes redirectAttributes) throws Exception {
		if(bindingResult.hasErrors()){
			redirectAttributes.addFlashAttribute("tagDTO", tagDTO);
			return new RedirectView("/news-admin/addUpdateTags");
		}
		tagService.insertTag(tagDTO);
		return new RedirectView("/news-admin/addUpdateTags");
	}
	
	@RequestMapping(value = "/edit/{newsId}/deleteComment/{commentId}", method = RequestMethod.GET)
	public ModelAndView deleteComment(ModelAndView model, @PathVariable long newsId, @PathVariable long commentId) 
			throws Exception {
		commentService.deleteComment(commentId);
		model.addObject("newsDTO", newsService.getNewsById(newsId));
		model.addObject("authorList", authorService.getAuthorList());
		model.addObject("tagList", tagService.getTagList());
		model.addObject("commentDTO", new CommentDTO());
		model.setViewName("newsEdit");
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
