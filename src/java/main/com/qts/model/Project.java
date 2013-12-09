package com.qts.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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
 
// @OneToMany(mappedBy= "project",fetch=FetchType.EAGER )
// private Set<Releases> releases =new HashSet();
// @ManyToMany(mappedBy = "project",fetch=FetchType.EAGER)  
// private Set<User1> user1= new HashSet<User1>();
// @OneToMany(mappedBy="project",fetch=FetchType.EAGER)
// private Set<TimeEntries> timeEntries= new HashSet();
// @OneToMany(mappedBy="project" ,fetch=FetchType.EAGER)
// private Set<UserProject> userProject =new HashSet(); 
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
 
 public int getObjectType() {  
  return ObjectTypes.PROJECT;
 }
 
}