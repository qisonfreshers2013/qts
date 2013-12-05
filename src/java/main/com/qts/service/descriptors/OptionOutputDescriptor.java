/**
 * 
 */
package com.qts.service.descriptors;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.qts.model.SearchUserRecord;

/**
 * @author AnilRam
 *
 */
public interface OptionOutputDescriptor extends JSONSerializationDescriptor {
//	@JsonProperty
//	public String getPhoto();

	@JsonProperty
	public String getEmail();
	
	@JsonProperty
	public String getPassword();
	
//	@JsonProperty
//	public String getDesignation();
	
//	@JsonProperty
//	public String getProject();	

	@JsonProperty
	public List<SearchUserRecord> getRecords();
 
}
