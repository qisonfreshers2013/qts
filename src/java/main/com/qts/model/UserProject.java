package com.qts.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="USER_PROJECT")
public class UserProject extends AbstractObject {
 @Id
 @GeneratedValue
 @Column(name="ID")
 private long id;
 @Column(name="user_id")
 private long userId;
 @Column(name="project_id")
 private long projectId;
 
 public UserProject(){
  
 }
 
 
 public long getUserId() {
  return userId;
 }
 public void setUserId(long userId) {
  this.userId = userId;
 }
 public long getProjectId() {
  return projectId;
 }
 public void setProjectId(long projectId) {
  this.projectId = projectId;
 }
 public long getId() {
	return id;
}


public void setId(long id) {
	this.id = id;
}

@Override
 public int getObjectType() {
  // TODO Auto-generated method stub
  return ObjectTypes.USER_PROJECT;
 }
}