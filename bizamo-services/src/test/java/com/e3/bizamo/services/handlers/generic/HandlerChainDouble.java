package com.e3.bizamo.services.handlers.generic;

import java.lang.reflect.Method;
import java.util.List;

import com.e3.bizamo.services.execution.IService;
import com.e3.bizamo.services.handlers.generic.HandlerChainBuilder;

public class HandlerChainDouble extends HandlerChainBuilder<IHandlerDouble> {
	public HandlerChainDouble(List<IHandlerDouble> handlers) {
		super(handlers);
	}
	protected HandlerChainDouble() {
		super();
	}
	public Object handleChain(IService<?, ?> service, Object requestObject, Method execute) {
		IHandlerDouble head = build();
		//TODO log ignored handlers
		return head.handle(service, requestObject, execute);
		
	}
	/*private List<IServiceExecutionHandler> handlers;
	private IServiceExecutionHandler head = null, tail = null;

	private Map<Class<? extends IServiceExecutionHandler>, IServiceExecutionHandler> map = new HashMap<Class<? extends IServiceExecutionHandler>, IServiceExecutionHandler>();
	
	HandlerChain() {}
	
	public HandlerChain(List<IServiceExecutionHandler> handlers) {
		this.handlers = handlers;
	}
	
	public Object handleChain(IService<?, ?> service, Object requestObject, Method execute) {
		@SuppressWarnings("unused")
		List<IServiceExecutionHandler> ignored = buildPrioritezedLinkedListOfHandlers(handlers);
		//TODO log ignored handlers
		return head.handle(service, requestObject, execute);
		
	}
	private List<IServiceExecutionHandler> buildPrioritezedLinkedListOfHandlers(List<IServiceExecutionHandler> handlers) {
		List<IServiceExecutionHandler> ignored = new ArrayList<IServiceExecutionHandler>();
		List<IServiceExecutionHandler> postponed = new ArrayList<IServiceExecutionHandler>();
		for(IServiceExecutionHandler handler: handlers) {
			if (!insertToMap(handler)) continue;
			
			Class<?> requiredHandler = getRequiredHandler(handler);
			Class<?> requiredBeforeHandler = getRequiredBeforeHandler(handler);

			if (requiredHandler!=null) {
				if (map.containsKey(requiredHandler)) {
					IServiceExecutionHandler current = map.get(requiredHandler);
					if (!satisfiesRequiredBefore(requiredBeforeHandler, current)) {
						map.remove(handler.getClass());
						ignored.add(handler);
						continue;
					}
					insertAfter(current, handler);
				}
				else {
					postponed.add(handler);
					map.remove(handler.getClass());
				}
			}
			else if (requiredBeforeHandler!=null) {
				if (map.containsKey(requiredBeforeHandler)) {
					IServiceExecutionHandler current = map.get(requiredBeforeHandler);
					insertBefore(current, handler);
				}
				else {
					postponed.add(handler);
					map.remove(handler.getClass());
				}
			}
			else {
				add(handler);
			}
		}
		if (postponed.size()>0) {
			ignored.addAll(buildPrioritezedLinkedListOfHandlers(postponed));
		}
		return ignored;
		
	}
	
	private boolean satisfiesRequiredBefore(Class<?> requiredBeforeHandler, IServiceExecutionHandler current) {
		if (requiredBeforeHandler==null) return true;
		current=(IServiceExecutionHandler)current.getNext();
		while (current!=null) {
			if (current.getClass().equals(requiredBeforeHandler)) 
				return true;
			current=(IServiceExecutionHandler)current.getNext();
		}
		return false;
	}

	private void insertAfter(IServiceExecutionHandler current, IServiceExecutionHandler handler) {
		handler.setNext(current.getNext());
		handler.setPrevious(current);
		if (current.getNext()!=null) 
			current.getNext().setPrevious(handler);
		current.setNext(handler);
		if (current==tail) tail = handler;
	}
	private void insertBefore(IServiceExecutionHandler current, IServiceExecutionHandler handler) {
		handler.setPrevious(current.getPrevious());
		handler.setNext(current);
		if (current.getPrevious()!=null) 
			current.getPrevious().setNext(handler);
		current.setPrevious(handler);
		if (current==head) head = handler;
	}

	private Class<?> getRequiredHandler(IServiceExecutionHandler handler) {
		RequiresHandler anno = handler.getClass().getAnnotation(RequiresHandler.class);
		if (anno==null) return null;
		return anno.handler();
	}
	private Class<?> getRequiredBeforeHandler(IServiceExecutionHandler handler) {
		RequiredBeforeHandler anno = handler.getClass().getAnnotation(RequiredBeforeHandler.class);
		if (anno==null) return null;
		return anno.handler();
	}

	private void add(IServiceExecutionHandler handler) {
		if (head == null)addFirst(handler);
		else addMore(handler);
	}

	private void addFirst(IServiceExecutionHandler handler) {
		head = handler;
		tail = handler;
		handler.setNext(null);
		handler.setPrevious(null);
	}
	
	private void addMore(IServiceExecutionHandler handler) {
		handler.setNext(tail.getNext());
		handler.setPrevious(tail);
		tail.setNext(handler);
		tail = handler;
	}

	private boolean insertToMap(IServiceExecutionHandler handler) {
		if (map.containsKey(handler.getClass())) return false;
		map.put(handler.getClass(), handler);
		return true;
	}
	*/
}
