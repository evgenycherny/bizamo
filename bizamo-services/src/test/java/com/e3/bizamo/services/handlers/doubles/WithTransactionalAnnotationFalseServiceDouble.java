package com.e3.bizamo.services.handlers.doubles;

import com.e3.bizamo.services.annotations.Transactional;
import com.e3.bizamo.services.execution.IService;
import com.e3.bizamo.services.execution.doubles.RequestDouble;
import com.e3.bizamo.services.execution.doubles.ResponseDouble;

@Transactional(false)
public class WithTransactionalAnnotationFalseServiceDouble implements IService<RequestDouble, ResponseDouble> {

	@Override
	public ResponseDouble execute(RequestDouble request) {
		return null;
	}

}
