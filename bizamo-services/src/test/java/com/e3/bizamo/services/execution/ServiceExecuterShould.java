package com.e3.bizamo.services.execution;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.e3.bizamo.commons.cache.Cacheable;
import com.e3.bizamo.ioc.inject.IoC;
import com.e3.bizamo.services.execution.ServiceExecuter;
import com.e3.bizamo.services.execution.ServiceExecutionException;
import com.e3.bizamo.services.execution.doubles.RequestDouble;
import com.e3.bizamo.services.execution.doubles.ResponseDouble;
import com.e3.bizamo.services.execution.doubles.ServiceDouble;
import com.e3.bizamo.services.execution.doubles.ServiceWithErrorDouble;
import com.e3.bizamo.services.handlers.IServiceExecutionHandler;
import com.e3.bizamo.services.handlers.MainExecutionHandler;
import com.e3.bizamo.services.locator.ServiceDescriptor;
import com.e3.bizamo.services.locator.ServiceLocator;
import com.e3.bizamo.services.parsers.IAPIParser;
import com.e3.bizamo.services.parsers.Version;

public class ServiceExecuterShould extends Mockito {
	@Before
	public void setUp() {
		IoC.getAndCleanResolver().register(IServiceExecutionHandler.class, MainExecutionHandler.class);
		Cacheable.invalidate();
	}
	@Test
	public void constructorCoverageStub() {
		new ServiceExecuter();
	}
	@Test
	public void executeService() {
		IAPIParser parser = prepareParserMock("someservice", ServiceDouble.class);
		
		ServiceExecuter executer = new ServiceExecuter(parser);
		executer.executePipeline();
		ResponseDouble expectedResponse = new ResponseDouble();
		expectedResponse.setS1("executed");
		
		verify(parser, times(1)).writeResponse(expectedResponse);
	}
	@Test
	public void executeServiceWithError() {
		IAPIParser parser = prepareParserMock("somebadservice", ServiceWithErrorDouble.class);
		
		ServiceExecuter executer = new ServiceExecuter(parser);
		executer.executePipeline();
		
		verify(parser, times(1)).writeErrorResponse(new ServiceExecutionException("java.lang.RuntimeException: msg"));
		verify(parser, times(0)).writeResponse(null);
	}
	@Test
	public void executePipelineHandlers() {
		IAPIParser parser = prepareParserMock("someservice", ServiceDouble.class);
		
		ServiceExecuter executer = new ServiceExecuter(parser);
		executer.executePipeline();
		ResponseDouble expectedResponse = new ResponseDouble();
		expectedResponse.setS1("executed");
		
		verify(parser, times(1)).writeResponse(expectedResponse);
	}
	private IAPIParser prepareParserMock(String name, Class<?> serviceClass) {
		ServiceDescriptor sd = new ServiceDescriptor(name, new Version(1));
		IAPIParser parser = mock(IAPIParser.class);
		when(parser.getServiceDescriptor()).thenReturn(sd);
		when(parser.getServiceRequest(RequestDouble.class)).thenReturn(new RequestDouble());
		ServiceLocator sl = new ServiceLocator();
		sl.registerService(sd, serviceClass);
		return parser;
	}
	

}
