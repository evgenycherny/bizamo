package com.e3.bizamo.ioc.inject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.e3.bizamo.commons.reflection.ReflectionUtil;

public class FactoryBinding implements IBinding {
	private IFactory<?> factory;
	public FactoryBinding(IFactory<?> factory) {
		this.factory = factory;
	}
	@Override
	public String getRegistrationKey() {
		return getCanonicalName();
	}

	@Override
	public Class<?> getImplementationClass() {
		Type[] types = factory.getClass().getGenericInterfaces();
		ParameterizedType type = (ParameterizedType)types[0];
		Type[] params = type.getActualTypeArguments();
		return ReflectionUtil.forName(params[0].getTypeName());		
	}

	@Override
	public String getCanonicalName() {
		return factory.getClass().getCanonicalName();
	}

	@Override
	public Object instanciate(Resolver resolver) {
		return factory.create(resolver);
	}

}
