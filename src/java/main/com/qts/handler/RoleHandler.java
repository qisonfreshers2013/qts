package com.qts.handler;

import java.util.List;

import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.ObjectNotFoundException;
import com.qts.exception.RolesException;
import com.qts.model.RoleBean;
import com.qts.model.Roles;
import com.qts.model.UserProject;
import com.qts.model.UserProjectRoles;
import com.qts.persistence.dao.DAOFactory;
import com.qts.persistence.dao.SessionFactoryUtil;

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
	public List<Roles> listRoles() throws Exception {
		return DAOFactory.getRoleDAOImplInstance().listRoles();
	}

	public RoleBean listUserRoles(RoleBean roleBean) throws Exception {
		try {
			if (validateBean(roleBean)) {
				UserProject userProject = UserProjectHandler.getInstance()
						.getUserProjectByIds(roleBean.getProjectId(),
								roleBean.getUserId());
				List<UserProjectRoles> listUserProjectRoles = UserProjectsRolesHandler
						.getInstance().getUserProjectRolesByUserProjectId(
								userProject.getId());
				return DAOFactory.getRoleDAOImplInstance().listUserRoles(
						roleBean, listUserProjectRoles);
			}
		} catch (Exception e) {
			throw e;
		}
		return roleBean;
	}

	public RoleBean allocateRole(RoleBean roleBean) throws Exception {
		RoleBean myRoleBean;
		UserProject userProject = UserProjectHandler.getInstance()
				.getUserProjectByIds(roleBean.getProjectId(),
						roleBean.getUserId());
		try {
			validateBean(roleBean);
			// if (roleBean.getRoleIds().isEmpty()) {
			// throw new RolesException(ExceptionCodes.ROLES_EMPTY_EXCEPTION,
			// ExceptionMessages.ROLES_EMPTY_EXCEPTION);
			// }
			myRoleBean = DAOFactory.getRoleDAOImplInstance().allocateRole(
					roleBean, userProject);
			return myRoleBean;
		} catch (Exception e) {
			throw e;
		}
	}
	public RoleBean deallocateRole(RoleBean roleBean) throws Exception {
		RoleBean myRoleBean;
		try {
			validateBean(roleBean);
			// if (roleBean.getRoleIds().isEmpty())

			UserProject userProject = DAOFactory
					.getUserProjectDAOImplInstance().getUserProjectByIds(
							roleBean.getProjectId(), roleBean.getUserId());
			// if (roleBean.getRoleIds().isEmpty()) {
			// throw new RolesException(
			// ExceptionCodes.ROLES_LIST_EMPTY_EXCEPTION,
			// ExceptionMessages.ROLES_LIST_EMPTY_EXCEPTION);
			// }
			myRoleBean = DAOFactory.getRoleDAOImplInstance().deallocateRole(
					roleBean, userProject);
			return myRoleBean;
		} catch (Exception e) {
			throw e;
		}
	}
	public boolean validateBean(RoleBean roleBean) throws Exception {
		try {
			if (roleBean == null)
				throw new ObjectNotFoundException(
						ExceptionCodes.OBJECT_NOT_FOUND, "Input cannot be null");
			else if (roleBean.getUserId() == 0 || roleBean.getProjectId() == 0)
				throw new RolesException(ExceptionCodes.USER_DOESNOT_EXIST,
						"Both userid and projectid should be given");
			else if (roleBean.getRoleIds() == null)// &&
													// roleBean.getRoleIds().isEmpty())
				throw new RolesException(ExceptionCodes.ROLES_EMPTY_EXCEPTION,
						ExceptionMessages.ROLES_EMPTY_EXCEPTION);
			// else if(roleBean.getRoleIds().contains(new Long(1)))
			// throw new RolesException(ExceptionCodes.);
		} catch (Exception e) {
			throw e;
		}
		return true;
	}
	public void passUserIdProjectId(RoleBean roleBean) throws Exception {
		UserProjectHandler.getInstance().updateReportingUserId(roleBean);
	}
}
