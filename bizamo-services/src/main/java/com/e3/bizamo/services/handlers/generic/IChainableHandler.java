package com.e3.bizamo.services.handlers.generic;

public interface IChainableHandler<T extends IChainableHandler<?>> {
	void setNext(T next);
	T getNext();
	void setPrevious(T previous);
	T getPrevious();
}
