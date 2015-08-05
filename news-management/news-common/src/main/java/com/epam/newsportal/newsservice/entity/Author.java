package com.epam.newsportal.newsservice.entity;

import java.sql.Timestamp;

public class Author {

	private long authorId;
	private String authorName;
	private Timestamp expired;

	public long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(long authorId) {
		this.authorId = authorId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public Timestamp getExpired() {
		return expired;
	}

	public void setExpired(Timestamp expired) {
		this.expired = expired;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (authorId ^ (authorId >>> 32));
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
		Author other = (Author) obj;
		if (authorId != other.authorId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Author [authorId=" + authorId + ", authorName=" + authorName + ", expired=" + expired + "]";
	}
}
