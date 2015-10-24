package com.e3.bizamo.services.parsers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.NotImplementedException;

import com.e3.bizamo.services.locator.ServiceDescriptor;

public class InProcessParser implements IAPIParser {
	
	private ServiceDescriptor descriptor;
	private Object request;
	private String token;

	public InProcessParser(ServiceDescriptor descriptor, Object request) {
		this.descriptor = descriptor;
		this.request = request;
	}

	@Override
	public void setServletRequest(HttpServletRequest servletRequest) {
		throw new NotImplementedException("Not implemented");
	}

	@Override
	public void setServletResponse(HttpServletResponse servletResponse) {
		throw new NotImplementedException("Not implemented");
	}

	@Override
	public String getSecurityToken() {
		return token;
	}

	@Override
	public ServiceDescriptor getServiceDescriptor() {
		return descriptor;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getServiceRequest(Class<T> requestClass) {
		return (T)request;
	}

	@Override
	public void writeResponse(Object responseObject) {

	}

	@Override
	public void writeErrorResponse(Throwable e) {
		// TODO Auto-generated method stub
		
	}

	public void setToken(String token) {
		this.token = token;
	}


}
