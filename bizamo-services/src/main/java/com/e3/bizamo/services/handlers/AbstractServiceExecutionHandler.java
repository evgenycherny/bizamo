package com.e3.bizamo.services.handlers;

import com.e3.bizamo.services.execution.IService;
import com.e3.bizamo.services.execution.ServiceExecutionException;
import com.e3.bizamo.services.handlers.generic.AbstractChainableHandler;
import com.e3.bizamo.services.parsers.IAPIParser;

public abstract class AbstractServiceExecutionHandler extends AbstractChainableHandler<IServiceExecutionHandler> implements IServiceExecutionHandler{
	protected Object handleNext(IService<?,?> service, Object requestObject, IAPIParser parser) {
		IServiceExecutionHandler next = getNext();
		while (next!=null && !next.isRequired(service)) {
			next = next.getNext();
		}
		if (next!=null) return next.handle(service, requestObject, parser);

		throw new ServiceExecutionException("Last handler reached without actual invoke. Ensure that service handler chain ends with MainExecutionHandler");
	}
}
