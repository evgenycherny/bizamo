package com.e3.bizamo.services.execution;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.e3.bizamo.services.execution.ServiceExecutionException;

public class ServiceExecutionExceptionShould {
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void instantiateWithDefaultConstructor() {
		ServiceExecutionException e = new ServiceExecutionException();
		TestCase.assertEquals("ServiceExecutionException", e.getCode());
	}
	@Test
	public void instantiateWithCause() {
		Exception me = Mockito.mock(Exception.class);
		ServiceExecutionException e = new ServiceExecutionException(me);
		TestCase.assertEquals("ServiceExecutionException", e.getCode());
		TestCase.assertEquals(me, e.getCause());
	}
	@Test
	public void instantiateWithMessage() {
		ServiceExecutionException e = new ServiceExecutionException("msg");
		TestCase.assertEquals("ServiceExecutionException", e.getCode());
		TestCase.assertEquals("msg", e.getMessage());
		TestCase.assertNull(e.getCause());
	}
	@Test
	public void instantiateWithMessageAndCause() {
		Exception me = Mockito.mock(Exception.class);
		ServiceExecutionException e = new ServiceExecutionException("msg", me);
		TestCase.assertEquals("ServiceExecutionException", e.getCode());
		TestCase.assertEquals("msg", e.getMessage());
		TestCase.assertEquals(me, e.getCause());
	}
	
}
