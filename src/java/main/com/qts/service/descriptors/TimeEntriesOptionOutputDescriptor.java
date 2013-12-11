package com.qts.service.descriptors;

import org.codehaus.jackson.annotate.JsonProperty;

public interface TimeEntriesOptionOutputDescriptor  extends JSONSerializationDescriptor{

	@JsonProperty
	public String getName();
	
	@JsonProperty
	public Long getDate();
	
	@JsonProperty
	public int getHours();
	
	@JsonProperty
	public int getStatus();
	
	@JsonProperty
	public int getProjectId();
	
	@JsonProperty
	public int getUSerId();
	
	@JsonProperty
	public int getId();
	
}
