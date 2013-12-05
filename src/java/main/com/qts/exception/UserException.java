package com.qts.exception;

import java.util.List;

public class UserException extends BusinessException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public UserException(){
		
	}
	public UserException(int code){
		super(code);
	}
	
	public UserException(int code,String message){
		super(code, message);
	}
	public UserException(int code,String message,List<Object> args){  //
		super(code, message, args);
				
	}
}
