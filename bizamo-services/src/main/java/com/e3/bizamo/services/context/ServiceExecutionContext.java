package com.e3.bizamo.services.context;

public class ServiceExecutionContext {
	private static ThreadLocal<RequestContext> requestContextOfThread = new ThreadLocal<RequestContext>();

	ServiceExecutionContext() {}
	public static RequestContext getRequestContext() {
		RequestContext context = requestContextOfThread.get();
		if (context==null) {
			context = createRequestContext();
		}
		return context;
	}
	private static RequestContext createRequestContext() {
		RequestContext context = new RequestContext();
		requestContextOfThread.set(context);
		return context;
	}
	
}
