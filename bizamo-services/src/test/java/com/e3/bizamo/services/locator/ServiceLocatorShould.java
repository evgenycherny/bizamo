package com.e3.bizamo.services.locator;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.e3.bizamo.ioc.inject.IoC;
import com.e3.bizamo.services.execution.IService;
import com.e3.bizamo.services.execution.ServiceExecuter;
import com.e3.bizamo.services.execution.doubles.BadServiceDouble;
import com.e3.bizamo.services.execution.doubles.ServiceDouble;
import com.e3.bizamo.services.locator.ServiceDescriptor;
import com.e3.bizamo.services.locator.ServiceInfo;
import com.e3.bizamo.services.locator.ServiceInstanciationException;
import com.e3.bizamo.services.locator.ServiceLocator;
import com.e3.bizamo.services.parsers.Version;

public class ServiceLocatorShould extends Mockito {
	@Before
	public void setUp() {
		IoC.getAndCleanResolver();
	}
	@Test
	public void constructorCoverageStub() {
		new ServiceExecuter(null);
	}
	
	@Test
	public void resolveServiceByDescriptor() {
		ServiceLocator sl = new ServiceLocator();
		sl.registerService("someservice", Version.parse("1.0.0"), ServiceDouble.class);
		ServiceInfo si = sl.getServiceInfo(new ServiceDescriptor().setName("someservice").setVersion(Version.parse("1.0.0")));
		IService<?,?> s = si.getService();
		TestCase.assertTrue(s instanceof ServiceDouble);
	}
	@Test(expected=ServiceInstanciationException.class)
	public void throwWhenParameterClassCantBeInstanciated() throws ClassNotFoundException {
		ServiceLocator sl = new ServiceLocator();
		sl.registerService("somebadservice", Version.parse("1.0.0"), BadServiceDouble.class);
		ServiceInfo si = sl.getServiceInfo(new ServiceDescriptor().setName("somebadservice").setVersion(Version.parse("1.0.0")));
		si.getService();
	}

}
