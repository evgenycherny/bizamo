package com.e3.bizamo.ioc.inject.doubles;

import java.util.List;

import com.e3.bizamo.ioc.inject.IBinding;
import com.e3.bizamo.ioc.inject.ISelector;

public class SelectorDouble implements ISelector {
	public int selectManyCallCount = 0;
	public int selectSingleCallCount = 0;
	@Override
	public List<IBinding> selectMany(List<IBinding> bindings) {
		selectManyCallCount++;
		return bindings;
	}

	@Override
	public IBinding selectSingle(List<IBinding> bindings) {
		selectSingleCallCount++;
		if (bindings.size()==0) return null;
		if (bindings.size()==1) return bindings.get(0);
		return null;
	}

}
