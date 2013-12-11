package com.qts.exception;

import java.util.List;

public class InvalidTimeEntryDataException extends BusinessException {

	public InvalidTimeEntryDataException() {
		// TODO Auto-generated constructor stub
	}
	public InvalidTimeEntryDataException(int code) {
		super(code);
	}

	public InvalidTimeEntryDataException(int code, String message) {
		super(code, message);
	}
	
	
}
