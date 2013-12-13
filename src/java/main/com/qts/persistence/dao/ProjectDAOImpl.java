package com.qts.persistence.dao;

/*
 * Methods for accessing the Project table data
 * author mani kumar
 */
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
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
	public List<Project> getProjects() throws ProjectException{
		Session session=getSession();
		List<Project> projectList=null;
		
		try{
			Criteria projectCriteria = session.createCriteria(Project.class);
			projectCriteria.setProjection(
					Projections.projectionList().
							add(Projections.property("id")).
							add(Projections.property("name"))	
					);
			projectCriteria.addOrder(Order.asc("name"));
			projectList=projectCriteria.list();
			if(projectList.isEmpty())
				throw new ProjectException(ExceptionCodes.NO_PROJECTS_AVAILABLE,ExceptionMessages.NO_PROJECTS_AVAILABLE);
		}catch(ProjectException e){
			throw e;
		}
		
		return projectList;
	}
	

	//returns Project object by using corresponding project Id
	@Override 
	public Project getObjectById(long id) throws ObjectNotFoundException{
		Session session=getSession();
		Project project=null;
		try{
			Criteria projectCriteria = session.createCriteria(Project.class);
			projectCriteria.add(Restrictions.eq("id", id));
			List<Project> list = projectCriteria.list();
			if(list.isEmpty())
				throw new ObjectNotFoundException(ExceptionCodes.PROJECT_ID_INVALID,ExceptionMessages.PROJECT_ID_INVALID);
			project=list.get(0);
		}catch(ObjectNotFoundException e){
			throw e;
		}
		
		return project;
	}
}
