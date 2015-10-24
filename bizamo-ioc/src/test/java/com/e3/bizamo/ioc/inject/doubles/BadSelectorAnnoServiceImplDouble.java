package com.e3.bizamo.ioc.inject.doubles;

import com.e3.bizamo.ioc.inject.annotations.Selector;



public class BadSelectorAnnoServiceImplDouble implements IServiceDouble {
	private boolean flag;

	public BadSelectorAnnoServiceImplDouble() {
		flag = false;
	}
	
	public BadSelectorAnnoServiceImplDouble(@Selector(BadSelectorDouble.class)IAnotherServiceDouble p) {
		flag = true;
	}
	
	
	public void foo() {}
	public boolean isFlag() {
		return flag;
	}

}
