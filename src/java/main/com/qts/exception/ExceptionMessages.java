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

	//Releases Exception Messages
	public static final String RELEASES_NAME_CANNOT_BE_EMPTY = "Releases Name Cannot Be Empty.";
	public static final String RELEASES_NAME_CANNOT_CONTAIN_SPECIALCHARS = "Releases Name Format Exception.";
	public static final String RELEASES_NAME_LENGTH_IS_MORE = "Releases Name Cannot be More Than 128 characters.";
	public static final String RELEASES_ID_INVALID = "Releases ID is Invalid.";
	public static final String RELEASES_EMPTY_FOR_THE_PROJECT="There are no releases for this project";
	public static final String RELEASESBEAN_NOT_NULL = "Project Id Cannot be null";
	public static final String RELEASES_CANNOT_BE_ADDED_FOR_THE_PROJECT = "Cannot add Entry..Entry already Exists.";
    public static final String USER_NOT_AUTHENTICATED = "Please login.";
    public static final String TIME_ENTRY_PRESENT = "Time Entry is Referring The Record";

	
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
	public static final String USER_ID_AND_PASSWORD_INVALID = "User id/password does not exist";
	public static final String PASSWORD_NULL = "pass word can not be null";


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

	//roles exception
	public static final String ROLES_EMPTY_EXCEPTION="Select atleast one role.";
	public static final String ROLES_LIST_EMPTY_EXCEPTION="No Roles available add few.";
	public static final String PARAMETERS_EMPTY_EXCEPTION = "You need to specify correct userid and projectid";
	public static final String NO_ROLES_FOR_THIS_USERPROJECT_ID = "No roles allocated for this user for this project";
	public static final String INVALID_ROLE_ID = "Invalid roleid";
	public static final String ROLE_ID_EXISTS = "This role already exists for this user";
	public static final String ONLY_ONE_APPROVER = "only one approver allowed for one project";

}
