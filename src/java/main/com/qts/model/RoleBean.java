package com.qts.model;

import java.util.Set;

public class RoleBean {

	private long projectId;
	private long userId;
	private Set<Long> roleIds;
	public RoleBean() {
	}
	public long getProjectId() {
		return projectId;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public Set<Long> getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(Set<Long> roleIds) {
		this.roleIds = roleIds;
	}
	
	
}
