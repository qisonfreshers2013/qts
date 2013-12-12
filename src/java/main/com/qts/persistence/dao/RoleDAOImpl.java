package com.qts.persistence.dao;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;

import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.ObjectNotFoundException;
import com.qts.exception.RolesException;
import com.qts.model.BaseObject;
import com.qts.model.RoleBean;
import com.qts.model.Roles;
import com.qts.model.UserProject;
import com.qts.model.UserProjectRoles;

public class RoleDAOImpl extends BaseDAOImpl implements RoleDAO {

	public static RoleDAO roleDAO = null;
	public Session session = null;
	private RoleDAOImpl() {

	}

	public static RoleDAO getInstance() {
		if (roleDAO == null)
			roleDAO = new RoleDAOImpl();
		return roleDAO;
	}

	@SuppressWarnings("unchecked")
	public List<Roles> listRoles() throws Exception {
		try {
			Criteria projectCriteria;
			session = getSession();
//			session = SessionFactoryUtil.getNewSession();
//			SessionFactoryUtil.getNewTransaction(transaction);
			//SessionFactoryUtil.getNewTransaction();
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
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public RoleBean listUserRoles(RoleBean roleBean,
			List<UserProjectRoles> listUserProjectRoles) throws Exception {
		Set<Long> roleIds = new LinkedHashSet<Long>();
		for (UserProjectRoles upr : listUserProjectRoles) {
			roleIds.add(upr.getRoleId());
		}
		roleBean.setRoleIds(roleIds);
		return roleBean;
	}

	@Override
	public RoleBean allocateRole(RoleBean roleBean, UserProject userProject)
			throws Exception {
		validateRoleId(roleBean.getRoleIds());
		// passUserIdProjectId(roleBean);
		for (Long roleId : roleBean.getRoleIds()) {

			UserProjectRoles userProjectRoles = new UserProjectRoles();
			userProjectRoles.setUserProjectId(userProject.getId());
			userProjectRoles.setRoleId(roleId);
			saveObject(userProjectRoles);
		}
		return roleBean;
	}

	// public RoleBean passUserIdProjectId(RoleBean roleBean) {
	// if (roleBean.getRoleIds().contains(new Long(2)))
	// return roleBean;
	// return null;
	// }

	@SuppressWarnings("unchecked")
	public boolean validateRoleId(Set<Long> roleIds) throws Exception {
		try {
			session = getSession();
			//SessionFactoryUtil.getNewTransaction(transaction);
			List<Roles> list = session.createCriteria(Roles.class)
					.setProjection(Projections.property("id")).list();
			// session.getTransaction().commit();
			if (list.containsAll(roleIds)) {
				return true;
			}
			throw new RolesException(ExceptionCodes.INVALID_ROLE_ID,
					ExceptionMessages.INVALID_ROLE_ID);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public RoleBean deallocateRole(RoleBean roleBean, UserProject userProject)
			throws Exception {
		validateRoleId(roleBean.getRoleIds());
		try {
			session = getSession();
			//session = SessionFactoryUtil.getNewSession();
			//transaction=SessionFactoryUtil.getNewTransaction(transaction);
			//SessionFactoryUtil.getNewTransaction();
			for (Long roleId : roleBean.getRoleIds()) {
				Query query = session
						.createQuery("delete from UserProjectRoles where user_project_id="
								+ userProject.getId()
								+ " and role_id="
								+ roleId);
				query.executeUpdate();
			}
			return roleBean;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public BaseObject getObjectById(long id) throws ObjectNotFoundException {
		session = getSession();
//			session = SessionFactoryUtil.getNewSession();
//			SessionFactoryUtil.getNewTransaction(transaction);
			if (session.createQuery("from Roles where id=" + id).list()
					.isEmpty())
				throw new ObjectNotFoundException(
						ExceptionCodes.OBJECT_NOT_FOUND, "Invalid role id");
			return (Roles) session.createQuery("from Roles where id=" + id)
					.list().iterator().next();
	}
}
