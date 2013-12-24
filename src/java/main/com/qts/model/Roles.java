package com.qts.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
	
	public static final long ROLE_ADMIN = 1;
	
	@Column(name="NAME")
	private String name;
	
	public Roles() {
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
	
	@Override
	public int getObjectType() {
		return ObjectTypes.ROLES;
	}
}
