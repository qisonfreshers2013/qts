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
	
	
	
	//Releases related Exceptions Codes
	public static final int RELEASES_NAME_NULL = 9001;
	public static final int RELEASES_NAME_LENGTH = 9002;
	public static final int RELEASES_NAME_CONTAINS_NONALPHANUMERIC = 9003;
	public static final int RELEASES_ID_DOES_NOT_EXISTS = 9004;
	public static final int RELEASES_EMPTY=9005;
	public static final int RELEASESBEAN_NOT_NULL = 9006;
	public static final int RELEASES_CANNOT_BE_ADDED = 9007;
	public static final int PROJECT_ID_INVALID = 5005;
	
}
