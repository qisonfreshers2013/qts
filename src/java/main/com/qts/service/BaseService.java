package com.qts.service;

import javax.ws.rs.CookieParam;

/**
 * Base Service for all the RESTFUL Services
 * @author Vinay Thandra
 *
 */
public class BaseService {

	public static final String SESSION_TOKEN_NAME = "qtsSessionId";
	public static final String SESSION_AFFINITY_URL = "affinityUrl";
	
	@CookieParam(SESSION_TOKEN_NAME)
	String qtsSessionId;
	
	@CookieParam(SESSION_AFFINITY_URL)
	String affinityUrl;
	/*
	 * Get the session id for the user session
	 */

    public BaseService() {
    }

    public String getSessionId() {
		return qtsSessionId;
	}
	
	public String getAffinityUrl() {
		return affinityUrl;
	}
	

}
