package com.e3.bizamo.services.handlers.doubles;

import com.e3.bizamo.services.annotations.Authenticate;
import com.e3.bizamo.services.execution.IService;
import com.e3.bizamo.services.execution.doubles.RequestDouble;
import com.e3.bizamo.services.execution.doubles.ResponseDouble;

@Authenticate(true)
public class WithAuthenticationAnnotationTrueServiceDouble implements IService<RequestDouble, ResponseDouble> {

	@Override
	public ResponseDouble execute(RequestDouble request) {
		ResponseDouble response =  new ResponseDouble();
		response.setS1("WithAuthenticationAnnotationTrueServiceDouble");
		return response;
	}

}
