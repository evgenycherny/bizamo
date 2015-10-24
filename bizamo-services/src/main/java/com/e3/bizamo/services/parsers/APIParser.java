package com.e3.bizamo.services.parsers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.exc.UnrecognizedPropertyException;

import com.e3.bizamo.commons.exceptions.ApplicationException;
import com.e3.bizamo.services.execution.ServiceError;
import com.e3.bizamo.services.locator.ServiceDescriptor;

public class APIParser implements IAPIParser {
	protected HttpServletRequest servletRequest;
	protected HttpServletResponse servletResponse;
	private OutputStream outputStream;
	
	public APIParser() {
		
	}

	@Override
	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}

	@Override
	public void setServletResponse(HttpServletResponse servletResponse) {
		try {
			this.servletResponse = servletResponse;
			this.outputStream = servletResponse.getOutputStream();
		} catch (IOException e) {
			throw new ApplicationException("Unable to get response output stream", e);
		}
	}
	@Override
	public String getSecurityToken() {
		return servletRequest.getHeader("token");
	}
	@Override
	public ServiceDescriptor getServiceDescriptor() {
		String[] pathParts = servletRequest.getPathInfo().split("/");
		
		ServiceDescriptor descriptor = new ServiceDescriptor();
		
		descriptor.setVersion(parseVersion(pathParts));
		descriptor.setName(parseServiceName(pathParts));
		descriptor.setServicePackage(parsePackage(pathParts));
		descriptor.setMethod(servletRequest.getMethod());
		return descriptor;
	}
	private String parsePackage(String[] pathParts) {
		String result = "";
		for(int i=2; i<pathParts.length-1; i++) result+=pathParts[i] + ".";
		if (result.length()==0) return "";
		return result.substring(0,result.length()-1);
	}
	private String parseServiceName(String[] pathParts) {
		return pathParts[pathParts.length-1];
	}
	private Version parseVersion(String[] pathParts) {
		return Version.parse(pathParts[1]);
	}
	public <T> T getServiceRequest(Class<T> requestClass) {

		try {
			InputStream is = servletRequest.getInputStream();
			ObjectMapper mapper = new ObjectMapper();
			T requestObject = mapper.readValue(is, requestClass);
			return requestObject;
		}
		catch(UnrecognizedPropertyException e) {
			throw new ApplicationException("Bad input format", e);
		}
		catch (IOException e) {
			throw new ApplicationException("Unable to read input stream from request", e);
		}
		
	}
	public void writeResponse(Object responseObject) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(outputStream, responseObject);
		}
		catch (Exception e){
			throw new ApplicationException(e);
		}
	}
	public void writeErrorResponse(Throwable e) {
		servletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		ServiceError error = new ServiceError();
		if (e instanceof ApplicationException) {
			ApplicationException ae = (ApplicationException)e;
			error.setCode(ae.getCode());
			error.setDescription(e.getMessage());	
		}
		else {
			error.setCode("1000");
			error.setDescription(e.getMessage());
		}
		writeResponse(error);

	}

}
