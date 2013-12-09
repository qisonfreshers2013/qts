package com.qts.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ProjectBean {
	
	private long id;
	private String name;
	private String technologies;
	private List<Long> userIds=new LinkedList();
	private long userId;
	private long reportingUserId;
	private List<User> users; 
	
	
	public ProjectBean() {
		super();
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public String getTechnologies() {
		return technologies;
	}


	public void setTechnologies(String technologies) {
		this.technologies = technologies;
	}

	public List<Long> getUserIds() {
		return userIds;
	}


	public void setUserIds(List<Long> ids) {
		this.userIds = ids;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getReportingUserId() {
		return reportingUserId;
	}

	public void setReportingUserId(long reportingUserId) {
		this.reportingUserId = reportingUserId;
	}
	
	
	
	

}
