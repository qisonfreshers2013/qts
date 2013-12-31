package com.qts.model;

import java.util.HashSet;
import java.util.Set;

public class ProjectUserRecord {

	private long id;
	private String photoFileUrl ;
	private String email;
	private String employeeId;
	private String designation;
	private String userId;
	private String firstName;
	private Set<String> roles=new HashSet<String>();
	
	public ProjectUserRecord(){
		
	}
	
	public ProjectUserRecord(User user){
		this.id = user.getId();		
		this.email = user.getEmail();
		this.employeeId = user.getEmployeeId();
		this.designation = user.getDesignation();
		this.firstName=user.getFirstName();
		this.userId=user.getUserId();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPhotoFileUrl() {
		return photoFileUrl;
	}

	public void setPhotoFileUrl(String photoFileUrl) {
		this.photoFileUrl = photoFileUrl;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	
	
	
}
