package com.e3.bizamo.services.facade;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mockito;

import com.e3.bizamo.ioc.inject.IoC;
import com.e3.bizamo.ioc.inject.Resolver;
import com.e3.bizamo.services.execution.IService;
import com.e3.bizamo.services.execution.doubles.RequestDouble;
import com.e3.bizamo.services.execution.doubles.ResponseDouble;
import com.e3.bizamo.services.execution.doubles.ServiceDouble;
import com.e3.bizamo.services.locator.ServiceDescriptor;
import com.e3.bizamo.services.parsers.IAPIParser;
import com.e3.bizamo.services.parsers.Version;


public class APIServletShould extends Mockito {
	
	@Test
	public void serveServiceRequest() throws ServletException, IOException {
		IAPIParser apiParser = mock(IAPIParser.class);
		when(apiParser.getServiceDescriptor())
		.thenReturn(new ServiceDescriptor().setName("ServiceDouble").setVersion(Version.parse("1.0.0")));
		
		IService<RequestDouble, ResponseDouble> service = new ServiceDouble();
		Resolver resolver = IoC.getAndCleanResolver();
		resolver.register("TEST", IAPIParser.class, apiParser);
		resolver.register("service::servicedouble:1.0.0:post",IService.class, service);
		
		ServletConfig servletConfig = mock(ServletConfig.class);
		when(servletConfig.getInitParameter("parser")).thenReturn("TEST");
		
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		APIServlet servlet = new APIServlet();
		servlet.init(servletConfig);
		servlet.service(request, response);
	}
}
