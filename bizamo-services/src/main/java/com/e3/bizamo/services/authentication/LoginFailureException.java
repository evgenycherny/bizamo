package com.e3.bizamo.services.authentication;

import com.e3.bizamo.commons.exceptions.ApplicationException;

public class LoginFailureException extends ApplicationException {
	public LoginFailureException(String msg) {
		super(msg);
		stampCode();
	}

	private void stampCode() {
		this.code = "LoginFailureException";
	}

	private static final long serialVersionUID = 1L;
	
}
