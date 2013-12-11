package com.qts.handler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.ObjectNotFoundException;
import com.qts.exception.RolesException;
import com.qts.model.RoleBean;
import com.qts.model.Roles;
import com.qts.model.UserProject;
import com.qts.model.UserProjectRoles;
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

	public List<Roles> listRoles() throws Exception {
		return DAOFactory.getInstance().getRoleDAOImplInstance().listRoles();
	}

	public RoleBean listUserRoles(RoleBean roleBean) throws Exception {
		try {
			if (validateBean(roleBean) && roleBean.getRoleIds()==null) {
				UserProject userProject = UserProjectHandler.getInstance()
						.getUserProjectByIds(roleBean.getProjectId(),
								roleBean.getUserId());
				List<UserProjectRoles> listUserProjectRoles = UserProjectsRolesHandler
						.getInstance().getUserProjectRolesByUserProjectId(
								userProject.getId());
				return DAOFactory.getInstance().getRoleDAOImplInstance().listUserRoles(
						roleBean, listUserProjectRoles);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
			if (roleBean.getRoleIds() == null || roleBean.getRoleIds().isEmpty())
				throw new RolesException(ExceptionCodes.ROLES_EMPTY_EXCEPTION,
						ExceptionMessages.ROLES_EMPTY_EXCEPTION);
			for (long id : roleBean.getRoleIds())
				if(!checkRoleAlreadyExists(userProject.getId(), id))
					throw new RolesException(ExceptionCodes.ROLE_ID_EXISTS,
							ExceptionMessages.ROLE_ID_EXISTS+"Role id is "+id);
			myRoleBean = DAOFactory.getInstance().getRoleDAOImplInstance().allocateRole(
					roleBean, userProject);
			//passUserIdProjectId(roleBean);
			return myRoleBean;
		} catch (Exception e) {
			throw e;
		}
	}

	public RoleBean deallocateRole(RoleBean roleBean) throws Exception {
		RoleBean myRoleBean;
		UserProject userProject = UserProjectHandler.getInstance()
				.getUserProjectByIds(roleBean.getProjectId(),
						roleBean.getUserId());
		try {
			validateBean(roleBean);
			if (roleBean.getRoleIds() == null || roleBean.getRoleIds().isEmpty())
				throw new RolesException(ExceptionCodes.ROLES_EMPTY_EXCEPTION,
						ExceptionMessages.ROLES_EMPTY_EXCEPTION);
			for (long id : roleBean.getRoleIds()){
				if(checkRoleAlreadyExists(userProject.getId(), id))
					throw new RolesException(ExceptionCodes.ROLE_ID_DOESNOT_EXISTS,
							ExceptionMessages.ROLE_ID_DOESNOT_EXISTS+"Remove role id:"+id);
			}
			myRoleBean = DAOFactory.getInstance().getRoleDAOImplInstance().deallocateRole(
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
		} catch (Exception e) {
			throw e;
		}
		return true;
	}
<<<<<<< HEAD
	public void passUserIdProjectId(RoleBean roleBean) throws Exception {
		//UserProjectHandler.getInstance().updateReportingUserId(roleBean);
=======

//	public void passUserIdProjectId(RoleBean roleBean) throws Exception {
//		//RoleBean myRoleBean=RoleDAOImpl.getInstance().passUserIdProjectId(roleBean);
//		if (roleBean.getRoleIds().contains(new Long(2)))
//			UserProjectHandler.getInstance().updateReportingUserId(roleBean);
//	}
	public boolean validateRoleId(long roleId) throws Exception {
		Set<Long> roleIds=new HashSet<Long>();
		roleIds.add(roleId);
		return DAOFactory.getInstance().getRoleDAOImplInstance().validateRoleId(roleIds);
	}
	private boolean checkRoleAlreadyExists(long id, long roleId)
			throws Exception {
//		try {
			if (UserProjectsRolesHandler.getInstance().getUserProjectRoleByIds(
					id, roleId) != null)
				return false;
			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		}
>>>>>>> 7719fa5f39d1939b4b46fc46756ffa075dd00157
	}
}
