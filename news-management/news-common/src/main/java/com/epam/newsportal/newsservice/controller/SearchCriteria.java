package com.epam.newsportal.newsservice.controller;

import java.util.List;

public class SearchCriteria {
	
	private long authorId;
	private List<Long> tagIdList;
	private int searchSize;
	private int newsCount;
	private int startWith;
	private long currentNewsId;
	private long prevNewsId;
	private long nextNewsId;

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
	public int getSearchSize() {
		return searchSize;
	}
	public void setSearchSize(int searchSize) {
		this.searchSize = searchSize;
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
	public long getCurrentNewsId() {
		return currentNewsId;
	}
	public void setCurrentNewsId(long currentNewsId) {
		this.currentNewsId = currentNewsId;
	}
	public long getPrevNewsId() {
		return prevNewsId;
	}
	public void setPrevNewsId(long prevNewsId) {
		this.prevNewsId = prevNewsId;
	}
	public long getNextNewsId() {
		return nextNewsId;
	}
	public void setNextNewsId(long nextNewsId) {
		this.nextNewsId = nextNewsId;
	}
}
