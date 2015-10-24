package com.e3.bizamo.commons.reflection;

import org.junit.Test;

public class ReflectionUtilShould {
	@Test(expected=RuntimeException.class)
	public void throwRuntimeException() {
		ReflectionUtil.forName("abcd");
	}
	@Test(expected=RuntimeException.class)
	public void throwRuntimeExceptionWithClassLoaderParam() {
		ReflectionUtil.forName("abcd", this.getClass().getClassLoader());
	}
	@Test
	public void returnClass() {
		ReflectionUtil.forName("java.lang.String");
	}
	@Test
	public void returnClassWithClassLoaderParam() {
		ReflectionUtil.forName("java.lang.String", this.getClass().getClassLoader());
	}
	@Test
	public void constructoreCoverageStub() {
		new ReflectionUtil();
	}
}
