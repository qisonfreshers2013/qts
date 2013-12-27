package com.qts.handler;

/*
 * All WebServices Requests  Which Are Related To Project Are Handled By Project Handler 
 * Author Mani kumar
 *
 */
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.hibernate.exception.ConstraintViolationException;

import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.ObjectNotFoundException;
import com.qts.exception.ProjectException;
import com.qts.exception.RolesException;
import com.qts.exception.UserException;
import com.qts.handler.annotations.AuthorizeEntity;
import com.qts.model.Project;
import com.qts.model.ProjectBean;
import com.qts.model.RoleBean;
import com.qts.model.Roles;
import com.qts.model.User;
import com.qts.model.UserProject;
import com.qts.model.UserProjectsRoles;
import com.qts.persistence.dao.DAOFactory;
import com.qts.service.common.ServiceRequestContextHolder;

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
	public List<Project> getProjects()
			throws ProjectException {
		List<Project> projects=null;
		try{
			projects= DAOFactory.getInstance().getProjectDAOImplInstance()
					.getProjects();
		}catch(ProjectException e){
			throw e;
		}
		return projects;
	}
	
	
	/*
	 * Fetching List of Projects for a particular User
	 */
	public List<Project> getProjectsForUser() throws ProjectException,ObjectNotFoundException{

		long userId=ServiceRequestContextHolder.getContext().getUserSessionToken().getUserId();
		List<Project> projectList= new LinkedList<Project>();

		//fetching userProject records using userId
		List<UserProject> UserProjectList = UserProjectHandler.getInstance()
		.getUserProjectsByUserId(userId);
		Iterator<UserProject> iterator = UserProjectList.iterator();
		while (iterator.hasNext()) {
			try{
				Project project = (Project) DAOFactory.getInstance()
				.getProjectDAOImplInstance()
				.getObjectById(iterator.next().getProjectId());
				projectList.add(project);
			}catch(ObjectNotFoundException e){
				throw e;
			}

		}
		return projectList;
	}
	
	
	/*
	 * Fetching List of Projects for a particular Member
	 */
	public List<Project> getProjectsForMember() throws ProjectException,ObjectNotFoundException,RolesException{

		long userId=ServiceRequestContextHolder.getContext().getUserSessionToken().getUserId();
		List<Project> projectList= new LinkedList<Project>();

		//fetching userProject records using userId
		List<UserProject> userProjectList = UserProjectHandler.getInstance()
		.getUserProjectsByUserId(userId);
		Iterator<UserProject> iterator = userProjectList.iterator();
		while (iterator.hasNext()) {
			UserProject userProject=iterator.next();
			long userProjectId=userProject.getId();
			long projectId=userProject.getProjectId();
			List<UserProjectsRoles> userProjectRoles=new LinkedList<UserProjectsRoles>();
			try{
			userProjectRoles=UserProjectsRolesHandler.getInstance().getUserProjectsRolesByUserProject(userProjectId);
			}catch(Exception e){
				
			}
			for(UserProjectsRoles userProjectRole:userProjectRoles){
				if(userProjectRole.getRoleId()==3){
					try{
						Project project = (Project) DAOFactory.getInstance()
						.getProjectDAOImplInstance()
						.getObjectById(projectId);
						projectList.add(project);
					}catch(ObjectNotFoundException e){
						throw e;
					}
				}
			}
			

		}
		if(projectList.isEmpty()){
			throw new RolesException(ExceptionCodes.NO_ROLES_OR_NOT_ASSOCIATED_WITH_ANY_PROJECT,ExceptionMessages.NO_ROLES_OR_NOT_ASSOCIATED_WITH_ANY_PROJECT);
		}
		return projectList;
	}
	
	
	

	/*
	 * Fetching List of Projects for a particular Approver
	 */
	public List<Project> getProjectsForApprover() throws ProjectException,ObjectNotFoundException,RolesException{

		long userId=ServiceRequestContextHolder.getContext().getUserSessionToken().getUserId();
		List<Project> projectList= new LinkedList<Project>();

		//fetching userProject records using userId
		List<UserProject> userProjectList = UserProjectHandler.getInstance()
		.getUserProjectsByUserId(userId);
		Iterator<UserProject> iterator = userProjectList.iterator();
		while (iterator.hasNext()) {
			UserProject userProject=iterator.next();
			long userProjectId=userProject.getId();
			long projectId=userProject.getProjectId();
			List<UserProjectsRoles> userProjectRoles=new LinkedList<UserProjectsRoles>();
			try{
			userProjectRoles=UserProjectsRolesHandler.getInstance().getUserProjectsRolesByUserProject(userProjectId);
			}catch(Exception e){
				
			}
			for(UserProjectsRoles userProjectRole:userProjectRoles){
				if(userProjectRole.getRoleId()==2){
					try{
						Project project = (Project) DAOFactory.getInstance()
						.getProjectDAOImplInstance()
						.getObjectById(projectId);
						projectList.add(project);
					}catch(ObjectNotFoundException e){
						throw e;
					}
				}
			}
			

		}
		if(projectList.isEmpty()){
			throw new RolesException(ExceptionCodes.NO_ROLES_OR_NOT_ASSOCIATED_WITH_ANY_PROJECT,ExceptionMessages.NO_ROLES_OR_NOT_ASSOCIATED_WITH_ANY_PROJECT);
		}
		return projectList;
	}
	
	
	


	/*
	 * Adding New Project
	 */
	@AuthorizeEntity(roles = {Roles.ROLE_ADMIN}, entity = "Project.java")
	public Project addProjectAOP(Project project) throws ProjectException{

		String name=project.getName();
		String technologies=project.getTechnologies();

		try{
			//Validations of ProjectName
			if (name == null)
				throw new ProjectException(ExceptionCodes.PROJECT_NAME_NOTNULL,
						ExceptionMessages.PROJECT_NAME_NOT_NULL);
			else if (name.length() > 128)
				throw new ProjectException(ExceptionCodes.PROJECT_NAME_LENGTH_MORE,
						ExceptionMessages.PROJECT_NAME_LENGTH_MORE);
			else if (!name.matches("^[a-zA-Z0-9]+[\\s\\._\\w]*"))
				throw new ProjectException(ExceptionCodes.PROJECT_NAME_FORMAT,
						ExceptionMessages.PROJECT_NAME_FORMAT);


			//Validations of Project Technology names
			if (technologies != null && technologies.length() > 512) {
				throw new ProjectException(
						ExceptionCodes.TECHNOLOGIES_FIELD_LENGTH_MORE,
						ExceptionMessages.TECHNOLOGIES_FIELD_LENGTH_MORE);
			}
			
			//saving project
			try{
				return (Project) DAOFactory.getInstance()
						.getProjectDAOImplInstance().saveObject(project);
			}catch(ConstraintViolationException e){
				throw new ProjectException(ExceptionCodes.DUPLICATE_PROJECT_ENTRY,ExceptionMessages.DUPLICATE_PROJECT_ENTRY);
			}
		}catch(ProjectException e){
			throw e;
		}
	}

	
	
	/*
	 * Fetching All Members Of Project
	 */
	public List<User> getProjectUsers(ProjectBean projectBean) throws ProjectException{

		List<UserProject> userProject=null;
		List<User> userList=new LinkedList();
		long projectId;
		try {

			//validating projectId
			try{
				projectId=projectBean.getProjectId();
			}catch(NullPointerException e){
				throw new ProjectException(ExceptionCodes.PROJECT_ID_NOT_NULL,
						ExceptionMessages.PROJECT_ID_NOT_NULL);
			}

			//validating whether project is existing or not
			try{
				getObjectById(projectId);
			}catch(ObjectNotFoundException e){
				throw new ProjectException(ExceptionCodes.PROJECT_ID_INVALID,
						ExceptionMessages.PROJECT_ID_INVALID);
			}

			//fetching userProject records using projectId
			userProject = UserProjectHandler.getInstance()
			.getUserProjectsByProjectId(projectBean.getProjectId());


			//if no user is associated with project return empty list
			if (userProject.isEmpty())
				return userList;

			//fetching userObjects
			Iterator<UserProject> iterator = userProject.iterator();
			List<Long> userIds=new LinkedList<Long>();
			while (iterator.hasNext()) {
				userIds.add(iterator.next().getUserId());
			}
			userList=DAOFactory.getInstance().getUserDAO().getUsersByIds(userIds);
		} catch (ProjectException e) {
			throw e;
		}
		return userList;
	}

	/*
	 * ASSIGNING USERS TO A PROJECT
	 */
	@AuthorizeEntity(roles = {Roles.ROLE_ADMIN}, entity = "Project.java")
	public List<User> allocateUsersToProjectAOP(ProjectBean projectBean)
			throws ProjectException,UserException {
		Set<Long> userIdsList;
		List<User> users=null;
		long projectId;
		try{
			//validating userIds
			try{
				userIdsList = projectBean.getUserIds();
				if (userIdsList.isEmpty()){
					throw new  UserException(ExceptionCodes.USER_ID_NOT_NULL,
							ExceptionMessages.USER_ID_NOT_NULL);	
					} 
			}catch(NullPointerException|UserException e){
				throw new  UserException(ExceptionCodes.USER_ID_NOT_NULL,
						ExceptionMessages.USER_ID_NOT_NULL);	
			}
			
			
			//validating whether project is existing or not
			projectId = projectBean.getProjectId();
			try{
				getObjectById(projectId);
			}catch(ObjectNotFoundException e){
				throw new ProjectException(ExceptionCodes.PROJECT_ID_INVALID,
						ExceptionMessages.PROJECT_ID_INVALID);
			}
			
		ProjectBean project = new ProjectBean();
		project.setProjectId(projectId);
		
		//fetching users of project
		List<User> userList= getProjectUsers(project);
		Set<Long> previous = new HashSet<Long>();
		Set<Long> userIds=null;
		
		
		if(userList.isEmpty()){
			userIds=userIdsList;
		}
		else{
			for (User user : userList) {
				previous.add(user.getId());
			}

			//filtering new users and previous users 
			userIds = filter(userIdsList, previous);
		}
		
		
		List<UserProject> userProjects = new LinkedList<UserProject>();
		if (!userIds.isEmpty()) {
			for (Long userId : userIds) {
				//validating whether user is valid or not
				if ((!UserHandler.getInstance().isUserDeleted(userId))) {
					UserProject userProject = new UserProject();
					userProject.setProjectId(projectId);
					userProject.setUserId(userId);
					userProjects.add(userProject);
				}
			}
			
			//allocating user to project
			UserProjectHandler.getInstance().addUsersToProject(userProjects);
		}
	 users=getProjectUsers(projectBean);
	}catch(ProjectException|UserException e){
		throw e;
	}
	return users;
}
	

	/*
	 * Returns project Object by using projectId
	 */

	public Project getObjectById(long id) throws ObjectNotFoundException {
		
		Project project=null;
		try{
		project= (Project) DAOFactory.getInstance().getProjectDAOImplInstance()
				.getObjectById(id);
		}catch(ObjectNotFoundException e){
			throw e;
		}
		return project;
	}
	
	
	
	/*
	 * Removing users from project
	 */
	@AuthorizeEntity(roles = {Roles.ROLE_ADMIN}, entity = "Project.java")
	public List<User> deAllocateUsersFromProjectAOP(ProjectBean projectBean)
	throws ProjectException, NullPointerException,Exception {

		Set<Long> userIdsList;
		List<User> users=null;
		long projectId;
		try{
			//validation for userIds
			try {
				userIdsList = projectBean.getUserIds();
				if(userIdsList.isEmpty()){
					throw new  UserException(ExceptionCodes.USER_ID_NOT_NULL,
							ExceptionMessages.USER_ID_NOT_NULL);	
				}	
			} catch (NullPointerException|UserException e) {
				throw new UserException(ExceptionCodes.USER_ID_NOT_NULL,
						ExceptionMessages.USER_ID_NOT_NULL);
			}

			//validating whether project is existing or not
			projectId = projectBean.getProjectId();
			try{
				getObjectById(projectId);
			}catch(ObjectNotFoundException e){
				throw new ProjectException(ExceptionCodes.PROJECT_ID_INVALID,
						ExceptionMessages.PROJECT_ID_INVALID);
			}

			List<UserProject> userProjectList = UserProjectHandler.getInstance()
			.getUserProjectsByIds(projectId, userIdsList);

			Iterator<UserProject> iterator = userProjectList.iterator();
			Iterator<Long> userId = userIdsList.iterator();
			UserProject userProject = null;

			while (iterator.hasNext()) {
				userProject = iterator.next();

				//deAllocating roles of user
				UserProjectsRolesHandler.getInstance()
				.deleteUserProjectsRolesByUserProject(userProject);

				//deAllocating user from project
				UserProjectHandler.getInstance().deAllocateUsersFromProject(userProject);
			}

			users=getProjectUsers(projectBean);
		}catch(ProjectException|NullPointerException e){
			throw e;
		}
		return users;

	}

	/*
	 * list of users not associated with a particular project
	 */
	public List<User> nonUsersOfProject(ProjectBean project) throws ProjectException {
		
		List<User> users=null;
		long projectId;
		try {
			
			
			try{
				projectId=project.getProjectId();
			}catch(NullPointerException e){
				throw new ProjectException(ExceptionCodes.PROJECT_ID_NOT_NULL,
						ExceptionMessages.PROJECT_ID_NOT_NULL);
			}
			
			//validating whether project is existing or not
			try{
				getObjectById(projectId);
			}catch(ObjectNotFoundException e){
				throw new ProjectException(ExceptionCodes.PROJECT_ID_INVALID,
						ExceptionMessages.PROJECT_ID_INVALID);
			}
			
			List<UserProject> userProject = UserProjectHandler.getInstance()
					.getUserProjectsByProjectId(projectId);
			
			List<Long> userIds = new LinkedList<Long>();
			for (UserProject userProjects : userProject){
				userIds.add(userProjects.getUserId());
			}	
			users= UserHandler.getInstance().getUsersOtherThanTheseIds(userIds);
			
		}catch (ProjectException e) {
			throw e;
		}
		return users;
	}


	
	private Set<Long> filter(Set<Long> previous, Set<Long> present) {
		Set<Long> onlyPrevious = new HashSet<Long>();
		for (Long x : previous) {
			if (!present.contains(x))
				onlyPrevious.add(x);
		}
		return onlyPrevious;
	}
	
}
