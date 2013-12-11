package com.qts.exception;

import java.util.List;

public class TimeEntryException extends BusinessException{

	public TimeEntryException() {
		// TODO Auto-generated constructor stub
	}
		

	public TimeEntryException(int code) {
		super(code);
	}

	public TimeEntryException(int code, String message) {
		super(code, message);
	}
	
	public TimeEntryException(int code, String message, List<Object> args) {
		super(code, message, args);
	}

}
