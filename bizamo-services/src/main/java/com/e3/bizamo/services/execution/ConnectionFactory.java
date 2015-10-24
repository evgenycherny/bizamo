package com.e3.bizamo.services.execution;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.e3.bizamo.commons.exceptions.ApplicationException;
import com.e3.bizamo.ioc.inject.annotations.Export;
import com.e3.bizamo.services.context.ServiceExecutionContext;

@Export(serviceType=IConnectionFactory.class)
public class ConnectionFactory implements IConnectionFactory {
	private Context initialContext;
	public ConnectionFactory(Context context) {
		this.initialContext = context;
	}
	@Override
	public Connection createConnection() {
		try {
			Connection cn = getExistingConnectionFromCurrentThread();
			if (cn==null) {
				cn = getConnectionFromPool();
				storeConnectionInCurrentThread(cn);
			}
			return cn;
		} catch (NamingException | SQLException e) {
			throw new ApplicationException(e);
		}

	}
	private void storeConnectionInCurrentThread(Connection cn) {
		ServiceExecutionContext.getRequestContext().put("connection.main", cn);
	}
	
	private Connection getExistingConnectionFromCurrentThread() {
		return (Connection)ServiceExecutionContext.getRequestContext().get("connection.main");
	}
	private Connection getConnectionFromPool()
			throws NamingException, SQLException {
		DataSource ds = (DataSource)initialContext.lookup("java:comp/env/jdbc/main");
		Connection cn = ds.getConnection();
		return cn;
	}


}
