package com.e3.bizamo.ioc.inject.doubles;

public class ServiceWithMultipleInjectionImplDouble implements IServiceWithMultipleInjectionDouble {
	private IServiceDouble[] dependentServices;
	
	
	public ServiceWithMultipleInjectionImplDouble(IServiceDouble[] dependentServices) {
		this.dependentServices = dependentServices;
	}
	public void foo() {
	}
	public IServiceDouble[] getDependentServices() {
		return dependentServices;
	}
}
