package com.e3.bizamo.services.handlers;

import java.util.List;

import com.e3.bizamo.commons.cache.Cacheable;
import com.e3.bizamo.services.execution.IService;
import com.e3.bizamo.services.handlers.generic.HandlerChainBuilder;
import com.e3.bizamo.services.parsers.IAPIParser;


public class ServiceExecutionHandlerChain extends HandlerChainBuilder<IServiceExecutionHandler> {
	public ServiceExecutionHandlerChain(List<IServiceExecutionHandler> handlers) {
		super(handlers);
	}
	protected ServiceExecutionHandlerChain() {
		super();
	}
	public Object handleChain(IService<?, ?> service, Object requestObject, IAPIParser parser) {
		IServiceExecutionHandler first = Cacheable.get(()->build());
		HeadExecutionHandler head = new HeadExecutionHandler();
		head.setNext(first);
		return head.handle(service, requestObject, parser);
	}
}

