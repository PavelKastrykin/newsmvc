package com.epam.newsportal.newsservice.controller;

import java.util.List;

public class SearchCriteria {
	
	private long authorId;
	private List<Long> tagIdList;
	private int newsCount;
	private int startWith;

	public long getAuthorId() {
		return authorId;
	}
	public void setAuthorId(long authorId) {
		this.authorId = authorId;
	}
	public List<Long> getTagIdList() {
		return tagIdList;
	}
	public void setTagIdList(List<Long> tagIdList) {
		this.tagIdList = tagIdList;
	}
	public int getNewsCount() {
		return newsCount;
	}
	public void setNewsCount(int newsCount) {
		this.newsCount = newsCount;
	}
	public int getStartWith() {
		return startWith;
	}
	public void setStartWith(int startWith) {
		this.startWith = startWith;
	}
	
	
	
}
