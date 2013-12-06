package com.qts.model.user;

import com.qts.model.UserEx;

/**
 * @author RAMMOHAN
 * 
 */
public class AuthenticationOutput {

	private String sessionToken;
	private int authStatus;
	private UserEx user;

	public AuthenticationOutput(String sessionToken, int authStatus, UserEx user) {
		this.sessionToken = sessionToken;
		this.authStatus = authStatus;
		this.user = user;
	}

	public String getSessionToken() {
		return sessionToken;
	}

	public int getAuthStatus() {
		return authStatus;
	}

	public UserEx getUser() {
		return user;
	}
}

