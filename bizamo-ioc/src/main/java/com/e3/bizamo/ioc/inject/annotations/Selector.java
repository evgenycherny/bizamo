package com.e3.bizamo.ioc.inject.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.e3.bizamo.ioc.inject.ISelector;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Selector {
	public Class<? extends ISelector> value();
}
