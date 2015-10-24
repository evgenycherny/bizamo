package com.e3.bizamo.services.locator;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.e3.bizamo.services.locator.ServiceInstanciationException;

public class ServiceInstanciationExceptionShould {
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void instantiateWithDefaultConstructor() {
		ServiceInstanciationException e = new ServiceInstanciationException();
		TestCase.assertEquals("ServiceInstanciationException", e.getCode());
	}
	@Test
	public void instantiateWithCause() {
		Exception me = Mockito.mock(Exception.class);
		ServiceInstanciationException e = new ServiceInstanciationException(me);
		TestCase.assertEquals("ServiceInstanciationException", e.getCode());
		TestCase.assertEquals(me, e.getCause());
	}
	@Test
	public void instantiateWithMessage() {
		ServiceInstanciationException e = new ServiceInstanciationException("msg");
		TestCase.assertEquals("ServiceInstanciationException", e.getCode());
		TestCase.assertEquals("msg", e.getMessage());
		TestCase.assertNull(e.getCause());
	}
	@Test
	public void instantiateWithMessageAndCause() {
		Exception me = Mockito.mock(Exception.class);
		ServiceInstanciationException e = new ServiceInstanciationException("msg", me);
		TestCase.assertEquals("ServiceInstanciationException", e.getCode());
		TestCase.assertEquals("msg", e.getMessage());
		TestCase.assertEquals(me, e.getCause());
	}
	
}
