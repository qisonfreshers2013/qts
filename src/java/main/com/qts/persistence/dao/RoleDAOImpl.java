package com.qts.persistence.dao;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
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
 * @author Jagadish 
 * 			
 * 
 */

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
	// lists all the current roles available
	@SuppressWarnings("unchecked")
	public List<Roles> getRoles() throws Exception {
		try {
			Criteria projectCriteria;
			session = getSession();
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
		} catch (RolesException e) {
			e.printStackTrace();
			throw e;
		}
	}

	// validate's the roles with the available roles.If available returns true.
	@SuppressWarnings("unchecked")
	public boolean validateRoleId(long roleIds) throws Exception {
		boolean valid=false;
			session = getSession();
			List<Roles> list = session.createCriteria(Roles.class)
					.setProjection(Projections.property("id")).list();
			if (list.contains(roleIds)) {
				valid=true;
			}
			return valid;

	}

	

	@Override
	public BaseObject getObjectById(long id) throws ObjectNotFoundException {
		session = getSession();
		//session.createQuery("from Roles where id=" + id).list()
		if (session.createCriteria(Roles.class).list().isEmpty())
			throw new ObjectNotFoundException(ExceptionCodes.OBJECT_NOT_FOUND,
					"Invalid role id");
		return (Roles) session.createQuery("from Roles where id=" + id).list()
				.iterator().next();
	}
}
