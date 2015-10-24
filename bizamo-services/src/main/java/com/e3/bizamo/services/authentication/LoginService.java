package com.e3.bizamo.services.authentication;

import java.util.UUID;

import com.e3.bizamo.components.account.IUserAccount;
import com.e3.bizamo.components.session.ISession;
import com.e3.bizamo.dao.account.IUserAccountDao;
import com.e3.bizamo.dao.session.ISessionDao;
import com.e3.bizamo.services.annotations.Authenticate;
import com.e3.bizamo.services.annotations.Transactional;
import com.e3.bizamo.services.execution.IService;

@Transactional
@Authenticate(false)
public class LoginService implements IService<LoginRequest, LoginResponse> {

	private IUserAccountDao userAccountDao;
	private ISessionDao sessionDao;
	private LoginResponse response;

	private IUserAccount userAccount;
	private ISession session;
	
	public LoginService(IUserAccountDao userAccountDao, ISessionDao sessionDao) {
		this.userAccountDao = userAccountDao;
		this.sessionDao = sessionDao;
	}

	@Override
	public LoginResponse execute(LoginRequest request) {
		userAccount = userAccountDao.read(request.getUsername());
		validateAccountCredentials(request.getPassword());
		createAndPersistSession();
		return buildResponse();
	}

	private void createAndPersistSession() {
		session = sessionDao.createInstance();
		session.setId(UUID.randomUUID().toString());
		session.setUserId(userAccount.getId());
		sessionDao.save(session);
	}

	private void validateAccountCredentials(String password) {
		if (userAccount==null || !userAccount.validateCredentials(password)) {
			failLogin();
		}
	}

	private LoginResponse buildResponse() {
		response = new LoginResponse();
		response.setUsername(userAccount.getUserName());
		response.setToken(session.getId());
		response.setSuccess(true);
		return response;
	}

	private void failLogin() {
		throw new LoginFailureException("Wrong username or password");
	}

}
