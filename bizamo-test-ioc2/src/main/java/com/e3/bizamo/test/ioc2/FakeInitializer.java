package com.e3.bizamo.test.ioc2;

import com.e3.bizamo.ioc.inject.IoC;
import com.e3.bizamo.ioc.inject.Resolver;
import com.e3.bizamo.ioc.module.IModuleInitializer;
import com.e3.bizamo.test.ioc2.doubles.ISomeService2;
import com.e3.bizamo.test.ioc2.doubles.SomeService2Implementation1;

public class FakeInitializer implements IModuleInitializer {

	@Override
	public void initModule() {
		Resolver resolver = IoC.getResolver();
		resolver.register(ISomeService2.class, SomeService2Implementation1.class);
	}

}
