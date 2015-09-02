package com.epam.newsportal.newsservice.entity.dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import com.epam.newsportal.newsservice.entity.News;

public class NewsDTO implements Comparable<NewsDTO>{
	
	private long newsId;
	
	@Size(min=3, max=30)
	private String title;
	
	@Size(min=3, max=100)
	private String shortText;
	
	@Size(min=3, max=2000)
	private String fullText;
	private Timestamp creationDate;
	private Date modificationDate;
	private AuthorDTO author;
	private List<CommentDTO> comments = new ArrayList<>();
	private List<TagDTO> tags = new ArrayList<>();
	private List<Long> tagIdList = new ArrayList<>();
	
	public long getNewsId() {return newsId;}
	public void setNewsId(long newsId) {this.newsId = newsId;}
	
	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title;}
	
	public String getShortText() {return shortText;}
	public void setShortText(String shortText) {this.shortText = shortText;}
	
	public String getFullText() {return fullText;}
	public void setFullText(String fullText) {this.fullText = fullText;}
	
	public Timestamp getCreationDate() {return creationDate;}
	public void setCreationDate(Timestamp creationDate) {this.creationDate = creationDate;}
	
	public Date getModificationDate() {return modificationDate;}
	public void setModificationDate(Date modificationDate) {this.modificationDate = modificationDate;}
	
	public AuthorDTO getAuthor() {return author;}
	public void setAuthor(AuthorDTO author) {this.author = author;}
	
	public List<CommentDTO> getComments() {return comments;}
	public void setComments(List<CommentDTO> comments) {this.comments = comments;}
	
	public List<TagDTO> getTags() {return tags;}
	public void setTags(List<TagDTO> tags) {this.tags = tags;}
	
	public List<Long> getTagIdList() {
		return tagIdList;
	}
	public void setTagIdList(List<Long> tagIdList) {
		this.tagIdList = tagIdList;
	}
	
	public News buildDTOtoNews(){
		News news = new News();
		news.setNewsId(this.getNewsId());
		news.setTitle(this.getTitle());
		news.setShortText(this.getShortText());
		news.setFullText(this.getFullText());
		news.setCreationDate(this.getCreationDate());
		news.setModificationDate(this.getModificationDate());
		return news;
	}
	
	public void buildNewsToDTO(News news){
		if (news != null) {
			this.setNewsId(news.getNewsId());
			this.setTitle(news.getTitle());
			this.setShortText(news.getShortText());
			this.setFullText(news.getFullText());
			this.setCreationDate(news.getCreationDate());
			this.setModificationDate(news.getModificationDate());
		}
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
		NewsDTO other = (NewsDTO) obj;
		if (newsId != other.newsId)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "NewsDTO [newsId=" + newsId + ", title=" + title + ", shortText=" + shortText + ", fullText=" + fullText
				+ ", creationDate=" + creationDate + ", modificationDate=" + modificationDate + ", author=" + author
				+ ", comments=" + comments + ", tags=" + tags + "]";
	}
	
	@Override
	public int compareTo(NewsDTO newsDTO) {
		if (this.comments.size() < newsDTO.getComments().size()){
			return -1;
		}
		else if (this.comments.size() > newsDTO.getComments().size()){
			return 1;
		}
		return 0;
	}
}
