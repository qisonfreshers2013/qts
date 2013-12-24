package com.qts.model;

import java.util.List;

public class SearchUserRecord {
	private long id;
	private String photoFileUrl ;
	private String email;
	private String employeeId;
	private String designation;
	private List<String> projects;		
	
	public SearchUserRecord(User user) {
		this.id = user.getId();		
		this.email = user.getEmail();
		this.employeeId = user.getEmployeeId();
		this.designation = user.getDesignation();	
	
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
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
	public List<String> getProjects() {
		return projects;
	}
	public void setProjects(List<String> projects) {
		this.projects = projects;
	}

	
}
