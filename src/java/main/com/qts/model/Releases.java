package com.qts.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


/*
 * author @ N V Siva Reddy
 * 
 */

@Entity
@Table(name="releases",uniqueConstraints={@UniqueConstraint(columnNames={"name","project_id"})})
public class Releases extends AbstractObject{
	@Id
	@GeneratedValue
	@Column(name="id")
	private long id;
	@Column(name="name")
	private String name;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "project_id")
	private Project project;
	@OneToMany(mappedBy="releases")
	private Set<TimeEntries> timeEntries=new HashSet<TimeEntries>();
	public Releases() {
		super();
	}
	public Releases(String name) {
		super();
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
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public Set<TimeEntries> getTimeEntries() {
		return timeEntries;
	}
	public void setTimeEntries(Set<TimeEntries> timeEntries) {
		this.timeEntries = timeEntries;
	}
	@Override
	public int getObjectType() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}

