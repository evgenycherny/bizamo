package com.e3.bizamo.ioc.inject.doubles;

public interface IServiceWithMultipleInjectionDouble {
	void foo();
	IServiceDouble[] getDependentServices();
}
