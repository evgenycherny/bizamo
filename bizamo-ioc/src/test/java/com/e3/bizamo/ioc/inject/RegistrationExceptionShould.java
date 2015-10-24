package com.e3.bizamo.ioc.inject;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class RegistrationExceptionShould {
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void instantiateWithDefaultConstructor() {
		RegistrationException e = new RegistrationException();
		TestCase.assertEquals("RegistrationException", e.getCode());
	}
	@Test
	public void instantiateWithCause() {
		Exception me = Mockito.mock(Exception.class);
		RegistrationException e = new RegistrationException(me);
		TestCase.assertEquals("RegistrationException", e.getCode());
		TestCase.assertEquals(me, e.getCause());
	}
	@Test
	public void instantiateWithMessage() {
		RegistrationException e = new RegistrationException("msg");
		TestCase.assertEquals("RegistrationException", e.getCode());
		TestCase.assertEquals("msg", e.getMessage());
		TestCase.assertNull(e.getCause());
	}
	@Test
	public void instantiateWithMessageAndCause() {
		Exception me = Mockito.mock(Exception.class);
		RegistrationException e = new RegistrationException("msg", me);
		TestCase.assertEquals("RegistrationException", e.getCode());
		TestCase.assertEquals("msg", e.getMessage());
		TestCase.assertEquals(me, e.getCause());
	}
	
}
