package com.qts.persistence.dao;

/*
 * Methods for accessing the UserProject table data
 * author mani kumar
 */
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.ObjectNotFoundException;
import com.qts.exception.ProjectException;
import com.qts.model.BaseObject;
import com.qts.model.RoleBean;
import com.qts.model.UserProject;

public class UserProjectDAOImpl extends BaseDAOImpl implements UserProjectDAO {
	private static UserProjectDAOImpl INSTANCE = null;
	private static Session session = null;

	private UserProjectDAOImpl() {

	}

	public static UserProjectDAOImpl getInstance() {
		if (INSTANCE == null) {
			return new UserProjectDAOImpl();
		}
		return INSTANCE;
	}
	
	/*
	 * fetches all userProject records based on userId
	 */
	public List<UserProject> getUserProjectsByUserId(long id) throws ProjectException{
		 session = getSession();
		 List<UserProject> userProjectList=null;
		 try{
			Criteria userProjectCriteria = session
					.createCriteria(UserProject.class);
			userProjectCriteria.add(Restrictions.eq("userId", id));
			userProjectList = userProjectCriteria.list();
			if(userProjectList.isEmpty()){
				throw new  ProjectException(ExceptionCodes.USER_NOT_PART_OF_ANY_PROJECT,ExceptionMessages.USER_NOT_PART_OF_ANY_PROJECT);
			}
		 }catch(ProjectException e){
			 throw e;
		 }
			return userProjectList;
	}

	/*
	 * fetches all userProject records based on projectId
	 */
	@Override
	public List<UserProject> getUserProjectsByProjectId(long projectId) {
		session = getSession();
		List<UserProject> userProjects=null;
			Criteria userProjectCriteria =session.createCriteria(UserProject.class);
			userProjectCriteria.add(Restrictions.eq("projectId",projectId));
			userProjects= userProjectCriteria.list();
		return userProjects;

	}


	/*
	 * fetches unique userProject record based on userId and projectId
	 */
	@Override
	public UserProject getUserProjectByIds(long projectId, long userId)
			throws ProjectException {
		session = getSession();
		UserProject userProject;
		try {
			Criteria userProjectCriteria =session.createCriteria(UserProject.class);
			userProjectCriteria.add(Restrictions.eq("projectId",projectId)).
								add(Restrictions.eq("userId",userId));
			List<UserProject> userProjectList  =userProjectCriteria.list();
			if (userProjectList.isEmpty())
				throw new ProjectException(
						ExceptionCodes.PROJECT_OR_USER_ID_INVALID,
						ExceptionMessages.PROJECT_OR_USER_ID_INVALID);
			userProject= userProjectList.get(0);
		} catch (ProjectException e) {
			throw e;
		} 
		return userProject;

	}

	/*
	 * inserts new userProject records into userProject Table
	 */
	@Override
	public void addUserToProject(List<UserProject> userProject)
			throws ProjectException {
		try {
			Iterator<UserProject> iterator = userProject.iterator();
			UserProject userProjectObject = new UserProject();
			System.out.println(userProject);
			while (iterator.hasNext()) {
				session = getSession();
				userProjectObject = iterator.next();
				session.save(userProjectObject);
			}
		} catch (ConstraintViolationException e) {
			e.printStackTrace();
			throw new ProjectException(
					ExceptionCodes.USER_PROJECT_CONSTRAINT_FAILED,
					ExceptionMessages.USER_PROJECT_CONSTRAINT_FAILED);
		} 
	}

	/*
	 * fetches unique userProject record based on primary key(id)
	 */
	@Override
	public BaseObject getObjectById(long id) throws ObjectNotFoundException {

		session = getSession();
		BaseObject baseObject=null;
		try {
			Criteria userProjectCriteria =session.createCriteria(UserProject.class);
			userProjectCriteria.add(Restrictions.eq("id",id));
			List<UserProject> list=userProjectCriteria.list();
			if (list.isEmpty())
				throw new ObjectNotFoundException(
						ExceptionCodes.USER_PROJECT_ID_INVALID,
						ExceptionMessages.USER_PROJECT_ID_INVALID);
			baseObject= list.get(0);
		} catch (ObjectNotFoundException e) {
			throw e;
		}
		return baseObject;

	}

	/*
	 * removes userProject record 
	 */
	@Override
	public boolean deAllocateUsersFromProject(UserProject userProject){
		session = getSession();
		boolean flag=false;
		Query query=session.createQuery("delete UserProject where id =:id");
		query.setParameter("id", userProject.getId());
		int count=query.executeUpdate();
		if(count>0){
			flag=true;
		}
		return flag;
	}

	/*
	 * fetches  userProject records based on userIds and projectid
	 */
	@Override
	public List<UserProject> getUserProjectsByIds(long projectId,
			List<Long> userIdsList)  {
		session = getSession();
		long userId;
		List<UserProject> userProjectList = new LinkedList<UserProject>();

			Iterator<Long> iterator = userIdsList.iterator();
			while (iterator.hasNext()) {
				Criteria userProjectCriteria = session
						.createCriteria(UserProject.class);
				userProjectCriteria
						.add(Restrictions.eq("projectId", projectId)).add(
								Restrictions.eq("userId", iterator.next()));
				List<UserProject> userProjects=userProjectCriteria.list();
				if(!userProjects.isEmpty())
				userProjectList.addAll(userProjects);
			}
			return userProjectList;
	}

}
