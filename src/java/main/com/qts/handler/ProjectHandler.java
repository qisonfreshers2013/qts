package com.qts.handler;

/*
 * Author Mani kumar
 *
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.omg.CORBA.UserException;

import com.qts.common.Utils;
import com.qts.exception.BusinessException;
import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.ObjectNotFoundException;
import com.qts.exception.ProjectException;
import com.qts.model.Project;
import com.qts.model.ProjectBean;
import com.qts.model.User;
import com.qts.model.UserProject;
import com.qts.persistence.dao.DAOFactory;
import com.qts.persistence.dao.SessionFactoryUtil;

public class ProjectHandler extends AbstractHandler {

	/*
	 * All WebServices Requests Are handled By Project Handler By Using DAOImpl Methods
	 */
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
		try{
			if(projectBean==null){
				return DAOFactory.getInstance().getProjectDAOImplInstance().getProjectList();
			}
			projectBean.getUserId();
			List<UserProject> list= UserProjectHandler.getInstance().getListOfUserProjectByUserId(projectBean.getUserId());
			List<Project> projectList=new LinkedList();
			Iterator<UserProject> itr=list.iterator();
			while(itr.hasNext()){
				Project project =(Project)DAOFactory.getInstance().getProjectDAOImplInstance().getObjectById(itr.next().getProjectId());
				projectList.add(project);
			}
			return projectList;
		}
		catch(ProjectException e){
			throw e;
		}
		catch(Exception e){
			e.printStackTrace();
			throw new ProjectException(ExceptionCodes.USER_ID_NOT_NULL,ExceptionMessages.USER_ID_NOT_NULL);
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
			List<User> userList=new LinkedList();
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
	public ProjectBean allocateUserToProject(ProjectBean project) throws Exception{
		List<Long> userIdsList;
		long projectId;
		try{
			userIdsList=project.getUserIds();
		}catch(NullPointerException e){
			e.printStackTrace();
			throw new NullPointerException(ExceptionMessages.USER_ID_NOT_NULL);
		}
		try{
		 projectId=project.getId();
		}catch(NullPointerException e){
			e.printStackTrace();
			throw new NullPointerException(ExceptionMessages.PROJECT_ID_NOT_NULL); 
		}
		Iterator<Long> iterator=userIdsList.iterator();
		List<UserProject> userProjectList=new LinkedList<UserProject>();
		Long id;
		while(iterator.hasNext()){
			id=iterator.next();
			UserProject userProject=new UserProject();
			userProject.setProjectId(projectId);
			userProject.setReportingUserId(11);
			userProject.setUserId(id);
			userProjectList.add(userProject);
		}
		UserProjectHandler.getInstance().addUserToProject(userProjectList);
		return project;
	}
	
	
	public Project getObjectById(long id) throws ObjectNotFoundException{
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
		UserProjectHandler.getInstance().deAllocateUsersFromProject(projectId,userIdsList);
		return projectBean;
		
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

}

