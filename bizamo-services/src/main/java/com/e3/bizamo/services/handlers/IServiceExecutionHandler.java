package com.e3.bizamo.services.handlers;

import com.e3.bizamo.services.execution.IService;
import com.e3.bizamo.services.handlers.generic.IChainableHandler;
import com.e3.bizamo.services.parsers.IAPIParser;

public interface IServiceExecutionHandler extends IChainableHandler<IServiceExecutionHandler> {
	Object handle(IService<?, ?> service, Object requestObject, IAPIParser parser);
	boolean isRequired(IService<?, ?> service);
}
