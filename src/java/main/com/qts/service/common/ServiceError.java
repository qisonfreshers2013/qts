package com.qts.service.common;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * This Error Object that will be sent to UI in case of any Exceptions (Business or System) 
 * @author Vinay Thandra
 *
 */
public class ServiceError {


	private int code;
	private String message;
	
	@JsonProperty
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	@JsonProperty
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
