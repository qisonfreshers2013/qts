package com.qts.persistence.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.ObjectNotFoundException;
import com.qts.exception.RolesException;
import com.qts.model.BaseObject;
import com.qts.model.Roles;
import com.qts.model.UserProject;
import com.qts.model.UserProjectRoles;

public class UserProjectsRolesDAOImpl extends BaseDAOImpl implements UserProjectsRolesDAO {
	private static UserProjectsRolesDAO userProjectsRolesDAO = null;
	private static Session session = null;

	public static UserProjectsRolesDAO getInstance() {
		if (userProjectsRolesDAO == null) {
			userProjectsRolesDAO = new UserProjectsRolesDAOImpl();
		}
		return userProjectsRolesDAO;
	}

	@SuppressWarnings("unchecked")
	public UserProjectRoles getUserProjectRoleByIds(long userProjectId,
			long roleId) throws RolesException {
			session = getSession();
			List<UserProjectRoles> listUserProjectRoles = session
					.createQuery(
							"from UserProjectRoles where user_project_Id="
									+ userProjectId + " and role_id=" + roleId)
					.list();
			return (UserProjectRoles) listUserProjectRoles.listIterator()
					.next();
	}

	@SuppressWarnings("unchecked")
	public List<UserProjectRoles> getUserProjectRolesByUserProjectId(
			long userProjectId) throws Exception {
			session = getSession();
			Criteria criteria = session.createCriteria(UserProjectRoles.class)
					.add(Restrictions.eq("userProjectId", userProjectId));
			if (criteria.list().isEmpty())
				throw new RolesException(
						ExceptionCodes.NO_ROLES_FOR_THIS_USERPROJECT_ID,
						ExceptionMessages.NO_ROLES_FOR_THIS_USERPROJECT_ID);
			return criteria.list();

	}

	public boolean deletUserProjectRoleByUserProjectId(UserProject userProject)
			throws Exception {
		session = getSession();
		try {
			UserProjectRoles userProjectRoles = (UserProjectRoles) session
					.createCriteria(UserProjectRoles.class)
					.add(Restrictions.eq("userProjectId", userProject.getId()))
					.uniqueResult();
			session.delete(userProjectRoles);
			return true;
		} catch (IllegalArgumentException e) {
		}
		return false;
	}

	@Override
	public BaseObject getObjectById(long id) throws ObjectNotFoundException {

		session = getSession();
		//SessionFactoryUtil.getNewTransaction();
		if (session.createQuery("from UserProjectRoles where id=" + id).list()
				.isEmpty())
			throw new ObjectNotFoundException(
					ExceptionCodes.OBJECT_NOT_FOUND, "Invalid role id");
		return (Roles) session.createQuery("from UserProjectRoles where id=" + id)
				.list().iterator().next();
	}
}
