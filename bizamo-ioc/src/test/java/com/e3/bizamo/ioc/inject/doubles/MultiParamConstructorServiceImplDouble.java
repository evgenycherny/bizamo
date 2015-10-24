package com.e3.bizamo.ioc.inject.doubles;

import com.e3.bizamo.ioc.inject.annotations.Key;


public class MultiParamConstructorServiceImplDouble implements IServiceDouble {
	private boolean flag;
	private IAnotherServiceDouble param1;
	private IAnotherServiceDouble param2;
	public MultiParamConstructorServiceImplDouble() {
		flag = false;
	}
	
	public MultiParamConstructorServiceImplDouble(IAnotherServiceDouble p1) {
		flag = false;
	}
	
	public MultiParamConstructorServiceImplDouble(@Key("key1")IAnotherServiceDouble p1, @Key("key2")IAnotherServiceDouble p2) {
		param1 = p1;
		param2 = p2;
		flag = true;
	}
	
	public void foo() {}
	public boolean isFlag() {
		return flag;
	}
	
	public IAnotherServiceDouble getServiceParam1() {
		return param1;
	}
	public IAnotherServiceDouble getServiceParam2() {
		return param2;
	}


}
