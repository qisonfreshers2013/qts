package com.qts.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonView;


/**
 * @author Vamsi Kuchi
 */
@JsonAutoDetect(JsonMethod.NONE)
public class UserSessionToken implements Serializable {

    private static final long serialVersionUID = -8723630138842849229L;
    protected String userSessionId;
    protected String userEmail;
    protected long userId;  
    protected String nickName; //anil

    /**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

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
    
	@Override
    public String toString() {
        return "[userSessionId:" + userSessionId +
                " :userId: " + userId +
                "]";
    }

}
