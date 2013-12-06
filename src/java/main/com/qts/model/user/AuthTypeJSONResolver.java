package com.qts.model.user;


import java.util.HashMap;

import org.codehaus.jackson.map.jsontype.impl.TypeNameIdResolver;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;

import com.qts.model.User;
import com.qts.model.UserExt;

public class AuthTypeJSONResolver extends TypeNameIdResolver {

	public AuthTypeJSONResolver() {
        super(getBaseType(), getTypeToId(), getIdToType());
	}
	
	public static JavaType getBaseType() {
		JavaType type = TypeFactory.type(AuthenticationInput.class);
		return type;
	}
	
	public static HashMap<String, String> getTypeToId() {
		HashMap<String, String> typeToId = new HashMap<String, String>();
		typeToId.put("com.qts.model.user.DatabaseAuthenticationInput", UserExt.AUTH_TYPE_REGULAR);
		return typeToId;
	}

	public static HashMap<String, JavaType> getIdToType() {
		HashMap<String, JavaType> idToType = new HashMap<String, JavaType>();
		idToType.put(UserExt.AUTH_TYPE_REGULAR, TypeFactory.type(DefaultAuthenticationInput.class));
		return idToType;
	}


}
