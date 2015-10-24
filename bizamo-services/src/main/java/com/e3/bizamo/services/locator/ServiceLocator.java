package com.e3.bizamo.services.locator;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.e3.bizamo.ioc.inject.IoC;
import com.e3.bizamo.services.execution.IService;
import com.e3.bizamo.services.parsers.Version;

public class ServiceLocator implements IServiceLocator {
	@Override
	public void registerService(ServiceDescriptor descriptor, Class<?> clazz) {
		String key = descriptor.getRegistrationKey();
		IoC.getResolver().register(key, IService.class, clazz);
		
	}
	@Override
	public void registerService(String name, Version version, Class<?> clazz) {
		ServiceDescriptor descriptor = new ServiceDescriptor();
		descriptor.setServicePackage("");
		descriptor.setName(name);
		descriptor.setVersion(version);
		registerService(descriptor, clazz);
	}


	@Override
	public ServiceInfo getServiceInfo(ServiceDescriptor descriptor) {
		ServiceInfo info = new ServiceInfo();
		info.setServiceDescriptor(descriptor);
		Class<?> serviceClass = IoC.getResolver().getImplementationClass(descriptor.getRegistrationKey(), IService.class);
		info.setServiceClass(serviceClass);
		
		Type[] params = getServiceClassGenericParameters(serviceClass);
		
		try {
			info.setRequestClass(createClassFromType(serviceClass, params[0]));
			info.setResponseClass(createClassFromType(serviceClass, params[1]));
		} catch (Throwable e) {
			throw new ServiceInstanciationException("Failed to instanciate service parameter classes", e);
		}
		
		return info;
	}

	private Class<?> createClassFromType(Class<?> serviceClass, Type requestType) throws ClassNotFoundException {
		return Class.forName(requestType.getTypeName(), true, serviceClass.getClassLoader());
	}

	private Type[] getServiceClassGenericParameters(Class<?> serviceClass) {
		Type[] types = serviceClass.getGenericInterfaces();
		ParameterizedType type = (ParameterizedType)types[0];
		Type[] params = type.getActualTypeArguments();
		return params;
	}

}
