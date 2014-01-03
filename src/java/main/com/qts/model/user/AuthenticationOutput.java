package com.qts.model.user;

import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonProperty;

import com.qts.model.User;
import com.qts.service.annotations.SerializationDescriptor;

/**
 * @author RAMMOHAN
 * 
 */
public class AuthenticationOutput {

 private String sessionToken;
 private int authStatus;
 private User user;
 ///////////////////////////////
 protected Set<String> roleNames=new HashSet<>();

 public Set<String> getRoleNames() {
		return roleNames;
	}
 
 /////////////////////////////
 public AuthenticationOutput(String sessionToken, int authStatus, User user, Set<String> roleNames) {
  this.sessionToken = sessionToken;
  this.authStatus = authStatus;
  this.user = user;
  this.roleNames=roleNames;
 }

 @JsonProperty
 public String getSessionToken() {
  return sessionToken;
 }

 @JsonProperty
 public int getAuthStatus() {
  return authStatus;
 }
 
 @SerializationDescriptor(value = User.class)
    @JsonProperty
 public User getUser() {
  return user;
 }
}