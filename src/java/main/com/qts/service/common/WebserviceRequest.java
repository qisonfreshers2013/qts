package com.qts.service.common;

/**
 * Wrapper class used to get the input for the webservice calls
 * @author Vinay Thandra
 *
 */
public class WebserviceRequest {

	private Object payload;

	private WebserviceSecurityObject securityObject;

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	public WebserviceSecurityObject getSecurityObject() {
		return securityObject;
	}

	public void setSecurityObject(WebserviceSecurityObject securityObject) {
		this.securityObject = securityObject;
	}
	
}
