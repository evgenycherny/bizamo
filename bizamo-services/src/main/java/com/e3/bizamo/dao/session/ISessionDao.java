package com.e3.bizamo.dao.session;

import com.e3.bizamo.components.session.ISession;

public interface ISessionDao {
	//boolean exists(String token);

	ISession createInstance();
	void save(ISession session);
	ISession read(String sessionId);
}
