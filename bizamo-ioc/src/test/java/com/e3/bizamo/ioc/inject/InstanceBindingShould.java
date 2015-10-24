package com.e3.bizamo.ioc.inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.e3.bizamo.ioc.inject.doubles.ServiceImplDouble;

import junit.framework.TestCase;

public class InstanceBindingShould {
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void returnCorrectCanonocalName() {
		ServiceImplDouble instance = new ServiceImplDouble();
		IBinding binding = new InstanceBinding(instance);
		TestCase.assertEquals(instance.getClass().getCanonicalName(), binding.getCanonicalName());
	}
	@Test
	public void returnRegistrationKeyAsCanonicalName() {
		ServiceImplDouble instance = new ServiceImplDouble();
		IBinding binding = new InstanceBinding(instance);
		TestCase.assertEquals(instance.getClass().getCanonicalName(), binding.getRegistrationKey());
	}
	@Test
	public void returnCorrectImplementationClass() {
		ServiceImplDouble instance = new ServiceImplDouble();
		IBinding binding = new InstanceBinding(instance);
		TestCase.assertEquals(instance.getClass(), binding.getImplementationClass());
	}
}
