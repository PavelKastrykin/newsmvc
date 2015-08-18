package com.epam.newsportal.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.epam.newsportal.newsservice.controller.SearchCriteria;

public class TestValidator implements Validator {

	public boolean supports(Class<?> paramClass) {
        return SearchCriteria.class.equals(paramClass);
    }

	public void validate(Object obj, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "testStringName", "Some error message");
    }
}
