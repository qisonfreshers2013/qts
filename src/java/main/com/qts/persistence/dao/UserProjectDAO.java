package com.qts.persistence.dao;

import java.util.List;

import com.qts.exception.ProjectException;
import com.qts.model.RoleBean;
import com.qts.model.UserProject;

public interface UserProjectDAO extends BaseDAO {
	
	public List<UserProject> getListOfUserProjectByUserId(long id) throws Exception;
	public List<UserProject> getListOfUserProjectByProjectId(long id) throws Exception;
	public void addUserToProject(List<UserProject> userProject) throws ProjectException,Exception;
	public UserProject getUserProjectByIds(long projectId,long userId) throws ProjectException, Exception;
	public RoleBean updateReportingUserId(RoleBean roleBean) throws ProjectException,Exception;
	public void deAllocateUsersFromProject(long projectId,
			List<Long> userIdsList) throws Exception;
}
