package com.e3.bizamo.services.handlers.generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.e3.bizamo.services.handlers.annotations.RequiredBeforeHandler;
import com.e3.bizamo.services.handlers.annotations.RequiresHandler;

public class HandlerChainBuilder<T extends IChainableHandler<T>> {
	private List<T> handlers;
	private T head = null, tail = null;

	private Map<Class<? extends T>, T> map = new HashMap<Class<? extends T>, T>();
	
	
	public HandlerChainBuilder(List<T> handlers) {
		this.setHandlers(handlers);
	}
	
	protected HandlerChainBuilder() {
	}

	public T build() {
		@SuppressWarnings("unused")
		List<T> ignored = buildPrioritezedLinkedListOfHandlers(getHandlers());
		return head;
	}
	private List<T> buildPrioritezedLinkedListOfHandlers(List<T> handlers) {
		List<T> ignored = new ArrayList<T>();
		List<T> postponed = new ArrayList<T>();
		for(T handler: handlers) {
			if (!insertToMap(handler)) continue;
			
			Class<?> requiredHandler = getRequiredHandler(handler);
			Class<?> requiredBeforeHandler = getRequiredBeforeHandler(handler);

			if (requiredHandler!=null) {
				if (map.containsKey(requiredHandler)) {
					T current = map.get(requiredHandler);
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
					T current = map.get(requiredBeforeHandler);
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
	
	private boolean satisfiesRequiredBefore(Class<?> requiredBeforeHandler, T current) {
		if (requiredBeforeHandler==null) return true;
		current=(T)current.getNext();
		while (current!=null) {
			if (current.getClass().equals(requiredBeforeHandler)) 
				return true;
			current=(T)current.getNext();
		}
		return false;
	}

	private void insertAfter(T current, T handler) {
		handler.setNext(current.getNext());
		handler.setPrevious(current);
		if (current.getNext()!=null) 
			current.getNext().setPrevious(handler);
		current.setNext(handler);
		if (current==tail) tail = handler;
	}
	private void insertBefore(T current, T handler) {
		handler.setPrevious(current.getPrevious());
		handler.setNext(current);
		if (current.getPrevious()!=null) 
			current.getPrevious().setNext(handler);
		current.setPrevious(handler);
		if (current==head) head = handler;
	}

	@SuppressWarnings("unchecked")
	private Class<? extends T> getRequiredHandler(T handler) {
		RequiresHandler anno = handler.getClass().getAnnotation(RequiresHandler.class);
		if (anno==null) return null;
		return (Class<? extends T>) anno.value();
	}
	@SuppressWarnings("unchecked")
	private Class<? extends T> getRequiredBeforeHandler(T handler) {
		RequiredBeforeHandler anno = handler.getClass().getAnnotation(RequiredBeforeHandler.class);
		if (anno==null) return null;
		return (Class<? extends T>) anno.value();
	}

	private void add(T handler) {
		if (head == null)addFirst(handler);
		else addMore(handler);
	}

	private void addFirst(T handler) {
		head = handler;
		tail = handler;
		handler.setNext(null);
		handler.setPrevious(null);
	}
	
	private void addMore(T handler) {
		handler.setNext(tail.getNext());
		handler.setPrevious(tail);
		tail.setNext(handler);
		tail = handler;
	}

	@SuppressWarnings("unchecked")
	private boolean insertToMap(T handler) {
		if (map.containsKey(handler.getClass())) return false;
		map.put((Class<? extends T>) handler.getClass(), handler);
		return true;
	}

	public List<T> getHandlers() {
		return handlers;
	}

	public void setHandlers(List<T> handlers) {
		this.handlers = handlers;
	}
}
