package com.qts.persistence.dao;

/*
 * author mani kumar
 */
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.ObjectNotFoundException;
import com.qts.exception.ProjectException;
import com.qts.model.BaseObject;
import com.qts.model.Project;
import com.qts.model.ProjectBean;
import com.qts.model.User;

public class ProjectDAOImpl extends BaseDAOHibernateImpl implements ProjectDAO {
	private static ProjectDAO INSTANCE=null;
	private ProjectDAOImpl(){
		
	}
	
	
	public static ProjectDAO getInstance(){
		if(INSTANCE==null){
		return new ProjectDAOImpl();
		}
		return INSTANCE;
	}

	//list of projects
	@Override
	public List<Project> getProjectList() throws Exception,ProjectException{
		Session session=SessionFactoryUtil.getInstance().getCurrentSession();
		session.beginTransaction();
		try{
			Criteria projectNameCriteria = session.createCriteria(Project.class);
			projectNameCriteria.setProjection(
					Projections.projectionList().
							add(Projections.property("id")).
							add(Projections.property("name"))	
					);
			
			List<Project> list= projectNameCriteria.list();
			if(list.isEmpty())
				throw new ProjectException(ExceptionCodes.NO_PROJECTS_AVAILABLE,ExceptionMessages.NO_PROJECTS_AVAILABLE);
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
	}
	
	//List of projects Using userId
	@Override
	public Project addProject(Project project) throws Exception{
		Session session=SessionFactoryUtil.getInstance().getCurrentSession();
		try{
			session.beginTransaction();
			session.save(project);
			session.getTransaction().commit();
			return project;
		}catch(Exception e){
			throw  new ProjectException(ExceptionCodes.ADD_PROJECT_FAILED,ExceptionMessages.ADD_PROJECT_FAILED);
		}
		
		
	}
	
	//returns Project object by using corresponding project Id
	@Override 
	public Project getObjectById(long id) throws ObjectNotFoundException{
		Session session=SessionFactoryUtil.getInstance().getCurrentSession();
		try{
			session.beginTransaction();
			List<Project> list = session.createQuery("from Project where id="+id).list();
			if(list.isEmpty())
				throw new ObjectNotFoundException(ExceptionCodes.PROJECT_ID_INVALID,ExceptionMessages.PROJECT_ID_INVALID);
			return list.iterator().next();
		}catch(ObjectNotFoundException e){
			e.printStackTrace();
			throw e;
		}finally{
			session.close();
		}
			
	}
}
