package com.qts.service.descriptors;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public interface ProjectBeanDescriptor extends JSONSerializationDescriptor {
	@JsonProperty
	public Long getId();
	
	@JsonProperty
	public List<Long> getUserIds();
}
