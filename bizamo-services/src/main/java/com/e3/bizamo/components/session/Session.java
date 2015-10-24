package com.e3.bizamo.components.session;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.e3.bizamo.ioc.inject.annotations.Export;

@Export(serviceType=ISession.class)
public class Session implements ISession {

	private String id;
	private int userId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	@Override 
	public boolean equals(Object obj) {
		ISession other = (ISession)obj;
		return new EqualsBuilder().append(this.getId(), other.getId()).build();
	}


}
