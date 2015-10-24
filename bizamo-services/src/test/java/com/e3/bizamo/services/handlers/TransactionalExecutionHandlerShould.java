package com.e3.bizamo.services.handlers;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import com.e3.bizamo.services.execution.ServiceExecutionException;
import com.e3.bizamo.services.handlers.doubles.NoAnnotationServiceDouble;
import com.e3.bizamo.services.handlers.doubles.WithTransactionalAnnotationFalseServiceDouble;
import com.e3.bizamo.services.handlers.doubles.WithTransactionalAnnotationTrueServiceDouble;

public class TransactionalExecutionHandlerShould extends Mockito {
	@Test
	public void beginAndCommitTranWithHandlerCallInBetween() throws NamingException, NotSupportedException, SystemException, SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		Context mockContext = mock(Context.class);
		UserTransaction mockTran = mock(UserTransaction.class);
		when(mockContext.lookup("java:comp/env/TransactionManager")).thenReturn(mockTran);
		
		IServiceExecutionHandler mockLastHandler = mock(IServiceExecutionHandler.class);
		when(mockLastHandler.handle(null, null, null)).thenReturn("answer");
		when(mockLastHandler.isRequired(null)).thenReturn(true);
		
		TransactionalExecutionHandler handler = new TransactionalExecutionHandler(mockContext);
		handler.setNext(mockLastHandler);
		handler.handle(null, null, null);
		
		InOrder inOrder = inOrder(mockTran, mockLastHandler);
		inOrder.verify(mockTran).begin();
		inOrder.verify(mockLastHandler, times(1)).handle(null, null, null);
		inOrder.verify(mockTran).commit();
	}
	
	@Test
	public void beginAndRollbackTranWithHandlerCallInBetween() throws NamingException, NotSupportedException, SystemException, SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		Context mockContext = mock(Context.class);
		UserTransaction mockTran = mock(UserTransaction.class);
		when(mockContext.lookup("java:comp/env/TransactionManager")).thenReturn(mockTran);
		
		IServiceExecutionHandler mockLastHandler = mock(IServiceExecutionHandler.class);
		when(mockLastHandler.handle(null, null,  null)).thenThrow(new NullPointerException());
		when(mockLastHandler.isRequired(null)).thenReturn(true);
		
		TransactionalExecutionHandler handler = new TransactionalExecutionHandler(mockContext);
		handler.setNext(mockLastHandler);
		
		try {
			handler.handle(null, null, null);
		}
		catch(ServiceExecutionException e) {
			InOrder inOrder = inOrder(mockTran, mockLastHandler);
			inOrder.verify(mockTran).begin();
			inOrder.verify(mockLastHandler, times(1)).handle(null, null, null);
			inOrder.verify(mockTran).rollback();
		}
		
	}
	@Test
	public void beginAndRollbackTranWithHandlerCallInBetweenWithServiceExecutionException() throws NamingException, NotSupportedException, SystemException, SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		Context mockContext = mock(Context.class);
		UserTransaction mockTran = mock(UserTransaction.class);
		when(mockContext.lookup("java:comp/env/TransactionManager")).thenReturn(mockTran);
		
		IServiceExecutionHandler mockLastHandler = mock(IServiceExecutionHandler.class);
		when(mockLastHandler.handle(null, null, null)).thenThrow(new ServiceExecutionException());
		when(mockLastHandler.isRequired(null)).thenReturn(true);
		
		TransactionalExecutionHandler handler = new TransactionalExecutionHandler(mockContext);
		handler.setNext(mockLastHandler);
		
		try {
			handler.handle(null, null, null);
		}
		catch(ServiceExecutionException e) {
			InOrder inOrder = inOrder(mockTran, mockLastHandler);
			inOrder.verify(mockTran).begin();
			inOrder.verify(mockLastHandler, times(1)).handle(null, null, null);
			inOrder.verify(mockTran).rollback();
		}
	}
	@Test
	public void failWhenTransactionCantBeCreated() throws NamingException, NotSupportedException, SystemException, SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		Context mockContext = mock(Context.class);
		UserTransaction mockTran = mock(UserTransaction.class);
		when(mockContext.lookup("java:comp/env/TransactionManager")).thenThrow(new SecurityException());
		
		IServiceExecutionHandler mockLastHandler = mock(IServiceExecutionHandler.class);
		when(mockLastHandler.handle(null, null, null)).thenThrow(new ServiceExecutionException());
		when(mockLastHandler.isRequired(null)).thenReturn(true);
		
		TransactionalExecutionHandler handler = new TransactionalExecutionHandler(mockContext);
		handler.setNext(mockLastHandler);
		
		try {
			handler.handle(null, null, null);
		}
		catch(ServiceExecutionException e) {
			InOrder inOrder = inOrder(mockTran, mockLastHandler);
			inOrder.verify(mockTran, times(0)).begin();
			inOrder.verify(mockLastHandler, times(0)).handle(null, null, null);
			inOrder.verify(mockTran, times(0)).rollback();
		}
	}
	@Test
	public void failRollbackFails() throws NamingException, NotSupportedException, SystemException, SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		Context mockContext = mock(Context.class);
		UserTransaction mockTran = mock(UserTransaction.class);
		doThrow(new SecurityException()).when(mockTran).rollback();
		when(mockContext.lookup("java:comp/env/TransactionManager")).thenReturn(mockTran);
		
		IServiceExecutionHandler mockLastHandler = mock(IServiceExecutionHandler.class);
		when(mockLastHandler.handle(null, null, null)).thenThrow(new ServiceExecutionException());
		when(mockLastHandler.isRequired(null)).thenReturn(true);
		
		TransactionalExecutionHandler handler = new TransactionalExecutionHandler(mockContext);
		handler.setNext(mockLastHandler);
		
		try {
			handler.handle(null, null, null);
		}
		catch(ServiceExecutionException e) {
			InOrder inOrder = inOrder(mockTran, mockLastHandler);
			inOrder.verify(mockTran, times(1)).begin();
			inOrder.verify(mockLastHandler, times(1)).handle(null, null, null);
			inOrder.verify(mockTran, times(1)).rollback();
		}
	}
	@Test
	public void requiredWhenTransactional()  {
		TransactionalExecutionHandler handler = new TransactionalExecutionHandler(null);
		TestCase.assertTrue(!handler.isRequired(new NoAnnotationServiceDouble()));
		TestCase.assertTrue(!handler.isRequired(new WithTransactionalAnnotationFalseServiceDouble()));
		TestCase.assertTrue(handler.isRequired(new WithTransactionalAnnotationTrueServiceDouble()));
	}
}
