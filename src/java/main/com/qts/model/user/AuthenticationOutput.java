package com.qts.model.user;

import com.qts.model.User;

/**
 * @author RAMMOHAN
 * 
 */
public class AuthenticationOutput {

	private String sessionToken;
	private int authStatus;
	private User user;

	public AuthenticationOutput(String sessionToken, int authStatus, User user) {
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

	public User getUser() {
		return user;
	}
}

