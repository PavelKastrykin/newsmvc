package com.epam.newsportal.newsservice.entity.dto;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OrderBy;

@Entity
@Table(name="NEWS")
public class NewsDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NEWS_ID_GENERATOR")
	@SequenceGenerator(name = "NEWS_ID_GENERATOR", sequenceName = "news_seq")
	@Column(name = "NEWS_ID")
	private Long newsId;
	
	@Version
	@Column(name = "VERSION")
	private long version;
	
	@Size(min=3, max=30)
	@Column(name = "TITLE")
	private String title;
	
	@Size(min=3, max=100)
	@Column(name = "SHORT_TEXT")
	private String shortText;
	
	@Size(min=3, max=2000)
	@Column(name = "FULL_TEXT")
	private String fullText;
	
	@Column(name = "CREATION_DATE")
	private Timestamp creationDate;
	
	@Column(name = "MODIFICATION_DATE")
	private Date modificationDate;
	
	@Transient
	@Valid
	private AuthorDTO author = new AuthorDTO();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "NEWS_AUTHORS", joinColumns = @JoinColumn(name = "NEWS_ID"), 
		inverseJoinColumns = @JoinColumn(name = "AUTHOR_ID"))
	private Set<AuthorDTO> authors = new HashSet<>(0);
	
	@OneToMany(mappedBy = "news", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@OrderBy(clause = "commentId")
	private Set<CommentDTO> comments = new HashSet<>(0);
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "NEWS_TAGS", joinColumns = @JoinColumn(name = "NEWS_ID"), 
		inverseJoinColumns = @JoinColumn(name = "TAG_ID"))
	private Set<TagDTO> tags = new HashSet<>(0);
	
	@Transient
	private Set<Long> tagIdList = new HashSet<>(0);
	
	public Long getNewsId() {
		return newsId;
	}
	public void setNewsId(Long newsId) {
		this.newsId = newsId;
	}
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
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
	public AuthorDTO getAuthor() {
		return author;
	}
	public void setAuthor(AuthorDTO author) {
		this.author = author;
	}
	public Set<TagDTO> getTags() {
		return tags;
	}
	public void setTags(Set<TagDTO> tags) {
		this.tags = tags;
	}
	public Set<AuthorDTO> getAuthors() {
		return authors;
	}
	public void setAuthors(Set<AuthorDTO> authors) {
		this.authors = authors;
	}
	
	public Set<CommentDTO> getComments() {
		return comments;
	}
	public void setComments(Set<CommentDTO> comments) {
		this.comments = comments;
	}
	public Set<Long> getTagIdList() {
		return tagIdList;
	}
	public void setTagIdList(Set<Long> tagIdList) {
		this.tagIdList = tagIdList;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((fullText == null) ? 0 : fullText.hashCode());
		result = prime * result + ((modificationDate == null) ? 0 : modificationDate.hashCode());
		result = prime * result + ((newsId == null) ? 0 : newsId.hashCode());
		result = prime * result + ((shortText == null) ? 0 : shortText.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		NewsDTO other = (NewsDTO) obj;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (fullText == null) {
			if (other.fullText != null)
				return false;
		} else if (!fullText.equals(other.fullText))
			return false;
		if (modificationDate == null) {
			if (other.modificationDate != null)
				return false;
		} else if (!modificationDate.equals(other.modificationDate))
			return false;
		if (newsId == null) {
			if (other.newsId != null)
				return false;
		} else if (!newsId.equals(other.newsId))
			return false;
		if (shortText == null) {
			if (other.shortText != null)
				return false;
		} else if (!shortText.equals(other.shortText))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NewsDTO [newsId=" + newsId + ", title=" + title + ", shortText=" + shortText + ", fullText=" + fullText
				+ ", creationDate=" + creationDate + ", modificationDate=" + modificationDate + "]";
	}
}
