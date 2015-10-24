package com.e3.bizamo.services.handlers.generic;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mockito;

import com.e3.bizamo.services.handlers.generic.doubles.Handler1Double;
import com.e3.bizamo.services.handlers.generic.doubles.Handler2Double;
import com.e3.bizamo.services.handlers.generic.doubles.Handler3Double;
import com.e3.bizamo.services.handlers.generic.doubles.HandlerRequiredBefore1Double;
import com.e3.bizamo.services.handlers.generic.doubles.HandlerRequiredBefore3Double;
import com.e3.bizamo.services.handlers.generic.doubles.HandlerRequires1Double;
import com.e3.bizamo.services.handlers.generic.doubles.HandlerRequires2RequiredBefore2Double;
import com.e3.bizamo.services.handlers.generic.doubles.HandlerRequires2RequiredBefore3Double;

public class HandlerChainBuilderShould extends Mockito {
	@Test
	public void constructorCoverageStub() {
		new HandlerChainDouble();
	}
	@Test
	public void sortInNaturalOrder() {
		IHandlerDouble h1 = new Handler1Double("1");
		IHandlerDouble h2 = new Handler2Double("2");
		IHandlerDouble h3 = new Handler3Double("3");
		List<IHandlerDouble> handlers = Arrays.asList(new IHandlerDouble[]{h1, h2, h3});
		
		HandlerChainDouble chain = new HandlerChainDouble(handlers);
		String result = (String)chain.handleChain(null, "", null);
		TestCase.assertEquals("123", result);
	}
	@Test
	public void ignoreDuplicatedHandler() {
		IHandlerDouble h1 = new Handler1Double("1");
		IHandlerDouble h2 = new Handler2Double("2");
		IHandlerDouble h3 = new Handler3Double("3");
		
		IHandlerDouble h4 = new Handler2Double("4");
		IHandlerDouble h5 = new Handler3Double("5");
		List<IHandlerDouble> handlers = Arrays.asList(new IHandlerDouble[]{h1, h2, h3, h4, h5});
		
		HandlerChainDouble chain = new HandlerChainDouble(handlers);
		String result = (String)chain.handleChain(null, "", null);
		TestCase.assertEquals("123", result);
	}
	
	@Test
	public void insertAfterRequired() {
		IHandlerDouble h1 = new Handler1Double("1");
		IHandlerDouble h2 = new Handler2Double("2");
		IHandlerDouble h3 = new Handler3Double("3");
		IHandlerDouble h4 = new HandlerRequires1Double("4");
		List<IHandlerDouble> handlers = Arrays.asList(new IHandlerDouble[]{h1, h2, h3, h4});
		
		HandlerChainDouble chain = new HandlerChainDouble(handlers);
		String result = (String)chain.handleChain(null, "", null);
		TestCase.assertEquals("1423", result);
	}
	@Test
	public void insertAfterRequiredLateBounding() {
		IHandlerDouble h2 = new Handler2Double("2");
		IHandlerDouble h3 = new Handler3Double("3");
		IHandlerDouble h4 = new HandlerRequires1Double("4");
		IHandlerDouble h1 = new Handler1Double("1");
		List<IHandlerDouble> handlers = Arrays.asList(new IHandlerDouble[]{h2, h3, h4, h1});
		
		HandlerChainDouble chain = new HandlerChainDouble(handlers);
		String result = (String)chain.handleChain(null, "", null);
		TestCase.assertEquals("2314", result);
	}
	@Test
	public void insertBeforeRequiredLateBounding() {
		IHandlerDouble h2 = new HandlerRequiredBefore3Double("2");
		IHandlerDouble h3 = new Handler3Double("3");
		
		List<IHandlerDouble> handlers = Arrays.asList(new IHandlerDouble[]{h2, h3});
		
		HandlerChainDouble chain = new HandlerChainDouble(handlers);
		String result = (String)chain.handleChain(null, "", null);
		TestCase.assertEquals("23", result);
	}
	
	@Test
	public void insertBeforeRequired() {
		IHandlerDouble h1 = new Handler1Double("1");
		IHandlerDouble h2 = new Handler2Double("2");
		IHandlerDouble h3 = new Handler3Double("3");
		IHandlerDouble h4 = new HandlerRequiredBefore3Double("4");
		List<IHandlerDouble> handlers = Arrays.asList(new IHandlerDouble[]{h1, h2, h3, h4});
		
		HandlerChainDouble chain = new HandlerChainDouble(handlers);
		String result = (String)chain.handleChain(null, "", null);
		TestCase.assertEquals("1243", result);
	}
	
	@Test
	public void insertAfterAndBeforeRequired() {
		IHandlerDouble h1 = new Handler1Double("1");
		IHandlerDouble h2 = new Handler2Double("2");
		IHandlerDouble h3 = new Handler3Double("3");
		IHandlerDouble h4 = new HandlerRequires2RequiredBefore3Double("4");
		List<IHandlerDouble> handlers = Arrays.asList(new IHandlerDouble[]{h1, h2, h3, h4});
		
		HandlerChainDouble chain = new HandlerChainDouble(handlers);
		String result = (String)chain.handleChain(null, "", null);
		TestCase.assertEquals("1243", result);
	}
	
	@Test
	public void ignoreHandlerWhenAfterAndBeforeContradicts() {
		IHandlerDouble h1 = new Handler1Double("1");
		IHandlerDouble h2 = new Handler2Double("2");
		IHandlerDouble h3 = new Handler3Double("3");
		IHandlerDouble h4 = new HandlerRequires2RequiredBefore2Double("4");
		List<IHandlerDouble> handlers = Arrays.asList(new IHandlerDouble[]{h1, h2, h3, h4});
		
		HandlerChainDouble chain = new HandlerChainDouble(handlers);
		String result = (String)chain.handleChain(null, "", null);
		TestCase.assertEquals("123", result);
	}
	
	@Test
	public void insertBeforeFirst() {
		IHandlerDouble h1 = new Handler1Double("1");

		IHandlerDouble h2 = new HandlerRequiredBefore1Double("2");
		List<IHandlerDouble> handlers = Arrays.asList(new IHandlerDouble[]{h1, h2});
		
		HandlerChainDouble chain = new HandlerChainDouble(handlers);
		String result = (String)chain.handleChain(null, "", null);
		TestCase.assertEquals("21", result);
	}
}
