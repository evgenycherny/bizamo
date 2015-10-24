package com.e3.bizamo.ioc.inject;

import com.e3.bizamo.ioc.module.ModuleScanner;


public class IoC {
	
	private static ModuleScanner scanner = new ModuleScanner();
	private static final Resolver resolver = new Resolver();
	
	IoC() {}
	
	public static Resolver getResolver() {
		return resolver;
	}
	public static Resolver getAndCleanResolver() {
		resolver.reset();
		return resolver;
	}
	public static void init(String classpath) {
		scanner.scan(classpath);
	}
	static void setScanner(ModuleScanner replacementScanner) {
		scanner = replacementScanner; 
	}
	
}
