package com.epam.newsportal.newsservice.entity.dto;

import java.io.Serializable;
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
import javax.validation.constraints.Size;

@Entity
@Table(name="TAGS")
public class TagDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TAG_ID_GENERATOR")
	@SequenceGenerator(name = "TAG_ID_GENERATOR", sequenceName = "tags_seq")
	@Column(name = "TAG_ID")
	private Long tagId;
	
	@Size(min=3, max=30)
	@Column(name = "TAG_NAME")
	private String tagName;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "NEWS_TAGS", joinColumns = @JoinColumn(name = "TAG_ID"), 
			inverseJoinColumns = @JoinColumn(name = "NEWS_ID"))
	private Set<NewsDTO> news = new HashSet<>();
	
	public Long getTagId() {
		return tagId;
	}
	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
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
		result = prime * result + ((tagId == null) ? 0 : tagId.hashCode());
		result = prime * result + ((tagName == null) ? 0 : tagName.hashCode());
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
		TagDTO other = (TagDTO) obj;
		if (tagId == null) {
			if (other.tagId != null)
				return false;
		} else if (!tagId.equals(other.tagId))
			return false;
		if (tagName == null) {
			if (other.tagName != null)
				return false;
		} else if (!tagName.equals(other.tagName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "TagDTO [tagId=" + tagId + ", tagName=" + tagName + "]";
	}
}
