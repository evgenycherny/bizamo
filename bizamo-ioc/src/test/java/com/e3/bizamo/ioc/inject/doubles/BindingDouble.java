package com.e3.bizamo.ioc.inject.doubles;

import com.e3.bizamo.ioc.inject.IBinding;
import com.e3.bizamo.ioc.inject.Resolver;

public class BindingDouble implements IBinding {
	private String id;

	public BindingDouble() {
		
		
	}
	public BindingDouble(String id) {
		this.id = id;
	}
	@Override
	public String getRegistrationKey() {
		return id;
	}

	@Override
	public Class<?> getImplementationClass() {
		return null;
	}

	@Override
	public String getCanonicalName() {
		return null;
	}

	@Override
	public Object instanciate(Resolver resolver) {
		return null;
	}


}
