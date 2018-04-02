package com.spring.model;

import org.hibernate.validator.constraints.NotEmpty;

public class FileDownload {

	@NotEmpty(message = "File name cannot be empty")
	private String file;

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
	
	
}
