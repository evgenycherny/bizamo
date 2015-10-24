package com.e3.bizamo.ioc.inject.doubles;

import com.e3.bizamo.ioc.inject.annotations.Selector;



public class SelectorAnnoServiceImplDouble implements IServiceDouble {
	private boolean flag;

	public SelectorAnnoServiceImplDouble() {
		flag = false;
	}
	
	public SelectorAnnoServiceImplDouble(@Selector(SelectorDouble.class)IAnotherServiceDouble p) {
		flag = true;
	}
	
	
	public void foo() {}
	public boolean isFlag() {
		return flag;
	}

}
