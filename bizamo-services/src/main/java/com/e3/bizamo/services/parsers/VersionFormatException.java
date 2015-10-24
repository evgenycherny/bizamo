package com.e3.bizamo.services.parsers;

import com.e3.bizamo.commons.exceptions.ApplicationException;

public class VersionFormatException extends ApplicationException {

	private static final long serialVersionUID = 1L;
	public VersionFormatException() {
		super();
		stampCode();
	}
	public VersionFormatException(Throwable cause) {
		super(cause);
		stampCode();
	}
	public VersionFormatException(String message, Throwable cause) {
		super(message, cause);
		stampCode();
	}
	public VersionFormatException(String message) {
		super(message);
		stampCode();
	}
	private void stampCode() {
		this.code = "VersionFormatException";
	}
}
