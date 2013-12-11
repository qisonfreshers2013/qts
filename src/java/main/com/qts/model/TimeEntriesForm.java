package com.qts.model;
/**
 * Pojo Class Used To Create Object for Supplied JSON
 * @author Ajay
 */
public class TimeEntriesForm {
    
	private Integer id;
    private Long userId;
	private String date;
	private Long projectId;
	private Long releaseId;
	private String task;
	private Long activityId;
	private Integer hours;
	private String from;
    private String to;
	private String userRemarks;
	private Integer status;
    private String approvedComments;
    private String rejectedComments;
	public TimeEntriesForm() {
	}

	public TimeEntriesForm(long userId, String date, long projectID,
			long releaseId, String task, long activityId, int hours,
			String remarks,String from,String to) {
		this.date = date;
		this.projectId = projectID;
		this.releaseId = releaseId;
		this.task = task;
		this.activityId = activityId;
		this.hours = hours;
		this.userRemarks = remarks;
		this.from=from;
		this.to=to;
	}
	public Integer getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public Long getReleaseId() {
		return releaseId;
	}

	public void setReleaseId(long releaseId) {
		this.releaseId = releaseId;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Integer getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}


	public String getUserRemarks() {
		return userRemarks;
	}

	public void setUserRemarks(String userRemarks) {
		this.userRemarks = userRemarks;
	}

	public String getApprovedComments() {
		return approvedComments;
	}

	public void setApprovedComments(String approvedComments) {
		this.approvedComments = approvedComments;
	}

	public String getRejectedComments() {
		return rejectedComments;
	}

	public void setRejectedComments(String rejectedComments) {
		this.rejectedComments = rejectedComments;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
	public Integer getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public String getFrom() {
		return from;
	}


}
