package com.qts.model;

import java.util.Set;

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
	@Column(name="reporting_user_id")
	private long reportingUserId;



	/**
	 * 
	 */
	public UserProject() {
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
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}



	/**
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}



	/**
	 * @return the projectId
	 */
	public long getProjectId() {
		return projectId;
	}



	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}



	/**
	 * @return the reportingUserId
	 */
	public long getReportingUserId() {
		return reportingUserId;
	}



	/**
	 * @param reportingUserId the reportingUserId to set
	 */
	public void setReportingUserId(long reportingUserId) {
		this.reportingUserId = reportingUserId;
	}



	@Override
	public int getObjectType() {
		// TODO Auto-generated method stub
		return 0;
	}
}

