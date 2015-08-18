package com.epam.newsportal.newsservice.entity.dto;

import com.epam.newsportal.newsservice.entity.Tag;

public class TagDTO {

	private long tagId;
	private String tagName;

	public long getTagId() {
		return tagId;
	}

	public void setTagId(long tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Tag buildDTOtoTag() {
		Tag tag = new Tag();
		tag.setTagId(this.getTagId());
		tag.setTagName(this.getTagName());
		return tag;
	}

	public void buildTagToDTO(Tag tag) {
		if (tag != null) {
			this.setTagId(tag.getTagId());
			this.setTagName(tag.getTagName());
		}
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (tagId ^ (tagId >>> 32));
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
		if (tagId != other.tagId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TagDto [tagId=" + tagId + ", tagName=" + tagName + "]";
	}

}
