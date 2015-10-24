package com.e3.bizamo.components.account;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingException;

import junit.framework.TestCase;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.e3.bizamo.commons.properties.IPropertyHolder;
import com.e3.bizamo.commons.properties.PropertyHolder;
import com.e3.bizamo.dao.account.IUserAccountDao;
import com.e3.bizamo.dao.account.UserAccountDao;
import com.e3.bizamo.ioc.inject.FactoryBinding;
import com.e3.bizamo.ioc.inject.IoC;
import com.e3.bizamo.ioc.inject.Resolver;
import com.e3.bizamo.services.execution.ConnectionFactory;
import com.e3.bizamo.services.execution.ContextFactory;
import com.e3.bizamo.services.execution.IConnectionFactory;

public class UserAccountDaoShould extends Mockito {
	private IUserAccountDao userAccountDao;
	
	@Before
	public void setUp() throws NamingException, SQLException {
		IPropertyHolder holder = new PropertyHolder();
		Properties props = new Properties();
		props.setProperty("password.digest.algorithm", "SHA-256");
		holder.setProperties(props);
		
		Resolver resolver = IoC.getAndCleanResolver();
		resolver.register(IPropertyHolder.class, holder);
		resolver.register(IUserAccountDao.class, UserAccountDao.class);
		resolver.register(IUserAccount.class, UserAccount.class);
		
		resolver.register(Context.class, new FactoryBinding(new ContextFactory()));
		ConnectionFactory connectionFactory = mock(ConnectionFactory.class);
		resolver.register(IConnectionFactory.class, connectionFactory);
		
		System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
        System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");            
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:test;MODE=MYSQL");
        Connection cn = ds.getConnection();
        cn.createStatement().execute(
        		"CREATE TABLE IDM_Account(Id INT(11) NOT NULL AUTO_INCREMENT, Email VARCHAR(128) NOT NULL, Password BLOB NOT NULL, Status INT(11),Created datetime DEFAULT CURRENT_TIMESTAMP, Updated datetime DEFAULT CURRENT_TIMESTAMP, PRIMARY KEY(Id), UNIQUE KEY Email_UNIQUE (Email))");        		
        when(connectionFactory.createConnection()).thenReturn(cn);
		
		this.userAccountDao = resolver.resolve(IUserAccountDao.class);
	}

	@Test
	public void saveAndReadUser() {
		IUserAccount userAccountToSave = userAccountDao.create();
		userAccountToSave.setUserName("test");
		userAccountToSave.setPassword("1234");
		userAccountDao.save(userAccountToSave);
		
		IUserAccount userAccount = userAccountDao.read("test");
		TestCase.assertNotNull(userAccount);
		TestCase.assertEquals(userAccountToSave, userAccount);
	}
	
}
