package com.e3.bizamo.services.execution;

import com.e3.bizamo.commons.exceptions.ApplicationException;

public class NotAuthorizedException extends ApplicationException {
	public NotAuthorizedException(String msg) {
		super(msg);
		stampCode();
	}

	private void stampCode() {
		this.code = "NotAuthorizedException";
	}

	private static final long serialVersionUID = 1L;
	
}
