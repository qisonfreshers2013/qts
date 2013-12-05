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

	
	//user web services releted exceptions
	public static final String USER_ID_NOT_NUMBER = "Id of user must be long";
	public static final String USER_NAME_FORMAT = "name cannot be null";
	public static final String USER_EMAIL_FORMAT = "email is invalid";
	public static final String EMP_ID_FORMAT = "employee id is invalid";
	public static final String INVALID_DESIGNATION_PATTERN = "designation is invalid";
	public static final String USER_ID_FORMAT = "invalid email pattern";
	public static final String PASSWORD_FORMAT = "invalid password";
	public static final String USER_ID_AND_PASSWORD_NULL = "user id and pass word are empty";
	public static final String USER_DETAILS_NULL = "Enter All Field";
	public static final String CONFIRM_PASSWORD_NULL = "Confirm password Null";
	public static final String CONFIRM_PASSWORD_NOT_EQUAL = "Confirm password is not equal to password";
	public static final String FIRST_NAME_SHOULD_NOT_NULL = "First Name should not empty";
	public static final String FIRST_NAME_INVALID = "First Name is invalid";
	public static final String LAST_NAME_SHOULD_NOT_NULL = "Last Name should  not be null";
	public static final String LAST_NAME_INVALID = "LastName is invalid";
	public static final String NICKNAME_INVALID = "Nickname is invalid";
	public static final String GENDER_NOT_NULL = "Gender Field is null";
	public static final String GENDER_INVALID = "Gender entered is invalid";
	public static final String EMAIL_FORMAT = "invalid Email";
	public static final String EMPLOYEE_ID_NULL = "Employee is null";
	public static final String EMPLOYEE_ID_INVALID = "Employee id is Invalid";
	public static final String USER_ID_NULL = "User Id can not be null";
}
