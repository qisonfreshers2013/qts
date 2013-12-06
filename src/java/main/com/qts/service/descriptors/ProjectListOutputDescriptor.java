package com.qts.service.descriptors;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.qts.model.ProjectBean;
import com.qts.service.annotations.SerializationDescriptor;

public interface ProjectListOutputDescriptor extends
		JSONSerializationDescriptor {
	@JsonProperty
	@SerializationDescriptor(value = OptionOutputDescriptor.class)
	public List<ProjectBean> getData();
}
