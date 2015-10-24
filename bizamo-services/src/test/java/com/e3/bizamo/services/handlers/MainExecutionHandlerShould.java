package com.e3.bizamo.services.handlers;

import java.lang.reflect.InvocationTargetException;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.e3.bizamo.services.execution.IService;
import com.e3.bizamo.services.execution.ServiceExecutionException;
import com.e3.bizamo.services.execution.doubles.RequestDouble;
import com.e3.bizamo.services.execution.doubles.ResponseDouble;
import com.e3.bizamo.services.execution.doubles.ServiceDouble;

public class MainExecutionHandlerShould extends Mockito {
	@Test
	public void invokeService() throws NoSuchMethodException, SecurityException {
		MainExecutionHandler handler = new MainExecutionHandler();
		IService<RequestDouble, ResponseDouble> mockService = mock(ServiceDouble.class);
		handler.handle(mockService, new RequestDouble(), null);
		verify(mockService,times(1)).execute(Matchers.any());
	}
	@Test(expected=ServiceExecutionException.class)
	public void throwOnServiceProblems() throws NoSuchMethodException, SecurityException  {
		MainExecutionHandler handler = new MainExecutionHandler();
		IService<RequestDouble, ResponseDouble> mockService = mock(ServiceDouble.class);
		when(mockService.execute(Matchers.any())).thenThrow(new NullPointerException());
		handler.handle(mockService, new RequestDouble(), null);
	}
	@Test(expected=ServiceExecutionException.class)
	public void throwOnOnvocationProblems() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		MainExecutionHandler handler = new MainExecutionHandler();
		handler.handle(null, new RequestDouble(), null);
	}
	
	@Test
	public void alwaysBeRequired()  {
		MainExecutionHandler handler = new MainExecutionHandler();
		TestCase.assertTrue(handler.isRequired(null));
	}
}
