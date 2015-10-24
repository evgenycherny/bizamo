package com.e3.bizamo.test.ioc6;

import com.e3.bizamo.ioc.inject.annotations.Export;

@Export(serviceType=IServiceDouble.class)
public class ServiceImplDouble implements IServiceDouble {
	@Override
	public void foo() {

	}

}
