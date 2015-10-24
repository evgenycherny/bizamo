package com.e3.bizamo.commons.exceptions;

public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	protected String code = "ApplicationException";
	
	public ApplicationException() {
		super();
	}
	public ApplicationException(Throwable cause) {
		super(cause);
	}
	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}
	public ApplicationException(String message) {
		super(message);
	}
	public String getCode() {
		return code;
	}
	@Override
	public boolean equals(Object otherObject) {
		ApplicationException other = (ApplicationException)otherObject;
		return this.code.equals(other.code) && 
				((this.getMessage()==null && other.getMessage()==null) || (this.getMessage()!=null && this.getMessage().equals(other.getMessage())));
	}
		
}
