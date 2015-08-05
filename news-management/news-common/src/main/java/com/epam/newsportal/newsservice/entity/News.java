package com.epam.newsportal.newsservice.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class News {

	private long newsId;
	private String title;
	private String shortText;
	private String fullText;
	private Timestamp creationDate;
	private Date modificationDate;

	public long getNewsId() {
		return newsId;
	}

	public void setNewsId(long newsId) {
		this.newsId = newsId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShortText() {
		return shortText;
	}

	public void setShortText(String shortText) {
		this.shortText = shortText;
	}

	public String getFullText() {
		return fullText;
	}

	public void setFullText(String fullText) {
		this.fullText = fullText;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (newsId ^ (newsId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		News other = (News) obj;
		if (newsId != other.newsId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "News [newsId=" + newsId + ", title=" + title + ", shortText=" + shortText + ", fullText=" + fullText
				+ ", creationDate=" + creationDate + ", modificationDate=" + modificationDate + "]";
	}
}
