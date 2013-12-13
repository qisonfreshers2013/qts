package com.qts.handler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.ObjectNotFoundException;
import com.qts.exception.ProjectException;
import com.qts.exception.RolesException;
import com.qts.model.RoleBean;
import com.qts.model.Roles;
import com.qts.model.UserProject;
import com.qts.model.UserProjectsRoles;
import com.qts.persistence.dao.DAOFactory;

/**
 * 
 * @author Jagadish
 * 
 */
public class RoleHandler extends AbstractHandler {

	public static RoleHandler INSTANCE = null;

	private RoleHandler() {

	}

	public static RoleHandler getInstance() {
		if (INSTANCE == null)
			INSTANCE = new RoleHandler();
		return INSTANCE;
	}

	public List<Roles> getRoles() throws Exception {
		return DAOFactory.getInstance().getRoleDAOImplInstance().getRoles();
	}

	public RoleBean getUserRoles(RoleBean roleBean) throws Exception {
		try {
			// validating the input whether both userid and projectid is
			// given,roleid should be intially null
			if (validateBean(roleBean) && roleBean.getRoleIds() == null) {

				UserProject userProject = UserProjectHandler.getInstance()
						.getUserProjectByIds(roleBean.getProjectId(),
								roleBean.getUserId());
				List<UserProjectsRoles> listUserProjectRoles = UserProjectsRolesHandler
						.getInstance().getUserProjectsRolesByUserProject(
								userProject.getId());
				return UserProjectsRolesHandler.getInstance().getUserRoles(
						roleBean, listUserProjectRoles);
			}
		} catch (ProjectException | RolesException e) {
			
		}
		return roleBean;
	}

	public RoleBean allocateRoles(RoleBean roleBean) throws Exception {
		RoleBean myRoleBean;
		UserProject userProject = UserProjectHandler.getInstance()
				.getUserProjectByIds(roleBean.getProjectId(),
						roleBean.getUserId());
		Set<Long> availableRoles = new HashSet<Long>();
		try {
			validateBean(roleBean);
			if (roleBean.getRoleIds() == null
					|| roleBean.getRoleIds().isEmpty())
				throw new RolesException(ExceptionCodes.ROLES_EMPTY_EXCEPTION,
						ExceptionMessages.ROLES_EMPTY_EXCEPTION);
			for (long id : roleBean.getRoleIds()) {
				try {
					if (checkRoleAlreadyExists(userProject.getId(), id)) {
						availableRoles.add(id);
					}
				} catch (RolesException e) {

				}
			}

			roleBean.getRoleIds().removeAll(availableRoles);
			myRoleBean = UserProjectsRolesHandler.getInstance().allocateRoles(
					roleBean, userProject);
			return myRoleBean;
		} catch (RolesException e) {
			throw e;
		}
	}

	public RoleBean deallocateRoles(RoleBean roleBean) throws Exception {
		UserProject userProject = UserProjectHandler.getInstance()
				.getUserProjectByIds(roleBean.getProjectId(),
						roleBean.getUserId());
		Set<Long> nonAvailableRoles = new HashSet<Long>();
		validateBean(roleBean);
		try {
			if (roleBean.getRoleIds() == null
					|| roleBean.getRoleIds().isEmpty())
				throw new RolesException(ExceptionCodes.ROLES_EMPTY_EXCEPTION,
						ExceptionMessages.ROLES_EMPTY_EXCEPTION);
			for (long id : roleBean.getRoleIds()) {
				try {
					if (!checkRoleAlreadyExists(userProject.getId(), id)) {
						nonAvailableRoles.add(id);
					}

				} catch (ProjectException | RolesException e) {

				}
			}
			if (!nonAvailableRoles.containsAll(roleBean.getRoleIds())) {
				roleBean = UserProjectsRolesHandler.getInstance()
						.deallocateRoles(roleBean, userProject);
			}
		} catch (RolesException e) {
			throw e;
		}
		return roleBean;
	}

	public boolean validateBean(RoleBean roleBean) throws Exception {
		try {
			if (roleBean == null)
				throw new ObjectNotFoundException(
						ExceptionCodes.OBJECT_NOT_FOUND, "Input cannot be null");
			else if (roleBean.getUserId() == 0 || roleBean.getProjectId() == 0)
				throw new RolesException(ExceptionCodes.USER_DOESNOT_EXIST,
						"Both userid and projectid should be given");
		} catch (RolesException e) {
			throw e;
		}
		return true;
	}

	public boolean validateRoleId(long roleId) throws Exception {

		return DAOFactory.getInstance().getRoleDAOImplInstance()
				.validateRoleId(roleId);
	}

	private boolean checkRoleAlreadyExists(long id, long roleId)
			throws Exception {
		boolean exists = false;
		if (UserProjectsRolesHandler.getInstance()
				.getUserProjectsRolesByUserProjectAndRole(id, roleId) != null)
			exists = true;
		return exists;
	}
}
