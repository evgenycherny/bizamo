package com.e3.bizamo.services.handlers.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.e3.bizamo.services.handlers.generic.IChainableHandler;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RequiredBeforeHandler {
	Class<? extends IChainableHandler<?>> value();
}
