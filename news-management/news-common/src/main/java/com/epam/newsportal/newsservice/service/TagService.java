package com.epam.newsportal.newsservice.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.newsportal.newsservice.dao.ITagDao;
import com.epam.newsportal.newsservice.entity.dto.TagDTO;
import com.epam.newsportal.newsservice.exception.DaoException;

@Service
@Transactional
public class TagService {

	public static final Logger logger = Logger.getLogger(TagService.class);

	@Autowired
	@Qualifier("tagDao")
	private ITagDao tagDao;

	public void setTagDao(ITagDao tagDao) {
		this.tagDao = tagDao;
	}

	public List<TagDTO> getTagList() throws DaoException {
		return tagDao.getList();
	}

	public Long insertTag(TagDTO tagDTO) throws DaoException {
		return tagDao.insert(tagDTO);
	}
	
	public void updateTag(TagDTO tagDTO) throws DaoException {
		tagDao.update(tagDTO);
	}
	
	public void deleteTag(long tagId) throws DaoException {
		tagDao.delete(tagId);
	}
}
