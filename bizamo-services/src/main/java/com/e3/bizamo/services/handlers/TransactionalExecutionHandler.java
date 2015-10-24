package com.e3.bizamo.services.handlers;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import com.e3.bizamo.commons.instrumentation.Debugging;
import com.e3.bizamo.ioc.inject.annotations.Export;
import com.e3.bizamo.services.annotations.Transactional;
import com.e3.bizamo.services.execution.IService;
import com.e3.bizamo.services.execution.ServiceExecutionException;
import com.e3.bizamo.services.handlers.annotations.RequiredBeforeHandler;
import com.e3.bizamo.services.parsers.IAPIParser;

@Export(serviceType=IServiceExecutionHandler.class)
@RequiredBeforeHandler(MainExecutionHandler.class)
public class TransactionalExecutionHandler extends AbstractServiceExecutionHandler {
	private Context context;
	public TransactionalExecutionHandler(Context ctx) {
		context = ctx;
	}
	@Override
	public Object handle(IService<?, ?> service, Object requestObject, IAPIParser parser) {
		UserTransaction ut = null;
		Object responseObject = null;
		try {
			ut = createTransaction();
			if (Debugging.isDebugMode()) ut.setTransactionTimeout(3600);
			ut.begin();
			responseObject = handleNext(service, requestObject, parser);
			ut.commit();
		}
		catch(ServiceExecutionException e) {
			rollback(ut, e);
			throw e;
		}
		catch(Throwable e) {
			rollback(ut, e);
			throw new ServiceExecutionException(e);
		}
		return responseObject;
	}

	@Override
	public boolean isRequired(IService<?, ?> service) {
		Transactional anno = service.getClass().getDeclaredAnnotation(Transactional.class);
		if (anno==null) return false;		
		return anno.value();
	}
	private UserTransaction createTransaction() throws NamingException {
		return (UserTransaction)context.lookup("java:comp/env/TransactionManager");
	}

	private void rollback(UserTransaction ut, Throwable e) {
		if (ut!=null) {
			try {
				ut.rollback();
			} catch (IllegalStateException | SecurityException | SystemException re) {
				throw new ServiceExecutionException(e);
			}
		}
	}
}
