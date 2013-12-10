package com.qts.service.descriptors;

import org.codehaus.jackson.annotate.JsonProperty;

public interface TimeEntriesOptionOutputDescriptor  extends JSONSerializationDescriptor{

	@JsonProperty
	public String getName();
	
	@JsonProperty
	public Long getDate();
	
	@JsonProperty
	public int getProjectId();
	
	@JsonProperty
	public int getUserId();
	
	@JsonProperty
	public int getReleaseId();
	
	@JsonProperty
	public int getTask();
	
	@JsonProperty
	public int ActivityId();
	@JsonProperty
	public int getHours();
	@JsonProperty
	public int getStatus();
	@JsonProperty
	public int getAppovedComments();
	@JsonProperty
	public int getRejectedComments();
	@JsonProperty
	public int getId();
	
}
