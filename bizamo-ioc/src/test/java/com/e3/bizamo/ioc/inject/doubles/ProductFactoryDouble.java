package com.e3.bizamo.ioc.inject.doubles;

import com.e3.bizamo.ioc.inject.IFactory;
import com.e3.bizamo.ioc.inject.Resolver;

public class ProductFactoryDouble implements IFactory<IProductDouble> {
	@Override
	public IProductDouble create(Resolver resolver) {
		return new ProductOneDouble();
	}

}
