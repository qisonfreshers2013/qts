package com.qts.service.descriptors;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.qts.model.Activities;
import com.qts.service.annotations.SerializationDescriptor;

public interface ActivitiesOptionsOutputDescriptor {

	@JsonProperty
	@SerializationDescriptor(value = OptionOutputDescriptor.class)
	public List<Activities> getData();

}
