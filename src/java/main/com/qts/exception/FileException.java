package com.qts.exception;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;

@JsonAutoDetect(JsonMethod.SETTER)
public class FileException extends ObjectNotFoundException {
	
	private Exception exception;
	
	public FileException() {
		
	}
	
	public FileException(int code) {
		this.setCode(code);
	}

	public FileException(int code, String message) {
		this.setCode(code);
		this.setMessage(message);
		this.setArguments(new ArrayList());
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}	

	
}
