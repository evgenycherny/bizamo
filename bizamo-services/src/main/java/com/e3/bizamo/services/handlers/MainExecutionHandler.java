package com.e3.bizamo.services.handlers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.e3.bizamo.ioc.inject.annotations.Export;
import com.e3.bizamo.services.execution.IService;
import com.e3.bizamo.services.execution.ServiceExecutionException;
import com.e3.bizamo.services.parsers.IAPIParser;

@Export(serviceType=IServiceExecutionHandler.class)
public class MainExecutionHandler extends AbstractServiceExecutionHandler {
	public MainExecutionHandler() {}
	@Override
	public Object handle(IService<?, ?> service, Object requestObject, IAPIParser parser) {
		try {
			
			Method method = service.getClass().getMethod("execute", requestObject.getClass());
			return method.invoke(service, requestObject);
		}
		catch(InvocationTargetException e) {
			throw new ServiceExecutionException(e.getTargetException());
		}
		catch(Throwable e) {
			throw new ServiceExecutionException(e);
		}
	}

	@Override
	public boolean isRequired(IService<?, ?> service) {
		return true;
	}
}
