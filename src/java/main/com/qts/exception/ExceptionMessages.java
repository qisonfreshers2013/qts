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
	public static final String DELETE_USER_FROM_PROJECT_FAILED="User already removed from project";
	public static final String REPORTING_USER_ID_NOT_NULL="Reporting user id can not be null";
	public static final String USER_PROJECT_CONSTRAINT_FAILED="User already a member of the project";
    public static final String USER_NOT_ASSOCIATED_WITH_PROJECT="User is not associated with project.";
	public static final String DUPLICATE_PROJECT_ENTRY="Duplicate Entry For Project Name ";
	public static final String USER_NOT_PART_OF_ANY_PROJECT = "user not associated with any project";
	public static final String NO_USER_ASSOCIATED_WITH_PROJECT ="No User is associated with this project";
	
	//Releases Exception Messages
	public static final String RELEASES_NAME_CANNOT_BE_EMPTY = "Releases Name Cannot Be Empty.";
	public static final String RELEASES_NAME_CANNOT_CONTAIN_SPECIALCHARS = "Releases Name Format Exception.";
	public static final String RELEASES_NAME_LENGTH_IS_MORE = "Releases Name Cannot be More Than 128 characters.";
	public static final String RELEASES_ID_INVALID = "Releases ID is Invalid.";
	public static final String RELEASES_EMPTY_FOR_THE_PROJECT="There are no releases for this project";
	public static final String RELEASESBEAN_NOT_NULL = "Project Id Cannot be null";
	public static final String RELEASES_CANNOT_BE_ADDED_FOR_THE_PROJECT = "Cannot add Entry..Entry already Exists.";
	public static final String TIME_ENTRY_PRESENT = "Time Entry is Referring The Record";
	public static final String RELEASE_ID_NULL = "Release ID cannot be null";


	//user web services releted exceptions
	public static final String USER_ID_NOT_NUMBER = "Id of user must be long";
	 public static final String USER_NAME_FORMAT = "name cannot be null";
	 public static final String USER_EMAIL_FORMAT = "email is invalid";
	 public static final String EMPLOYEE_ID_INVALID_FORMAT = "employee id is invalid";
	 public static final String DESIGNATION_PATTERN_INVALID = "designation is invalid";
	 public static final String USER_ID_FORMAT = "invalid email pattern";
	 public static final String PASSWORD_FORMAT = "invalid password";
	 public static final String USER_ID_AND_PASSWORD_NULL = "user id and pass word are empty";
	 public static final String USER_DETAILS_NULL = "Enter mandatory fields";
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
	 public static final String EMPLOYEE_ID_NULL = "Employee id is null";
	 public static final String EMPLOYEE_ID_INVALID = "Employee id is Invalid";
	 public static final String USER_ID_NULL = "User Id can not be null";
	 public static final String USER_ID_AND_PASSWORD_INVALID = "User id/password does not exist";
	 public static final String PASSWORD_NULL = "password can not be null";
	 public static final String USER_DOESNOT_EXIST = "user not existing with this id";
	 public static final String USER_CAN_NOT_ADDED = "user can not be added";
	 public static final String LOCATION_INVALID = "Invalid location";
	 public static final String DELETE_INVALID = "User not existing";
	 public static final String UPDATE_NOT_EXIST_INVALID = "invalid user id,Action can not be performed";
	 public static final String DELETED_ALREADY = "User already deleted";
	 public static final String SEND_MAIL_FAILED = "An Error occured.Try Again";
	 public static final String DELETE_ID_ZERO = "Choose id from one to perform delete operation";
	 public static final String OLD_PASSWORD_INVALID = "old password is invalid";
	 public static final String DUPLICATE_ENTRY_EMAIL = "Duplicate entry for Email";
	 public static final String DUPLICATE_ENTRY_EMPLOYEE_ID = "Duplicate entry for Employee Id";
	 public static final String DUPLICATE_ENTRY = "Duplicate Entry for Email or EmployeeId";
	 public static final String DESIGNATION_NULL = "designation Can not null";
	 public static final String EMAIL_USERID_NOT_EQUAL = "UserId must equal to Email";
	 public static final String UPDATE_HANDLAER_EXCEPTION = "exception in update handler,validations return false";
	 public static final String EMAIL_NOT_EXISTS = "Email is not existing";
	 public static final String USER_NOT_AUTHENTICATED = "Please login.";
	 public static final String SEARCH_RESULTS_NO_MATCH = "no matchs found";
	//Time Entry Exception messages
	public static final String DATE_CANNOT_BE_NULL= "DATE CANNOT BE NULL";
	public static final String DATE_LENGTH_MISMATCH= "DATE LENGTH SHOULD BE 10 AND DATE PATTERN IS MM/DD/YYYY";
	public static final String DATE_MONTH_EXCEPTION="MONTH LIES BETWEEN 1 AND 12 AND DATE PATTERN IS MM/DD/YYYY"; 
	public static final String DATE_EXCEPTION="DATE DOES NOT EXIST IN THE PRESENT MONTH AND DATE PATTERN IS MM/DD/YYYY";
	public static final String DATE_YEAR_EXCEPTION="ONLY CURRENT YEAR ACCEPTED.";
	public static final String INVALID_DATE_PATTERN= "ENTER DATE IN MM/DD/YYYY FORMAT.";
	public static final String PROJECTNAME_CANNOT_BE_EMPTY= "PROJECT NAME CANNOT BE EMPTY.";
	public static final String RELEASENAME_CANNOT_BE_EMPTY= "RELEASE NAME CANNOT BE EMPTY.";
	public static final String ACTIVITY_CANNOT_BE_EMPTY= "ACTIVITY CANNOT BE EMPTY.";
	public static final String HOURS_CANNOT_BE_NULL= "HOURS CANNOT BE NULL.";
	public static final String REMARKS_EXCEEDS_LENGTH= "REMARKS FIELD EXCEEDS DEFAULT LENGTH.";
	public static final String TASK_CANNOT_BE_EMPTY= "TASK CANNOT BE EMPTY.";
	public static final String TIMEENTRY_UPDATE_FAILED= "COULD NOT UPDATE DATA.";
	public static final String TIMEENTRY_DELETE_FAILED= "COULD NOT DELETE THE ENTRY.";
	public static final String TIEMENTRY_ADD_FAILED= "COULD NOT ADD THE TIME ENTRY.";
	public static final String TIMEENTRY_SUBMIT_FAILED= "COULD NOT SUBMIT TIME ENTRIES.";
	public static final String TIMEENTRY_REJECT_FAILED= "REJECTION OF TIME ENTRY FAILED.";
	public static final String TIMEENTRY_APPROVE_FAILED= " APPROVING TIME ENTRY FAILED.";
	public static final String TIMEENTRY_USERSEARCH="No TimeEntries Found.";
	public static final String TIMEENTRY_FILLING_FOR_APPROVER="APPROVER CANNOT FILL TIMESHEET UNLESS HE IS A MEMBER.";
	public static final String TIMEENTRY_APPROVER_NOT_AUTHORIZED="APPROVER IS NOT AUTHORIZED FOR THIS PROJECT";
	public static final String FORMFIELD_TO_NOT_PRESENT="search from which date to given date pass the from field";
	public static final String ILLEGAL_HOURS_ARGUMENT_PASSED="USER ALREADY SUBMITTED TIME SHEETS FOR 24 HOURS.";
	public static final String INVALID_ARGUMENTS_FOR_DATE="DATE CONTAINS ONLY NUMBERS AND PATTERN IS MM/DD/YYYY or MM-DD-YYYY";
	public static final String ILLEGAL_YEAR_PASSED="Only in January Year can be previous year.";
	public static final String PASSED_DATA_IS_NOT_RELATED="Passed data such as Project name,Release name,Activity mismatch.";
	public static final String SEARCH_NOT_ALLOWED="Pass Dates in Correct Order";
	public static final String SEARCH_FUTURE_SHEETS="PASSED ILLEGAL DATE I.E.,FUTURE DATE";
	public static final String FILLING_FUTURE_TIMESHEETS="FUTURE TIME SHEETS ARE NOT ALLOWED TO FILL";
	public static final String TIMEENTRY_USER_NOT_AUTHORIZED="USERS/ADMIN ARE NOT AUTHORIZED TO PERFORM SEARCH OPERATION";
	public static final String SELECT_APPROPRIATE_FIELDS_FOR_SEARCH="Select USERID,PROJECTID,STATUS of the user for search";
	//roles exception
	public static final String ROLES_EMPTY_EXCEPTION="Select atleast one role.";
	public static final String ROLES_LIST_EMPTY_EXCEPTION="No Roles available add few.";
	public static final String PARAMETERS_EMPTY_EXCEPTION = "You need to specify correct userid and projectid";
	public static final String NO_ROLES_FOR_THIS_USERPROJECT_ID = "No roles allocated for this user for this project";
	public static final String INVALID_ROLE_ID = "Invalid roleid";
	public static final String ROLE_ID_EXISTS = "This role already exists for this user";
	public static final String ONLY_ONE_APPROVER = "only one approver allowed for one project";
	public static final String ROLE_ID_DOESNOT_EXISTS = "This role id doesnot exists for this user.";

	
	

}

