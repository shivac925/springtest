package com.rest.model;

public class EndResponse {

	private String status;
	
	private Object data;
	
	private String token;
	
	private String error;
	
	private String message;

	@Override
	public String toString() {
		return "EndResponse [status=" + status + ", data=" + data + ", token=" + token + ", error=" + error
				+ ", message=" + message + "]";
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
