package com.epam.newsportal.controller.form;

import org.springframework.web.servlet.mvc.SimpleFormController;

import com.epam.newsportal.newsservice.entity.dto.AuthorDTO;

public class DropdownBoxController extends SimpleFormController {
	
	public DropdownBoxController() {
		setCommandClass(AuthorDTO.class);
		
	}
}
