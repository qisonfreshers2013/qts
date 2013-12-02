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
public class User1 extends AbstractObject{
	
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
	
	@OneToOne
	//@JoinColumn(name = "PHOTO_FILE_ID")
	@PrimaryKeyJoinColumn
	private File photoFile;
	
	@ManyToMany
	@JoinTable(name = "USER_PROJECT",joinColumns  = {@JoinColumn(name = "USER_ID")},inverseJoinColumns = {@JoinColumn(name = "PROJECT_ID")})
	private Set<Project> project = new HashSet<Project>();
	@OneToMany(MappedBy = "user1" )
	private Set<TimeEntries> timeEntries = new HashSet<TimeEntries>();
	@ManyToMany(MappedBy = "user1")
	private Set<Roles> roles = new HashSet<Roles>();
	@OneToMany(mappedBy = "user1")
	private Set<UserProject> userProject = new HashSet<UserProject>();
	//@ManyToMany()
	//private Set<Roles> roles = new HashSet<Roles>();
	
	
	
	public User1() {
		
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
	public User1(String email, String password, String employeeId,
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

//	/**
//	 * @return the photoFileId
//	 */
////	public long getPhotoFileId() {
////		return photoFileId;
////	}
//
//	/**
//	 * @param photoFileId the photoFileId to set
////	 */
////	public void setPhotoFileId(long photoFileId) {
//////		if(photoFileId = null)
//////		{
//////			this.photoFileId = 1;
//////		}
////		this.photoFileId = photoFileId;
////	}

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

	/**
	 * @return the photoFile
	 */
	public File getPhotoFile() {
		return photoFile;
	}

	/**
	 * @param photoFile the photoFile to set
	 */
	public void setPhotoFile(File photoFile) {
		this.photoFile = photoFile;
	}
	/**
	 * @return the project
	 */
	public Set<Project> getProject() {
		return project;
	}
	/**
	 * @param project the project to set
	 */
	public void setProject(Set<Project> project) {
		this.project = project;
	}
	@Override
	public int getObjectType() {
		
		return 0;//what to return
	}
	/**
	 * @return the timeEntries
	 */
	public Set<TimeEntries> getTimeEntries() {
		return timeEntries;
	}
	/**
	 * @param timeEntries the timeEntries to set
	 */
	public void setTimeEntries(Set<TimeEntries> timeEntries) {
		this.timeEntries = timeEntries;
	}
	/**
	 * @return the roles
	 */
	public Set<Roles> getRoles() {
		return roles;
	}
	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
	}
	/**
	 * @return the userProject
	 */
	public Set<UserProject> getUserProject() {
		return userProject;
	}
	/**
	 * @param userProject the userProject to set
	 */
	public void setUserProject(Set<UserProject> userProject) {
		this.userProject = userProject;
	}
	
	
	
	

}
