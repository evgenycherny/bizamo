package com.e3.bizamo.services.tomcat;

import java.util.Hashtable;
import java.util.Vector;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.StringRefAddr;

import junit.framework.TestCase;

import org.apache.naming.ResourceRef;
import org.junit.Test;
import org.mockito.Mockito;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.atomikos.jms.AtomikosConnectionFactoryBean;

public class TomcatAtomikosBeanFactoryShould extends Mockito {
	@Test
	public void createInstanceOfDataSourceBean() throws NamingException {
		ResourceRef resourceRef = new ResourceRef("com.atomikos.jdbc.AtomikosDataSourceBean","AtomikosDataSourceBean","Shareable","Container",true);
		resourceRef.add(new StringRefAddr("uniqueResourceName","jdbc/main"));
		resourceRef.add(new StringRefAddr("xaDataSourceClassName","com.mysql.jdbc.jdbc2.optional.MysqlXADataSource"));
		resourceRef.add(new StringRefAddr("uniqueResourceName","jdbc/main"));

		Vector<String> components = new Vector<String>();
		components.add("main");
		components.add("main");
		NameDouble name = new NameDouble(components.elements());
		@SuppressWarnings("rawtypes")
		Hashtable<?,?> ht = new Hashtable();
		Object obj = new TomcatAtomikosBeanFactory().getObjectInstance(resourceRef, name, new InitialContext(), ht);
		TestCase.assertTrue(obj instanceof AtomikosDataSourceBean);
	}
	@Test(expected=NamingException.class)
	public void failOnNonExistingBean() throws NamingException {
		ResourceRef resourceRef = new ResourceRef("FakeBean","AtomikosDataSourceBean","Shareable","Container",true);
		resourceRef.add(new StringRefAddr("uniqueResourceName","jdbc/main"));
		resourceRef.add(new StringRefAddr("xaDataSourceClassName","com.mysql.jdbc.jdbc2.optional.MysqlXADataSource"));
		resourceRef.add(new StringRefAddr("uniqueResourceName","jdbc/main"));

		Vector<String> components = new Vector<String>();
		components.add("main");
		components.add("main");
		NameDouble name = new NameDouble(components.elements());
		@SuppressWarnings("rawtypes")
		Hashtable<?,?> ht = new Hashtable();
		new TomcatAtomikosBeanFactory().getObjectInstance(resourceRef, name, new InitialContext(), ht);
	}
	@Test(expected=NamingException.class)
	public void failOnBeanThatNotAtomikosDataSourceBean() throws NamingException {
		ResourceRef resourceRef = new ResourceRef("com.e3.bizamo.services.doubles.RequestDouble","AtomikosDataSourceBean","Shareable","Container",true);
		resourceRef.add(new StringRefAddr("uniqueResourceName","jdbc/main"));
		resourceRef.add(new StringRefAddr("xaDataSourceClassName","com.mysql.jdbc.jdbc2.optional.MysqlXADataSource"));

		Vector<String> components = new Vector<String>();
		components.add("main");
		NameDouble name = new NameDouble(components.elements());
		@SuppressWarnings("rawtypes")
		Hashtable<?,?> ht = new Hashtable();
		new TomcatAtomikosBeanFactory().getObjectInstance(resourceRef, name, new InitialContext(), ht);
	}
	@Test
	public void createBeanFromFactory() throws NamingException {
		ResourceRef resourceRef = new ResourceRef("com.atomikos.jms.AtomikosConnectionFactoryBean","AtomikosDataSourceBean","Shareable","Container",true);
		resourceRef.add(new StringRefAddr("uniqueResourceName","jdbc/queue"));
		resourceRef.add(new StringRefAddr("xaConnectionFactoryClassName","com.e3.bizamo.services.tomcat.XAQueueConnectionFactoryDouble"));
		resourceRef.add(new StringRefAddr("uniqueResourceName","jdbc/queue"));

		Vector<String> components = new Vector<String>();
		components.add("queue");
		NameDouble name = new NameDouble(components.elements());
		@SuppressWarnings("rawtypes")
		Hashtable<?,?> ht = new Hashtable();
		Object obj = new TomcatAtomikosBeanFactory().getObjectInstance(resourceRef, name, new InitialContext(), ht);
		TestCase.assertTrue(obj instanceof AtomikosConnectionFactoryBean);
	}



}
