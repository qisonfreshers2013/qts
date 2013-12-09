package com.qts.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TIME_ENTRIES")
public class TimeEntries extends AbstractObject {
    @Id
    @Column(name="ID")
    @GeneratedValue
    private long id;
    @Column(name="USER_ID")
	private long userId;
    @Column(name="DATE")
    private long date;
    @Column(name="HOURS")
    private int hours;
    @Column(name="project_id")
    private long projectId;
    @Column(name="release_id")
    private long releaseId;
    @Column(name="activity_id")
    private long activityId;
    @Column(name="TASK")
    private String task;
    @Column(name="REMARKS")
    private String remarks;
    @Column(name="CTS")
    private long cts;
    @Column(name="MTS")
    private long mts;
    @Column(name="CREATED_BY")
    private long created_by;
    @Column(name="MODIFIED_BY")
	private long modified_by;
    @Column(name="STATUS")
	private int status;

   
    public TimeEntries()
    {
    	super();
    }
   public TimeEntries(long userId,long date,int hours,long projectId,long activityId,long releaseId,String task,String remarks,long cts,long mts,
	long createdBy,long modifiedBy)
   {
	   this.userId=userId;
	   this.date=date;
	   this.hours=hours;
	   this.projectId=projectId;
	   this.activityId=activityId;
	   this.releaseId=releaseId;
	   this.task=task;
	   this.remarks=remarks;
	   this.cts=cts;
	   this.mts=mts;
	   this.created_by=createdBy;
	   this.modified_by=modifiedBy;
   }
 
public long getReleaseId() {
	return releaseId;
}
public void setReleaseId(long releaseId) {
	this.releaseId = releaseId;
}
public long getActivityId() {
	return activityId;
}
public void setActivityId(long activityId) {
	this.activityId = activityId;
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

	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}

	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public long getCreated_by() {
		return created_by;
	}
	public void setCreated_by(long created_by) {
		this.created_by = created_by;
	}
	public long getModified_by() {
		return modified_by;
	}
	public void setModified_by(long modified_by) {
		this.modified_by = modified_by;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public int getObjectType() {
		return ObjectTypes.TIME_ENTRIES;
	}
}


