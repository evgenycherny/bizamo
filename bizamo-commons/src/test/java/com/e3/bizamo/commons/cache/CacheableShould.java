package com.e3.bizamo.commons.cache;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.e3.bizamo.commons.cache.Cacheable;

public class CacheableShould {
	
	@Before
	public void setUp() {
		new Cacheable();
		Cacheable.invalidate();
	}
	@Test
	public void executeExpressionOnlyFirstTime() {
		int res1 = methodDouble(1,2);
		int res2 = methodDouble(3,4);
		TestCase.assertEquals(3, res1);
		TestCase.assertEquals(3, res2);
	}
	@Test
	public void refreshAfterInvalidateCall() {
		int res1 = methodDouble(1,2);
		Cacheable.invalidate();
		int res2 = methodDouble(3,4);
		TestCase.assertEquals(3, res1);
		TestCase.assertEquals(7, res2);
	}
	
	private boolean cachedMethodExecuted = false;
	@Test
	public void test() {
		int res1 = parametrizedMethodDouble(1,2);
		TestCase.assertEquals(3, res1);
		TestCase.assertTrue(cachedMethodExecuted);
		cachedMethodExecuted = false;
		
		int res2 = parametrizedMethodDouble(3,4);
		TestCase.assertEquals(7, res2);
		TestCase.assertTrue(cachedMethodExecuted);
		cachedMethodExecuted = false;
		
		int res3 = parametrizedMethodDouble(1,2);
		TestCase.assertEquals(3, res3);
		TestCase.assertFalse(cachedMethodExecuted);
		
	}
	
	private int methodDouble(int a, int b) {
		return Cacheable.get(()->cachedMethod(a,b));
	}
	private int parametrizedMethodDouble(int a, int b) {
		return Cacheable.get(()->cachedMethod(a,b),a,b);
	}
	private int cachedMethod(int a, int b) {
		cachedMethodExecuted = true;
		return a + b;
	}
}
