package com.e3.bizamo.services.handlers;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mockito;

import com.e3.bizamo.services.context.ServiceExecutionContext;

public class ContextExecutionHandlerShould extends Mockito {
	@Test
	public void createContext() {
		ContextExecutionHandler handler = new ContextExecutionHandler();
		IServiceExecutionHandler lastHandler = mock(IServiceExecutionHandler.class);
		when(lastHandler.handle(null, null, null)).thenReturn("answer");
		when(lastHandler.isRequired(null)).thenReturn(true);
		handler.setNext(lastHandler);
		handler.handle(null, null, null);
		TestCase.assertNotNull(ServiceExecutionContext.getRequestContext());
	}
	@Test
	public void alwaysBeRequired()  {
		ContextExecutionHandler handler = new ContextExecutionHandler();
		TestCase.assertTrue(handler.isRequired(null));
	}
}
