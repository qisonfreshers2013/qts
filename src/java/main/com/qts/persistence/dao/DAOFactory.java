package com.qts.persistence.dao;


/**
 * DAO Factory. Handlers use this factory to get appropriate DAO.
 * 
 * @author Vinay Thandra
 * 
 */
public class DAOFactory {

	private static DAOFactory INSTANCE = null;
	private static RoleDAO ROLEDAO_INSTANCE = null;
	private static UserProjectDAO USERPROJECTDAO_INSTANCE = null;
	private static UserProjectsRolesDAO USER_PROJECT_ROLESDAO_INSTANCE = null;
	private DAOFactory() {
	}
   
	public static DAOFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DAOFactory();
		}
		return INSTANCE;
	}
	public static RoleDAO getRoleDAOImplInstance()
	{
		if (ROLEDAO_INSTANCE== null) {
			ROLEDAO_INSTANCE=RoleDAOImpl.getInstance();
		}
		return ROLEDAO_INSTANCE;
	}
	public static UserProjectDAO getUserProjectDAOImplInstance()
	{
		if (USERPROJECTDAO_INSTANCE== null) {
			USERPROJECTDAO_INSTANCE=UserProjectDAOImpl.getInstance();
		}
		return USERPROJECTDAO_INSTANCE;
	}
	public static UserProjectsRolesDAO getUserProjectsRolesDAOInstance() {
		if(USER_PROJECT_ROLESDAO_INSTANCE==null){
			USER_PROJECT_ROLESDAO_INSTANCE=UserProjectsRolesDAOImpl.getInstance();
		}
		return USER_PROJECT_ROLESDAO_INSTANCE;
	}
}
