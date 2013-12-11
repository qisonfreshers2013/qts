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

	private long id;
	private String releaseName;
	private long projectId;

//	public ReleasesInput(long id,String releaseName, long projectId) {
//		this.id=id;
//		this.releaseName = releaseName;
//		this.projectId = projectId;
//	}

	public ReleasesInput() {

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


}
