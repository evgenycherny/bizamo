package com.e3.bizamo.services.execution;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mockito;

import com.e3.bizamo.commons.exceptions.ApplicationException;
import com.e3.bizamo.ioc.inject.IoC;

public class ConnectionFactoryShould extends Mockito {
	@Test
	public void constructorCoverageStub() throws NamingException {
		new ConnectionFactory(new InitialContext());
	}
	@Test
	public void createConnection() throws NamingException, SQLException {
		Context ctxMock = mock(Context.class);
		DataSource dataSourceMock = mock(DataSource.class);
		when(dataSourceMock.getConnection()).thenReturn(mock(Connection.class));
		when(ctxMock.lookup("java:comp/env/jdbc/main")).thenReturn(dataSourceMock);
		IoC.getAndCleanResolver().register(Context.class, ctxMock);
		ConnectionFactory factory = new ConnectionFactory(ctxMock);
		TestCase.assertTrue(factory.createConnection() instanceof Connection);
	}
	@Test(expected=ApplicationException.class)
	public void failWhenConnectionCantBeLookedUp() throws NamingException {
		Context ctxMock = mock(Context.class);
		when(ctxMock.lookup("java:comp/env/jdbc/main")).thenThrow(new NamingException());
		IoC.getAndCleanResolver().register(Context.class, ctxMock);
		ConnectionFactory factory = new ConnectionFactory(ctxMock);
		factory.createConnection();
	}


}
