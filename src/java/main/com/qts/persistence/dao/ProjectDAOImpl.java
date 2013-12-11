package com.qts.persistence.dao;

/*
 * Methods for accessing the Project table data
 * author mani kumar
 */
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.ObjectNotFoundException;
import com.qts.exception.ProjectException;
import com.qts.model.Project;

public class ProjectDAOImpl extends BaseDAOImpl implements ProjectDAO {
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
		Session session=getSession();
		try{
			Criteria projectCriteria = session.createCriteria(Project.class);
			projectCriteria.setProjection(
					Projections.projectionList().
							add(Projections.property("id")).
							add(Projections.property("name"))	
					);
			
			List<Project> list= projectCriteria.list();
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
			
		}
	}
	

	//
	//returns Project object by using corresponding project Id
	@Override 
	public Project getObjectById(long id) throws ObjectNotFoundException{
		Session session=getSession();
		try{
			Criteria projectCriteria = session.createCriteria(Project.class);
			projectCriteria.add(Restrictions.eq("id", id));
			List<Project> list = projectCriteria.list();//session.createQuery("from Project where id="+id).list();
			if(list.isEmpty())
				throw new ObjectNotFoundException(ExceptionCodes.PROJECT_ID_INVALID,ExceptionMessages.PROJECT_ID_INVALID);
			return list.iterator().next();
		}catch(ObjectNotFoundException e){
			e.printStackTrace();
			throw e;
		}
	}
}
