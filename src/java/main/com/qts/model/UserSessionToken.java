package com.qts.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonView;


/**
 * @author Vinay Thandra
 */
@JsonAutoDetect(JsonMethod.NONE)
public class UserSessionToken implements Serializable {

    private static final long serialVersionUID = -8723630138842849229L;
    public static final long ANONYMOUS_USER_ID = 0;

    protected String userSessionId;
    protected String userEmail;
    protected long userId;
    protected long roleId;
    protected String communityType;
    protected String registrationType;

    protected int authLevel;
    private long lastActiveAt;

    private String userIPAddress; 
    private String appKey;
    private String facebookProfileId;
    private String facebookAccessToken;
    
    public UserSessionToken() {
    }

    @JsonProperty
    @JsonView
    public String getUserSessionId() {
        return userSessionId;
    }

    public void setUserSessionId(String hash) {
        this.userSessionId = hash;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    
	/**
	 * @return the roleId
	 */
	public long getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId
	 *            the roleId to set
	 */
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	

	public boolean isAnonymousUser() {
        return userId == ANONYMOUS_USER_ID;
    }

    public int getAuthLevel() {
        return authLevel;
    }

    public void setAuthLevel(int authLevel) {
        this.authLevel = authLevel;
    }

    public long getLastActiveAt() {
        return lastActiveAt;
    }

    public void setLastActiveAt(long lastActiveAt) {
        this.lastActiveAt = lastActiveAt;
    }

    public String getUserIPAddress() {
        return this.userIPAddress;
    }

    public void setUserIPAddress(String userIPAddress) {
        this.userIPAddress = userIPAddress;
    }
    
    

    /**
	 * @return the appKey
	 */
	public String getAppKey() {
		return appKey;
	}

	/**
	 * @param appKey the appKey to set
	 */
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	/**
	 * @return the facebookProfileId
	 */
	public String getFacebookProfileId() {
		return facebookProfileId;
	}

	/**
	 * @param facebookProfileId the facebookProfileId to set
	 */
	public void setFacebookProfileId(String facebookProfileId) {
		this.facebookProfileId = facebookProfileId;
	}

	/**
	 * @return the facebookAccessToken
	 */
	public String getFacebookAccessToken() {
		return facebookAccessToken;
	}

	/**
	 * @param facebookAccessToken the facebookAccessToken to set
	 */
	public void setFacebookAccessToken(String facebookAccessToken) {
		this.facebookAccessToken = facebookAccessToken;
	}
	
	/**
	 * @return the communityType
	 */
	public String getCommunityType() {
		return communityType;
	}

	/**
	 * @param communityType
	 *            the communityType to set
	 */
	public void setCommunityType(String communityType) {
		this.communityType = communityType;
	}

	/**
	 * @return the registrationType
	 */
	public String getRegistrationType() {
		return registrationType;
	}

	/**
	 * @param registrationType
	 *            the registrationType to set
	 */
	public void setRegistrationType(String registrationType) {
		this.registrationType = registrationType;
	}

	@Override
    public String toString() {
        return "[userSessionId:" + userSessionId +
                " :userId: " + userId +
                " :lastActiveAt: " + lastActiveAt + "]";
    }

}
