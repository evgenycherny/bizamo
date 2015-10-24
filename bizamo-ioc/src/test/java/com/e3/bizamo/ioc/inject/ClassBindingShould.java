package com.e3.bizamo.ioc.inject;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.e3.bizamo.ioc.inject.doubles.AnnotatedConstructorServiceImplDouble;
import com.e3.bizamo.ioc.inject.doubles.AnotherServiceImpl2Double;
import com.e3.bizamo.ioc.inject.doubles.AnotherServiceImplDouble;
import com.e3.bizamo.ioc.inject.doubles.BadSelectorAnnoServiceImplDouble;
import com.e3.bizamo.ioc.inject.doubles.IAnotherServiceDouble;
import com.e3.bizamo.ioc.inject.doubles.MultiParamConstructorServiceImplDouble;
import com.e3.bizamo.ioc.inject.doubles.SelectorAnnoServiceImplDouble;
import com.e3.bizamo.ioc.inject.doubles.ServiceImplDouble;

public class ClassBindingShould {
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void returnCorrectCanonocalName() {
		IBinding binding = new ClassBinding(ServiceImplDouble.class);
		TestCase.assertEquals(ServiceImplDouble.class.getCanonicalName(), binding.getCanonicalName());
	}
	@Test
	public void returnRegistrationKeyAsCanonicalName() {
		IBinding binding = new ClassBinding(ServiceImplDouble.class);
		TestCase.assertEquals(ServiceImplDouble.class.getCanonicalName(), binding.getRegistrationKey());
	}
	@Test
	public void returnCorrectImplementationClass() {
		IBinding binding = new ClassBinding(ServiceImplDouble.class);
		TestCase.assertEquals(ServiceImplDouble.class, binding.getImplementationClass());
	}
	@Test
	public void instanciateSingleton() {
		ClassBinding binding = new ClassBinding(ServiceImplDouble.class).setLifeCycle(LifeCycle.SINGLETON);
		Object o1 = binding.instanciate(IoC.getResolver());
		Object o2 = binding.instanciate(IoC.getResolver());
		TestCase.assertTrue(o1==o2);
	}
	@Test
	public void useInjectionConstructorForInstanciation() {
		ClassBinding binding = new ClassBinding(AnnotatedConstructorServiceImplDouble.class);
		AnnotatedConstructorServiceImplDouble acd = (AnnotatedConstructorServiceImplDouble)binding.instanciate(IoC.getResolver());
		TestCase.assertTrue(acd.isAnnotatedConstructorCalled());
	}
	
	@Test
	public void useConsructorWithLongestParamList() {
		Resolver resolver = new Resolver();
		resolver.register("key1",IAnotherServiceDouble.class, AnotherServiceImplDouble.class);
		resolver.register("key2",IAnotherServiceDouble.class, AnotherServiceImpl2Double.class);
		ClassBinding binding = new ClassBinding(MultiParamConstructorServiceImplDouble.class);
		MultiParamConstructorServiceImplDouble acd = (MultiParamConstructorServiceImplDouble)binding.instanciate(resolver);
		TestCase.assertTrue(acd.isFlag());
	}
	
	@Test
	public void injectConstructorParameterByKeyAnnotation() {
		Resolver resolver = new Resolver();
		resolver.register("key1",IAnotherServiceDouble.class, AnotherServiceImplDouble.class);
		resolver.register("key2",IAnotherServiceDouble.class, AnotherServiceImpl2Double.class);
		ClassBinding binding = new ClassBinding(MultiParamConstructorServiceImplDouble.class);
		MultiParamConstructorServiceImplDouble acd = (MultiParamConstructorServiceImplDouble)binding.instanciate(resolver);
		TestCase.assertTrue(acd.getServiceParam1() instanceof AnotherServiceImplDouble);
		TestCase.assertTrue(acd.getServiceParam2() instanceof AnotherServiceImpl2Double);
	}
	@Test
	public void injectConstructorUsingSelector() {
		Resolver resolver = new Resolver();
		resolver.register(IAnotherServiceDouble.class, AnotherServiceImplDouble.class);
		ClassBinding binding = new ClassBinding(SelectorAnnoServiceImplDouble.class);
		SelectorAnnoServiceImplDouble impl = (SelectorAnnoServiceImplDouble)binding.instanciate(resolver);
		TestCase.assertTrue(impl.isFlag());
	}
	
	@Test(expected=ResolutionException.class)
	public void injectConstructorUsingBadSelector() {
		Resolver resolver = new Resolver();
		resolver.register(IAnotherServiceDouble.class, AnotherServiceImplDouble.class);
		ClassBinding binding = new ClassBinding(BadSelectorAnnoServiceImplDouble.class);
		binding.instanciate(resolver);
	}
}
