package com.qts.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="user_project_role")
public class UserProjectRoles extends AbstractObject {
	@Id
	@GeneratedValue
	private long id;
	@OneToOne
	@PrimaryKeyJoinColumn
	private UserProject userProject;
	@ManyToOne()
	@JoinColumn(name="role_id")
	private Roles roles;
	
	
	public UserProjectRoles() {
		super();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public UserProject getUserProject() {
		return userProject;
	}
	public void setUserProject(UserProject userProject) {
		this.userProject = userProject;
	}
	public Roles getRoles() {
		return roles;
	}
	public void setRoles(Roles roles) {
		this.roles = roles;
	}
	public int getObjectType() {
	return 0;
}

}
