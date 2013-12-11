/**
 * 
 */
package com.qts.model;

/**
 * @author AnilRam
 *
 */
public class ChangePasswordBean {
	String oldPassword;
	String password;
	String confirmPassword;
	public ChangePasswordBean() {
		
	}
	/**
	 * @param oldPassword
	 * @param password
	 * @param confirmPassword
	 */
	public ChangePasswordBean(String oldPassword, String password,
			String confirmPassword) {
		
		this.oldPassword = oldPassword;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}
	/**
	 * @return the oldPassword
	 */
	public String getOldPassword() {
		return oldPassword;
	}
	/**
	 * @param oldPassword the oldPassword to set
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}
	/**
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	

}
