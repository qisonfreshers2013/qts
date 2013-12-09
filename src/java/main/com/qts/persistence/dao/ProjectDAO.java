package com.qts.persistence.dao;

import java.util.List;

import com.qts.model.Project;

public interface ProjectDAO extends BaseDAO{
	public List<Project> getProjectList() throws Exception;
	public Project addProject(Project project) throws Exception;
}
