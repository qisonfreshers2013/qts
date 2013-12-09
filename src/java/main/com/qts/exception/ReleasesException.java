package com.qts.exception;

import java.util.List;
/**
 * 
 * @author Shiva
 *
 */

//ReleasesException Class
public class ReleasesException extends BusinessException {
public ReleasesException() {
		
	}
	
	public ReleasesException(int code) {
		this.setCode(code);
	}

	public ReleasesException(int code, String message) {
		this.setCode(code);
		this.setMessage(message);
	}
	
	public ReleasesException(int code, String message, List<Object> args) {
		this.setCode(code);
		this.setMessage(message);
		this.setArguments(args);
	}

}
