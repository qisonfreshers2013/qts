package com.qts.model;

/**
 * 
 * @author Shiva
 *
 */

/*
 * Bean Class for Converting Input JSON into Java Object
 */

public class ReleasesInput {
	private String releaseName;
	private long projectId;

	public ReleasesInput(String releaseName, long projectId) {
		super();
		this.releaseName = releaseName;
		this.projectId = projectId;
	}
	
	public ReleasesInput() {
		super();
	}
	
	public String getReleaseName() {
		return releaseName;
	}
	
	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}
	
	public long getProjectId() {
		return projectId;
	}
	
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	

}
