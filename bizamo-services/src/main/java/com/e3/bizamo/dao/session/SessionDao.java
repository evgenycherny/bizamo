package com.e3.bizamo.dao.session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.e3.bizamo.commons.exceptions.ApplicationException;
import com.e3.bizamo.components.session.ISession;
import com.e3.bizamo.components.session.Session;
import com.e3.bizamo.ioc.inject.annotations.Export;
import com.e3.bizamo.services.execution.IConnectionFactory;

@Export(serviceType=ISessionDao.class)
public class SessionDao implements ISessionDao {
	private IConnectionFactory connectionFactory;

	public SessionDao(IConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	@Override
	public ISession createInstance() {
		ISession session = new Session();
		return session;
	}

	@Override
	public void save(ISession session) {
		try {
			Connection cn = connectionFactory.createConnection();
			PreparedStatement stmt = cn.prepareStatement("INSERT INTO IDM_Session (Id, UserId) VALUES(?,?)");
			stmt.setString(1, session.getId());
			stmt.setInt(2, session.getUserId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}

	@Override
	public ISession read(String sessionId) {
		Connection cn = connectionFactory.createConnection();
		ISession session = createInstance();
		
		try {
			PreparedStatement stmt = cn.prepareStatement("SELECT * FROM IDM_Session WHERE Id=?");
			stmt.setString(1, sessionId);
			ResultSet rs = stmt.executeQuery();
			if (rs.first()) {
				session.setId(rs.getString("Id"));
				session.setUserId(rs.getInt("UserId"));
			}
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
		return session;
	}
}
