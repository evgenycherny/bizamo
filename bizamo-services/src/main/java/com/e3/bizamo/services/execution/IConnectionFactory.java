package com.e3.bizamo.services.execution;

import java.sql.Connection;

public interface IConnectionFactory {

	Connection createConnection();

}
