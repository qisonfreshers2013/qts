package com.qts.exception;


/**
 * All the English Version Exception messages in the system 
 * @author Vinay Thandra
 *
 */
public interface ExceptionMessages {

	//General exceptions
	public static final String INVALID_EMAIL_PATTERN = "Invalid email pattern.";
	public static final String WEAK_PASSWORD = "Weak password.";
	public static final String EMAIL_DOESNOT_EXIST = "Wrong email or password.";
	public static final String INVALID_PASSWORD = "Wrong email or password.";//need change
	public static final String EMAIL_CANNOT_BE_EMPTY = "Email cannot be empty.";
	public static final String PASSWORD_CANNOT_BE_EMPTY = "Password cannot be empty.";
	public static final String INVALID_NAME = "Invalid name.";
	public static final String  CACHE_REGION_NOT_FOUND = "Cache region not found.";

	public static final String INTERNAL_ERROR = "Internal Error.";
	public static final String AUTH_INPUT_NULL = "Authentication error has occurred. Please try again.";
	
	
	//Project WebService related Exceptions
	public static final String PROJECT_NAME_NOT_NULL="Project Name field Can't Be Null.";
	public static final String PROJECT_NAME_LENGTH_MORE="Project Name field Can't Be More Than 128 Characters.";
	public static final String TECHNOLOGIES_FIELD_LENGTH_MORE="Technologies field Can't Be More Than 512 Characters.";
	public static final String ADD_PROJECT_FAILED="Adding of project failed. Please try again.";
	public static final String PROJECT_ID_INVALID="Invalid project Id";
	public static final String PROJECT_ID_NOT_NULL="Project Id can't be null";
	public static final String ADD_USER_TO_PROJECT_FAILED="Allocating user to project failed. Please try again.";
	public static final String PROJECT_OR_USER_ID_INVALID="Invalid project or user Id";
	public static final String PROJECT_NAME_FORMAT="Invalid format for project name";
	public static final String TECHNOLOGIES_NAME_FORMAT="Invalid format for project technologies";
	public static final String NO_PROJECTS_AVAILABLE="There is no projects";
	public static final String USER_PROJECT_ID_INVALID="User Project Id invalid";
	public static final String USER_ID_NOT_NULL="User id can not be null";
	public static final String USER_ID_INVALID="User id is invalid";
	public static final String DELETE_USER_FROM_PROJECT_FAILED="Deallocating users fro project failed.Invalid user and project id combination . Please try again.";
	
}
