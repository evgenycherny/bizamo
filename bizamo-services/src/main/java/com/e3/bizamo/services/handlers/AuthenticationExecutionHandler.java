package com.e3.bizamo.services.handlers;

import com.e3.bizamo.components.session.ISession;
import com.e3.bizamo.dao.session.ISessionDao;
import com.e3.bizamo.ioc.inject.annotations.Export;
import com.e3.bizamo.services.annotations.Authenticate;
import com.e3.bizamo.services.context.ServiceExecutionContext;
import com.e3.bizamo.services.execution.IService;
import com.e3.bizamo.services.execution.NotAuthorizedException;
import com.e3.bizamo.services.handlers.annotations.RequiredBeforeHandler;
import com.e3.bizamo.services.handlers.annotations.RequiresHandler;
import com.e3.bizamo.services.parsers.IAPIParser;

@Export(serviceType=IServiceExecutionHandler.class)
@RequiredBeforeHandler(MainExecutionHandler.class)
@RequiresHandler(ContextExecutionHandler.class)
public class AuthenticationExecutionHandler extends AbstractServiceExecutionHandler implements IServiceExecutionHandler { 

	private ISessionDao sessionDao;

	public AuthenticationExecutionHandler(ISessionDao sessionDao) {
		this.sessionDao = sessionDao;
	}
	@Override
	public boolean isRequired(IService<?, ?> service) {
		Authenticate anno = service.getClass().getAnnotation(Authenticate.class);
		if (anno==null) return true;
		return anno.value();
	}

	@Override
	public Object handle(IService<?, ?> service, Object requestObject, IAPIParser parser) {
		String sessionId = parser.getSecurityToken();
		if (sessionId==null) 
			throw new NotAuthorizedException(String.format("This service call requires security token", sessionId));
		ISession session = sessionDao.read(sessionId);
		if (session==null) {
			throw new NotAuthorizedException(String.format("Session [%s] is invalid, service call is not authorized", sessionId));
		}
		ServiceExecutionContext.getRequestContext().setCurrentSession(session);
		
		return handleNext(service, requestObject, parser);
	}
}
