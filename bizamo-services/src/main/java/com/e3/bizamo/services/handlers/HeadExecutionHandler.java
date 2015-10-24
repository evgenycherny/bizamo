package com.e3.bizamo.services.handlers;

import com.e3.bizamo.services.execution.IService;
import com.e3.bizamo.services.parsers.IAPIParser;

public class HeadExecutionHandler extends AbstractServiceExecutionHandler implements IServiceExecutionHandler { 

	public HeadExecutionHandler() {
	}
	@Override
	public boolean isRequired(IService<?, ?> service) {
		return true;
	}

	@Override
	public Object handle(IService<?, ?> service, Object requestObject, IAPIParser parser) {
		return handleNext(service, requestObject, parser);
	}
}
