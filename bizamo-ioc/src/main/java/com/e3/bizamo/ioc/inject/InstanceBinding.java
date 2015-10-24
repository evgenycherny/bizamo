package com.e3.bizamo.ioc.inject;

public class InstanceBinding implements IBinding{
	private Object instance;
	public InstanceBinding(Object instance) {
		this.instance = instance;
	}
	@Override
	public String getRegistrationKey() {
		return getCanonicalName();
	}

	@Override
	public Class<?> getImplementationClass() {
		return instance.getClass();
	}

	@Override
	public String getCanonicalName() {
		return instance.getClass().getCanonicalName();
	}

	@Override
	public Object instanciate(Resolver resolver) {
		return instance;
	}

}
