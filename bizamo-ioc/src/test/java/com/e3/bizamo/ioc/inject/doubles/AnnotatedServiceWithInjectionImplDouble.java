package com.e3.bizamo.ioc.inject.doubles;

import com.e3.bizamo.ioc.inject.annotations.Key;

public class AnnotatedServiceWithInjectionImplDouble implements IServiceWithInjectionDouble{
	private IServiceDouble dependentService;
	
	
	public AnnotatedServiceWithInjectionImplDouble(@Key("key2") IServiceDouble dependentService) {
		this.dependentService = dependentService;
	}
	public void foo() {
	}
	public IServiceDouble getDependentService() {
		return dependentService;
	}
}
