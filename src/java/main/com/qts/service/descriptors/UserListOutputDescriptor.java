/**
 * 
 */
package com.qts.service.descriptors;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.qts.model.User;
import com.qts.service.annotations.SerializationDescriptor;

/**
 * @author AnilRam
 *
 */
public interface UserListOutputDescriptor {

	@JsonProperty
	 @SerializationDescriptor(value = OptionOutputDescriptor.class)
	 public List<User> getData();
	
}
