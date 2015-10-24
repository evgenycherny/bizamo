package com.e3.bizamo.components.account;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Properties;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.e3.bizamo.commons.exceptions.ApplicationException;
import com.e3.bizamo.commons.properties.IPropertyHolder;
import com.e3.bizamo.ioc.inject.annotations.Export;

@Export(serviceType=IUserAccount.class)
public class UserAccount implements IUserAccount {
	private String userName;
	private byte[] passwordHash;
	private Properties props;
	private int id;
	

	public UserAccount(IPropertyHolder props) {
		this.props = props.getProperties();
	}
	@Override
	public void setPassword(String password) {
		this.passwordHash = hashPassword(password);
		
	}
	@Override
	public byte[] getPasswordHash() {
		return this.passwordHash;
	}
	
	@Override
	public void setPasswordHash(byte[] passwordHash) {
		this.passwordHash = passwordHash;
	}

	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Override
	public String getUserName() {
		return this.userName;
	}

	@Override
	public boolean validateCredentials(String password) {
		byte[] hash = hashPassword(password);
		return Arrays.equals(this.getPasswordHash(), hash);
	}

	private byte[] hashPassword(String text) {
		try {
			MessageDigest md = MessageDigest.getInstance(props.getProperty("password.digest.algorithm", "SHA-256"));
			md.update(text.getBytes("UTF-8")); 
			byte[] digest = md.digest();
			return digest;
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			throw new ApplicationException(e);
		}
	}
	
	@Override 
	public boolean equals(Object obj) {
		IUserAccount other = (IUserAccount)obj;

		return new EqualsBuilder()
			.append(this.getUserName(), other.getUserName())
			.append(this.getPasswordHash(), other.getPasswordHash())
			.build();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	
}
