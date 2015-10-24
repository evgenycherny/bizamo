package com.e3.bizamo.ioc.inject.doubles;

public interface IServiceWithInjectionDouble {
	void foo();
	IServiceDouble getDependentService();
}
