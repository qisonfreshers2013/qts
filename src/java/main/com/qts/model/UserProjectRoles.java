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
	@Column(name="User_project_id")
	private long userProjectId;
	@Column(name="Role_id")
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
	public int getObjectType() {
	return ObjectTypes.USER_PROJECT_ROLES;
}

}
