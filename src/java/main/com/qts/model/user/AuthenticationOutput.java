package com.qts.model.user;

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

 public AuthenticationOutput(String sessionToken, int authStatus, User user) {
  this.sessionToken = sessionToken;
  this.authStatus = authStatus;
  this.user = user;
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