package com.rest.model;

public class LoginData {

	private String userName;
	
	private String token;

	@Override
	public String toString() {
		return "LoginData [userName=" + userName + ", token=" + token + "]";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}
