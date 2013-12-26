package com.qts.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ProjectBean {
	
	private long projectId;
	private String name;
	private String technologies;
	private Set<Long> userIds=new HashSet();
	private long userId;
	private List<User> users =new LinkedList(); 
	
	public ProjectBean() {
		super();
	}


	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
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

	public Set<Long> getUserIds() {
		return userIds;
	}


	public void setUserIds(Set<Long> ids) {
		this.userIds = ids;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public List<User> getUsers() {
		return users;
	}


	public void setUsers(List<User> users) {
		this.users = users;
	}


	
	
	
	

}
