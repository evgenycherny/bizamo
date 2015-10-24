package com.e3.bizamo.services.handlers.generic;

import java.lang.reflect.Method;

import com.e3.bizamo.services.execution.IService;
import com.e3.bizamo.services.handlers.generic.AbstractChainableHandler;



public abstract class AbstractHandlerDouble extends AbstractChainableHandler<IHandlerDouble> implements IHandlerDouble{
	protected Object handleNext(IService<?,?> service, Object requestObject, Method execute) {
		if (this.getNext()!=null) return this.getNext().handle(service, requestObject, execute);
		else return null;
	}
}
