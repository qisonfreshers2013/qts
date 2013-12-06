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
	
	//Releases Exception Messages
	public static final String RELEASES_NAME_CANNOT_BE_EMPTY = "Releases Name Cannot Be Empty.";
	public static final String RELEASES_NAME_CANNOT_CONTAIN_SPECIALCHARS = "Releases Name Format Exception.";
	public static final String RELEASES_NAME_LENGTH_IS_MORE = "Releases Name Cannot be More Than 128 characters.";
	public static final String RELEASES_ID_INVALID = "Releases ID is Invalid.";
	public static final String RELEASES_EMPTY_FOR_THE_PROJECT="There are no releases for this project";
	public static final String RELEASESBEAN_NOT_NULL = "Project Id Cannot be null";
	public static final String RELEASES_CANNOT_BE_ADDED_FOR_THE_PROJECT = "Cannot add Entry..Entry already Exists.";
	public static final String PROJECT_ID_INVALID = "Invalid Project Id";

}
