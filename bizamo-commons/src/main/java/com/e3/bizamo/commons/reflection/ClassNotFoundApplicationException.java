package com.e3.bizamo.commons.reflection;

import com.e3.bizamo.commons.exceptions.ApplicationException;

public class ClassNotFoundApplicationException extends ApplicationException {
	private static final long serialVersionUID = 1L;
	public ClassNotFoundApplicationException() {
		super();
	}
	public ClassNotFoundApplicationException(Throwable e) {
		super(e);
	}
}
