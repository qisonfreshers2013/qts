package com.qts.service.descriptors;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.qts.model.Project;
import com.qts.model.Releases;
import com.qts.service.annotations.SerializationDescriptor;

public interface OptionsOutputDescriptor extends JSONSerializationDescriptor{
	
	@JsonProperty
	@SerializationDescriptor(value = OptionOutputDescriptor.class)
	public List<Project> getData();
	
	@JsonProperty
	@SerializationDescriptor(value = OptionOutputDescriptor.class)
	public List<Releases> getReleasesData();
}
