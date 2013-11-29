package com.qts.exception;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;

/**
 * Should be thrown when we want to notify more details on exception
 * and expect clients to take some actions based on type of exception.
 * @author Vinay Thandra
 *
 */
@JsonAutoDetect(JsonMethod.SETTER)
public class FileUploadException extends BusinessException {

	public FileUploadException() {
		
	}
	
	public FileUploadException(int code) {
		this.setCode(code);
	}

	public FileUploadException(int code, String message) {
		this.setCode(code);
		this.setMessage(message);
		this.setArguments(new ArrayList());
	}	
	
}
