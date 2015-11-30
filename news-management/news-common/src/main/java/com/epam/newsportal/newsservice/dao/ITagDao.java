package com.epam.newsportal.newsservice.dao;

import java.util.List;

import com.epam.newsportal.newsservice.entity.dto.TagDTO;
import com.epam.newsportal.newsservice.exception.DaoException;

public interface ITagDao extends IEntityDao<TagDTO> {

	List<TagDTO> getList() throws DaoException; 
	
}
