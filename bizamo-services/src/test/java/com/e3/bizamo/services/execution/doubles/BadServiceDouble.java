package com.e3.bizamo.services.execution.doubles;

import com.e3.bizamo.services.execution.IService;

public class BadServiceDouble implements
		IService<RequestDouble, BadInstanciationResponseDouble> {

	@Override
	public BadInstanciationResponseDouble execute(RequestDouble request) {
		return new BadInstanciationResponseDouble();
	}

}
