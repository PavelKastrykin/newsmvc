package com.epam.newsportal.newsservice.entity.dto;

import java.sql.Timestamp;

import com.epam.newsportal.newsservice.entity.Comment;

public class CommentDTO {

	private long commentId;
	private long newsId;
	private String commentText;
	private Timestamp creationDate;

	public long getCommentId() {
		return commentId;
	}

	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}

	public long getNewsId() {
		return newsId;
	}

	public void setNewsId(long newsId) {
		this.newsId = newsId;
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

	public Comment buildDTOtoComment() {
		Comment comment = new Comment();
		comment.setCommentId(this.getCommentId());
		comment.setNewsId(this.getNewsId());
		comment.setCommentText(this.getCommentText());
		comment.setCreationDate(this.getCreationDate());
		return comment;
	}

	public void buildCommentToDTO(Comment comment) {
		this.setCommentId(comment.getCommentId());
		this.setNewsId(comment.getNewsId());
		this.setCommentText(comment.getCommentText());
		this.setCreationDate(comment.getCreationDate());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (commentId ^ (commentId >>> 32));
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
		if (commentId != other.commentId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CommentDto [commentId=" + commentId + ", newsId=" + newsId + ", commentText=" + commentText
				+ ", creationDate=" + creationDate + "]";
	}
}
