package com.e3.bizamo.services.handlers.generic;



public abstract class AbstractChainableHandler<T extends IChainableHandler<T>> implements IChainableHandler<T> {
		protected T next;
		protected T previous;

		@Override
		public void setNext(T next) {
			this.next = next;
		}

		@Override
		public T getNext() {
			return next;
		}

		@Override
		public void setPrevious(T previous) {
			this.previous = previous;
		}

		@Override
		public T getPrevious() {
			return previous;
		}
}
