package com.qts.exception;

public class RolesException extends BusinessException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public RolesException() {
		
	}
	public RolesException(int code,String message)
	{
		this.code=code;
		this.message=message;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	private int code;
	private String message;
}
