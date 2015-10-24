package com.e3.bizamo.ioc.inject;

import java.util.List;

public class DefaultSelector implements ISelector {

	@Override
	public List<IBinding> selectMany(List<IBinding> bindings) {
		return bindings;
	}

	@Override
	public IBinding selectSingle(List<IBinding> bindings) {
		if (bindings.size()==0) return null;
		if (bindings.size()==1) return bindings.get(0);
		return null;
	}

}
