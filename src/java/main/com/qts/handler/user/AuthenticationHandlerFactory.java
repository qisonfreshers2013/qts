package com.qts.handler.user;

import com.qts.model.UserEx;

/**
 * @author RAMMOHAN
 * 
 */
public class AuthenticationHandlerFactory {

	public static AuthenticationHandler getAuthenticationHandler(String authType) {

		if (authType.equals(UserEx.AUTH_TYPE_REGULAR)) {
			return new DefaultAuthenticationHandler();
		}
		return new DefaultAuthenticationHandler();
	}
}
