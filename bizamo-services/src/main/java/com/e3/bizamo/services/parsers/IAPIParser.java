package com.e3.bizamo.services.parsers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.e3.bizamo.services.locator.ServiceDescriptor;

public interface IAPIParser {
	void setServletRequest(HttpServletRequest servletRequest);
	
	void setServletResponse(HttpServletResponse servletResponse);	

	String getSecurityToken();
	ServiceDescriptor getServiceDescriptor();
	<T> T getServiceRequest(Class<T> requestClass);
	
	void writeResponse(Object responseObject);
	void writeErrorResponse(Throwable e);
}