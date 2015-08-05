package com.epam.newsportal.newsservice.dao;

import java.util.List;

import com.epam.newsportal.newsservice.entity.Tag;
import com.epam.newsportal.newsservice.exception.DaoException;

public interface ITagDao extends IEntityDao<Tag> {

	List<Tag> getTagByNews(long newsId) throws DaoException;

	List<Tag> getList() throws DaoException;
	
	void insertTagListForNews(List<Tag> tagList, long newsId) throws DaoException;
	
	void deleteTagXref(long newsId, long tagId) throws DaoException;
	
	void deleteNewsTagXref(long newsId) throws DaoException;
}
