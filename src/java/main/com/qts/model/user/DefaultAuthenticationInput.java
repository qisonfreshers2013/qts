package com.qts.model.user;

import com.qts.model.User;

//import com.qts.model.UserExt;

public class DefaultAuthenticationInput implements AuthenticationInput {

  private String email;
  private String password;
 
  public String getEmail() {
   return email;
  }
 public void setEmail(String email) {
  this.email = email;
 }

 public String getPassword() {
  return password;
 }

	public void setPassword(String password) {
		this.password = password;
	}
	public String getAuthType() {
		
		return User.AUTH_TYPE_REGULAR;
	}
	



}
