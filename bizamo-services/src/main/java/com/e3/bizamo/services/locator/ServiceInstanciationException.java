package com.e3.bizamo.services.locator;

import com.e3.bizamo.commons.exceptions.ApplicationException;

public class ServiceInstanciationException extends ApplicationException {
	private static final long serialVersionUID = 1L;
	public ServiceInstanciationException() {
		super();
		stampCode();
	}
	public ServiceInstanciationException(Throwable cause) {
		super(cause);
		stampCode();
	}
	public ServiceInstanciationException(String message, Throwable cause) {
		super(message, cause);
		stampCode();
	}
	public ServiceInstanciationException(String message) {
		super(message);
		stampCode();
	}
	private void stampCode() {
		this.code = "ServiceInstanciationException";
	}
}
