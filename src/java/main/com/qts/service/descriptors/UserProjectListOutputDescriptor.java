package com.qts.service.descriptors;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.qts.model.UserProject;
import com.qts.service.annotations.SerializationDescriptor;

public interface UserProjectListOutputDescriptor extends
		JSONSerializationDescriptor {
	@JsonProperty
	@SerializationDescriptor(value = UserProjectOutputDescriptor.class)
	public List<UserProject> getData();
}
