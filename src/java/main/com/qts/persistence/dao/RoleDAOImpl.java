package com.qts.persistence.dao;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.ObjectNotFoundException;
import com.qts.exception.RolesException;
import com.qts.handler.UserProjectHandler;
import com.qts.model.BaseObject;
import com.qts.model.RoleBean;
import com.qts.model.Roles;
import com.qts.model.UserProject;
import com.qts.model.UserProjectRoles;

/**
 * 
 * @author Jagadish
 * 
 */
public class RoleDAOImpl extends BaseDAOHibernateImpl implements RoleDAO {
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

	public RoleBean listUserRoles(RoleBean roleBean,List<UserProjectRoles> listUserProjectRoles)
			throws Exception {
//			List<UserProjectRoles> listUserProjectRoles = DAOFactory
//					.getUserProjectsRolesDAOInstance()
//					.getUserProjectRolesByUserProjectId(userProject.getId());
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

	public RoleBean allocateRole(RoleBean roleBean,UserProject userProject) throws Exception {	
		if (roleBean.getRoleIds().contains(new Long(2))) {
				validateApprover(userProject.getProjectId());
				passUserIdProjectId(roleBean);
			}
			for (Long roleId : roleBean.getRoleIds()) {
				validateRoleId(roleId);
				if (DAOFactory.getUserProjectsRolesDAOInstance()
						.getUserProjectRoleByIds(userProject.getId(), roleId) != null)
					throw new RolesException(ExceptionCodes.ROLE_ID_EXISTS,
							ExceptionMessages.ROLE_ID_EXISTS);
				UserProjectRoles userProjectRoles = new UserProjectRoles();
				userProjectRoles.setUserProjectId(userProject.getId());
				userProjectRoles.setRoleId(roleId);
				saveObject(userProjectRoles);
			}
		return roleBean;
	}

	public RoleBean passUserIdProjectId(RoleBean roleBean) throws Exception {
//			DAOFactory.getUserProjectDAOImplInstance().updateReportingUserId(
//					roleBean.getUserId(), roleBean.getProjectId());
		return roleBean;
	}

	public boolean deleteUserRole(UserProject userProject) throws Exception {
		try{
			session = SessionFactoryUtil.getInstance().getCurrentSession();
			session.beginTransaction();
			List<UserProjectRoles> listUPR = DAOFactory
					.getUserProjectsRolesDAOInstance()
					.getUserProjectRolesByUserProjectId(userProject.getId());
			for (Iterator<UserProjectRoles> iterator = listUPR.iterator(); iterator 
					.hasNext();) {
				UserProjectRoles userProjectRoles = (UserProjectRoles) iterator
						.next();
				session.delete(session.get(UserProjectRoles.class,
						userProjectRoles.getId()));
			}
			 
		return true;
	}
	finally{
		session.close();
	}
	}

	public RoleBean deallocateRole(RoleBean roleBean, UserProject userProject)
			throws Exception {
			if (deleteUserRole(userProject))
				allocateRole(roleBean,userProject);
			return roleBean;
	}

	@SuppressWarnings("unchecked")
	public boolean validateApprover(long id) throws Exception {
			List<UserProjectRoles> listProjectRoles = SessionFactoryUtil.query(
					"from UserProjectRoles where role_Id=2").list();
			for (UserProjectRoles upr : listProjectRoles) {
				if (((UserProject) UserProjectHandler.getInstance()
					.getObjectById(upr.getUserProjectId())).getProjectId() == id)
					throw new RolesException(ExceptionCodes.ONLY_ONE_APPROVER,
							ExceptionMessages.ONLY_ONE_APPROVER);
			}
		return true;
	}

	public boolean validateRoleId(long roleId) throws Exception {
		if (SessionFactoryUtil.getCurrentSession()
				.createCriteria(Roles.class).setProjection(Projections.property("id"))
				.list().contains(roleId))
				return true;
			throw new RolesException(ExceptionCodes.INVALID_ROLE_ID,
					ExceptionMessages.INVALID_ROLE_ID);
	}

	@Override
	public BaseObject getObjectById(long id) throws ObjectNotFoundException {
		try{
			session = SessionFactoryUtil.getInstance().getCurrentSession();
			if (session.createQuery("from Roles where id=" + id).list()
					.isEmpty())				
				throw new ObjectNotFoundException(
						ExceptionCodes.OBJECT_NOT_FOUND, "Invalid role id");
			return (Roles) session.createQuery("from Roles where id=" + id)
					.list().iterator().next();
	}finally{
		session.close();
	}
	}
}
