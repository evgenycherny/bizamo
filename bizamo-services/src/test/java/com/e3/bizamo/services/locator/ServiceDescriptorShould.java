package com.e3.bizamo.services.locator;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mockito;

import com.e3.bizamo.services.locator.ServiceDescriptor;
import com.e3.bizamo.services.parsers.Version;

public class ServiceDescriptorShould extends Mockito {
	@Test
	public void instanciate() {
		new ServiceDescriptor();
	}
	@Test
	public void instanciateWithNameVersion() {
		ServiceDescriptor sd = new ServiceDescriptor("name", Version.parse("1.0.0"));
		TestCase.assertEquals("name", sd.getName());
		TestCase.assertEquals("1.0.0", sd.getVersion().toString());
	}
	@Test
	public void instanciateWithNameVersionPAckage() {
		ServiceDescriptor sd = new ServiceDescriptor("my.package","name", Version.parse("1.0.0"));
		TestCase.assertEquals("name", sd.getName());
		TestCase.assertEquals("1.0.0", sd.getVersion().toString());
		TestCase.assertEquals("my.package", sd.getServicePackage());
	}
	@Test
	public void convertToString() {
		ServiceDescriptor sd = new ServiceDescriptor("my.package","name", Version.parse("1.0.0"));
		TestCase.assertNotNull(sd.toString());
	}


}
