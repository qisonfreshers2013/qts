package com.qts.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="user_projects_roles")
public class UserProjectRoles extends AbstractObject {
	@Id
	@GeneratedValue
	@Column(name="Id")
	private long id;
	@Column(name="user_project_id")
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
	
	/**
	 * @return the userProjectId
	 */
	public long getUserProjectId() {
		return userProjectId;
	}
	/**
	 * @param userProjectId the userProjectId to set
	 */
	public void setUserProjectId(long userProjectId) {
		this.userProjectId = userProjectId;
	}
	/**
	 * @return the roleId
	 */
	public long getRoleId() {
		return roleId;
	}
	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public int getObjectType() {
	return 0;
}

}
