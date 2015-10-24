package com.e3.bizamo.ioc.inject;

import java.util.List;

public interface ISelector {
	List<IBinding> selectMany(List<IBinding> bindings);
	IBinding selectSingle(List<IBinding> bindings);
}
