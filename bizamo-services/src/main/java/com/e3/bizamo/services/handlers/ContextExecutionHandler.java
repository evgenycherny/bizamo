package com.e3.bizamo.services.handlers;

import java.sql.Connection;
import java.sql.SQLException;

import com.e3.bizamo.ioc.inject.annotations.Export;
import com.e3.bizamo.services.context.ServiceExecutionContext;
import com.e3.bizamo.services.execution.IService;
import com.e3.bizamo.services.handlers.annotations.RequiredBeforeHandler;
import com.e3.bizamo.services.parsers.IAPIParser;

@Export(serviceType=IServiceExecutionHandler.class)
@RequiredBeforeHandler(MainExecutionHandler.class)
public class ContextExecutionHandler extends AbstractServiceExecutionHandler {
	public ContextExecutionHandler() {}
	
	
	@Override
	public Object handle(IService<?, ?> service, Object requestObject, IAPIParser parser) {
		Object result = handleNext(service, requestObject, parser);
		clearContext();
		return result;
		
	}
	
	private void clearContext() {
		Connection cn = (Connection)ServiceExecutionContext.getRequestContext().get("connection.main");
		closeIfNeeded(cn);
		ServiceExecutionContext.getRequestContext().clear();
	}

	private void closeIfNeeded(Connection cn) {
		if (cn!=null)
			try {
				cn.close();
			} catch (SQLException e) {
			}
	}


	@Override
	public boolean isRequired(IService<?, ?> service) {
		return true;
	}


}
