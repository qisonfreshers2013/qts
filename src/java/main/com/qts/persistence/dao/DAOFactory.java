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

	// Implement all the factory methods, after this line.

    
	public static TimeEntryDAO getTimeEntryDAOInstance()
	{
		return TimeEntryDAOImpl.getTimeEntryDAOInstance();
	}
	
	
	
	
	
}
