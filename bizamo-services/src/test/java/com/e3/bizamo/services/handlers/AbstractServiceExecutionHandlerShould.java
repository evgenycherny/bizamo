package com.e3.bizamo.services.handlers;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mockito;

import com.e3.bizamo.services.execution.IService;
import com.e3.bizamo.services.execution.ServiceExecutionException;
import com.e3.bizamo.services.parsers.IAPIParser;

public class AbstractServiceExecutionHandlerShould extends Mockito {
	@Test
	public void executeChainFromHead() {
		ServiceExecutionHandlerDouble handler1 = new ServiceExecutionHandlerDouble("1");
		LastServiceExecutionHandlerDouble handler2 = new LastServiceExecutionHandlerDouble("2");
		handler1.setNext(handler2);
		handler2.setNext(null);
		String response=(String)handler1.handle(null, "", null);
		TestCase.assertEquals("12", response);
	}
	@Test
	public void bypassNotRequiredHandler() {
		ServiceExecutionHandlerDouble handler1 = new ServiceExecutionHandlerDouble("1");
		NotRequiredServiceExecutionHandlerDouble handler2 = new NotRequiredServiceExecutionHandlerDouble("2");
		LastServiceExecutionHandlerDouble handler3 = new LastServiceExecutionHandlerDouble("3");
		handler1.setNext(handler2);
		handler2.setNext(handler3);
		handler3.setNext(null);
		String response=(String)handler1.handle(null, "", null);
		TestCase.assertEquals("13", response);
	}
	@Test(expected=ServiceExecutionException.class)
	public void throwWhenNoActualExecuterFound() {
		ServiceExecutionHandlerDouble handler1 = new ServiceExecutionHandlerDouble("1");
		ServiceExecutionHandlerDouble handler2 = new ServiceExecutionHandlerDouble("2");
		handler1.setNext(handler2);
		handler2.setNext(null);
		handler1.handle(null, "", null);
	}
	
	class ServiceExecutionHandlerDouble extends AbstractServiceExecutionHandler {
		private String id;
		ServiceExecutionHandlerDouble(String id) {
			this.id = id;
		}
		@Override
		public Object handle(IService<?, ?> service, Object requestObject, IAPIParser parser) {
			String request = (String)requestObject + id;
			return this.handleNext(service, request, parser);
		}

		@Override
		public boolean isRequired(IService<?, ?> service) {
			return true;
		}
	}
	class LastServiceExecutionHandlerDouble extends AbstractServiceExecutionHandler {
		private String id;
		LastServiceExecutionHandlerDouble(String id) {
			this.id = id;
		}
		@Override
		public Object handle(IService<?, ?> service, Object requestObject, IAPIParser parser) {
			return (String)requestObject + id;
		}

		@Override
		public boolean isRequired(IService<?, ?> service) {
			return true;
		}
	}
	class NotRequiredServiceExecutionHandlerDouble extends AbstractServiceExecutionHandler {
		private String id;
		NotRequiredServiceExecutionHandlerDouble(String id) {
			this.id = id;
		}
		@Override
		public Object handle(IService<?, ?> service, Object requestObject, IAPIParser parser) {
			return (String)requestObject + id;
		}

		@Override
		public boolean isRequired(IService<?, ?> service) {
			return false;
		}
	}
}
