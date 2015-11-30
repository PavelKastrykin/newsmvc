package com.epam.newsportal.newsservice.dao;

import java.util.List;

import com.epam.newsportal.newsservice.entity.dto.AuthorDTO;
import com.epam.newsportal.newsservice.exception.DaoException;

public interface IAuthorDao extends IEntityDao<AuthorDTO> {

	List<AuthorDTO> getList() throws DaoException;
}
