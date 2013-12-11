package com.qts.model.user;

import com.qts.model.User;


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

<<<<<<< HEAD

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getAuthType() {
		return User.AUTH_TYPE_REGULAR;
	}
=======
 public void setPassword(String password) {
  this.password = password;
 }
 
 public String getAuthType() {
  return User.AUTH_TYPE_REGULAR;
 }
>>>>>>> aa6fb43f09ad3c0280514b8e976f1af9f568cf71

}