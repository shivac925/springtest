package com.spring.model;

public class BeanCopyModel {
	
	private String key1;
	
	private String key2;
	
	private String description;
	
	private String comments;

	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
	}

	public String getKey2() {
		return key2;
	}

	public void setKey2(String key2) {
		this.key2 = key2;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "BeanCopyModel [key1=" + key1 + ", key2=" + key2 + ", description=" + description + ", comments="
				+ comments + "]";
	}
	
}
