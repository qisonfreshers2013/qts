package com.qts.persistence.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

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
	    session=SessionFactoryUtil.getInstance().getNewSession();
		session.beginTransaction();
		try{
			List<UserProject> list=session.createQuery("from UserProject where user_id="+id).list();
			if( list.size()==0)
				throw new ProjectException(ExceptionCodes.USER_ID_INVALID,ExceptionMessages.USER_ID_INVALID);
			return list;
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

	public List<UserProject> getListOfUserProjectByProjectId(long id) throws ProjectException,Exception{
		session=SessionFactoryUtil.getInstance().getNewSession();
		session.beginTransaction();
		try{
			List<UserProject> list=session.createQuery("from UserProject where project_id="+id).list();
			if( list.size()==0)
				throw new ProjectException(ExceptionCodes.PROJECT_ID_INVALID,ExceptionMessages.PROJECT_ID_INVALID);
			return list;
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
		session=SessionFactoryUtil.getInstance().getNewSession();
		try{
			
			Iterator<UserProject> iterator=userProject.iterator();
			while(iterator.hasNext()){
			session.beginTransaction();
			session.save(iterator.next());
			session.getTransaction().commit();
			}
		}catch(Exception e){
			throw  new ProjectException(ExceptionCodes.ADD_USER_TO_PROJECT_FAILED,ExceptionMessages.ADD_USER_TO_PROJECT_FAILED);
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
	
	@Override
	public RoleBean updateReportingUserId(RoleBean roleBean) throws ProjectException,Exception {
		
		session=SessionFactoryUtil.getInstance().getNewSession();
		try{
		List<UserProject> listup=getListOfUserProjectByProjectId(roleBean.getProjectId());
		for(UserProject up:listup){
			if(up.getUserId()!=roleBean.getUserId()){
				up.setReportingUserId(roleBean.getUserId());
				session.update(up);
			}
		}
		return roleBean;
		}catch(Exception e){
			e.printStackTrace();
			throw new ProjectException(ExceptionCodes.PROJECT_OR_USER_ID_INVALID,ExceptionMessages.PROJECT_OR_USER_ID_INVALID);
		}
	}

	@Override
	public boolean deAllocateUsersFromProject(long projectId,List<Long> userIdsList) throws Exception {
		session=SessionFactoryUtil.getInstance().getNewSession();
		try{
			session.beginTransaction();
			Iterator<Long> iterator=userIdsList.iterator();
			while(iterator.hasNext()){
					session.createQuery("delete from UserProject where project_id="+projectId+" and user_id="+iterator.next()).executeUpdate();
			}
			session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
			throw  new ProjectException(ExceptionCodes.DELETE_USER_FROM_PROJECT_FAILED,ExceptionMessages.DELETE_USER_FROM_PROJECT_FAILED);
		}
		return true;
	}
}
