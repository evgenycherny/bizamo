package com.e3.bizamo.ioc.inject;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ResolutionExceptionShould {
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void instantiateWithDefaultConstructor() {
		ResolutionException e = new ResolutionException();
		TestCase.assertEquals("ResolutionException", e.getCode());
	}
	@Test
	public void instantiateWithCause() {
		Exception me = Mockito.mock(Exception.class);
		ResolutionException e = new ResolutionException(me);
		TestCase.assertEquals("ResolutionException", e.getCode());
		TestCase.assertEquals(me, e.getCause());
	}
	@Test
	public void instantiateWithMessage() {
		ResolutionException e = new ResolutionException("msg");
		TestCase.assertEquals("ResolutionException", e.getCode());
		TestCase.assertEquals("msg", e.getMessage());
		TestCase.assertNull(e.getCause());
	}
	@Test
	public void instantiateWithMessageAndCause() {
		Exception me = Mockito.mock(Exception.class);
		ResolutionException e = new ResolutionException("msg", me);
		TestCase.assertEquals("ResolutionException", e.getCode());
		TestCase.assertEquals("msg", e.getMessage());
		TestCase.assertEquals(me, e.getCause());
	}
	
}
