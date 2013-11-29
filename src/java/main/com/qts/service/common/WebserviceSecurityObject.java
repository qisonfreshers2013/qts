package com.qts.service.common;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

public class WebserviceSecurityObject implements Serializable {
	
	public static final String SESSION_TOKEN_NAME = "sessionId";
	public static final String SESSION_AFFINITY_URL = "affinityUrl";
	
	@JsonProperty private String sessionId;
	@JsonProperty private String affinityUrl;
	
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public String getAffinityUrl() {
		return affinityUrl;
	}

	public void setAffinityUrl(String affinityUrl) {
		this.affinityUrl = affinityUrl;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[WebserviceSecurityObject]").append("\n");
		builder.append("sessionId = ").append(sessionId).append("\n");
		builder.append("affinityUrl = ").append(affinityUrl).append("\n");
		return builder.toString();
	}
}