package com.e3.bizamo.services.handlers.generic.doubles;

import java.lang.reflect.Method;

import com.e3.bizamo.services.execution.IService;
import com.e3.bizamo.services.handlers.annotations.RequiredBeforeHandler;
import com.e3.bizamo.services.handlers.generic.AbstractHandlerDouble;

@RequiredBeforeHandler(Handler1Double.class)
public class HandlerRequiredBefore1Double extends AbstractHandlerDouble {
	private String id;
	public HandlerRequiredBefore1Double(String id) {
		this.id = id;
	}
	@Override
	public Object handle(IService<?, ?> service, Object requestObject, Method execute) {
		String request = (String)requestObject;
		request+=id;
		Object response = handleNext(service, request, execute);
		if (response==null) response=request;
		return response;
	}

	@Override
	public boolean isRequired(IService<?, ?> service) {
		return true;
	}
}
