package com.e3.bizamo.services.execution.doubles;

import com.e3.bizamo.services.execution.IService;

public class ServiceWithErrorDouble implements
		IService<RequestDouble, ResponseDouble> {

	@Override
	public ResponseDouble execute(RequestDouble request) {
		throw new RuntimeException("msg");
	}

}
