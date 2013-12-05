package com.qts.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
/**
 * 
 * @author Jagadish
 *
 */

@Entity
@Table(name="ROLES")
public class Roles extends AbstractObject
{
	@Id
	@GeneratedValue
	@Column(name="ID")
	private long id;
	@Column(name="NAME")
	private String name;
//	@ManyToMany
//	@JoinTable(name="User_Project_Roles",joinColumns={@JoinColumn(name="ROLE_ID")},inverseJoinColumns={@JoinColumn(name="USER_PROJECT_ID")})
//	private Set<User1> user1 = new HashSet();
//	@OneToMany(mappedBy="roles")
//	private Set<UserProjectRoles> userProjectRoles = new HashSet();
	public Roles() {
	}
	public Roles(String name) {
		this.name = name;
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
	
//	public Set<User1> getUser1() {
//		return user1;
//	}
//	public void setUser1(Set<User1> user1) {
//		this.user1 = user1;
//	}
//	public Set<UserProjectRoles> getUserProjectRoles() {
//		return userProjectRoles;
//	}
//	public void setUserProjectRoles(Set<UserProjectRoles> userProjectRoles) {
//		this.userProjectRoles = userProjectRoles;
//	}
	@Override
	public int getObjectType() {
		return 0;//ObjectTypes.ROLE;
	}
}
