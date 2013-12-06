package com.qts.service.common;

import java.io.Serializable;

import org.hibernate.Session;

import com.qts.common.security.RequestId;
import com.qts.model.UserSessionToken;


public class ServiceRequestContext implements Serializable, Cloneable {
	private static final long serialVersionUID = -8321298246752932991L;
	protected String hashcode;
	UserSessionToken userSessionToken;
	protected RequestId requestId;
	private long affinityId;
	private long categoryId;
	

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

	/**
	 * @return the affinityId
	 */
	public long getAffinityId() {
		return affinityId;
	}

	/**
	 * @param affinityId the affinityId to set
	 */
	public void setAffinityId(long affinityId) {
		this.affinityId = affinityId;
	}

	/**
	 * @return the categoryId
	 */
	public long getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public Session getDBSession() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	
}