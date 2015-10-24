package com.e3.bizamo.services.context;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mockito;

public class ServiceExecutionContextShould extends Mockito {
	@Test
	public void constructorCoverageStub() {
		new ServiceExecutionContext();
	}
	
	@Test
	public void maintainContextValues() {
		RequestContext ctx1 = ServiceExecutionContext.getRequestContext();
		ctx1.put("key", "value");
		RequestContext ctx2 = ServiceExecutionContext.getRequestContext();
		TestCase.assertEquals("value", ctx2.get("key"));
	}
	
	@Test
	public void maintainContexPerThread() throws InterruptedException {
		RequestContext ctx1 = ServiceExecutionContext.getRequestContext();
		ctx1.put("key", "value");
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				RequestContext ctx = ServiceExecutionContext.getRequestContext();
				if (ctx!=null) ctx.put("key", "OTHERVALUE");
			}});
		t.start();
		t.join();
		
		RequestContext ctx2 = ServiceExecutionContext.getRequestContext();
		TestCase.assertEquals("value", ctx2.get("key"));
	}


}
