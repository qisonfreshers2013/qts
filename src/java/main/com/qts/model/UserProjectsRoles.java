package com.qts.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_projects_roles")
public class UserProjectsRoles extends AbstractObject 
{
	@Id
	@GeneratedValue
	@Column(name="ID")
	private long id;
	@Column(name="USER_PROJECT_ID")
	private long userProjectId;
	@Column(name="ROLE_ID")
	private long roleId;

	public UserProjectsRoles() {
		
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserProjectId() {
		return userProjectId;
	}
	public void setUserProjectId(long userProjectId) {
		this.userProjectId = userProjectId;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	@Override
	public int getObjectType() {
		return ObjectTypes.USER_PROJECTS_ROLES;
	}
}