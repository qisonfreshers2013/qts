package com.qts.common;

/**
 * @author User
 *
 */
public interface Constants {

	//Mongo Configuration related constants
	public static final String MONGO_CONNECTION_CONFIG_FILE = "mongoconfiguration.properties";
	public static final String MONGO_COLLECTION_CONFIG_FILE = "mongocollection.properties";
	public static final String MONGO_COLLECTION_DBNAMES_FILE = "mongocollectiondbname.properties";
	public static final String MONGO_DBNAME = "qtsdb";
	public static String MEMCACHED_FILE = "memcached.properties";
	public static String MEMCACHED_SERVERS = "servers";

	public static String SYSTEM_EXCEPTION_LOGGER = "appsystemexception";
    public static String BUSINESS_EXCEPTION_LOGGER = "appbusinessexception";
    public static String UNHANDLED_EXCEPTION_LOGGER = "appunhandledexception";
    public static String BUSINESS_INFODUMPS_LOGGER = "infodumps";
    
    public static final Short NOT_DELETED = 0;
    public static final Short DELETED = 1;
    
    public static final long ENGAGEMENT_MODEL_DISCUSSIONS = 100;
    public static final long ENGAGEMENT_MODEL_POLLS = 200;
    public static final long ENGAGEMENT_MODEL_SESSIONS = 300;
    public static final long ENGAGEMENT_MODEL_STATIC_CONTENT = 5000;
    
    public static final String ENTITY_ARTICLE = "Article";
    public static final String ENTITY_TAG = "Tag";

    public static String AGREED = "agreed";
    public static String DIS_AGREED = "disagreed";
    public static String NO_ACTION = "noAction";
    
    public static String LIKED = "liked";
    public static String NOT_LIKED = "notLiked";
    public static String USER_NOT_LOGGED_IN = "userNotLoggedIn";
    
    public static final String LOGIN = "Login";
    
    public static final String REGISTRATION_SUCCESS = "Registered successfully";
    public static final String REGISTRATION_PENDING = "Your request is received, please wait for further process";
    
    public static String STATUS_ACTIVE = "active";
    public static String STATUS_INACTIVE = "inactive";
    public static String STATUS_COMPLETED = "completed";
    public static String STATUS_DELETED	 = "deleted";
    
    public static String SOURCE_GLOBAL = "global";
    public static String SOURCE_CATEGORY = "category";
	public static String SOURCE_ARTICLE = "article";
	public static String SOURCE_POLL = "poll";
	public static String SOURCE_USER = "user";
	public static String SOURCE_FEED = "feed";

}
