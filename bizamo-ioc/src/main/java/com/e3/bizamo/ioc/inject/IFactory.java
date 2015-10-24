package com.e3.bizamo.ioc.inject;

public interface IFactory<T> {
	T create(Resolver resolver);
}
