package com.qts.persistence.dao;

import java.util.List;

import com.qts.exception.ProjectException;
import com.qts.model.RoleBean;
import com.qts.model.UserProject;

public interface UserProjectDAO extends BaseDAO {
	
	public List<UserProject> getUserProjectsByUserId(long id) throws ProjectException;
	
	public List<UserProject> getUserProjectsByProjectId(long id);
	
	public void addUserToProject(List<UserProject> userProject) throws ProjectException;
	
	public UserProject getUserProjectByIds(long projectId,long userId) throws ProjectException;
	
	public boolean deAllocateUsersFromProject(UserProject userProject);
	
	public List<UserProject> getUserProjectsByIds(long projectId,List<Long> userIdsList);
	
	
}
