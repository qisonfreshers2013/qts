package com.qts.persistence.dao;

import java.util.List;
import java.util.Set;

import com.qts.exception.ProjectException;
import com.qts.model.UserProject;

public interface UserProjectDAO extends BaseDAO {
	
	public List<UserProject> getUserProjectsByUserId(long id);
	
	public List<UserProject> getUserProjectsByProjectId(long id);
	
	public void addUsersToProject(List<UserProject> userProject) throws ProjectException;
	
	public UserProject getUserProjectByIds(long projectId,long userId) throws ProjectException;
	
	public boolean deAllocateUsersFromProject(UserProject userProject);
	
	public List<UserProject> getUserProjectsByIds(long projectId,Set<Long> userIdsList);
	
	
}
