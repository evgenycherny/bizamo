package com.e3.bizamo.services.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.e3.bizamo.commons.cache.Cacheable;
import com.e3.bizamo.components.session.ISession;
import com.e3.bizamo.components.session.Session;
import com.e3.bizamo.dao.session.ISessionDao;
import com.e3.bizamo.ioc.inject.IoC;
import com.e3.bizamo.services.execution.IService;
import com.e3.bizamo.services.execution.NotAuthorizedException;
import com.e3.bizamo.services.execution.doubles.RequestDouble;
import com.e3.bizamo.services.execution.doubles.ResponseDouble;
import com.e3.bizamo.services.execution.doubles.ServiceDouble;
import com.e3.bizamo.services.handlers.doubles.WithAuthenticationAnnotationFalseServiceDouble;
import com.e3.bizamo.services.handlers.doubles.WithAuthenticationAnnotationTrueServiceDouble;
import com.e3.bizamo.services.parsers.IAPIParser;

public class AuthenticationExecutionHandlerShould extends Mockito {
	@Before
	public void setUp() {
		Cacheable.invalidate();
		IoC.getAndCleanResolver();
	}
	@Test
	public void beRequiredWhenAuthenticationAnnotationPresentsAndTrue()  {
		AuthenticationExecutionHandler handler = new AuthenticationExecutionHandler(null);
		
		TestCase.assertTrue(handler.isRequired(new ServiceDouble()));
		TestCase.assertTrue(handler.isRequired(new WithAuthenticationAnnotationTrueServiceDouble()));
		TestCase.assertFalse(handler.isRequired(new WithAuthenticationAnnotationFalseServiceDouble()));
	}
	@Test
	public void allowServiceExecutionWhenValidTokenPresents()  {
		String token = UUID.randomUUID().toString();
		ISessionDao sessionDao = mock(ISessionDao.class);
		ISession session = new Session();
		session.setId(token);
		when(sessionDao.read(token)).thenReturn(session);
		IAPIParser parser = mock(IAPIParser.class);
		when(parser.getSecurityToken()).thenReturn(token);
		
		List<IServiceExecutionHandler> handlers = new ArrayList<IServiceExecutionHandler>();
		handlers.add(new ContextExecutionHandler());
		handlers.add(new AuthenticationExecutionHandler(sessionDao));
		handlers.add(new MainExecutionHandler());
		 
		ServiceExecutionHandlerChain chain = new ServiceExecutionHandlerChain(handlers);
		IService<RequestDouble,ResponseDouble> service = new WithAuthenticationAnnotationTrueServiceDouble();
		
		ResponseDouble response = (ResponseDouble)chain.handleChain(service, new RequestDouble(), parser);
		TestCase.assertEquals("WithAuthenticationAnnotationTrueServiceDouble", response.getS1());
	}
	
	@Test(expected=NotAuthorizedException.class)
	public void failServiceExecutionWhenInValidTokenPresents()  {
		String token = UUID.randomUUID().toString();
		ISessionDao sessionDao = mock(ISessionDao.class);
		when(sessionDao.read(token)).thenReturn(null);
		IAPIParser parser = mock(IAPIParser.class);
		when(parser.getSecurityToken()).thenReturn(token);
		
		List<IServiceExecutionHandler> handlers = new ArrayList<IServiceExecutionHandler>();
		handlers.add(new ContextExecutionHandler());
		handlers.add(new AuthenticationExecutionHandler(sessionDao));
		handlers.add(new MainExecutionHandler());
		
		ServiceExecutionHandlerChain chain = new ServiceExecutionHandlerChain(handlers);
		IService<RequestDouble,ResponseDouble> service = new WithAuthenticationAnnotationTrueServiceDouble();
		chain.handleChain(service, new RequestDouble(), parser);
	}
}
