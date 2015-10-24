package com.e3.bizamo.services.execution;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.e3.bizamo.commons.exceptions.ApplicationException;
import com.e3.bizamo.ioc.inject.IFactory;
import com.e3.bizamo.ioc.inject.Resolver;

public class ContextFactory implements IFactory<Context> {

	public ContextFactory() {
		
	}
	@Override
	public Context create(Resolver resolver) {
		try {
			return new InitialContext();
		} catch (NamingException e) {
			throw new ApplicationException(e);
		}
	}

}
