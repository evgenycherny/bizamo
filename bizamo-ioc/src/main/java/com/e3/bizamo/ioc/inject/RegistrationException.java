package com.e3.bizamo.ioc.inject;

import com.e3.bizamo.commons.exceptions.ApplicationException;

public class RegistrationException extends ApplicationException {
	private static final long serialVersionUID = 1L;
	
	public RegistrationException() {
		super();
		stampCode();
	}
	public RegistrationException(Throwable cause) {
		super(cause);
		stampCode();
	}
	public RegistrationException(String message, Throwable cause) {
		super(message, cause);
		stampCode();
	}
	public RegistrationException(String message) {
		super(message);
		stampCode();
	}
	private void stampCode() {
		this.code = "RegistrationException";
	}
}
