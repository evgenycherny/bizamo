package com.e3.bizamo.ioc.inject;

import com.e3.bizamo.commons.exceptions.ApplicationException;

public class ResolutionException extends ApplicationException {
	private static final long serialVersionUID = 1L;
	
	public ResolutionException() {
		super();
		stampCode();
	}
	public ResolutionException(Throwable cause) {
		super(cause);
		stampCode();
	}
	public ResolutionException(String message, Throwable cause) {
		super(message, cause);
		stampCode();
	}
	public ResolutionException(String message) {
		super(message);
		stampCode();
	}
	private void stampCode() {
		this.code = "ResolutionException";
	}
	
}
