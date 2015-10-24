package com.e3.bizamo.services.execution;

import java.util.List;

import com.e3.bizamo.commons.cache.Cacheable;
import com.e3.bizamo.ioc.inject.IoC;
import com.e3.bizamo.services.handlers.IServiceExecutionHandler;
import com.e3.bizamo.services.handlers.ServiceExecutionHandlerChain;
import com.e3.bizamo.services.locator.IServiceLocator;
import com.e3.bizamo.services.locator.ServiceDescriptor;
import com.e3.bizamo.services.locator.ServiceInfo;
import com.e3.bizamo.services.locator.ServiceLocator;
import com.e3.bizamo.services.parsers.IAPIParser;

public class ServiceExecuter {
	private IAPIParser parser;
	private List<IServiceExecutionHandler> handlers;
	ServiceExecuter() {

	}
	public ServiceExecuter(IAPIParser parser) {
		this.parser = parser;
		this.handlers = Cacheable.get(()->IoC.getResolver().resolveAll(IServiceExecutionHandler.class));
	}
	public void executePipeline() {
		try {
			ServiceInfo info = locateServiceInfoFromParser();
			IService<?,?> service = info.getService();
			
			Object requestObject = parser.getServiceRequest(info.getRequestClass());
			Object responseObject = executePipeline(service, requestObject);
			parser.writeResponse(responseObject);

		} catch (Throwable e) {
			parser.writeErrorResponse(e);
		}
	}
	private ServiceInfo locateServiceInfoFromParser() {
		ServiceDescriptor descriptor = parser.getServiceDescriptor();
		IServiceLocator locator = new ServiceLocator();
		return locator.getServiceInfo(descriptor);
	}
	
	private Object executePipeline(IService<?, ?> service, Object requestObject) {
		ServiceExecutionHandlerChain chain = new ServiceExecutionHandlerChain(handlers);
		Object responseObject = chain.handleChain(service, requestObject, parser);
		return responseObject;
	}
}
