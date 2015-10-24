package com.e3.bizamo.services.execution.doubles;

import com.e3.bizamo.services.execution.IService;

public class ServiceDouble implements IService<RequestDouble, ResponseDouble> {

	@Override
	public ResponseDouble execute(RequestDouble request) {
		ResponseDouble res = new ResponseDouble();
		res.setS1("executed");
		return res;
	}

}
