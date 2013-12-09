package com.qts.handler;

import java.util.List;

import com.qts.exception.ObjectNotFoundException;
import com.qts.exception.ProjectException;
import com.qts.model.BaseObject;
import com.qts.model.RoleBean;
import com.qts.model.UserProject;
import com.qts.persistence.dao.DAOFactory;

public class UserProjectHandler {
	
	private static UserProjectHandler INSTANCE = null;

	private UserProjectHandler() {
	}



	public static UserProjectHandler getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new UserProjectHandler();
		}
		return INSTANCE;
	}
	
	
	public List<UserProject> getListOfUserProjectByUserId(long id) throws Exception{
		return DAOFactory.getInstance().getUserProjectDAOImplInstance().getListOfUserProjectByProjectId(id);
	}
	
	
	public List<UserProject> getListOfUserProjectByProjectId(long id) throws Exception{
		return DAOFactory.getInstance().getUserProjectDAOImplInstance().getListOfUserProjectByProjectId(id);
	}
	
	
	public void addUserToProject(List<UserProject> userProject) throws ProjectException,Exception{
		 DAOFactory.getInstance().getUserProjectDAOImplInstance().addUserToProject(userProject);
	}
	
	
	public UserProject getUserProjectByIds(long projectId,long userId) throws ProjectException, Exception{
		return DAOFactory.getInstance().getUserProjectDAOImplInstance().getUserProjectByIds(projectId, userId);
	}
	
	
	public RoleBean updateReportingUserId(RoleBean roleBean) throws ProjectException,Exception{
		return DAOFactory.getInstance().getUserProjectDAOImplInstance().updateReportingUserId(roleBean);
	}
	
	
	public BaseObject getObjectById(long id) throws ObjectNotFoundException {
		return DAOFactory.getInstance().getUserProjectDAOImplInstance().getObjectById(id);
	}



	public boolean deAllocateUsersFromProject(long projectId,List<Long> userIdsList) throws Exception {
		 return DAOFactory.getInstance().getUserProjectDAOImplInstance().deAllocateUsersFromProject(projectId,userIdsList);
	}
}
