package com.qts.exception;

/**
 * All Exception Codes in the system
 * @author Vinay Thandra
 *
 */
public interface ExceptionCodes {

	//General exceptions
	public static final int OBJECT_NOT_FOUND = 101;
	public static final int EMAIL_ALREADY_IN_USE = 102;
	public static final int INVALID_EMAIL_PATTERN = 103;
	public static final int WEAK_PASSWORD = 104;
	public static final int ENCRYPTION_FAILED = 105;
	public static final int EMAIL_DOESNOT_EXIST = 106;
	public static final int INVALID_PASSWORD = 107;
	public static final int AFFINITY_TYPE_DOESNOT_EXIST = 108;
	public static final int USER_NOT_AUTHENTICATED = 109;
	public static final int CACHE_REGION_NOT_FOUND = 110;
	public static final int CACHE_NAME_NOT_PROVIDED = 111;
	public static final int CATEGORY_DOESNOT_EXIST = 112;
	public static final int ARTICLE_DOESNOT_EXIST = 113;
	public static final int AFFINITY_DOESNOT_EXIST = 114;
	public static final int MARKER_DOESNOT_EXIST = 115;
	public static final int USER_DOESNOT_EXIST = 116;
	public static final int TAG_DOESNOT_EXIST = 117;
	public static final int TAG_ALREADY_IN_USE = 118;
	public static final int ARTICLE_ALREADY_LIKED = 119;
	public static final int ARTICLE_ALREADY_RATED = 120;
	public static final int COMMENT_ALREADY_LIKED = 121;
	public static final int COMMENT_DOESNOT_EXIST = 122;
	public static final int PARENT_COMMENT_DOESNOT_EXIST = 123;
	public static final int PARENT_COMMENT_ARCHIVED = 124;
	public static final int COMMENT_LEVEL_EXCEEDED = 125;
	public static final int COMMENT_ARCHIVED = 126;
	public static final int ARTICLE_ARCHIVED = 127;
	public static final int COMMENT_CANNOT_BE_EMPTY  = 128;
	public static final int COMMENT_MAX_CHARECTERS_LENGTH  = 129;
	public static final int ROLE_DOESNOT_EXIST = 130;
	public static final int USER_NOT_AUTHORIZED = 131;
	public static final int CATEGORY_PRIVILEGE_DOESNOT_EXIST = 132;
	public static final int ACTION_DOESNOT_EXIST = 133;
	public static final int ENGAGEMENT_MODEL_DOESNOT_EXIST =134;
	public static final int FILE_DOESNOT_EXIST =135;
	public static final int AUTHOR_CANNOT_BE_EMPTY =136;
	public static final int MINIMUM_CHARECTERS_FOR_SEARCH =137;
	public static final int PASSWORD_DIDNOT_MATCH =138;
	public static final int ARTICLE_CANNOT_BE_EDITED = 139;
	public static final int INVALID_RESET_PASSWORD_TOKEN = 140;
	public static final int RESET_PASSWORD_TOKEN_EXPIRED = 141;
	public static final int FB_CONFIG_NOT_FOUND = 142;
	public static final int NOT_A_REGULAR_USER = 143;
	public static final int FORGOT_PASSWORD_DETAIL_NOTFOUND = 144;
	public static final int FORGOT_PASSWORD_EMAIL_ALREADY_SENT = 145;
	public static final int TEMPLATE_NOT_AVAILABLE = 146;
	public static final int AFFINITY_RECOMMENDATION_INFO_DOESNOT_EXIST = 147;
	public static final int FIRSTNAME_CANNOT_BE_EMPTY = 148;
	public static final int LASTNAME_CANNOT_BE_EMPTY = 149;
	public static final int EMAIL_CANNOT_BE_EMPTY = 150;
	public static final int PASSWORD_CANNOT_BE_EMPTY = 151;
	public static final int OLD_PASSWORD_CANNOT_BE_EMPTY = 152;
	public static final int NEW_PASSWORD_CANNOT_BE_EMPTY = 153;
	public static final int CATEGORY_CANNOT_BE_EMPTY = 154;
	public static final int CATEGORY_ALREADY_IN_USE = 155;
	public static final int TAG_CANNOT_BE_EMPTY = 156;
	public static final int AFFINITY_TYPE_CANNOT_BE_EMPTY = 157; 
	public static final int AFFINITY_TYPE_ALREADY_IN_USE = 158;
	public static final int AFFINITY_CANNOT_BE_EMPTY = 159;
	public static final int USER_RATINGS_FOR_ARTICLE_NOT_FOUND = 160;
	public static final int MARKER_CANNOT_BE_EMPTY = 161;
	public static final int POLL_DOESNOT_EXIST = 162;
	public static final int OPTION_TYPE_DOESNOT_EXIST = 163;
	public static final int QUESTION_DOESNOT_EXIST = 164;
	public static final int OPTION_DOESNOT_EXIST = 165;
	public static final int PAGE_DOESNOT_EXIST = 166;
	public static final int INVALID_PAGE = 167;
	public static final int MINIMUM_OPTIONS = 168;
	public static final int MAXIMUM_OPTIONS = 169;
	public static final int GO_LIVE_ON_DATE_CANNOT_BE_EMPTY = 170;
	public static final int ARTICLE_TITLE_CANNOT_BE_EMPTY = 171;
	public static final int ARTICLE_MIN_CHARECTERS_LENGTH  = 172;
	public static final int ARTICLE_MAX_CHARECTERS_LENGTH  = 173;
	public static final int POLL_CANNOT_BE_EDITED = 174;
	public static final int POLL_TITLE_CANNOT_BE_EMPTY = 175;
	public static final int ENGAGEMENT_MODEL_CANNOT_BE_EMPTY = 176;
	public static final int TEXT_CANNOT_BE_EMPTY = 177;
	public static final int IMAGE_CANNOT_BE_EMPTY = 178;
	public static final int VIDEO_CANNOT_BE_EMPTY = 179;
	public static final int AUDIO_CANNOT_BE_EMPTY = 180;
	public static final int POLL_EXPIRED = 181;
	public static final int POLL_START_DATE_CANNOT_BE_EMPTY = 182;
	public static final int POLL_END_DATE_CANNOT_BE_EMPTY = 183;
	public static final int POLL_DESCRIPTION_CANNOT_BE_EMPTY = 184;
	public static final int INVALID_POLL_DATE = 185;
	public static final int INVALID_NAME = 186;
	public static final int PROMOTED_CONTENT_DOESNOT_EXIST = 187;
	public static final int CONFIGURED_WHITE_LABEL_DOESNOT_EXIST = 188;
	public static final int CONFIGURED_WHITE_LABEL_ALREADY_IN_USE = 189;
	public static final int MAIL_DOESNOT_EXIST = 190;
	public static final int WRONG_PASSWORD = 191;
	public static final int MARKER_ALREADY_IN_USE = 192;
	public static final int FEATURED_ALREADY_IN_USE = 193;
	public static final int FEATURED_MAX_LIMIT_REACHED = 194;
	public static final int FEATURED_DOES_NOT_EXIST = 195;
	public static final int INVALID_PROMOTED_CONTENT_REORDER_INPUT = 196;
	public static final int QUESTION_NOTYET_ANSWERED_BY_USERS = 197;
	public static final int COMMENT_AGREED_ALREADY = 198;
	public static final int COMMENT_DISAGREED_ALREADY = 199;
	public static final int COMMENT_FLAGGED_ALREADY = 200;
	public static final int COMMENT_ALREADY_IN_UNFLAGGED_STATE = 201;
	public static final int COMMENT_AGREE_OR_DISAGREE_OBJECT_NOT_FOUND = 202;
	public static final int YOU_ARE_NOT_AUTHORIZED_MEMBER_TO_LOGIN = 203;
	public static final int YOUR_REQUEST_IS_PENDING = 204;
	public static final int YOUR_REQUEST_REJECTED = 205;
	public static final int STATIC_CONTENT_EXISTS_IN_THIS_CATEGORY = 206;
	public static final int STATIC_CONTENT_DOESNOT_EXIST = 207;
	public static final int FEED_DOESNOT_EXIST = 208;
	public static final int STATIC_CONTENT_SAVE_FAILED = 209;
	public static final int STATIC_CONTENT_UPDATION_FAILED = 210;
	public static final int STATIC_BLOCK_DOESNOT_EXIST = 211;
	public static final int STATIC_BLOCK_TYPE_ALREADY_IN_USE = 212;
	public static final int CANT_FLAG_SELF_COMMENT = 213;
	public static final int CANNOT_AGREE_ON_OWN_COMMENT = 214;
	public static final int CANNOT_DISAGREE_ON_OWN_COMMENT = 215;
	public static final int INVALID_FEATURED_CONTENT_REORDER_INPUT = 216;
	public static final int TAG_SOURCE_DOESNOT_EXIST = 217;
	public static final int EVENT_START_DATE_MUST_BE_FUTURE_DATE = 218;
	public static final int EVENT_IN_LIVE_CONTENT_CANNOT_BE_CHANGED = 219;
	public static final int CANNOT_AGREE_OR_DISAGREE_FLAGGED_OR_REMOVED_COMMENT = 220;

	//Mongo related exception Codes
	public static final int COULD_NOT_START_MONGO_CLIENT = 1001;
	public static final int DATABASE_CONFIGURATION_LOAD_ERROR = 1002;

	public static final int INTERNAL_ERROR = 2001;
	public static final int INVALID_USER_SESSION = 2002;
	public static final int PROP_FILE_NOT_FOUND = 2003;
	public static final int EMAIL_SENDING_FAILED = 2004;

	public static final int USER_FOLDER_SIZE_EXCEEDED = 3001;
	public static final int FILE_SIZE_EXCEEDED = 3002;
	public static final int FILE_EXTENSION_NOT_ALLOWED = 3003;
	public static final int EMPTY_LIST = 3004;
	public static final int FILE_SAVE_EXCEPTION = 3005;
	public static final int FILE_IS_NULL = 3006;	


	// facebook related exceptions

	public static final int URI_EXCEPTION = 4001;
	public static final int FB_REQUEST_FAILED = 4002;
	public static final int FB_GET_PROFILE_FAILED = 4003;
	public static final int FB_GET_FRIENDS_FAILED = 4004;
	public static final int FB_POST_FAILED = 4005;
	public static final int FB_UNKNOWN = 4006;
	public static final int AUTH_INPUT_NULL = 4007;
	public static final int FB_USER_SETTING_INPUT_NULL = 4008;
	public static final int FACEBOOK_ID_DOESNOT_EXIST = 4009;






	//Project WebService related Exceptions
	public static final int PROJECT_NAME_NOTNULL=5001;
	public static final int PROJECT_NAME_LENGTH_MORE=5002;
	public static final int TECHNOLOGIES_FIELD_LENGTH_MORE=5003;
	public static final int ADD_PROJECT_FAILED=5004;
	public static final int PROJECT_ID_INVALID=5005;
	public static final int PROJECT_ID_NOT_NULL=5006;
	public static final int ADD_USER_TO_PROJECT_FAILED=5007;
	public static final int PROJECT_OR_USER_ID_INVALID=5008;
	public static final int PROJECT_NAME_FORMAT=5009;
	public static final int TECHNOLOGIES_NAME_FORMAT=5010;
	public static final int NO_PROJECTS_AVAILABLE=5011;
	public static final int USER_PROJECT_ID_INVALID=5012;
	public static final int USER_ID_NOT_NULL=5013;
	public static final int USER_ID_INVALID=5014;
	public static final int DELETE_USER_FROM_PROJECT_FAILED=5015;
	public static final int USER_PROJECT_CONSTRAINT_FAILED=5016;
	public static final int REPORTING_USER_ID_NOT_NULL=5017;
	public static final int DUPLICATE_PROJECT_ENTRY=5018;
	public static final int USER_NOT_PART_OF_ANY_PROJECT = 5019;
	public static final int NO_USER_ASSOCIATED_WITH_PROJECT =5020;






	//Time Sheet entry
	public static final int DATE_CANNOT_BE_NULL = 6001;
	public static final int DATE_LENGTH_MISMATCH = 6002;
	public static final int DATE_FORMAT_EXCEPTION = 6003;
	public static final int PROJECTNAME_CANNOT_BE_NULL = 6004;
	public static final int PROJECTNAME_EXCEEDS_SIZE = 6005;
	public static final int RELEASENAME_CANNOT_BE_NULL = 6006;
	public static final int RELEASENAME_EXCEEDS_SIZE = 6007;
	public static final int ACTIVITY_CANNOT_BE_NULL = 6008;
	public static final int ACTIVITY_EXCEEDS_SIZE = 6009;
	public static final int TASK_CANNOT_BE_NULL = 6010;
	public static final int TASK_EXCEEDS_SIZE = 6011;
	public static final int HOURS_CANNOT_BE_NULL = 6012;
	public static final int HOURS_EXCEEDS_SIZE = 6013;
	public static final int REMARK_EXCEEDS_SIZE = 6014;
	public static final int TIMEENTRY_SUBMISSION_FAILED=6015;
	public static final int TIMEENTRY_UPDATE_FAILED=6016;
	public static final int TIMEENTRY_DELETION_FAILED=6017;
	public static final int TIMEENTRY_ADDITION_FAILED=6018;
	public static final int TIMEENTRY_REJECT_FAILED=6019;
	public static final int TIMEENTRY_APPROVE_FAILED=6020;
	public static final int TIMEENTRY_SEARCH_STATUS=6021;
	public static final int TIMEENTRY_FILLING_IS_NOT_ALLOWED_FOR_APPROVER=6022;
    public static final int ILLEGAL_ARGUMENT_HOURS_FIELD=6023;
    public static final int MANDATORY_FIELDS_MISMATCH=6024;
    public static final int SEARCH_NOT_ALLOWED=6025;
	 //user codes
	 public static final int USER_ID_NOT_NUMBER = 7001;
	 public static final int USER_NAME_FORMAT = 7002;
	 public static final int USER_EMAIL_FORMAT = 7003;
	 public static final int EMPLOYEE_ID_INVALID_FORMAT = 7004;
	 public static final int DESIGNATION_PATTERN_INVALID = 7005;
	 //public static final String DESIGNATION_FORMAT = null;
	 public static final int USER_ID_FORMAT = 7006;
	 public static final int PASSWORD_FORMAT = 7007;
	 public static final int USER_ID_AND_PASSWORD_NULL = 7008;
	 public static final int USER_DETAILS_NULL = 7009;
	 public static final int CONFIRM_PASSWORD_NULL = 7010;
	 public static final int CONFIRM_PASSWORD_NOT_EQUAL = 7011;
	 public static final int FIRST_NAME_SHOULD_NOT_NULL = 7012;
	 public static final int FIRST_NAME_INVALID = 7013;
	 public static final int LAST_NAME_SHOULD_NOT_NULL = 7014;
	 public static final int LAST_NAME_INVALID = 7015;
	 public static final int NICKNAME_INVALID = 7016;
	 public static final int GENDER_NOT_NULL = 7017;
	 public static final int GENDER_INVALID = 7018;
	 public static final int EMAIL_FORMAT = 7019;
	 public static final int EMPLOYEE_ID_NULL = 7020;
	 public static final int EMPLOYEE_ID_INVALID = 7021;
	 public static final int USER_ID_NULL = 7022;
	 public static final int USER_ID_AND_PASSWORD_INVALID = 7023;
	 public static final int PASSWORD_NULL = 7024;
	 public static final int USER_CAN_NOT_ADDED = 7025;
	 public static final int LOCATION_INVALID = 7026;
	 public static final int DELETE_INVALID = 7027;
	 public static final int UPDATE_NOT_EXIST_INVALID = 7028;
	 public static final int DELETED_ALREADY = 7029;
	 public static final int SEND_MAIL_FAILED = 7030;
	 public static final int DELETE_ID_ZERO = 7031;
	 public static final int SEARCH_RESULTS_NO_MATCH = 7032;
	 public static final int UPDATE_HANDLAER_EXCEPTION = 7035;
	 public static final int OLD_PASSWORD_INVALID = 7036; 
	 public static final int DUPLICATE_ENTRY_EMAIL = 7037;//not used
	 public static final int DUPLICATE_ENTRY_EMPLOYEE_ID = 7038;//not used
	 public static final int DUPLICATE_ENTRY = 7039;
	 public static final int DESIGNATION_NULL = 7040;
	 public static final int EMAIL_USERID_NOT_EQUAL = 7041;
	 public static final int EMAIL_NOT_EXISTS = 7042;

	//roles related exception
	public static final int ROLES_EMPTY_EXCEPTION=8000;
	public static final int rolesList_EMPTY_EXCEPTION=8001;
	public static final int PARAMETERS_EMPTY_EXCEPTION =8002;
	public static final int NO_ROLES_FOR_THIS_USERPROJECT_ID = 8003;
	public static final int INVALID_ROLE_ID = 8004;
	public static final int ROLE_ID_EXISTS = 8005;
	public static final int ONLY_ONE_APPROVER = 8006;
	public static final int PARAMETERS_ZERO_EXCEPTION = 8007;
	public static final int ROLE_ID_DOESNOT_EXISTS =8008;
	public static final int NULL_POINTER_EXCEPTION = 8009;
	public static final int BOTH_ADMIN_MEMBER_NOT_POSSIBLE = 8010;
	public static final int USER_IS_ADMIN_SO_CANNOT_BE_MEMBER = 8011;
	public static final int USER_IS_MEMBER_SO_CANNOT_BE_ADMIN = 8012;
	
	//Releases related Exceptions Codes
	public static final int RELEASES_NAME_NULL = 9001;
	public static final int RELEASES_NAME_LENGTH = 9002;
	public static final int RELEASES_NAME_CONTAINS_NONALPHANUMERIC = 9003;
	public static final int RELEASES_ID_DOES_NOT_EXISTS = 9004;
	public static final int RELEASES_EMPTY=9005;
	public static final int RELEASESBEAN_NOT_NULL = 9006;
	public static final int RELEASES_CANNOT_BE_ADDED = 9007;
	public static final int TIME_ENTRY_PRESENT = 9008;
	public static final int RELEASE_ID_NULL = 9009;
	public static final int DB_EXCEPTION = 9010;
	
	
	
	

}
