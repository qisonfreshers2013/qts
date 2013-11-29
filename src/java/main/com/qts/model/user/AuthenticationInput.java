package com.qts.model.user;

/**
 * @author RAMMOHAN
 *
 */
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.As;
import org.codehaus.jackson.map.annotate.JsonTypeIdResolver;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=As.PROPERTY, property="authType")
@JsonTypeIdResolver(AuthTypeJSONResolver.class)
public interface AuthenticationInput {
	
	public String getAuthType();

}
