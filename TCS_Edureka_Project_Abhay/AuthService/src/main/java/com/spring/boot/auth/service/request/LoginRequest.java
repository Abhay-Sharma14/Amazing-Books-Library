package com.spring.boot.auth.service.request;

public class LoginRequest {

	private String username;
	private String email;

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	

}
