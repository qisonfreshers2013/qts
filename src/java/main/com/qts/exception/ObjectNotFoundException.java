package com.qts.exception;

import java.util.List;

/**
 * Throws when data is input or output is not found
 * @author Vinay Thandra
 *
 */
public class ObjectNotFoundException extends BusinessException {
	
	public ObjectNotFoundException() {
		
	}		

	public ObjectNotFoundException(int code) {
		super(code);
	}

	public ObjectNotFoundException(int code, String message) {
		super(code, message);
	}
	
	public ObjectNotFoundException(int code, String message, List<Object> args) {
		super(code, message, args);
	}
}
