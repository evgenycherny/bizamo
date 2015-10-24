package com.e3.bizamo.services.authentication;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.e3.bizamo.commons.exceptions.ApplicationException;
import com.e3.bizamo.commons.properties.IPropertyHolder;
import com.e3.bizamo.commons.properties.PropertyHolder;
import com.e3.bizamo.components.account.IUserAccount;
import com.e3.bizamo.components.account.UserAccount;
import com.e3.bizamo.dao.account.IUserAccountDao;
import com.e3.bizamo.dao.session.ISessionDao;
import com.e3.bizamo.dao.session.SessionDao;

public class LoginServiceShould extends Mockito {
	private IUserAccountDao userAccountDao;
	private LoginService loginService;
	private IPropertyHolder holder;
	private ISessionDao sessionDao;
	@Before
	public void setUp() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		holder = new PropertyHolder();
		Properties props = new Properties();
		props.setProperty("password.digest.algorithm", "SHA-256");
		holder.setProperties(props);
		
		this.userAccountDao = (IUserAccountDao) mock(IUserAccountDao.class);
		IUserAccount ua = new UserAccount(holder);
		ua.setUserName("test");
		ua.setPassword("1234");
		when(this.userAccountDao.read("test")).thenReturn(ua);
		
		this.sessionDao = (ISessionDao) mock(SessionDao.class);
		when(sessionDao.createInstance()).thenCallRealMethod();
		
		this.loginService = new LoginService(userAccountDao, sessionDao);
	}
	
	
	@Test
	public void returnSuccessWhenCorrectCredentialsProvided() {
		LoginRequest request = new LoginRequest();
		request.setUsername("test");
		request.setPassword("1234");
		LoginResponse response = loginService.execute(request);
		TestCase.assertEquals(true,response.isSuccess());
		TestCase.assertEquals("test",response.getUsername());
	}
	@Test(expected=LoginFailureException.class)
	public void failOnWrongPassword() {
		LoginRequest request = new LoginRequest();
		request.setUsername("test");
		request.setPassword("12345");
		loginService.execute(request);
	}
	@Test(expected=LoginFailureException.class)
	public void failOnNonExistingUser() {
		LoginRequest request = new LoginRequest();
		request.setUsername("fake");
		request.setPassword("1234");
		loginService.execute(request);
	}
	@Test(expected=ApplicationException.class)
	public void failWhenWrongDigestingAlogorithmSpecified() {
		holder.getProperties().setProperty("password.digest.algorithm", "fake");
		LoginRequest request = new LoginRequest();
		request.setUsername("test");
		request.setPassword("1234");
		loginService.execute(request);
	}
	@Test
	public void generateNewSessionTokenOnEveryLogin() {
		LoginRequest request = new LoginRequest();
		request.setUsername("test");
		request.setPassword("1234");
		LoginResponse response1 = loginService.execute(request);
		LoginResponse response2 = loginService.execute(request);
		TestCase.assertNotSame(response1.getToken(), response2.getToken());
	}
	
	@Test
	public void createSession() {
		LoginRequest request = new LoginRequest();
		request.setUsername("test");
		request.setPassword("1234");
		loginService.execute(request);
		verify(this.sessionDao, times(1)).save(Matchers.any());
	}
	
	
	public byte[] digest(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(text.getBytes("UTF-8")); 
		byte[] digest = md.digest();
		return digest;
	}
}
