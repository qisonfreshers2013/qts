package com.qts.persistence.dao;

import java.util.List;

import org.hibernate.Session;

import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.ObjectNotFoundException;
import com.qts.model.BaseObject;
import com.qts.model.Project;

public class ProjectDAOImpl extends BaseDAOHibernateImpl implements ProjectDAO{
	private static ProjectDAOImpl INSTANCE=null;
	private ProjectDAOImpl(){
		
	}
	
	public static ProjectDAOImpl getInstance(){
		if(INSTANCE==null){
		return new ProjectDAOImpl();
		}
		return INSTANCE;
	}	
	@SuppressWarnings("unchecked")
	public BaseObject getObjectById(long id) throws ObjectNotFoundException{
		Session session=SessionFactoryUtil.getInstance().openSession();
		session.beginTransaction();
		List<Project> list=session.createQuery("from Project where id="+id).list();
		if(list.isEmpty())
			throw new ObjectNotFoundException(ExceptionCodes.PROJECT_ID_INVALID,ExceptionMessages.PROJECT_ID_INVALID);
		return list.get(0);
	}
}
