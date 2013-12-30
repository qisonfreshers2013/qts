package com.qts.service.descriptors;

import org.codehaus.jackson.annotate.JsonProperty;

public interface TimeEntriesOptionOutputDescriptor  extends JSONSerializationDescriptor{

	@JsonProperty
	public String getName();
	
	@JsonProperty
	public Long getDate();
	
	@JsonProperty
	public long getProjectId();
	
	@JsonProperty
	public long getUserId();
	
	@JsonProperty
	public int getReleaseId();
	
	@JsonProperty
	public String getTask();
	
	@JsonProperty
	public int ActivityId();
	@JsonProperty
	public int getHours();
	@JsonProperty
	public int getStatus();
	@JsonProperty
	public String getAppovedComments();
	@JsonProperty
	public String getRejectedComments();
	@JsonProperty
	public Long getId();
	@JsonProperty
	public String getActivity();
	@JsonProperty
	public String getReleaseVersion();
	@JsonProperty
	public String getUserName();
	
}
