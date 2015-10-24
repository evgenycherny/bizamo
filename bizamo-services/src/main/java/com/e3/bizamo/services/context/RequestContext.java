package com.e3.bizamo.services.context;

import java.util.HashMap;

import com.e3.bizamo.components.account.IUserAccount;
import com.e3.bizamo.components.session.ISession;

public class RequestContext extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	private IUserAccount currentUser;
	private ISession currentSession;

	public IUserAccount getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(IUserAccount currentUser) {
		this.currentUser = currentUser;
	}

	public ISession getCurrentSession() {
		return currentSession;
	}

	public void setCurrentSession(ISession currentSession) {
		this.currentSession = currentSession;
	}

}
