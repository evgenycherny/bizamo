package com.e3.bizamo.ioc.inject.doubles;

import com.e3.bizamo.ioc.inject.annotations.Inject;

public class AnnotatedConstructorServiceImplDouble implements IServiceDouble {
	private boolean annotatedConstructorCalled;
	
	@Inject
	public AnnotatedConstructorServiceImplDouble() {
		annotatedConstructorCalled = true;
	}
	
	public AnnotatedConstructorServiceImplDouble(IAnotherServiceDouble asd) {
		annotatedConstructorCalled = false;
	}
	public void foo() {}
	public boolean isAnnotatedConstructorCalled() {
		return annotatedConstructorCalled;
	}


}
