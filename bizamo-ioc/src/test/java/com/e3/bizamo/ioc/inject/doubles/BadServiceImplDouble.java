package com.e3.bizamo.ioc.inject.doubles;

public class BadServiceImplDouble implements IServiceDouble {
	public BadServiceImplDouble() {
		throw new RuntimeException();
	}
	public void foo() {
		
	}
}
