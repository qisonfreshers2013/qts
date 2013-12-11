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
		return DAOFactory.getInstance().getUserProjectsRolesDAOInstance().getUserProjectRoleByIds(userProjectId,roleId);
	}	
	public List<UserProjectRoles> getUserProjectRolesByUserProjectId(long userProjectId) throws Exception{
		return DAOFactory.getInstance().getUserProjectsRolesDAOInstance().getUserProjectRolesByUserProjectId(userProjectId);
	}
	public boolean deletUserProjectRoleByUserProjectId(UserProject next) throws Exception {
		return DAOFactory.getInstance().getUserProjectsRolesDAOInstance().deletUserProjectRoleByUserProjectId(next);
		
	}
}
