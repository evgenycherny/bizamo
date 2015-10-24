package com.e3.bizamo.dao.account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.e3.bizamo.commons.exceptions.ApplicationException;
import com.e3.bizamo.commons.properties.IPropertyHolder;
import com.e3.bizamo.components.account.IUserAccount;
import com.e3.bizamo.components.account.UserAccount;
import com.e3.bizamo.ioc.inject.annotations.Export;
import com.e3.bizamo.services.execution.IConnectionFactory;

@Export(serviceType=IUserAccountDao.class)
public class UserAccountDao implements IUserAccountDao {
	private IConnectionFactory connectionFactory;
	private IPropertyHolder props;

	public UserAccountDao(IConnectionFactory connectionFactory, IPropertyHolder props) {
		this.connectionFactory = connectionFactory;
		this.props = props;
	}
	
	@Override
	public IUserAccount read(String userName) {
		Connection connection = connectionFactory.createConnection();
		IUserAccount account = create();
		
		try {
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM IDM_Account WHERE Email=?");
			stmt.setString(1, userName);
			ResultSet rs = stmt.executeQuery();
			if (rs.first()) {
				account.setId(rs.getInt("Id"));
				account.setUserName(rs.getString("Email"));
				account.setPasswordHash(rs.getBytes("Password"));
				return account;
			}
			return null;
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
		
	}

	public IUserAccount create() {
		return new UserAccount(props);
	}

	@Override
	public void save(IUserAccount account) {
		Connection connection = connectionFactory.createConnection();
		try {
			PreparedStatement stmt = connection.prepareStatement("INSERT INTO IDM_Account (Email, Password) VALUES(?,?)");
			stmt.setString(1, account.getUserName());
			stmt.setBytes(2, account.getPasswordHash());
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}

}
