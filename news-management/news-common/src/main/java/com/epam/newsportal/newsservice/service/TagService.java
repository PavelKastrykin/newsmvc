package com.epam.newsportal.newsservice.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.epam.newsportal.newsservice.dao.ITagDao;
import com.epam.newsportal.newsservice.entity.Tag;
import com.epam.newsportal.newsservice.entity.dto.TagDTO;
import com.epam.newsportal.newsservice.exception.DaoException;

public class TagService {

	public static final Logger logger = Logger.getLogger(TagService.class);

	private ITagDao tagDao;

	public void setTagDao(ITagDao tagDao) {
		this.tagDao = tagDao;
	}

	public List<TagDTO> getTagList() throws DaoException {
		List<Tag> tagList = tagDao.getList();
		return buildTagList(tagList);
	}
	
	public TagDTO getTagById(long tagId) throws DaoException {
		Tag tag = tagDao.getById(tagId);
		TagDTO tagDTO = new TagDTO();
		tagDTO.buildTagToDTO(tag);
		return tagDTO;
	}
	
	public long insertTag(TagDTO tagDTO) throws DaoException {
		Tag tag = tagDTO.buildDTOtoTag();
		return tagDao.insert(tag);
	}
	
	public void updateTag(TagDTO tagDTO) throws DaoException {
		Tag tag = tagDTO.buildDTOtoTag();
		tagDao.update(tag);
	}
	
	public void deleteTag(long tagId) throws DaoException {
		tagDao.deleteTagXrefOnTagDelete(tagId);
		tagDao.delete(tagId);
	}
	
	public List<TagDTO> getTagByNews(long newsId) throws DaoException {
		List<Tag> tagList = tagDao.getTagByNews(newsId);
		return buildTagList(tagList);
	}
	
	public void insertTagListForNews(List<Long> tagIdList, long newsId) throws DaoException {
		tagDao.insertTagListForNews(tagIdList, newsId);
	}
	
	public void deleteTagXref(long newsId, long tagId) throws DaoException {
		tagDao.deleteTagXref(newsId, tagId);
	}
	
	public void deleteNewsTagXref(long newsId) throws DaoException {
		tagDao.deleteNewsTagXref(newsId);
	}
	
	private List<TagDTO> buildTagList(List<Tag> tagList) {
		List<TagDTO> tagDTOlist = new ArrayList<>();
		if (!tagList.isEmpty()) {
			for(Tag tag : tagList){
				TagDTO tagDTO = new TagDTO();
				tagDTO.buildTagToDTO(tag);
				tagDTOlist.add(tagDTO);
			}
		}
		return tagDTOlist;
	}
}
