package com.e3.bizamo.components.account;

public interface IUserAccount {

	void setUserName(String string);

	boolean validateCredentials(String password);

	String getUserName();

	byte[] getPasswordHash();

	void setPassword(String string);

	void setPasswordHash(byte[] passwordHash);

	int getId();
	
	void setId(int id);

}
