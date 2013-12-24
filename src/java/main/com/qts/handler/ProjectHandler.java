package com.qts.handler;

/*
 * All WebServices Requests Are handled By Project Handler By Calling DAOImpl Methods
 * Author Mani kumar
 *
 */
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;

import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.ObjectNotFoundException;
import com.qts.exception.ProjectException;
import com.qts.exception.UserException;
import com.qts.model.Project;
import com.qts.model.ProjectBean;
import com.qts.model.User;
import com.qts.model.UserProject;
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
		List<Project> projectList= new LinkedList<Project>();;
		try{
			
			//fetching userProject records using userId
			List<UserProject> list = UserProjectHandler.getInstance()
					.getUserProjectsByUserId(userId);
			Iterator<UserProject> itr = list.iterator();
			while (itr.hasNext()) {

				try{
					Project project = (Project) DAOFactory.getInstance()
							.getProjectDAOImplInstance()
							.getObjectById(itr.next().getProjectId());
					projectList.add(project);
				}catch(ObjectNotFoundException e){
					throw e;
				}

			}
		}catch(ProjectException|ObjectNotFoundException e){
			throw e;
		}
		return projectList;
	}


	/*
	 * Adding New Project
	 */
	public Project addProject(Project project) throws ProjectException{

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
			else if (!name.matches("^[a-zA-Z0-9]+([ {1}][\\._-a-zA-Z0-9]+)*\\w$"))
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
	public List<User> getProjectUsers(Project project) throws ProjectException,UserException,NullPointerException {
		
		List<UserProject> userProject=null;
		long projectId;
		try {
			//validating projectId
			try{
				projectId=project.getId();
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
					.getUserProjectsByProjectId(project.getId());
			
			List<User> userList = new LinkedList<User>();
			if (userProject.isEmpty())
				return userList;
			Iterator<UserProject> iterator = userProject.iterator();
			
			//fetching userObjects
			while (iterator.hasNext()) {
				User user = UserHandler.getInstance().getUserById(
						iterator.next().getUserId());
				if (user != null)
					userList.add(user);
			}
			return userList;

		} catch (UserException|ProjectException|NullPointerException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/*
	 * ASSIGNING USERS TO A PROJECT
	 */
	public List<UserProject> allocateUsersToProject(ProjectBean projectBean)
			throws Exception {
		List<Long> userIdsList;
		long projectId;
		try{
			//validating userIds
			userIdsList = projectBean.getUserIds();
			if (userIdsList.isEmpty()){
				throw new  UserException(ExceptionCodes.USER_ID_NOT_NULL,
						ExceptionMessages.USER_ID_NOT_NULL);		
				} 
			
			//validating projectId
			projectId = projectBean.getProjectId();
			if (projectId == 0){
				throw  new ProjectException(ExceptionCodes.PROJECT_ID_NOT_NULL,
						ExceptionMessages.PROJECT_ID_NOT_NULL);
			}
			
		Project project = new Project();
		project.setId(projectId);
		List<User> userList=null;
		
		//fetching users of project
		userList= getProjectUsers(project);
		List<Long> previous = new LinkedList<Long>();
		for (User user : userList) {
			previous.add(user.getId());
		}
		
		//filtering new users and previous users 
		List<Long> userIds = filter(userIdsList, previous);
		
		List<UserProject> userProjects = new LinkedList<UserProject>();
		if (!userIds.isEmpty()) {
			for (Long userId : userIds) {
				//validating whether user is valid or not
				if ((!UserHandler.getInstance().isUserDeleted(userId))&&(UserHandler.getInstance().getUserByUserId(userId)!=null)) {
					UserProject userProject = new UserProject();
					userProject.setProjectId(projectId);
					userProject.setUserId(userId);
					userProjects.add(userProject);
				}
			}
			
			//allocating user to project
			UserProjectHandler.getInstance().addUserToProject(userProjects);
		}
		return UserProjectHandler.getInstance().getUserProjectsByProjectId(projectId);
	}catch(Exception e){
		throw e;
	}
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
	public List<UserProject> deAllocateUsersFromProject(ProjectBean projectBean)
			throws ProjectException, NullPointerException {
		
		List<Long> userIdsList;
		long projectId;
		
		//validation for userIds
		try {
			userIdsList = projectBean.getUserIds();
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new NullPointerException(ExceptionMessages.USER_ID_NOT_NULL);
		}

		//validations for projectId
		projectId = projectBean.getProjectId();
		if(projectId==0){
			throw new NullPointerException(
					ExceptionMessages.PROJECT_ID_NOT_NULL);
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
			.deleteUserProjectRoleByUserProjectId(userProject);
			
			//deAllocating user from project
			UserProjectHandler.getInstance().deAllocateUsersFromProject(userProject);
		}

		return UserProjectHandler.getInstance().getUserProjectsByProjectId(projectId);

	}

	/*
	 * list of users not associated with a particular project
	 */
	public List<User> nonUsersOfProject(Project project) throws ProjectException {
		try {
			
			List<UserProject> userProject = UserProjectHandler.getInstance()
					.getUserProjectsByProjectId(project.getId());
			
			List<Long> userIds = new LinkedList<Long>();
			for (UserProject userProjects : userProject)
				userIds.add(userProjects.getUserId());
			return UserHandler.getInstance().getUserByIds(userIds);
			
		}catch (NullPointerException e) {
			e.printStackTrace();
			throw new ProjectException(ExceptionCodes.PROJECT_ID_NOT_NULL,
					ExceptionMessages.PROJECT_ID_NOT_NULL);
		}
	}


	
	private List<Long> filter(List<Long> previous, List<Long> present) {
		List<Long> onlyPrevious = new LinkedList<Long>();
		for (Long x : previous) {
			if (!present.contains(x))
				onlyPrevious.add(x);
		}
		return onlyPrevious;
	}
	
}
