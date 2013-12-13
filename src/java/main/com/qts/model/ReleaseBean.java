package com.qts.model;

/**
 * 
 * @author Shiva
 *
 */

/*
 * Bean Class for Converting Input JSON into Java Object
 */

public class ReleaseBean {

	private long releaseId;
	private String releaseName;
	private long projectId;

	public ReleaseBean() {

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

	public long getReleaseId() {
		return releaseId;
	}

	public void setReleaseId(long releaseId) {
		this.releaseId = releaseId;
	}


}
