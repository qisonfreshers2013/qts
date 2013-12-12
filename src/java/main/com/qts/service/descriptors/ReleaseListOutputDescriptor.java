package com.qts.service.descriptors;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.qts.model.Release;
import com.qts.service.annotations.SerializationDescriptor;

public interface ReleaseListOutputDescriptor extends
		JSONSerializationDescriptor {

	@JsonProperty
	@SerializationDescriptor(value = ReleaseOutputDescriptor.class)
	public List<Release> getReleasesData();

}
