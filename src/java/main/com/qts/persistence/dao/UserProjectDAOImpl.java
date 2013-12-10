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

public class UserProjectDAOImpl extends BaseDAOHibernateImpl implements UserProjectDAO{
	private static UserProjectDAOImpl INSTANCE=null;
	private static Session session=null;
	private UserProjectDAOImpl(){

	}

	public static UserProjectDAOImpl getInstance(){
		if(INSTANCE==null){
			return new UserProjectDAOImpl();
		}
		return INSTANCE;
	}


	public List<UserProject> getListOfUserProjectByUserId(long id) throws ProjectException,Exception{
		Session session=SessionFactoryUtil.getInstance().getNewSession();
		session.beginTransaction();
		try{
			Criteria userProjectCriteria = session.createCriteria(UserProject.class);
			userProjectCriteria.add(Restrictions.eq("userId", id));
			List<UserProject> list = userProjectCriteria.list();
			if(list.isEmpty())
				throw new ProjectException(ExceptionCodes.USER_ID_INVALID,ExceptionMessages.USER_ID_INVALID);
			return list;
		}catch(ProjectException e){
			e.printStackTrace();
			throw e;
		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			session.close();
		}
//	    session=SessionFactoryUtil.getInstance().getNewSession();
//		session.beginTransaction();
//		try{
//			List<UserProject> list=session.createQuery("from UserProject where user_id="+id).list();
//			if( list.size()==0)
//				throw new ProjectException(ExceptionCodes.USER_ID_INVALID,ExceptionMessages.USER_ID_INVALID);
//			return list;
//		}
//		catch(ProjectException e){
//			e.printStackTrace();
//			throw e;
//		}
//		catch(Exception e){
//			e.printStackTrace();
//			throw e;
//		}finally{
//			session.close();
//		}


	}

	public List<UserProject> getListOfUserProjectByProjectId(long id) throws ProjectException,Exception{
		session=SessionFactoryUtil.getInstance().getNewSession();
		session.beginTransaction();
		try{
			List<UserProject> list=session.createQuery("from UserProject where project_id="+id).list();
//			if( list.size()==0)
//				throw new ProjectException(ExceptionCodes.PROJECT_ID_INVALID,ExceptionMessages.PROJECT_ID_INVALID);
			return list;
		}
//		catch(ProjectException e){
//			e.printStackTrace();
//			throw e;
//		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			session.close();
		}

	}

	@Override
	public UserProject getUserProjectByIds(long projectId,long userId) throws ProjectException, Exception{
		session=SessionFactoryUtil.getInstance().getNewSession();
		try{
			session.beginTransaction();
			List<UserProject> list=session.createQuery("from UserProject where project_id="+projectId+"and user_id="+userId).list();
			if(list.size()==0)
				throw new ProjectException(ExceptionCodes.PROJECT_OR_USER_ID_INVALID,ExceptionMessages.PROJECT_OR_USER_ID_INVALID);
			return list.iterator().next();
		}
		catch(ProjectException e){
			e.printStackTrace();
			throw e;
		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			session.close();
		}

	}

	@Override
	public void addUserToProject(List<UserProject> userProject) throws ProjectException,Exception {
		try{
			Iterator<UserProject> iterator=userProject.iterator();
			UserProject userProjectObject=new UserProject();
			System.out.println(userProject);
			while(iterator.hasNext()){
			session=SessionFactoryUtil.getInstance().getNewSession();
			session.beginTransaction();
			userProjectObject=iterator.next();	
			long i=userProjectObject.getProjectId();
			long j=userProjectObject.getUserId();
			session.save(userProjectObject);
			session.getTransaction().commit();
			}
		}catch(ConstraintViolationException e){
			e.printStackTrace();
			throw  new ProjectException(ExceptionCodes.USER_PROJECT_CONSTRAINT_FAILED,ExceptionMessages.USER_PROJECT_CONSTRAINT_FAILED);
		}
		catch(Exception e){
			e.printStackTrace();
			throw  new ProjectException(ExceptionCodes.ADD_USER_TO_PROJECT_FAILED,ExceptionMessages.ADD_USER_TO_PROJECT_FAILED);
		}finally{
			session.close();
		}
	}

	@Override
	public BaseObject getObjectById(long id) throws ObjectNotFoundException {

		session=SessionFactoryUtil.getInstance().getNewSession();
		session.beginTransaction();
		try{
			List<UserProject> list=session.createQuery("from UserProject where id="+id).list();
			if( list.size()==0)
				throw new ObjectNotFoundException(ExceptionCodes.USER_PROJECT_ID_INVALID,ExceptionMessages.USER_PROJECT_ID_INVALID);
			return list.get(0);
		}
		catch(ObjectNotFoundException e){
			e.printStackTrace();
			throw e;
		}finally{
			session.close();
		}

	}
	
//	@Override
//	public RoleBean updateReportingUserId(RoleBean roleBean) throws ProjectException,Exception {
//		
//		session=SessionFactoryUtil.getInstance().getNewSession();
//		try{
//		List<UserProject> listup=getListOfUserProjectByProjectId(roleBean.getProjectId());
//		for(UserProject up:listup){
//			if(up.getUserId()!=roleBean.getUserId()){
//				up.setReportingUserId(roleBean.getUserId());
//				session.update(up);
//			}
//		}
//		return roleBean;
//		}catch(Exception e){
//			e.printStackTrace();
//			throw new ProjectException(ExceptionCodes.PROJECT_OR_USER_ID_INVALID,ExceptionMessages.PROJECT_OR_USER_ID_INVALID);
//		}
//	}

	@Override
	public boolean deAllocateUsersFromProject(long projectId,Long userId) throws Exception {
		session=SessionFactoryUtil.getInstance().getNewSession();
		try{
				session.beginTransaction();
				UserProject userProject = (UserProject) session.createCriteria(UserProject.class).
												add(Restrictions.eq("projectId",projectId)).
												add(Restrictions.eq("userId", userId)).uniqueResult();
				session.delete(userProject);
				session.getTransaction().commit();
				return true;
		}catch(Exception e){
			e.printStackTrace();
			throw  new ProjectException(ExceptionCodes.DELETE_USER_FROM_PROJECT_FAILED,ExceptionMessages.DELETE_USER_FROM_PROJECT_FAILED);
		}finally{
			session.close();
		}
	}

	@Override
	public List<UserProject> getUserProjectListByIds(long projectId,List<Long> userIdsList) throws ProjectException{
		session=SessionFactoryUtil.getInstance().getNewSession();
		long userId;
		List<UserProject> userProjectList =new LinkedList();
		try{
			session.beginTransaction();
			Iterator<Long> iterator=userIdsList.iterator();
			while(iterator.hasNext()){
				Criteria userProjectCriteria=session.createCriteria(UserProject.class);
				userProjectCriteria.add(Restrictions.eq("projectId",projectId)).
									add(Restrictions.eq("userId",iterator.next()));
				userProjectList.addAll(userProjectCriteria.list());
			}
			return userProjectList;
		}
		catch(Exception e){
			e.printStackTrace();
			throw new ProjectException(ExceptionCodes.DELETE_USER_FROM_PROJECT_FAILED,ExceptionMessages.DELETE_USER_FROM_PROJECT_FAILED);
		}finally{
			session.close();
		}
	}

	@Override
	public List<UserProject> getListOfNonUserProjectByProjectId(long id)throws Exception {
		session=SessionFactoryUtil.getInstance().getNewSession();
		session.beginTransaction();
		try{
			Criteria userProjectCriteria=session.createCriteria(UserProject.class);
			userProjectCriteria.add(Restrictions.ne("projectId",id));
			List<UserProject> list=userProjectCriteria.list();
			return list;
		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			session.close();
		}
	}
	
//	@Override
//	public boolean updateUserProjectReportingId(UserProject userProject)
//			throws Exception {
//		session=SessionFactoryUtil.getInstance().getNewSession();
//		session.beginTransaction();
//		try{
//			Query query=session.createQuery("update UserProject set reporting_user_id =:reportingId where project_id =:projectId and userId= :userId");
//			query.setParameter("reportingId",11);
//			query.setParameter("projectId",userProject.getProjectId());
//			query.setParameter("userId",userProject.getUserId());
//			query.executeUpdate();
//			session.getTransaction().commit();
//		}
//		catch(Exception e){
//			e.printStackTrace();
//			throw e;
//		}finally{
//			session.close();
//		}
//		return false;
//	}
}
