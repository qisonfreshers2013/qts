package com.qts.persistence.dao;


import org.hibernate.SessionFactory;


/**
 * DAO Factory. Handlers use this factory to get appropriate DAO.
 * 
 * @author vinay
 * 
 */
public class DAOFactory {
	private static DAOFactory INSTANCE = null;

	private DAOFactory() {
	}

	public static DAOFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DAOFactory();
		}
		return INSTANCE;
	}

	public  RoleDAO getRoleDAOImplInstance() {
		return 	RoleDAOImpl.getInstance();
		
	}

	
	public ReleaseDAO getReleaseDAOImplInstance() {
		return ReleaseDAOImpl.getInstance();
	}

	
	public ProjectDAO getProjectDAOImplInstance() {
		return ProjectDAOImpl.getInstance();
	}

	
	public UserDAO getUserDAO() {
		return UserDAOImpl.getInstance();
	}

	
	public  UserProjectDAO getUserProjectDAOImplInstance() {
		return  UserProjectDAOImpl.getInstance();
	}

    
	public TimeEntryDAO getTimeEntryDAOInstance(){
		return TimeEntryDAOImpl.getTimeEntryDAOInstance();
	}
	

	public  UserProjectsRolesDAO getUserProjectsRolesDAOInstance() {
		return UserProjectsRolesDAOImpl.getInstance(); 
	}
}

