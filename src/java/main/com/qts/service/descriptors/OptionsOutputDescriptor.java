/**
 * 
 */
package com.qts.service.descriptors;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.qts.model.Project;
import com.qts.service.annotations.SerializationDescriptor;

public interface OptionsOutputDescriptor extends JSONSerializationDescriptor{
	
	@JsonProperty
	@SerializationDescriptor(value = OptionOutputDescriptor.class)
	public List<Project> getData();
}
