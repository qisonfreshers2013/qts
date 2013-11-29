package com.qts.exception;

import java.util.List;

/**
 * Configuration Initialization Exception
 * @author Vinay Thandra
 *
 */
public class ConfigurationInitializationException extends SystemException {

	public ConfigurationInitializationException(int code) {
		super(code);
	}

	public ConfigurationInitializationException(int code, String message) {
		super(code, message);
	}

	public ConfigurationInitializationException(int code, String message, List<Object> args) {
		super(code, message, args);
	}

}
