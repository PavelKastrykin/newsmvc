package com.epam.newsportal.newsservice.entity.dto;

import java.sql.Timestamp;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.epam.newsportal.newsservice.entity.Author;

public class AuthorDTO {

	@Min(1)
	private long authorId;
	
	@Size(min=3, max=30)
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

	public Author buildDTOtoAuthor() {
		Author author = new Author();
		author.setAuthorId(this.getAuthorId());
		author.setAuthorName(this.getAuthorName());
		author.setExpired(this.getExpired());
		return author;
	}

	public void buildAuthorToDTO(Author author) {
		if (author != null) {
			this.setAuthorId(author.getAuthorId());
			this.setAuthorName(author.getAuthorName());
			this.setExpired(author.getExpired());
		}
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
		AuthorDTO other = (AuthorDTO) obj;
		if (authorId != other.authorId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AuthorDto [authorId=" + authorId + ", authorName=" + authorName + ", expired=" + expired + "]";
	}
}
