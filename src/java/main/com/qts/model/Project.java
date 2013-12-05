package com.qts.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@Entity
@Table(name="project")
public class Project extends AbstractObject{
	@Id
	@GeneratedValue
	@Column(name="id")
	private long id;
	@Column(name="name")
	private String name;
	@Column(name="Technologies")
	private String technologies;
	
//	@OneToMany(mappedBy= "project",fetch=FetchType.EAGER )
//	private Set<Releases> releases =new HashSet();
//	@ManyToMany(mappedBy = "project",fetch=FetchType.EAGER)  
//	private Set<User1> user1= new HashSet<User1>();
//	@OneToMany(mappedBy="project",fetch=FetchType.EAGER)
//	private Set<TimeEntries> timeEntries= new HashSet();
//	@OneToMany(mappedBy="project" ,fetch=FetchType.EAGER)
//	private Set<UserProject> userProject =new HashSet(); 
	public Project() {
		super();
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the technologies
	 */
	public String getTechnologies() {
		return technologies;
	}
	/**
	 * @param technologies the technologies to set
	 */
	public void setTechnologies(String technologies) {
		this.technologies = technologies;
	}
	/**
	 * @return the releases
	 */
//	public Set<Releases> getReleases() {
//		return releases;
//	}
//	/**
//	 * @param releases the releases to set
//	 */
//	public void setReleases(Set<Releases> releases) {
//		this.releases = releases;
//	}
//	/**
//	 * @return the user
//	 */
//	public Set<User1> getUser1() {
//		return user1;
//	}
//	/**
//	 * @param user the user to set
//	 */
//	public void setUser1(Set<User1> user1) {
//		this.user1 = user1;
//	}
//	public Set<TimeEntries> getTimeEntries() {
//		return timeEntries;
//	}
//	public void setTimeEntries(Set<TimeEntries> timeEntries) {
//		this.timeEntries = timeEntries;
//	}
	
	public int getObjectType() {		
		return ObjectTypes.PROJECT;
	}
	
}

