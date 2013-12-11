package com.qts.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 * @author Shiva
 * 
 */

/*
 * POJO Class that is mapped to RELEASES Table in MySQL DataBase
 */

@Entity
@Table(name = "releases", uniqueConstraints = { @UniqueConstraint(columnNames = {"name", "project_id" }) })
public class Releases extends AbstractObject {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	@Column(name = "name")
	private String name;
	@Column(name = "project_id")
	private long projectId;

	/**
	 * No Args Constructor
	 */
	public Releases() {
		super();
	}

	/**
	 * 
	 * @param name
	 */
	public Releases(String name) {
		super();
		this.name = name;
	}

	// getter Methods
	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public long getProjectId() {
		return projectId;
	} 


	// setter Methods
	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	} 

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	@Override
	public int getObjectType() {
		return ObjectTypes.RELEASES;
	}

}