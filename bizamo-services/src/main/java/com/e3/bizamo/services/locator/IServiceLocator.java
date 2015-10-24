package com.e3.bizamo.services.locator;

import com.e3.bizamo.services.parsers.Version;

public interface IServiceLocator {	
	void registerService(ServiceDescriptor descriptor,  Class<?> clazz);
	void registerService(String name, Version version, Class<?> clazz);
	
	ServiceInfo getServiceInfo(ServiceDescriptor descriptor);



}
