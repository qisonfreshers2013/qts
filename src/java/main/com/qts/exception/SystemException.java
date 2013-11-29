package com.qts.exception;

import java.util.List;

/**
 * Instance of this class throws runtime exceptions that need not be caught by any clients
 * This also means that this kind of exceptions are from failure in system and only option 
 * available is to notify the user about system state
 * @author Vinay Thandra
 *
 */
public class SystemException extends RuntimeException {

	private int code;
	private String message;
	private List<Object> arguments;

	public SystemException(int code) {
		this.code = code;
	}

	public SystemException(int code, Throwable t) {
		this.code = code;
	}

	public SystemException(int code, String[] args, Throwable t) {
		this.code = code;
	}
	
	public SystemException(int code, String msg, Throwable t) {
		this.code = code;
		this.message = msg;
	}
	
	public SystemException(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public SystemException(int code, String message, List<Object> args) {
		this.code = code;
		this.message = message;
		this.arguments = args;
	}
	
	public SystemException(String message) {
		this.message = message;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<Object> getArguments() {
		return arguments;
	}
	public void setArguments(List<Object> arguments) {
		this.arguments = arguments;
	}
	
}
