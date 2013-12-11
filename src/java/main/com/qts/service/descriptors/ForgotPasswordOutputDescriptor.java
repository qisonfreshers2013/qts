/**
 * 
 */
package com.qts.service.descriptors;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author AnilRam
 *
 */
public interface ForgotPasswordOutputDescriptor {
	@JsonProperty
	public boolean booleanValue();

}
