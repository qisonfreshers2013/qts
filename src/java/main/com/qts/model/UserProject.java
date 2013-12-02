package com.qts.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name="USER_PROJECT")
public class UserProject {
	@Id
	@GeneratedValue
	@Column(name="ID")
	private long id;
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="USER_ID")
	private User1 user1;
	@ManyToOne
	@JoinColumn(name="PROJECT_ID")
	private Project project;
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="REPORTING_USER_ID")
	private User1 reportingUserId;
	public UserProject()
	{
		
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User1 getUser1() {
		return user1;
	}

	public void setUser1(User1 user1) {
		this.user1 = user1;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public long getReportingUserId() {
		return reportingUserId;
	}

	public void setReportingUserId(long reportingUserId) {
		this.reportingUserId = reportingUserId;
	}

}
