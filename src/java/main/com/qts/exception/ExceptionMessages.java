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

	//Time Entry Exception messages
	public static final String DATE_CANNOT_BE_NULL= "DATE CANNOT BE NULL";
	public static final String DATE_LENGTH_MISMATCH= "DATE LENGTH SHOULD BE 10 AND DATE PATTERN IS MM/DD/YYYY";
	public static final String DATE_MONTH_EXCEPTION="MONTH LIES BETWEEN 1 AND 12 AND DATE PATTERN IS MM/DD/YYYY";	
	public static final String DATE_EXCEPTION="DATE DOES NOT EXIST IN THE PRESENT MONTH AND DATE PATTERN IS MM/DD/YYYY";
	public static final String DATE_YEAR_EXCEPTION="ONLY CURRENT YEAR ACCEPTED";
	public static final String INVALID_DATE_PATTERN= "ENTER DATE IN MM/DD/YYYY FORMAT";
	public static final String PROJECTNAME_CANNOT_BE_EMPTY= "PROJECT NAME CANNOT BE EMPTY";
	public static final String RELEASENAME_CANNOT_BE_EMPTY= "RELEASE NAME CANNOT BE EMPTY";
	public static final String ACTIVITY_CANNOT_BE_EMPTY= "ACTIVITY CANNOT BE EMPTY";
	public static final String HOURS_CANNOT_BE_NULL= "HOURS CANNOT BE NULL";
	public static final String REMARKS_EXCEEDS_LENGTH= "REMARKS FIELD EXCEEDS DEFAULT LENGTH";
	public static final String TASK_CANNOT_BE_EMPTY= "TASK CANNOT BE EMPTY";

}
