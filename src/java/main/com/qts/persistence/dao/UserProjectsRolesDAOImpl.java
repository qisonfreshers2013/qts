package com.qts.persistence.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.ObjectNotFoundException;
import com.qts.exception.RolesException;
import com.qts.model.BaseObject;
import com.qts.model.RoleBean;
import com.qts.model.Roles;
import com.qts.model.UserProject;
import com.qts.model.UserProjectsRoles;

/**
 * 
 * @author Jagadish Implementation for allocating,deallocating roles and listing
 *         all the roles,listing all the roles of a particular roles
 * 
 */
public class UserProjectsRolesDAOImpl extends BaseDAOImpl implements
		UserProjectsRolesDAO {
	private static UserProjectsRolesDAO userProjectsRolesDAO = null;
	private static Session session = null;

	public static UserProjectsRolesDAO getInstance() {
		if (userProjectsRolesDAO == null) {
			userProjectsRolesDAO = new UserProjectsRolesDAOImpl();
		}
		return userProjectsRolesDAO;
	}

	@SuppressWarnings("unchecked")
	public UserProjectsRoles getUserProjectsRolesByUserProjectAndRole(
			long userProjectId, long roleId) throws RolesException {
		session = getSession();
		List<UserProjectsRoles> listUserProjectsRoles = session.createQuery(
				"from UserProjectsRoles where user_project_Id=" + userProjectId
						+ " and role_id=" + roleId).list();
		if (listUserProjectsRoles.isEmpty())
			return null;
		return (UserProjectsRoles) listUserProjectsRoles.listIterator().next();
	}

	@SuppressWarnings("unchecked")
	public List<UserProjectsRoles> getUserProjectsRolesByUserProject(
			long userProjectId) throws Exception {
		try {
			session = getSession();
			Criteria criteria = session.createCriteria(UserProjectsRoles.class)
					.add(Restrictions.eq("userProjectId", userProjectId));
			if (criteria.list().isEmpty())
				throw new RolesException(
						ExceptionCodes.NO_ROLES_FOR_THIS_USERPROJECT_ID,
						ExceptionMessages.NO_ROLES_FOR_THIS_USERPROJECT_ID);
			return criteria.list();

		} catch (RolesException e) {
			throw e;
		}
	}

	@Override
	public RoleBean getUserRoles(RoleBean roleBean,
			List<UserProjectsRoles> listUserProjectsRoles) throws Exception {
		Set<Long> roleIds = new HashSet<Long>();
		for (UserProjectsRoles upr : listUserProjectsRoles) {
			roleIds.add(upr.getRoleId());
		}
		roleBean.setRoleIds(roleIds);
		return roleBean;
	}

	@Override
	public RoleBean allocateRoles(RoleBean roleBean, UserProject userProject)
			throws Exception {

		for (Long roleId : roleBean.getRoleIds()) {

			UserProjectsRoles userProjectsRoles = new UserProjectsRoles();
			userProjectsRoles.setUserProjectId(userProject.getId());
			userProjectsRoles.setRoleId(roleId);
			saveObject(userProjectsRoles);
		}
		Set<Long> allRoles = new HashSet<Long>();
		try {
			for (UserProjectsRoles userProjectsRoles : getUserProjectsRolesByUserProject(userProject
					.getId())) {

				allRoles.add(userProjectsRoles.getRoleId());
			}
		} catch (RolesException e) {
			// roleBean.setRoleIds(new HashSet<Long>());
			return roleBean;
		}
		roleBean.setRoleIds(allRoles);
		return roleBean;
	}

	@Override
	public RoleBean deallocateRoles(RoleBean roleBean, UserProject userProject)
			throws Exception {
		try {
			session = getSession();
			for (Long roleId : roleBean.getRoleIds()) {
				Criteria criteria = session
						.createCriteria(UserProjectsRoles.class);
				criteria.add(Restrictions.eq("userProjectId",
						userProject.getId()));
				criteria.add(Restrictions.eq("roleId", roleId));
				if (!criteria.list().isEmpty())
					session.delete(criteria.list().listIterator().next());
			}
			Set<Long> allRoles = new HashSet<Long>();
			for (UserProjectsRoles userProjectsRoles : getUserProjectsRolesByUserProject(userProject
					.getId())) {
				allRoles.add(userProjectsRoles.getRoleId());
			}
			roleBean.setRoleIds(allRoles);
			return roleBean;
		} catch (RolesException e) {
			// roleBean.setRoleIds(new HashSet<Long>());
			return roleBean;
		}

	}

	@Override
	public BaseObject getObjectById(long id) throws ObjectNotFoundException {

		session = getSession();
		Criteria criteria = session.createCriteria(UserProjectsRoles.class);
		criteria.add(Restrictions.eq("roleId", id));
		if (criteria.list().isEmpty())
			throw new ObjectNotFoundException(ExceptionCodes.OBJECT_NOT_FOUND,
					"Invalid role id");
		return (Roles) criteria.list().listIterator().next();
	}

	@Override
	public boolean deleteUserProjectsRolesByUserProject(UserProject userProject) {
		session = getSession();
		boolean deleted = false;
		Query query = session
				.createQuery("delete UserProjectsRoles where user_project_id = :userProjectId");
		query.setParameter("userProjectId", userProject.getId());
		int count = query.executeUpdate();
		if (count > 0)
			deleted = true;
		return deleted;
	}
}
