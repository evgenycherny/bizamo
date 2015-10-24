package com.e3.bizamo.ioc.inject;

public interface IBinding {
	String getRegistrationKey();
	Class<?> getImplementationClass();
	String getCanonicalName();
	Object instanciate(Resolver resolver);
}
