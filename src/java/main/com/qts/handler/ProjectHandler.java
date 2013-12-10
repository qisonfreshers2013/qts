package com.qts.handler;

/*
 * All WebServices Requests Are handled By Project Handler By Calling DAOImpl Methods
 * Author Mani kumar
 *
 */
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.ProjectException;
import com.qts.exception.UserException;
import com.qts.model.Project;
import com.qts.model.ProjectBean;
import com.qts.model.User;
import com.qts.model.UserProject;
import com.qts.persistence.dao.DAOFactory;

public class ProjectHandler extends AbstractHandler {

	private static ProjectHandler INSTANCE = null;

	private ProjectHandler() {
	}



	public static ProjectHandler getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ProjectHandler();
		}
		return INSTANCE;
	}



	/*
	 * Fetching List of Projects
	 */
	public  List<Project> getProjectList(ProjectBean projectBean) throws Exception{
		long userId;
			if(projectBean==null){
				return DAOFactory.getInstance().getProjectDAOImplInstance().getProjectList();
			}
			try{
			userId=projectBean.getUserId();
			}catch(NullPointerException e){
				e.printStackTrace();
				throw  new UserException(ExceptionCodes.USER_ID_NOT_NULL,ExceptionMessages.USER_ID_NOT_NULL);
			}
			try{
			if(UserHandler.getInstance().isUserDeleted(userId)){
				throw new UserException(ExceptionCodes.DELETED_ALREADY,ExceptionMessages.DELETED_ALREADY);
			}
			List<UserProject> list= UserProjectHandler.getInstance().getListOfUserProjectByUserId(userId);
			List<Project> projectList=new LinkedList<Project>();
			Iterator<UserProject> itr=list.iterator();
			while(itr.hasNext()){
				Project project =(Project)DAOFactory.getInstance().getProjectDAOImplInstance().getObjectById(itr.next().getProjectId());
				projectList.add(project);
			}
			return projectList;
		}
		catch(UserException e){
			e.printStackTrace();
			throw e;
		}
		catch(ProjectException e){
			e.printStackTrace();
			throw e;
		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}


	/*
	 * Adding New Project
	 */
	public  Project addProject(Project project) throws Exception{
		try{
			if(validateProjectName(project.getName())&&validateProjectTechnologies(project.getTechnologies()))
				return DAOFactory.getInstance().getProjectDAOImplInstance().addProject(project);
			else 
				return null;
		}catch(Exception e){
			throw e;
		}
	}


	/*
	 * Fetching All Members Of  Project
	 */
	public  List<User> listOfProjectUsers(Project project) throws Exception{
		try{
			
			List<UserProject> userProject=UserProjectHandler.getInstance().getListOfUserProjectByProjectId(project.getId());
			List<User> userList=new LinkedList<User>();
			if(userProject.isEmpty())
				return userList;
			Iterator<UserProject> iterator=userProject.iterator();
			while(iterator.hasNext()){
				User user = UserHandler.getInstance().getUserByUserId(iterator.next().getUserId());
				userList.add(user);
			}
			return userList;
			
		}catch(UserException e){
			e.printStackTrace();
			throw e;
		}
		catch(ProjectException e){
			e.printStackTrace();
			throw e;
		}
		catch(NullPointerException e){
			e.printStackTrace();
			throw new ProjectException(ExceptionCodes.PROJECT_ID_NOT_NULL,ExceptionMessages.PROJECT_ID_NOT_NULL); 
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}


	/*
	 * ASSIGNING MEMBERS TO A PROJECT
	 */
	public ProjectBean allocateUserToProject(ProjectBean projectBean) throws Exception{
		List<Long> userIdsList;
		long projectId;
		try{
			userIdsList=projectBean.getUserIds();
			if(userIdsList.isEmpty())
				throw new NullPointerException();
		}catch(NullPointerException e){
			e.printStackTrace();
			throw new UserException(ExceptionCodes.USER_ID_NOT_NULL,ExceptionMessages.USER_ID_NOT_NULL);
		}
		try{
			projectId=projectBean.getId();
			if(projectId==0)
				throw new NullPointerException();
		}catch(NullPointerException e){
			e.printStackTrace();
			throw new ProjectException(ExceptionCodes.PROJECT_ID_NOT_NULL,ExceptionMessages.PROJECT_ID_NOT_NULL); 
		}
		
		
		Project project=new Project();
		project.setId(projectId);
		List<User> userList=listOfProjectUsers(project);
		List<Long> previous=new LinkedList<Long>();
		for(User user:userList){
			previous.add(user.getId());
		}
		
		
		ProjectBean projectBeanDeAllocate=new ProjectBean(); 
		projectBeanDeAllocate.setId(projectId);
		projectBeanDeAllocate.setUserIds(filter(previous, userIdsList));
		
		
		deAllocateUsersFromProject(projectBeanDeAllocate);
		List<Long> userIds=filter(userIdsList,previous);
		List<UserProject> userProjects=new LinkedList<UserProject>();
		if(!userIds.isEmpty()){
		for(Long userid:userIds){
			UserProject userProject=new UserProject();
			userProject.setProjectId(projectId);
			userProject.setUserId(userid);
			userProjects.add(userProject);
		}
		UserProjectHandler.getInstance().addUserToProject(userProjects);
		}
		return null;
//		try{
//		while(iterator.hasNext()){
//			userId=iterator.next();
////			if(UserHandler.getInstance().isUserDeleted(userId))
////				throw new UserException(ExceptionCodes.DELETED_ALREADY,ExceptionMessages.DELETED_ALREADY);
//			UserProject userProject=new UserProject();
//			userProject.setProjectId(projectId);
//			userProject.setUserId(userId);
//			userProjectList.add(userProject);
//		}
	
//		return project;
//		}catch(ProjectException e){
//			e.printStackTrace();
//			throw e;
//		}
//		catch(UserException e){
//			e.printStackTrace();
//			throw e;
//		}
	}
	/*
	 * Returns project Object by using projectId
	 */
	
	public Project getObjectById(long id) throws Exception{
		return (Project) DAOFactory.getInstance().getProjectDAOImplInstance().getObjectById(id);
	}
	
	
	public ProjectBean deAllocateUsersFromProject(ProjectBean projectBean) throws Exception, NullPointerException {
		List<Long> userIdsList;
		long projectId;
		try{
			userIdsList=projectBean.getUserIds();
		}catch(NullPointerException e){
			e.printStackTrace();
			throw new NullPointerException(ExceptionMessages.USER_ID_NOT_NULL);
		}
		try{
		 projectId=projectBean.getId();
		}catch(NullPointerException e){
			e.printStackTrace();
			throw new NullPointerException(ExceptionMessages.PROJECT_ID_NOT_NULL); 
		}
		List<UserProject> userProjectList=UserProjectHandler.getInstance().getUserProjectListByIds(projectId,userIdsList);
		Iterator<UserProject> iterator=userProjectList.iterator();
		Iterator<Long> userId=userIdsList.iterator();
		UserProject userProject=null;
		while(userId.hasNext()){
			userProject=iterator.next();
			if(UserProjectsRolesHandler.getInstance().deletUserProjectRoleByUserProjectId(userProject)) 
				UserProjectHandler.getInstance().deAllocateUsersFromProject(projectId,userId.next()); 
		}
		return projectBean;
		
	}

	
	public List<User> nonMembersOfProject(Project project) throws Exception {
		try{
			List<UserProject> userProject=UserProjectHandler.getInstance().getListOfUserProjectByProjectId(project.getId());
			List<Long> userIds=new LinkedList<Long>();
			for(UserProject userProjects:userProject)
				userIds.add(userProjects.getUserId());
			return UserHandler.getInstance().getUserById(userIds);
		}catch(UserException e){
			e.printStackTrace();
			throw e;
		}
		catch(NullPointerException e){
			e.printStackTrace();
			throw new ProjectException(ExceptionCodes.PROJECT_ID_NOT_NULL,ExceptionMessages.PROJECT_ID_NOT_NULL); 
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	
	/*
	 * Validations of ProjectName 
	 */

	public boolean validateProjectName(String name) throws Exception{
		if(name==null)
			throw new ProjectException(ExceptionCodes.PROJECT_NAME_NOTNULL,ExceptionMessages.PROJECT_NAME_NOT_NULL);
		else if(name.length()>128)
			throw new ProjectException(ExceptionCodes.PROJECT_NAME_LENGTH_MORE,ExceptionMessages.PROJECT_NAME_LENGTH_MORE);
		else if(!name.matches("^[a-zA-Z0-9]+([ {1}][\\._-a-zA-Z0-9]+)*\\w$"))
			throw new ProjectException(ExceptionCodes.PROJECT_NAME_FORMAT,ExceptionMessages.PROJECT_NAME_FORMAT);
		return true;
	}

	/*
	 * Validations of Project Technology Names 
	 */
	public boolean validateProjectTechnologies(String technologies) throws Exception{
		if(technologies!=null&&technologies.length()>512){
			throw new ProjectException(ExceptionCodes.TECHNOLOGIES_FIELD_LENGTH_MORE,ExceptionMessages.TECHNOLOGIES_FIELD_LENGTH_MORE);	
		}
		return true;

	}
	public List<Long>  filter(List<Long> previous,List<Long> present){
		List<Long> onlyPrevious=new LinkedList<Long>();
		for(Long x:previous){
			if(!present.contains(x))
				onlyPrevious.add(x);
		}
		return onlyPrevious;
	}
//	public List<Long>  getOnlyPresent(List<Long> previous,List<Long> present){
//		List<Long> onlyPresent=new LinkedList();
//		for(Long x:present){
//			if(!previous.contains(x))
//				onlyPresent.add(x);
//		}
//		return onlyPresent;
//	}
}

