package com.qts.handler.user;

import com.qts.model.UserExt;

/**
 * @author RAMMOHAN
 * 
 */
public class AuthenticationHandlerFactory {

	public static AuthenticationHandler getAuthenticationHandler(String authType) {

		if (authType.equals(UserExt.AUTH_TYPE_REGULAR)) {
			return new DefaultAuthenticationHandler();
		}
		return null;
	}
}
