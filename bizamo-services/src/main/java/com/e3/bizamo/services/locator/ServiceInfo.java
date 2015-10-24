package com.e3.bizamo.services.locator;

import com.e3.bizamo.ioc.inject.IoC;
import com.e3.bizamo.services.execution.IService;

public class ServiceInfo {
	private Class<?> serviceClass;
	private Class<?> requestClass;
	private Class<?> responseClass;
	private ServiceDescriptor serviceDescriptor;
	
	public Class<?> getServiceClass() {
		return serviceClass;
	}
	public void setServiceClass(Class<?> serviceClass) {
		this.serviceClass = serviceClass;
	}
	public Class<?> getRequestClass() {
		return requestClass;
	}
	public void setRequestClass(Class<?> requestClass) {
		this.requestClass = requestClass;
	}
	public Class<?> getResponseClass() {
		return responseClass;
	}
	public void setResponseClass(Class<?> responseClass) {
		this.responseClass = responseClass;
	}
	public ServiceDescriptor getServiceDescriptor() {
		return serviceDescriptor;
	}
	public void setServiceDescriptor(ServiceDescriptor serviceDescriptor) {
		this.serviceDescriptor = serviceDescriptor;
	}
	public IService<?, ?> getService() {
		return IoC.getResolver().resolve(serviceDescriptor.getRegistrationKey(), IService.class);
	}
}
