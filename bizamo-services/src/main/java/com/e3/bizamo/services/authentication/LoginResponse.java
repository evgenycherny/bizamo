package com.e3.bizamo.services.authentication;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

public class LoginResponse {
	private boolean success;
	private String username;
	private String token;
	public boolean isSuccess() {
		return success;
	}
	void setSuccess(boolean success) {
		this.success = success;
	}
	
	@JsonSerialize(include=Inclusion.NON_NULL)
	public String getUsername() {
		return username;
	}
	void setUsername(String username) {
		this.username = username;
	}
	
	@JsonSerialize(include=Inclusion.NON_NULL)
	public String getToken() {
		return token;
	}
	void setToken(String token) {
		this.token = token;
	}	
}
