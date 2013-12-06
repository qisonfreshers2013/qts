package com.qts.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * @author 
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
	
	@Column(name = "photo_file_id")
	private long photoFileId;	
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
	
	@Column(name = "CTS")
	private long cts;
	@Column(name = "MTS")
	private long mts;
	@Column(name = "CREATED_BY")
	private String createdBy;	
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;
	@Column(name = "IS_DELETED" , columnDefinition= "bit")
	private Boolean isDeleted;

	@Column(name = "USER_ID")
	private String userId;
	
	
	
	public User() {
		
	}
	/**
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
	 */
	public User(String email, String password, String employeeId,
			String firstName, String lastName,
			String nickName, String location, boolean gender, String designation,
			long cts, long mts, String createdBy, String modifiedBy,
			Boolean isDeleted, String userId) {
		super();
		this.email = email;
		this.password = password;
		this.employeeId = employeeId;
		//this.photoFileId = photoFileId;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNickName() {
		return nickName;
	}

	
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	
	public boolean getGender() {
		return gender;
	}

	
	public void setGender(boolean gender) {
		this.gender = gender;
	}

	
	public String getDesignation() {
		return designation;
	}

	
	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public long getCts() {
		return cts;
	}

	
	public void setCts(long cts) {
		this.cts = cts;
	}

	
	public long getMts() {
		return mts;
	}

	
	public void setMts(long mts) {
		this.mts = mts;
	}

	
	public String getCreatedBy() {
		return createdBy;
	}

	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	
	public String getUserId() {
		return userId;
	}

	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public int getObjectType() {
		// TODO Auto-generated method stub
		return 0;
	}

}
