package com.e3.bizamo.dao.account;

import com.e3.bizamo.components.account.IUserAccount;

public interface IUserAccountDao {
	IUserAccount read(String username);
	void save(IUserAccount userAccountToSave);
	IUserAccount create();
}
