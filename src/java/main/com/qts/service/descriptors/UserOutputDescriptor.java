package com.qts.service.descriptors;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.qts.model.SearchUserRecord;

public interface UserOutputDescriptor {

	@JsonProperty
	public Long getId();
	
	@JsonProperty
	public String getName();
}
