package com.qts.persistence.dao;

import java.util.List;

import com.qts.exception.ObjectNotFoundException;
import com.qts.model.BaseObject;
import com.qts.model.Project;
import com.qts.model.ProjectBean;
import com.qts.model.User;

public interface ProjectDAO extends BaseDAO{
	
	
	public List<Project> getProjectList() throws Exception;
	public Project addProject(Project project) throws Exception;

}
