package com.e3.bizamo.services.handlers;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mockito;

import com.e3.bizamo.commons.cache.Cacheable;
import com.e3.bizamo.services.execution.IService;
import com.e3.bizamo.services.parsers.IAPIParser;

public class ServiceExecutionHandlerChainShould extends Mockito {
	@Test
	public void constructorCoverageStub() {
		new ServiceExecutionHandlerChain();
	}
	@Test
	public void checkRequirementOfAllHAndlers() {
		Cacheable.invalidate();
		HandlerDouble1 h1 = new HandlerDouble1(false);
		HandlerDouble2 h2 = new HandlerDouble2(true);
		HandlerDouble3 h3 = new HandlerDouble3(true);
		
		List<IServiceExecutionHandler> handlers = Arrays.asList(new IServiceExecutionHandler[]{h1, h2, h3});
		ServiceExecutionHandlerChain chain = new ServiceExecutionHandlerChain(handlers);
		chain.handleChain(null, null, null);
		
		TestCase.assertEquals(1, h1.getExecCount_isRequired());
		TestCase.assertEquals(1, h2.getExecCount_isRequired());
		TestCase.assertEquals(1, h3.getExecCount_isRequired());
		
		TestCase.assertEquals(0, h1.getExecCount_handle());
		TestCase.assertEquals(1, h2.getExecCount_handle());
		TestCase.assertEquals(1, h3.getExecCount_handle());
	}
	
	class HandlerDouble1 extends AbstractServiceExecutionHandler {
		private int execCount_handle = 0;
		private int execCount_isRequired = 0;
		private boolean required;
		HandlerDouble1(boolean required) {
			this.required = required;
		}
		@Override
		public Object handle(IService<?, ?> service, Object requestObject,
				IAPIParser parser) {
			execCount_handle++;
			return handleNext(service, requestObject, parser);
		}

		@Override
		public boolean isRequired(IService<?, ?> service) {
			execCount_isRequired++;
			return required;
		}
		
		public int getExecCount_handle() {
			return execCount_handle;
		}
		public int getExecCount_isRequired() {
			return execCount_isRequired;
		}
	}
	class HandlerDouble2 extends AbstractServiceExecutionHandler {
		private int execCount_handle = 0;
		private int execCount_isRequired = 0;
		private boolean required;
		HandlerDouble2(boolean required) {
			this.required = required;
		}
		@Override
		public Object handle(IService<?, ?> service, Object requestObject,
				IAPIParser parser) {
			execCount_handle++;
			return handleNext(service, requestObject, parser);
		}

		@Override
		public boolean isRequired(IService<?, ?> service) {
			execCount_isRequired++;
			return required;
		}
		
		public int getExecCount_handle() {
			return execCount_handle;
		}
		public int getExecCount_isRequired() {
			return execCount_isRequired;
		}
	}
	class HandlerDouble3 extends AbstractServiceExecutionHandler {
		private int execCount_handle = 0;
		private int execCount_isRequired = 0;
		private boolean required;
		HandlerDouble3(boolean required) {
			this.required = required;
		}
		@Override
		public Object handle(IService<?, ?> service, Object requestObject,
				IAPIParser parser) {
			execCount_handle++;
			return null;
		}

		@Override
		public boolean isRequired(IService<?, ?> service) {
			execCount_isRequired++;
			return required;
		}
		
		public int getExecCount_handle() {
			return execCount_handle;
		}
		public int getExecCount_isRequired() {
			return execCount_isRequired;
		}
	}
}
