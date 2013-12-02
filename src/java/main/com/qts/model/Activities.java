package com.qts.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="ACTIVITIES")
public class Activities {

	@Id
	@GeneratedValue
	@Column(name="ID")
	private long id;
	@Column(name="NAME")
	private String name;
	@Column(name="DESCRIPTION")
	private String description;
	@OneToMany(mappedBy="activities")
	private Set<TimeEntries> timeentries=new HashSet<TimeEntries>();
	public Set<TimeEntries> getTimeentries() {
		return timeentries;
	}
	public void setTimeentries(Set<TimeEntries> timeentries) {
		this.timeentries = timeentries;
	}
	public Activities() {
		
	}
	public Activities(String name, String description) {
		super();
		this.name = name;
		this.description = description;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
