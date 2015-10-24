package com.e3.bizamo.services.module;

import javax.naming.Context;

import com.e3.bizamo.ioc.inject.FactoryBinding;
import com.e3.bizamo.ioc.inject.IoC;
import com.e3.bizamo.ioc.inject.Resolver;
import com.e3.bizamo.ioc.module.IModuleInitializer;
import com.e3.bizamo.services.authentication.LoginService;
import com.e3.bizamo.services.execution.ContextFactory;
import com.e3.bizamo.services.locator.ServiceDescriptor;
import com.e3.bizamo.services.locator.ServiceLocator;
import com.e3.bizamo.services.parsers.APIParser;
import com.e3.bizamo.services.parsers.IAPIParser;
import com.e3.bizamo.services.parsers.Version;

public class Initializer implements IModuleInitializer {

	@Override
	public void initModule() {
		Resolver resolver = IoC.getResolver();
		resolver.register("REST", IAPIParser.class, APIParser.class);
		resolver.register("BASE", Context.class, new FactoryBinding(new ContextFactory()));

		ServiceLocator locator = new ServiceLocator();
		locator.registerService(new ServiceDescriptor().setServicePackage("user").setName("Login").setVersion(Version.parse("1.0")), 
			LoginService.class);
		//resolver.register(IServiceExecutionHandler.class, MainExecutionHandler.class);
		//resolver.register(IServiceExecutionHandler.class, ContextExecutionHandler.class);
		//resolver.register(IServiceExecutionHandler.class, TransactionalExecutionHandler.class);
		
	}

}
