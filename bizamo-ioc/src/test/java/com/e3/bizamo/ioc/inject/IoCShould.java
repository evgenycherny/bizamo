package com.e3.bizamo.ioc.inject;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.e3.bizamo.ioc.module.ModuleScanner;

public class IoCShould extends Mockito {
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void defaultContructorStub() {
		new IoC();
	}
	@Test
	public void returnStaticResolverInstance() {
		Resolver r1 = IoC.getResolver();
		Resolver r2 = IoC.getResolver();
		TestCase.assertNotNull(r1);
		TestCase.assertNotNull(r2);
		TestCase.assertTrue(r1==r2);
	}
	@Test
	public void runModuleScannerOnInit() {
		ModuleScanner mockScanner = mock(ModuleScanner.class);
		IoC.setScanner(mockScanner);
		IoC.init("/my/path");
		verify(mockScanner, times(1)).scan("/my/path");
	}
}
