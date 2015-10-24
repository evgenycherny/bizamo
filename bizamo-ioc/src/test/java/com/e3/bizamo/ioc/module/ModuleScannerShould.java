package com.e3.bizamo.ioc.module;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.e3.bizamo.commons.reflection.ReflectionUtil;
import com.e3.bizamo.ioc.inject.IoC;
import com.e3.bizamo.ioc.inject.Resolver;

public class ModuleScannerShould {
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void scanFolder() {
		ClassLoader cl = this.getClass().getClassLoader();
		String path = cl.getResource("folder1").getPath();
		
		ModuleScanner scanner = new ModuleScanner();
		ClassLoader generatedClassLoader = scanner.scan(path);
		Class<?> clazz = ReflectionUtil.forName("com.e3.bizamo.test.ioc1.SomeClass", generatedClassLoader);
		TestCase.assertNotNull(clazz);
	}
	
	@Test
	public void scanMultipleFolders() {
		ClassLoader cl = this.getClass().getClassLoader();
		String path = cl.getResource("folder1").getPath();
		path+= ";" + cl.getResource("folder2").getPath();
		ModuleScanner scanner = new ModuleScanner();
		ClassLoader generatedClassLoader = scanner.scan(path);
		Class<?> clazz1 = ReflectionUtil.forName("com.e3.bizamo.test.ioc1.SomeClass", generatedClassLoader);
		TestCase.assertNotNull(clazz1);
		Class<?> clazz2 = ReflectionUtil.forName("com.e3.bizamo.test.ioc2.SomeClass", generatedClassLoader);
		TestCase.assertNotNull(clazz2);
	}
	@Test
	public void findInitializerByScanning() {
		ClassLoader cl = this.getClass().getClassLoader();
		String path = cl.getResource("folder1").getPath();
		
		ModuleScanner scanner = new ModuleScanner();
		ClassLoader generatedClassLoader = scanner.scan(path);
		Class<?> clazz = ReflectionUtil.forName("com.e3.bizamo.test.ioc1.doubles.ISomeService1", generatedClassLoader);
		TestCase.assertNotNull(IoC.getResolver().resolve(clazz));
	}
	@Test
	public void findInitializerFromManifest() {
		ClassLoader cl = this.getClass().getClassLoader();
		String path = cl.getResource("folder2").getPath();
		
		ModuleScanner scanner = new ModuleScanner();
		ClassLoader generatedClassLoader = scanner.scan(path);
		Class<?> clazz = ReflectionUtil.forName("com.e3.bizamo.test.ioc2.doubles.ISomeService1", generatedClassLoader);
		TestCase.assertNotNull(IoC.getResolver().resolve(clazz));
	}
	@Test
	public void notFailOnManifestReferenceToBadInitializer() {
		ClassLoader cl = this.getClass().getClassLoader();
		String path = cl.getResource("folder3").getPath();
		
		ModuleScanner scanner = new ModuleScanner();
		scanner.scan(path);
	}	
	@Test
	public void notFailOnBadManifestReferenceForInitializer() {
		ClassLoader cl = this.getClass().getClassLoader();
		String path = cl.getResource("folder4").getPath();
		
		ModuleScanner scanner = new ModuleScanner();
		scanner.scan(path);
	}
	@Test
	public void notFailOnNotExistingPathEntry() {
		ClassLoader cl = this.getClass().getClassLoader();
		String path = cl.getResource("").getPath() + "/folder97";
		
		ModuleScanner scanner = new ModuleScanner();
		scanner.scan(path);
	}
	@Test
	public void notFailOnBadPathEntry() {
		ClassLoader cl = this.getClass().getClassLoader();
		String path = cl.getResource("folder99").getPath();
		
		ModuleScanner scanner = new ModuleScanner();
		scanner.scan(path);
	}
	@Test
	public void automaticalyRegisterAnnotatedImplementations() throws ClassNotFoundException {
		ClassLoader cl = this.getClass().getClassLoader();
		String path = cl.getResource("folder6").getPath();
		
		ModuleScanner scanner = new ModuleScanner();
		ClassLoader generatedClassLoader = scanner.scan(path);
		Resolver resolver = IoC.getResolver();
		Class<?> serviceType = Class.forName("com.e3.bizamo.test.ioc6.IServiceDouble", true, generatedClassLoader);
		Object impl = resolver.resolve(serviceType);
		TestCase.assertEquals("com.e3.bizamo.test.ioc6.ServiceImplDouble",impl.getClass().getCanonicalName());
	}

	
}
