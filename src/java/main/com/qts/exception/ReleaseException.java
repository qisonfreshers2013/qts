package com.qts.exception;

import java.util.List;

/**
 * 
 * @author Shiva
 * 
 */

// ReleasesException Class
public class ReleaseException extends BusinessException {
	public ReleaseException() {

	}

	public ReleaseException(int code) {
		this.setCode(code);
	}

	public ReleaseException(int code, String message) {
		this.setCode(code);
		this.setMessage(message);
	}

	public ReleaseException(int code, String message, List<Object> args) {
		this.setCode(code);
		this.setMessage(message);
		this.setArguments(args);
	}

}
