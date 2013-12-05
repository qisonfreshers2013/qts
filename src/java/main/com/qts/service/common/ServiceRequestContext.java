package com.qts.service.common;

import java.io.Serializable;

import com.qts.common.security.RequestId;
import com.qts.model.UserSessionToken;


public class ServiceRequestContext implements Serializable, Cloneable {
	private static final long serialVersionUID = -8321298246752932991L;
	protected String hashcode;
	UserSessionToken userSessionToken;
	protected RequestId requestId;
	private long userId;

	public String getHashcode() {
		if (getUserSessionToken() != null)
			return "" + getUserSessionToken().getUserSessionId();
		else
			return "" + "?" + "-" + System.currentTimeMillis();
	}

	public void setHashcode(String code) {
		this.hashcode = code;
	}

	public UserSessionToken getUserSessionToken() {
		return userSessionToken;
	}

	public void setUserSessionToken(UserSessionToken userSessionToken) {
		this.userSessionToken = userSessionToken;
	}

	/**
	 * Show the value of the context.
	 * 
	 * @return a string representation of the object.
	 */
	@Override
	public String toString() {
		return null;
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	public RequestId getRequestId() {
		return requestId;
	}

	public void setRequestId(RequestId requestId) {
		this.requestId = requestId;
	}

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}