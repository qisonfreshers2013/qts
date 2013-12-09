package com.qts.service.descriptors;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.qts.model.Project;
import com.qts.model.TimeEntries;
import com.qts.service.annotations.SerializationDescriptor;

public interface TimeEntriesOptionsOutputDescriptor extends JSONSerializationDescriptor{
	
	@JsonProperty
	@SerializationDescriptor(value = TimeEntriesOptionOutputDescriptor.class)
	public List<Project> getData();
	
	@JsonProperty
	@SerializationDescriptor(value = TimeEntriesOptionOutputDescriptor.class)
	public List<TimeEntries> getTimeEntriesList();
	
}
