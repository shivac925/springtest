package com.spring.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "BEAN_TEST")
public class BeanCopyDto {

	@EmbeddedId
	private BeanCopyDtoPk beanCopyDtoPk;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "COMMENTS")
	private String comments;

	public BeanCopyDtoPk getBeanCopyDtoPk() {
		return beanCopyDtoPk;
	}

	public void setBeanCopyDtoPk(BeanCopyDtoPk beanCopyDtoPk) {
		this.beanCopyDtoPk = beanCopyDtoPk;
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
		return "BeanCopyDto [beanCopyDtoPk=" + beanCopyDtoPk + ", description=" + description + ", comments=" + comments
				+ "]";
	}

}
