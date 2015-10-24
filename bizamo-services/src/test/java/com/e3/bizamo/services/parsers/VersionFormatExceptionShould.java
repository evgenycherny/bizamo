package com.e3.bizamo.services.parsers;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.e3.bizamo.services.parsers.VersionFormatException;

public class VersionFormatExceptionShould {
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void instantiateWithDefaultConstructor() {
		VersionFormatException e = new VersionFormatException();
		TestCase.assertEquals("VersionFormatException", e.getCode());
	}
	@Test
	public void instantiateWithCause() {
		Exception me = Mockito.mock(Exception.class);
		VersionFormatException e = new VersionFormatException(me);
		TestCase.assertEquals("VersionFormatException", e.getCode());
		TestCase.assertEquals(me, e.getCause());
	}
	@Test
	public void instantiateWithMessage() {
		VersionFormatException e = new VersionFormatException("msg");
		TestCase.assertEquals("VersionFormatException", e.getCode());
		TestCase.assertEquals("msg", e.getMessage());
		TestCase.assertNull(e.getCause());
	}
	@Test
	public void instantiateWithMessageAndCause() {
		Exception me = Mockito.mock(Exception.class);
		VersionFormatException e = new VersionFormatException("msg", me);
		TestCase.assertEquals("VersionFormatException", e.getCode());
		TestCase.assertEquals("msg", e.getMessage());
		TestCase.assertEquals(me, e.getCause());
	}
	
}
