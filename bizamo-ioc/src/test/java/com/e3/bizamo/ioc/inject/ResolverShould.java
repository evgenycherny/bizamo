package com.e3.bizamo.ioc.inject;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.e3.bizamo.ioc.inject.RegistrationException;
import com.e3.bizamo.ioc.inject.ResolutionException;
import com.e3.bizamo.ioc.inject.Resolver;
import com.e3.bizamo.ioc.inject.doubles.AnnotatedServiceWithInjectionImplDouble;
import com.e3.bizamo.ioc.inject.doubles.AnotherServiceImplDouble;
import com.e3.bizamo.ioc.inject.doubles.BadServiceImplDouble;
import com.e3.bizamo.ioc.inject.doubles.IAnotherServiceDouble;
import com.e3.bizamo.ioc.inject.doubles.IProductDouble;
import com.e3.bizamo.ioc.inject.doubles.IServiceDouble;
import com.e3.bizamo.ioc.inject.doubles.IServiceWithInjectionDouble;
import com.e3.bizamo.ioc.inject.doubles.IServiceWithMultipleInjectionDouble;
import com.e3.bizamo.ioc.inject.doubles.ProductFactoryDouble;
import com.e3.bizamo.ioc.inject.doubles.ProductOneDouble;
import com.e3.bizamo.ioc.inject.doubles.SecondLevelServiceImplDouble;
import com.e3.bizamo.ioc.inject.doubles.SecondServiceImplDouble;
import com.e3.bizamo.ioc.inject.doubles.SelectorDouble;
import com.e3.bizamo.ioc.inject.doubles.ServiceImplDouble;
import com.e3.bizamo.ioc.inject.doubles.ServiceWithInjectionImplDouble;
import com.e3.bizamo.ioc.inject.doubles.ServiceWithMultipleInjectionImplDouble;
import com.e3.bizamo.ioc.inject.doubles.WrongServiceImplDouble;

import junit.framework.TestCase;

public class ResolverShould {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void allowRegistrationOfBinding() {
		Resolver resolver = new Resolver();		
		resolver.register(IServiceDouble.class, ServiceImplDouble.class);
	}
	@Test
	public void allowRegistrationOfInstanceWithKey() {
		Resolver resolver = new Resolver();		
		IServiceDouble impl = new ServiceImplDouble();
		resolver.register("key1",IServiceDouble.class, impl);
	}
	@Test
	public void allowRegistrationOfMultipleBindings() {
		Resolver resolver = new Resolver();		
		resolver.register(IServiceDouble.class, ServiceImplDouble.class);
		resolver.register(IAnotherServiceDouble.class, AnotherServiceImplDouble.class);
		TestCase.assertEquals(2, resolver.map.keySet().size());
	}
	
	@Test(expected=RegistrationException.class)
	public void failWhenImplementationDoesNotImplementService() {
		Resolver resolver = new Resolver();		
		resolver.register(IServiceDouble.class, WrongServiceImplDouble.class);
	}
	@Test
	public void allowRegistrationOfSameBindingTwice() {
		Resolver resolver = new Resolver();		
		resolver.register(IServiceDouble.class, ServiceImplDouble.class);
		resolver.register(IServiceDouble.class, ServiceImplDouble.class);
		TestCase.assertEquals(1, resolver.map.keySet().size());
		TestCase.assertEquals(1, resolver.map.get(IServiceDouble.class).size());
	}
	
	@Test
	public void resolveInstanceOfRegistredServiceImplementation() {
		Resolver resolver = new Resolver();		
		resolver.register(IServiceDouble.class, ServiceImplDouble.class);
		
		IServiceDouble service = resolver.resolve(IServiceDouble.class);
		TestCase.assertTrue(service instanceof ServiceImplDouble);
	}
	
	@Test
	public void resolveByKey() {
		Resolver resolver = new Resolver();		
		resolver.register("key1", IServiceDouble.class, ServiceImplDouble.class);
		
		IServiceDouble service = resolver.resolve("key1",IServiceDouble.class);
		TestCase.assertTrue(service instanceof ServiceImplDouble);
	}
	
	@Test(expected=ResolutionException.class)
	public void failWhenClassCantBeInstanciated() {
		Resolver resolver = new Resolver();		
		resolver.register(IServiceDouble.class, BadServiceImplDouble.class);
		resolver.resolve(IServiceDouble.class);
	}
	@Test
	public void throwExceptionWithCorrectInnerException() {
		Resolver resolver = new Resolver();		
		resolver.register(IServiceDouble.class, BadServiceImplDouble.class);
		try {
			resolver.resolve(IServiceDouble.class);
		} catch(ResolutionException e) {
			TestCase.assertTrue(e.getCause() instanceof InvocationTargetException);
			TestCase.assertTrue(e.getCause().getCause() instanceof RuntimeException);
		}
	}
	
	@Test
	public void allowResolutionWithInheritedInterfaces() {
		Resolver resolver = new Resolver();		
		resolver.register(IServiceDouble.class, SecondLevelServiceImplDouble.class);
		resolver.resolve(IServiceDouble.class);
	}
	@Test
	public void allowRegistrationOfMultipleImplementations() {
		Resolver resolver = new Resolver();		
		resolver.register(IServiceDouble.class, ServiceImplDouble.class);
		resolver.register(IServiceDouble.class, SecondServiceImplDouble.class);
		TestCase.assertEquals(1, resolver.map.keySet().size());
		TestCase.assertEquals(2, resolver.map.get(IServiceDouble.class).size());
	}
	
	@Test(expected=ResolutionException.class)
	public void failOnResolutionWhenTwoImplementationRegistred() {
		Resolver resolver = new Resolver();		
		resolver.register(IServiceDouble.class, ServiceImplDouble.class);
		resolver.register(IServiceDouble.class, SecondServiceImplDouble.class);
		resolver.resolve(IServiceDouble.class);
	}
	
	@Test
	public void resolveByDefaultKeyWhenTwoImplementationRegistred() {
		Resolver resolver = new Resolver();		
		resolver.register(IServiceDouble.class, ServiceImplDouble.class);
		resolver.register(IServiceDouble.class, SecondServiceImplDouble.class);
		IServiceDouble service = resolver.resolve(ServiceImplDouble.class.getCanonicalName(), IServiceDouble.class);
		TestCase.assertTrue(service instanceof ServiceImplDouble);
		service = resolver.resolve(SecondServiceImplDouble.class.getCanonicalName(), IServiceDouble.class);
		TestCase.assertTrue(service instanceof SecondServiceImplDouble);
	}
	
	@Test
	public void allowRegistrationWithKeyAndResolutionByKey() {
		Resolver resolver = new Resolver();		
		resolver.register("key1", IServiceDouble.class, ServiceImplDouble.class);
		resolver.register("key2", IServiceDouble.class, SecondServiceImplDouble.class);
		IServiceDouble service = resolver.resolve("key1", IServiceDouble.class);
		TestCase.assertTrue(service instanceof ServiceImplDouble);
		service = resolver.resolve("key2", IServiceDouble.class);
		TestCase.assertTrue(service instanceof SecondServiceImplDouble);
	}
	
	@Test(expected=ResolutionException.class)
	public void failWhenResolvedByNonExistingKey() {
		Resolver resolver = new Resolver();		
		resolver.register("key1", IServiceDouble.class, ServiceImplDouble.class);
		resolver.register("key2", IServiceDouble.class, SecondServiceImplDouble.class);
		resolver.resolve("key3", IServiceDouble.class);
	}
	@Test(expected=ResolutionException.class)
	public void failOnNonExistingService() {
		Resolver resolver = new Resolver();		
		resolver.resolve(IServiceDouble.class);
	}
	
	@Test
	public void resolveMultipleImplementations() {
		Resolver resolver = new Resolver();		
		resolver.register(IServiceDouble.class, ServiceImplDouble.class);
		resolver.register(IServiceDouble.class, SecondServiceImplDouble.class);
		List<IServiceDouble> services = resolver.resolveAll(IServiceDouble.class);
		TestCase.assertEquals(2, services.size());
	}

	@Test
	public void allowRegistrationOfInstanceBinding() {
		Resolver resolver = new Resolver();
		IServiceDouble registredInstance = new ServiceImplDouble();
		resolver.register(IServiceDouble.class, registredInstance);
		IServiceDouble resolvedInstance = resolver.resolve(IServiceDouble.class);
		TestCase.assertTrue(registredInstance==resolvedInstance);
	}
	@Test
	public void allowRegistrationOfClassBinding() {
		Resolver resolver = new Resolver();
		resolver.register(IServiceDouble.class, new ClassBinding(ServiceImplDouble.class));
		IServiceDouble resolvedInstance = resolver.resolve(IServiceDouble.class);
		TestCase.assertTrue(resolvedInstance instanceof ServiceImplDouble);
	}
	@Test
	public void allowRegistrationOfFactoryBinding() {
		Resolver resolver = new Resolver();
		IFactory<IProductDouble> factory = new ProductFactoryDouble();
		resolver.register(IProductDouble.class, new FactoryBinding(factory));
		IProductDouble resolvedInstance = resolver.resolve(IProductDouble.class);
		TestCase.assertTrue(resolvedInstance instanceof ProductOneDouble);
	}
	
	@Test
	public void resolveWithSelector() {
		Resolver resolver = new Resolver();
		resolver.register(IServiceDouble.class, ServiceImplDouble.class);
		SelectorDouble selector = new SelectorDouble();
		IServiceDouble resolvedInstance = resolver.resolve(IServiceDouble.class, selector);
		TestCase.assertTrue(resolvedInstance instanceof ServiceImplDouble);
		TestCase.assertEquals(1, selector.selectSingleCallCount);
	}
	@Test
	public void resolveMultipleWithSelector() {
		Resolver resolver = new Resolver();
		resolver.register(IServiceDouble.class, ServiceImplDouble.class);
		resolver.register(IServiceDouble.class, SecondServiceImplDouble.class);
		SelectorDouble selector = new SelectorDouble();
		List<IServiceDouble> resolvedInstances = resolver.resolveAll(IServiceDouble.class, selector);
		TestCase.assertEquals(2, resolvedInstances.size());
		TestCase.assertEquals(1, selector.selectManyCallCount);
	}
	@Test
	public void allowRegisterTwoDifferentServicesWithSameKey() {
		Resolver resolver = new Resolver();
		resolver.register("key1", IServiceDouble.class, ServiceImplDouble.class);
		resolver.register("key1", IAnotherServiceDouble.class, AnotherServiceImplDouble.class);
		IServiceDouble serviceImpl = resolver.resolve("key1", IServiceDouble.class);
		IAnotherServiceDouble anotheServiceImpl = resolver.resolve("key1", IAnotherServiceDouble.class);
		TestCase.assertTrue(serviceImpl instanceof ServiceImplDouble);
		TestCase.assertTrue(anotheServiceImpl instanceof AnotherServiceImplDouble);
	}
	
	@Test
	public void injectDependentService() {
		Resolver resolver = new Resolver();
		resolver.register(IServiceWithInjectionDouble.class, ServiceWithInjectionImplDouble.class);
		resolver.register(IServiceDouble.class, ServiceImplDouble.class);
		IServiceWithInjectionDouble serviceImpl = resolver.resolve(IServiceWithInjectionDouble.class);
		TestCase.assertTrue(serviceImpl instanceof ServiceWithInjectionImplDouble);
		TestCase.assertTrue(serviceImpl.getDependentService() instanceof ServiceImplDouble);
	}
	@Test
	public void injectDependentServiceByKey() {
		Resolver resolver = new Resolver();
		resolver.register(IServiceWithInjectionDouble.class, AnnotatedServiceWithInjectionImplDouble.class);
		resolver.register("key1", IServiceDouble.class, ServiceImplDouble.class);
		resolver.register("key2", IServiceDouble.class, SecondServiceImplDouble.class);
		IServiceWithInjectionDouble serviceImpl = resolver.resolve(IServiceWithInjectionDouble.class);
		TestCase.assertTrue(serviceImpl instanceof AnnotatedServiceWithInjectionImplDouble);
		TestCase.assertTrue(serviceImpl.getDependentService() instanceof SecondServiceImplDouble);
	}
	@Test
	public void injectMultipleDependentServices() {
		Resolver resolver = new Resolver();
		resolver.register(IServiceWithMultipleInjectionDouble.class, ServiceWithMultipleInjectionImplDouble.class);
		resolver.register("key1", IServiceDouble.class, ServiceImplDouble.class);
		resolver.register("key2", IServiceDouble.class, SecondServiceImplDouble.class);
		IServiceWithMultipleInjectionDouble serviceImpl = resolver.resolve(IServiceWithMultipleInjectionDouble.class);
		TestCase.assertTrue(serviceImpl instanceof ServiceWithMultipleInjectionImplDouble);
		TestCase.assertEquals(2, serviceImpl.getDependentServices().length);
	}
	
	@Test
	public void returnImplementationClass() {
		Resolver resolver = new Resolver();
		resolver.register(IServiceWithMultipleInjectionDouble.class, ServiceWithMultipleInjectionImplDouble.class);
		resolver.register("key1", IServiceDouble.class, ServiceImplDouble.class);
		resolver.register("key2", IServiceDouble.class, SecondServiceImplDouble.class);
		
		TestCase.assertEquals(ServiceImplDouble.class,resolver.getImplementationClass("key1", IServiceDouble.class));
		TestCase.assertEquals(SecondServiceImplDouble.class,resolver.getImplementationClass("key2", IServiceDouble.class, new DefaultSelector()));
	}
	
	@Test
	public void resolveStringPropertiesFromPropertyHolder() {
		
	}
	
}
