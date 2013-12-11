package com.qts.handler;

import java.util.List;

import com.qts.model.UserProject;
import com.qts.model.UserProjectRoles;
import com.qts.persistence.dao.DAOFactory;

public class UserProjectsRolesHandler extends AbstractHandler {

	public static UserProjectsRolesHandler INSTANCE = null;
	private UserProjectsRolesHandler() {
		
	}
	public static UserProjectsRolesHandler getInstance() {
		if (INSTANCE == null)
			INSTANCE = new UserProjectsRolesHandler();
		return INSTANCE;
	}
	public UserProjectRoles getUserProjectRoleByIds(long userProjectId, long roleId) throws Exception{
<<<<<<< HEAD
		return DAOFactory.getInstance().getUserProjectsRolesDAOInstance().getUserProjectRoleByIds(userProjectId,roleId);
	}	
=======
		try{
		if(RoleHandler.getInstance().validateRoleId(roleId))
			return DAOFactory.getInstance().getUserProjectsRolesDAOInstance().getUserProjectRoleByIds(userProjectId,roleId);
		}catch(Exception e){
			throw e; 
		}
		return null;
	}
>>>>>>> 7719fa5f39d1939b4b46fc46756ffa075dd00157
	public List<UserProjectRoles> getUserProjectRolesByUserProjectId(long userProjectId) throws Exception{
		return DAOFactory.getInstance().getUserProjectsRolesDAOInstance().getUserProjectRolesByUserProjectId(userProjectId);
	}
	public boolean deletUserProjectRoleByUserProjectId(UserProject next) throws Exception {
<<<<<<< HEAD
		return DAOFactory.getInstance().getUserProjectsRolesDAOInstance().deletUserProjectRoleByUserProjectId(next);
		
=======
		  return DAOFactory.getInstance().getUserProjectsRolesDAOInstance().deletUserProjectRoleByUserProjectId(next);	  
>>>>>>> 7719fa5f39d1939b4b46fc46756ffa075dd00157
	}
}
