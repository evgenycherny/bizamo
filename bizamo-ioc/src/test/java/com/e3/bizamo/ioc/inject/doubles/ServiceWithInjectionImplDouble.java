package com.e3.bizamo.ioc.inject.doubles;

public class ServiceWithInjectionImplDouble implements IServiceWithInjectionDouble{
	private IServiceDouble dependentService;
	
	public ServiceWithInjectionImplDouble() {
		
	}
	
	public ServiceWithInjectionImplDouble(IServiceDouble dependentService) {
		this.dependentService = dependentService;
	}
	public void foo() {
	}
	public IServiceDouble getDependentService() {
		return dependentService;
	}
}
