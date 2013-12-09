/**
 * 
 */
package com.qts.exception;

import java.util.List;

/**
 * @author Mani Kumar
 *
 */
public class ProjectException extends BusinessException{
	
public ProjectException() {
		
	}		

	public ProjectException(int code) {
		super(code);
	}

	public ProjectException(int code, String message) {
		super(code, message);
	}
	
	public ProjectException(int code, String message, List<Object> args) {
		super(code, message, args);
	}

}
