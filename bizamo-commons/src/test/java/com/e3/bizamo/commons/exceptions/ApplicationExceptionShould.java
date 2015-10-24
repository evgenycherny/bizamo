package com.e3.bizamo.commons.exceptions;


import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ApplicationExceptionShould {
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void instantiateWithDefaultConstructor() {
		ApplicationException e = new ApplicationException();
		TestCase.assertEquals("ApplicationException", e.getCode());
	}
	@Test
	public void instantiateWithCause() {
		Exception me = Mockito.mock(Exception.class);
		ApplicationException e = new ApplicationException(me);
		TestCase.assertEquals("ApplicationException", e.getCode());
		TestCase.assertEquals(me, e.getCause());
	}
	@Test
	public void instantiateWithMessage() {
		ApplicationException e = new ApplicationException("msg");
		TestCase.assertEquals("ApplicationException", e.getCode());
		TestCase.assertEquals("msg", e.getMessage());
		TestCase.assertNull(e.getCause());
	}
	@Test
	public void instantiateWithMessageAndCause() {
		Exception me = Mockito.mock(Exception.class);
		ApplicationException e = new ApplicationException("msg", me);
		TestCase.assertEquals("ApplicationException", e.getCode());
		TestCase.assertEquals("msg", e.getMessage());
		TestCase.assertEquals(me, e.getCause());
	}
	@Test
	public void beComparableForEquality() {
		ApplicationException e1 = new ApplicationException("msg");
		ApplicationException e2 = new ApplicationException("msg");
		TestCase.assertEquals(e1, e2);
		
		e1 = new ApplicationException();
		e2 = new ApplicationException();
		TestCase.assertEquals(e1, e2);
		
		e1 = new ApplicationException("msg1");
		e2 = new ApplicationException("msg2");
		TestCase.assertNotSame(e1, e2);
		
		e1 = new ApplicationException();
		e2 = new ApplicationException("msg");
		TestCase.assertNotSame(e1, e2);
		TestCase.assertNotSame(e2, e1);

	}
}
