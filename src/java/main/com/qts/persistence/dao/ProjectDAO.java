package com.qts.persistence.dao;

import java.util.List;

import com.qts.exception.ProjectException;
import com.qts.model.Project;

public interface ProjectDAO extends BaseDAO{
	
	public List<Project> getProjects() throws ProjectException;
	
}
