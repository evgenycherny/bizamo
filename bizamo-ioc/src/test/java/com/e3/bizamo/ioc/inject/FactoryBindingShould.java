package com.e3.bizamo.ioc.inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.e3.bizamo.ioc.inject.doubles.IProductDouble;
import com.e3.bizamo.ioc.inject.doubles.ProductFactoryDouble;

import junit.framework.TestCase;

public class FactoryBindingShould {
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void returnCorrectCanonocalName() {
		IFactory<IProductDouble> factory = new ProductFactoryDouble();
		IBinding binding = new FactoryBinding(factory);
		TestCase.assertEquals(ProductFactoryDouble.class.getCanonicalName(), binding.getCanonicalName());
	}
	@Test
	public void returnRegistrationKeyAsCanonicalName() {
		IFactory<IProductDouble> factory = new ProductFactoryDouble();
		IBinding binding = new FactoryBinding(factory);
		TestCase.assertEquals(ProductFactoryDouble.class.getCanonicalName(), binding.getRegistrationKey());
	}
	@Test
	public void returnCorrectImplementationClass() {
		IFactory<IProductDouble> factory = new ProductFactoryDouble();
		IBinding binding = new FactoryBinding(factory);
		TestCase.assertEquals(IProductDouble.class, binding.getImplementationClass());
	}
}
