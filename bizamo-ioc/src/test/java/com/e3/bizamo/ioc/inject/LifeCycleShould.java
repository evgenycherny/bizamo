package com.e3.bizamo.ioc.inject;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LifeCycleShould {
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void supportStringConversion() {
		TestCase.assertEquals(LifeCycle.TRANSIENT, LifeCycle.valueOf("TRANSIENT"));
		TestCase.assertEquals(LifeCycle.SINGLETON, LifeCycle.valueOf("SINGLETON"));
	}
	
}
