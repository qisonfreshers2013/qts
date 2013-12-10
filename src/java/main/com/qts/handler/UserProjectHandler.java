package com.qts.handler;
/*
 * handling userProject table operations
 * author mani kumar
 * 
 */
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
	
	public  Set<Long> reportingIds=new HashSet<Long>();
	public List<UserProject> getListOfUserProjectByUserId(long id) throws Exception{
		return DAOFactory.getInstance().getUserProjectDAOImplInstance().getListOfUserProjectByUserId(id);
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
	
	
//	public RoleBean updateReportingUserId(RoleBean roleBean) throws ProjectException,Exception{
//		return DAOFactory.getInstance().getUserProjectDAOImplInstance().updateReportingUserId(roleBean);
//	}
	
	
	public BaseObject getObjectById(long id) throws ObjectNotFoundException {
		return DAOFactory.getInstance().getUserProjectDAOImplInstance().getObjectById(id);
	}



	public boolean deAllocateUsersFromProject(long projectId,Long userId) throws Exception {
		 return DAOFactory.getInstance().getUserProjectDAOImplInstance().deAllocateUsersFromProject(projectId,userId);
	}



	public List<UserProject> getUserProjectListByIds(long projectId, List<Long> userIdsList) throws ProjectException {
		return DAOFactory.getInstance().getUserProjectDAOImplInstance().getUserProjectListByIds(projectId, userIdsList);
	}



	public List<UserProject> getListOfNonUserProjectByProjectId(long id) throws Exception {
		return DAOFactory.getInstance().getUserProjectDAOImplInstance().getListOfNonUserProjectByProjectId(id);
	}
	
//	public boolean updateUserProjectReportingId(UserProject userProject) throws Exception {
//		return DAOFactory.getInstance().getUserProjectDAOImplInstance().updateUserProjectReportingId(userProject);
//		
//	}
	
//	public Set<Long> getApproversListByProjectId(long projectId) throws Exception{
//		List<UserProject> userProjects=UserProjectHandler.getInstance().getListOfUserProjectByProjectId(projectId);
//		Iterator<UserProject> iterator=userProjects.iterator();
//		while(iterator.hasNext()){
//			reportingIds.add(iterator.next().getReportingUserId());
//		}
//		return reportingIds;
//	}
}
