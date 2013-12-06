package com.qts.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_projects_roles")
public class UserProjectRoles extends AbstractObject {
	@Id
	@GeneratedValue
	private long id;
	@Column(name="user_project_id")
	private long user_project_id;
	@Column(name="role_id")
	private long roleId;



	public UserProjectRoles() {
		super();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public long getUser_project_id() {
		return user_project_id;
	}
	public void setUser_project_id(long user_project_id) {
		this.user_project_id = user_project_id;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public int getObjectType() {
		return ObjectTypes.USER_PROJECT_ROLES;
	}

}
