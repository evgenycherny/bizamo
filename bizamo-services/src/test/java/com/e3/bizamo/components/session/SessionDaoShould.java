package com.e3.bizamo.components.session;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import javax.naming.Context;
import javax.naming.NamingException;

import junit.framework.TestCase;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.e3.bizamo.dao.session.ISessionDao;
import com.e3.bizamo.dao.session.SessionDao;
import com.e3.bizamo.ioc.inject.FactoryBinding;
import com.e3.bizamo.ioc.inject.IoC;
import com.e3.bizamo.ioc.inject.Resolver;
import com.e3.bizamo.services.execution.ConnectionFactory;
import com.e3.bizamo.services.execution.ContextFactory;
import com.e3.bizamo.services.execution.IConnectionFactory;

public class SessionDaoShould extends Mockito {
	private ISessionDao sessionDao;
	
	@Before
	public void setUp() throws NamingException, SQLException {
		Resolver resolver = IoC.getAndCleanResolver();
		resolver.register(ISessionDao.class, SessionDao.class);
		resolver.register(ISession.class, Session.class);
		
		resolver.register(Context.class, new FactoryBinding(new ContextFactory()));
		ConnectionFactory connectionFactory = mock(ConnectionFactory.class);
		resolver.register(IConnectionFactory.class, connectionFactory);
		
		System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
        System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");            
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:test;MODE=MYSQL");
        Connection cn = ds.getConnection();
        cn.createStatement().execute(
        		"CREATE TABLE IDM_Session(Id VARCHAR(100) NOT NULL, UserId VARCHAR(100), LastActive datetime DEFAULT CURRENT_TIMESTAMP, Created datetime DEFAULT CURRENT_TIMESTAMP, Updated datetime DEFAULT CURRENT_TIMESTAMP, PRIMARY KEY(Id))");        		
        when(connectionFactory.createConnection()).thenReturn(cn);
		
		this.sessionDao = resolver.resolve(ISessionDao.class);
	}

	@Test
	public void saveAndReadSession() {
		String sessionId = UUID.randomUUID().toString();
		ISession sessionToSave = sessionDao.createInstance();
		sessionToSave.setId(sessionId);
		sessionDao.save(sessionToSave);
		
		ISession session = sessionDao.read(sessionId);
		TestCase.assertNotNull(session);
		TestCase.assertEquals(sessionToSave, session);
	}
	
}
