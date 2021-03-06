package com.qts.handler;
/*
 * handling userProject table operations
 * author mani kumar
 * 
 */

import java.util.List;
import java.util.Set;

import com.qts.exception.ObjectNotFoundException;
import com.qts.exception.ProjectException;
import com.qts.model.BaseObject;
import com.qts.model.UserProject;
import com.qts.persistence.dao.DAOFactory;

public class UserProjectHandler extends AbstractHandler{
	
	private static UserProjectHandler INSTANCE = null;

	private UserProjectHandler() {
	}



	public static UserProjectHandler getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new UserProjectHandler();
		}
		return INSTANCE;
	}
	


	public List<UserProject> getUserProjectsByUserId(long id){
		
		return	DAOFactory.getInstance().getUserProjectDAOImplInstance().getUserProjectsByUserId(id);
	}
		
	
	
	public List<UserProject> getUserProjectsByProjectId(long id) {
		
			return DAOFactory.getInstance().getUserProjectDAOImplInstance().getUserProjectsByProjectId(id);
		
	}
	
	
	public void addUsersToProject(List<UserProject> userProject) throws ProjectException{
		 DAOFactory.getInstance().getUserProjectDAOImplInstance().addUsersToProject(userProject);
	}
	
	
	public UserProject getUserProjectByIds(long projectId,long userId) throws ProjectException, Exception{
		
		return DAOFactory.getInstance().getUserProjectDAOImplInstance().getUserProjectByIds(projectId, userId);
		
	}
	
	
	
	public BaseObject getObjectById(long id) throws ObjectNotFoundException {
		return DAOFactory.getInstance().getUserProjectDAOImplInstance().getObjectById(id);
	}



	public boolean deAllocateUsersFromProject(UserProject userProject){
		 return DAOFactory.getInstance().getUserProjectDAOImplInstance().deAllocateUsersFromProject(userProject);
	}



	public List<UserProject> getUserProjectsByIds(long projectId, Set<Long> userIdsList){
		return DAOFactory.getInstance().getUserProjectDAOImplInstance().getUserProjectsByIds(projectId, userIdsList);
	}

	
}
