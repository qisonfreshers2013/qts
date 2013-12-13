package com.qts.service.descriptors;

import org.codehaus.jackson.annotate.JsonProperty;

public interface UserProjectOutputDescriptor extends
		JSONSerializationDescriptor {
	@JsonProperty
	public Long getUserId();
	
	@JsonProperty
	public String getProjectId();

}
