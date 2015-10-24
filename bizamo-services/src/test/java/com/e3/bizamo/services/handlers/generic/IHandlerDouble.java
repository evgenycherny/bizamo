package com.e3.bizamo.services.handlers.generic;

import java.lang.reflect.Method;

import com.e3.bizamo.services.execution.IService;
import com.e3.bizamo.services.handlers.generic.IChainableHandler;

public interface IHandlerDouble extends IChainableHandler<IHandlerDouble> {
	Object handle(IService<?, ?> service, Object requestObject, Method execute);
	boolean isRequired(IService<?, ?> service);
}
