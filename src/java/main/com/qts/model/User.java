package com.qts.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Table;

/**
 * @author Anil Ram
 *
 */
@Entity
@Table(name = "USER")
public class User extends AbstractObject{
 
 @Id
 @GeneratedValue
 @Column(name = "ID")
 private long id;
 @Column(name = "EMAIL")
 private String email;
 @Column(name = "PASSWORD")
 private String password; 
 @Column(name = "EMPLOYEE_ID")
 private String employeeId;
 
 
 //private long photoFileId; 
 @Column(name = "FIRST_NAME")
 private String firstName;
 @Column(name = "LAST_NAME")
 private String lastName;
 @Column(name = "NICKNAME")
 private String nickName;
 @Column(name = "LOCATION")
 private String location;
 
 @Column(name = "GENDER" , columnDefinition= "bit")
 private boolean gender;
 
 @Column(name = "DESIGNATION")
 private String designation;
 
 @Column(name = "CTS", columnDefinition= "BIGINT")
 private long cts;
 @Column(name = "MTS",columnDefinition= "BIGINT")
 private long mts;
 @Column(name = "CREATED_BY")
 private String createdBy; 
 @Column(name = "MODIFIED_BY")
 private String modifiedBy;
 @Column(name = "IS_DELETED" , columnDefinition= "bit")
 private Boolean isDeleted;
 @Column(name = "USER_ID")
 private String userId; 

 @Column(name = "PHOTO_FILE_ID",columnDefinition= "BIGINT")
 private long photoFileId;
 public User() {
  
 }
 /**
  * 
  * @param email
  * @param password
  * @param employeeId
  * @param photoFileId
  * @param firstName
  * @param lastName
  * @param nickName
  * @param location
  * @param gender
  * @param designation
  * @param cts
  * @param mts
  * @param createdBy
  * @param modifiedBy
  * @param isDelete
  * @param userId
  * @param photoFileId
  * 
  */
 
 public static final String AUTH_TYPE_REGULAR = "REGULAR";
 public static final int AUTH_STATUS_EXISTING = 0;
 public static final int AUTH_STATUS_NEW = 1; 
 public static final int AUTH_STATUS_NONE = 2;
 
 public User(String email, String password, String employeeId,
   String firstName, String lastName,
   String nickName, String location, boolean gender, String designation,
   long cts, long mts, String createdBy, String modifiedBy,
   Boolean isDeleted, String userId,long photoFileId) {
  this.email = email;
  this.password = password;
  this.employeeId = employeeId;
  this.firstName = firstName;
  this.lastName = lastName;
  this.nickName = nickName;
  this.location = location;
  this.gender = gender;
  this.designation = designation;
  this.cts = cts;
  this.mts = mts;
  this.createdBy = createdBy;
  this.modifiedBy = modifiedBy;
  this.isDeleted = isDeleted;
  this.userId = userId;
  this.photoFileId = photoFileId;
  
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
  * @return the email
  */
 public String getEmail() {
  return email;
 }

 /**
  * @param email the email to set
  */
 public void setEmail(String email) {
  this.email = email;
 }

 /**
  * @return the password
  */
 public String getPassword() {
  return password;
 }

 /**
  * @param password the password to set
  */
 public void setPassword(String password) {
  this.password = password;
 }

 /**
  * @return the employeeId
  */
 public String getEmployeeId() {
  return employeeId;
 }

 /**
  * @param employeeId the employeeId to set
  */
 public void setEmployeeId(String employeeId) {
  this.employeeId = employeeId;
 }

 /**
  * @return the photoFileId
  */
 public long getPhotoFileId() {
  return photoFileId;
 }

 /**
  * @param photoFileId the photoFileId to set
  */
 public void setPhotoFileId(long photoFileId) { 
  this.photoFileId = photoFileId;
 }

 /**
  * @return the firstName
  */
 public String getFirstName() {
  return firstName;
 }

 /**
  * @param firstName the firstName to set
  */
 public void setFirstName(String firstName) {
  this.firstName = firstName;
 }

 /**
  * @return the lastName
  */
 public String getLastName() {
  return lastName;
 }

 /**
  * @param lastName the lastName to set
  */
 public void setLastName(String lastName) {
  this.lastName = lastName;
 }

 /**
  * @return the nickName
  */
 public String getNickName() {
  return nickName;
 }

 /**
  * @param nickName the nickName to set
  */
 public void setNickName(String nickName) {
  this.nickName = nickName;
 }

 /**
  * @return the location
  */
 public String getLocation() {
  return location;
 }

 /**
  * @param location the location to set
  */
 public void setLocation(String location) {
  this.location = location;
 }

 /**
  * @return the gender
  */
 public boolean getGender() {
  return gender;
 }

 /**
  * @param gender the gender to set
  */
 public void setGender(boolean gender) {
  this.gender = gender;
 }

 /**
  * @return the designation
  */
 public String getDesignation() {
  return designation;
 }

 /**
  * @param designation the designation to set
  */
 public void setDesignation(String designation) {
  this.designation = designation;
 }

 /**
  * @return the cts
  */
 public long getCts() {
  return cts;
 }

 /**
  * @param cts the cts to set
  */
 public void setCts(long cts) {
  this.cts = cts;
 }

 /**
  * @return the mts
  */
 public long getMts() {
  return mts;
 }

 /**
  * @param mts the mts to set
  */
 public void setMts(long mts) {
  this.mts = mts;
 }

 /**
  * @return the createdBy
  */
 public String getCreatedBy() {
  return createdBy;
 }

 /**
  * @param createdBy the createdBy to set
  */
 public void setCreatedBy(String createdBy) {
  this.createdBy = createdBy;
 }

 /**
  * @return the modifiedBy
  */
 public String getModifiedBy() {
  return modifiedBy;
 }

 /**
  * @param modifiedBy the modifiedBy to set
  */
 public void setModifiedBy(String modifiedBy) {
  this.modifiedBy = modifiedBy;
 }

 /**
  * @return the isDelete
  */
 public Boolean getIsDeleted() {
  return isDeleted;
 }

 /**
  * @param isDelete the isDelete to set
  */
 public void setIsDeleted(Boolean isDeleted) {
  this.isDeleted = isDeleted;
 }

 /**
  * @return the userId
  */
 public String getUserId() {
  return userId;
 }

 /**
  * @param userId the userId to set
  */
 public void setUserId(String userId) {
  this.userId = userId;
 }

// /**
//  * @return the photoFile
//  */
// public File getPhotoFile() {
//  return photoFile;
// }
//
//	/**
//	 * @param photoFile the photoFile to set
//	 */
//	public void setPhotoFile(File photoFile) {
//		this.photoFile = photoFile;
//	}
//	
	@Override
	public int getObjectType() {
		
		return ObjectTypes.USER;
	}

}

