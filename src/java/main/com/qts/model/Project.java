package com.qts.model;
/*
 *author mani kumar 
 */
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
 
 public Project() {
  super();
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

 public String getTechnologies() {
  return technologies;
 }

 public void setTechnologies(String technologies) {
  this.technologies = technologies;
 }
 
 public int getObjectType() {  
  return ObjectTypes.PROJECT;
 }
 
}