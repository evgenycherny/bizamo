package com.e3.bizamo.services.locator;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mockito;

import com.e3.bizamo.services.execution.doubles.RequestDouble;
import com.e3.bizamo.services.execution.doubles.ResponseDouble;
import com.e3.bizamo.services.execution.doubles.ServiceDouble;
import com.e3.bizamo.services.locator.ServiceDescriptor;
import com.e3.bizamo.services.locator.ServiceInfo;

public class ServiceInfoShould extends Mockito {
	@Test
	public void constructorCoverageStub() {
		new ServiceInfo();
	}
	@Test
	public void getterSetterCoverageStub() {
		ServiceInfo si = new ServiceInfo();
		si.setServiceClass(ServiceDouble.class);
		si.setRequestClass(RequestDouble.class);
		si.setResponseClass(ResponseDouble.class);
		ServiceDescriptor sd = new ServiceDescriptor();
		si.setServiceDescriptor(sd);
		
		TestCase.assertEquals(ServiceDouble.class, si.getServiceClass());
		TestCase.assertEquals(RequestDouble.class, si.getRequestClass());
		TestCase.assertEquals(ResponseDouble.class, si.getResponseClass());
		TestCase.assertEquals(sd, si.getServiceDescriptor());
	}


}
