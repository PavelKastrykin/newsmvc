package com.epam.newsportal.newsservice.entity.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="COMMENTS")
public class CommentDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMENT_ID_GENERATOR")
	@SequenceGenerator(name = "COMMENT_ID_GENERATOR", sequenceName = "comments_seq")
	@Column(name = "COMMENT_ID")
	private Long commentId;
	
	@Size(min=3, max=100)
	@Column(name = "COMMENT_TEXT")
	private String commentText;
	
	@Column(name = "CREATION_DATE")
	private Timestamp creationDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NEWS_ID", referencedColumnName = "NEWS_ID")
	private NewsDTO news;

	public Long getCommentId() {
		return commentId;
	}
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	public Timestamp getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}
	public NewsDTO getNews() {
		return news;
	}
	public void setNews(NewsDTO news) {
		this.news = news;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commentId == null) ? 0 : commentId.hashCode());
		result = prime * result + ((commentText == null) ? 0 : commentText.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
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
		CommentDTO other = (CommentDTO) obj;
		if (commentId == null) {
			if (other.commentId != null)
				return false;
		} else if (!commentId.equals(other.commentId))
			return false;
		if (commentText == null) {
			if (other.commentText != null)
				return false;
		} else if (!commentText.equals(other.commentText))
			return false;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CommentDTO [commentId=" + commentId + ", commentText=" + commentText + ", creationDate=" + creationDate
				+ "]";
	}
}
