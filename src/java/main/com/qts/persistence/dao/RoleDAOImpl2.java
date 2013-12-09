package com.qts.persistence.dao;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.RolesException;
import com.qts.model.RoleBean;
import com.qts.model.Roles;
import com.qts.model.UserProject;
import com.qts.model.UserProjectRoles;

public class RoleDAOImpl2 extends BaseDAOHibernateImpl implements RoleDAO{

	public static RoleDAO roleDAO = null;
	public Session session = null;

	private RoleDAOImpl2() {

	}

	public static RoleDAO getInstance() {
		if (roleDAO == null)
			roleDAO = new RoleDAOImpl2();
		return roleDAO;
	}

	@SuppressWarnings("unchecked")
	public List<Roles> listRoles() throws Exception {
		try{
		Criteria projectCriteria;
			session = SessionFactoryUtil.getInstance().getCurrentSession();
			projectCriteria = session.createCriteria(Roles.class);
			projectCriteria.setProjection(Projections.projectionList()
					.add(Projections.property("id"))
					.add(Projections.property("name")));
			if (projectCriteria.list().isEmpty()) {
				throw new RolesException(
						ExceptionCodes.ROLES_LIST_EMPTY_EXCEPTION,
						ExceptionMessages.ROLES_LIST_EMPTY_EXCEPTION);
			}
			return projectCriteria.list();
		}
		finally{
			session.close();
		}
	}


	@Override
	public RoleBean listUserRoles(RoleBean roleBean,
			List<UserProjectRoles> listUserProjectRoles) throws Exception {
		try{
			Set<Long> roleIds = new LinkedHashSet<Long>();
			for (UserProjectRoles upr : listUserProjectRoles) {
				roleIds.add(upr.getRoleId());
			}
			roleBean.setRoleIds(roleIds);
		return roleBean;
		}
		finally{
			session.close();
		}
	}

	@Override
	public RoleBean allocateRole(RoleBean roleBean, UserProject userProject)
			throws Exception {
		validateRoleId(roleBean.getRoleIds());
		for (Long roleId : roleBean.getRoleIds()) {
			if (checkRoleAlreadyExists(userProject.getId()))
				throw new RolesException(ExceptionCodes.ROLE_ID_EXISTS,
						ExceptionMessages.ROLE_ID_EXISTS);
			UserProjectRoles userProjectRoles = new UserProjectRoles();
			userProjectRoles.setUserProjectId(userProject.getId());
			userProjectRoles.setRoleId(roleId);
			saveObject(userProjectRoles);
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public boolean validateRoleId(Set<Long> roleIds) throws Exception {
		if (SessionFactoryUtil.getCurrentSession()
				.createCriteria(Roles.class).setProjection(Projections.property("id"))
				.list().containsAll(roleIds))
				return true;
			throw new RolesException(ExceptionCodes.INVALID_ROLE_ID,
					ExceptionMessages.INVALID_ROLE_ID);
	}

	@Override
	public boolean deleteUserRole(UserProject userProject) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RoleBean deallocateRole(RoleBean rolebean, UserProject userProject)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
