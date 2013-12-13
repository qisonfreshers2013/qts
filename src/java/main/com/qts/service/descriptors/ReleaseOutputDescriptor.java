package com.qts.service.descriptors;

import org.codehaus.jackson.annotate.JsonProperty;

public interface ReleaseOutputDescriptor extends JSONSerializationDescriptor {
	
	@JsonProperty
	public Long getId();

	@JsonProperty
	public String getName();

}
