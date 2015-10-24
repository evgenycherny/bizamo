package com.e3.bizamo.services.execution;

public interface IService<REQUEST, RESPONSE> {
	RESPONSE execute(REQUEST request);
}
