package com.e3.bizamo.services.execution;

import com.e3.bizamo.commons.exceptions.ApplicationException;

public class ServiceExecutionException extends ApplicationException {
	private static final long serialVersionUID = 1L;
	public ServiceExecutionException() {
		super();
		stampCode();
	}
	public ServiceExecutionException(Throwable cause) {
		super(cause);
		stampCode();
	}
	public ServiceExecutionException(String message, Throwable cause) {
		super(message, cause);
		stampCode();
	}
	public ServiceExecutionException(String message) {
		super(message);
		stampCode();
	}
	private void stampCode() {
		this.code = "ServiceExecutionException";
	}
}
