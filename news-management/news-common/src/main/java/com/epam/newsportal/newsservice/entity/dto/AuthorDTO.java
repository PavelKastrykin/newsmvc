package com.epam.newsportal.newsservice.entity.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@Table(name="AUTHORS")
public class AuthorDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Min(1)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUTHOR_ID_GENERATOR")
	@SequenceGenerator(name = "AUTHOR_ID_GENERATOR", sequenceName = "authors_seq")
	@Column(name = "AUTHOR_ID")
	private Long authorId;
	
	@Size(min=3, max=30)
	@Column(name = "AUTHOR_NAME")
	private String authorName;
	
	@Column(name = "EXPIRED")
	private Timestamp expired;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "NEWS_AUTHORS", joinColumns = @JoinColumn(name = "AUTHOR_ID"), 
			inverseJoinColumns = @JoinColumn(name = "NEWS_ID"))
	private Set<NewsDTO> news = new HashSet<>();
	
	public AuthorDTO(){}
	
	public Long getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Long authorId) {
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
	public Set<NewsDTO> getNews() {
		return news;
	}
	public void setNews(Set<NewsDTO> news) {
		this.news = news;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authorId == null) ? 0 : authorId.hashCode());
		result = prime * result + ((authorName == null) ? 0 : authorName.hashCode());
		result = prime * result + ((expired == null) ? 0 : expired.hashCode());
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
		if (authorId == null) {
			if (other.authorId != null)
				return false;
		} else if (!authorId.equals(other.authorId))
			return false;
		if (authorName == null) {
			if (other.authorName != null)
				return false;
		} else if (!authorName.equals(other.authorName))
			return false;
		if (expired == null) {
			if (other.expired != null)
				return false;
		} else if (!expired.equals(other.expired))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "AuthorDTO [authorId=" + authorId + ", authorName=" + authorName + ", expired=" + expired + "]";
	}
}
