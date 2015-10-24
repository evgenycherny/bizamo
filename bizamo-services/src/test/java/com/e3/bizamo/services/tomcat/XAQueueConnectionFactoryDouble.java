package com.e3.bizamo.services.tomcat;

import javax.jms.Connection;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.XAConnection;
import javax.jms.XAJMSContext;
import javax.jms.XAQueueConnection;
import javax.jms.XAQueueConnectionFactory;

public class XAQueueConnectionFactoryDouble implements XAQueueConnectionFactory {

	@Override
	public XAConnection createXAConnection() throws JMSException {
		return null;
	}

	@Override
	public XAConnection createXAConnection(String userName, String password)
			throws JMSException {
		return null;
	}

	@Override
	public XAJMSContext createXAContext() {
		return null;
	}

	@Override
	public XAJMSContext createXAContext(String userName, String password) {
		return null;
	}

	@Override
	public QueueConnection createQueueConnection() throws JMSException {
		return null;
	}

	@Override
	public QueueConnection createQueueConnection(String userName,
			String password) throws JMSException {
		return null;
	}

	@Override
	public Connection createConnection() throws JMSException {
		return null;
	}

	@Override
	public Connection createConnection(String userName, String password)
			throws JMSException {
		return null;
	}

	@Override
	public JMSContext createContext() {
		return null;
	}

	@Override
	public JMSContext createContext(String userName, String password) {
		return null;
	}

	@Override
	public JMSContext createContext(String userName, String password,
			int sessionMode) {
		return null;
	}

	@Override
	public JMSContext createContext(int sessionMode) {
		return null;
	}

	@Override
	public XAQueueConnection createXAQueueConnection() throws JMSException {
		return null;
	}

	@Override
	public XAQueueConnection createXAQueueConnection(String userName,
			String password) throws JMSException {
		return null;
	}

}
