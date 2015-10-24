package com.e3.bizamo.ioc.inject.doubles;

import java.util.List;

import com.e3.bizamo.commons.exceptions.ApplicationException;
import com.e3.bizamo.ioc.inject.IBinding;
import com.e3.bizamo.ioc.inject.ISelector;

public class BadSelectorDouble implements ISelector {
	public int selectManyCallCount = 0;
	public int selectSingleCallCount = 0;
	
	public BadSelectorDouble() throws InstantiationException {
		throw new InstantiationException();
	}
	@Override
	public List<IBinding> selectMany(List<IBinding> bindings) {
		throw new ApplicationException();
	}

	@Override
	public IBinding selectSingle(List<IBinding> bindings) {
		throw new ApplicationException();
	}

}
