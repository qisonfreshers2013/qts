package com.qts.service.descriptors;

import org.codehaus.jackson.annotate.JsonProperty;

public interface DeleteUserOutputDescriptor {
	@JsonProperty
	public boolean getIsDeleted();
	
	

}
