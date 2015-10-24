package com.e3.bizamo.test.ioc2;

import com.e3.bizamo.ioc.inject.IoC;
import com.e3.bizamo.ioc.inject.Resolver;
import com.e3.bizamo.ioc.module.IModuleInitializer;
import com.e3.bizamo.test.ioc2.doubles.ISomeService1;
import com.e3.bizamo.test.ioc2.doubles.SomeService1Implementation1;

public class Initializer implements IModuleInitializer {

	@Override
	public void initModule() {
		Resolver resolver = IoC.getResolver();
		resolver.register(ISomeService1.class, SomeService1Implementation1.class);

	}

}
