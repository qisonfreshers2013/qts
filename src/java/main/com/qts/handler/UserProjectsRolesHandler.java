package com.qts.handler;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.qts.exception.RolesException;
import com.qts.model.RoleBean;
import com.qts.model.UserProject;
import com.qts.model.UserProjectsRoles;
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

	public UserProjectsRoles getUserProjectsRolesByUserProjectAndRole(
			long userProjectId, long roleId) throws Exception {
		try {
			RoleHandler.getInstance().validateRoleId(roleId);
		} catch (RolesException e) {

		}
		return DAOFactory
				.getInstance()
				.getUserProjectsRolesDAOInstance()
				.getUserProjectsRolesByUserProjectAndRole(userProjectId, roleId);

	}

	public List<UserProjectsRoles> getUserProjectsRolesByUserProject(
			long userProjectId) throws Exception {
		return DAOFactory.getInstance().getUserProjectsRolesDAOInstance()
				.getUserProjectsRolesByUserProject(userProjectId);
	}

	public RoleBean getUserRoles(RoleBean roleBean,
			List<UserProjectsRoles> listUserProjectRoles) throws Exception {
		return DAOFactory.getInstance().getUserProjectsRolesDAOInstance()
				.getUserRoles(roleBean, listUserProjectRoles);
	}

	// allocates user to specified set of roles
	public RoleBean allocateRoles(RoleBean roleBean, UserProject userProject)
			throws Exception {
		long roleId = 0;
		Set<Long> notValid=new HashSet<Long>();
			for (Iterator<Long> iterator = roleBean.getRoleIds().iterator(); iterator.hasNext();) {
				roleId = (long) iterator.next();
				if(!RoleHandler.getInstance().validateRoleId(roleId))
					notValid.add(roleId);
		} 
		roleBean.getRoleIds().removeAll(notValid);
		if(roleBean.getRoleIds().isEmpty()){
			roleBean.setRoleIds(RoleHandler.getInstance().getUserRoles(roleBean).getRoleIds());
			return roleBean;
		}
		return DAOFactory.getInstance().getUserProjectsRolesDAOInstance()
				.allocateRoles(roleBean, userProject);
	}

	// remove's particular role of a user
	public RoleBean deallocateRoles(RoleBean roleBean, UserProject userProject)
			throws Exception {
		long roleId = 0;
		Set<Long> notValid=new HashSet<Long>();
			for (Iterator<Long> iterator = roleBean.getRoleIds().iterator(); iterator.hasNext();) {
				roleId = (long) iterator.next();
				if(!RoleHandler.getInstance().validateRoleId(roleId))
					notValid.add(roleId);
		}
		roleBean.getRoleIds().removeAll(notValid);
		if(roleBean.getRoleIds().isEmpty()){
			//roleBean.setRoleIds(RoleHandler.getInstance().getUserRoles(roleBean).getRoleIds());
			return roleBean;
		}
		return DAOFactory.getInstance().getUserProjectsRolesDAOInstance()
				.deallocateRoles(roleBean, userProject);
	}

	public boolean deleteUserProjectsRolesByUserProject(UserProject next)
			{
		return DAOFactory.getInstance().getUserProjectsRolesDAOInstance()
				.deleteUserProjectsRolesByUserProject(next);
	}
}
