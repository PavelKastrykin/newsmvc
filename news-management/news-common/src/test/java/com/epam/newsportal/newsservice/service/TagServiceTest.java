package com.epam.newsportal.newsservice.service;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.epam.newsportal.newsservice.dao.ITagDao;
import com.epam.newsportal.newsservice.entity.Tag;
import com.epam.newsportal.newsservice.entity.dto.TagDTO;

public class TagServiceTest {

	@Mock
	private ITagDao tagDao;

	@Mock
	private ResultSet resultSet;
	
	private TagService tagService;

	@Mock
	private Tag tag;
	
	@Mock
	private TagDTO tagDTO;
	
	private List<TagDTO> tagDTOlist;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		tagService = new TagService();
		ReflectionTestUtils.setField(tagService, "tagDao", tagDao);
		
		when(tagDao.getById(anyLong())).thenReturn(tag);
		
		tagDTOlist = new ArrayList<>();
		tagDTOlist.add(tagDTO);
		tagDTOlist.add(tagDTO);
		tagDTOlist.add(tagDTO);
		tagDTOlist.add(tagDTO);
		tagDTOlist.add(tagDTO);
		
	}
	
	@Test
	public void testAllNoExceptions() throws Exception {
		tagService.getTagList();
		tagService.getTagById(anyLong());
		tagService.insertTag(tagDTO);
		tagService.updateTag(tagDTO);
		tagService.deleteTag(anyLong());
		tagService.getTagByNews(anyLong());
		tagService.insertTagListForNews(tagDTOlist, 1L);
		tagService.deleteTagXref(anyLong(), anyLong());
		tagService.deleteNewsTagXref(anyLong());
	}
	
	public void T() throws Exception {
		tagService.getTagList();
		verify(tagDao, times(1)).getList();
	}
	
	public void getTagByIdTest() throws Exception {
		tagService.getTagById(anyLong());
		verify(tagDao, times(1)).getById(anyLong());
	}
	
	public void inserTagTest() throws Exception {
		tagService.insertTag(tagDTO);
		verify(tagDao, times(1)).insert((Tag)anyObject());
	}
	
	public void updateTagTest() throws Exception {
		tagService.updateTag(tagDTO);
		verify(tagDao, times(1)).update((Tag)anyObject());
	}
	
	public void getTagByNewsTest() throws Exception {
		tagService.getTagByNews(anyLong());
		verify(tagDao, times(1)).getTagByNews(anyLong());
	}
	
	public void deleteTagTest() throws Exception {
		tagService.deleteTag(anyLong());
		verify(tagDao, times(1)).delete(anyLong());
	}
	
	public void inserTagListForNewsTest() throws Exception {
		tagService.insertTagListForNews(tagDTOlist, 1L);
		verify(tagDao, times(1)).insertTagListForNews((ArrayList<Tag>)anyObject(), anyLong());
	}

}
