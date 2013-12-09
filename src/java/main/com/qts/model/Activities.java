package com.qts.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ACTIVITIES")
public class Activities extends AbstractObject {

 @Id
 @GeneratedValue
 @Column(name = "ID")
 private long id;
 @Column(name = "NAME")
 private String name;
 @Column(name = "DESCRIPTION")
 private String description;

 public Activities() {

 }

 public Activities(String name, String description) {
  super();
  this.name = name;
  this.description = description;
 }

 // @OneToMany(mappedBy="activities",fetch=FetchType.EAGER)
 // private Set<TimeEntries> timeentries=new HashSet<TimeEntries>();
 // public Set<TimeEntries> getTimeentries() {
 // return timeentries;
 // }
 // public void setTimeentries(Set<TimeEntries> timeentries) {
 // this.timeentries = timeentries;
 // }

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

 @Override
 public int getObjectType() {
  // TODO Auto-generated method stub
  return ObjectTypes.ACTIVITIES;
 }

}