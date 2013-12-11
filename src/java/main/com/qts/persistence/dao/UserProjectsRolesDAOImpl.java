package com.qts.persistence.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.ObjectNotFoundException;
import com.qts.exception.RolesException;
<<<<<<< HEAD
=======
import com.qts.model.BaseObject;
import com.qts.model.Roles;
>>>>>>> 7719fa5f39d1939b4b46fc46756ffa075dd00157
import com.qts.model.UserProject;
import com.qts.model.UserProjectRoles;

public class UserProjectsRolesDAOImpl extends BaseDAOHibernateImpl implements UserProjectsRolesDAO {
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
		try {
			session = SessionFactoryUtil.getInstance().getNewSession();
			session.beginTransaction();
//			session = SessionFactoryUtil.getInstance().getNewSession();
//			SessionFactoryUtil.getNewTransaction(transaction);
			// Criteria
			// criteria=session.createCriteria(UserProjectRoles.class)
			// .add(Restrictions.eq("userProjectId", userProjectId))
			// .add(Restrictions.eq("roleId", roleId))
			// ;
			List<UserProjectRoles> listUserProjectRoles = session
					.createQuery(
							"from UserProjectRoles where user_project_Id="
									+ userProjectId + " and role_id=" + roleId)
					.list();
			if (listUserProjectRoles.isEmpty())
				return null;
			return (UserProjectRoles) listUserProjectRoles.listIterator()
					.next();
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
<<<<<<< HEAD
	public List<UserProjectRoles> getUserProjectRolesByUserProjectId(long userProjectId) throws Exception{
//		Connection.query(
//				"from UserProjectRoles where user_Project_Id=" + userProjectId).list();
		Criteria criteria=SessionFactoryUtil.getInstance().getNewSession().createCriteria(UserProjectRoles.class)
				.add(Restrictions.eq("userProjectId", userProjectId));
		if(criteria.list().isEmpty())
			throw new RolesException(ExceptionCodes.NO_ROLES_FOR_THIS_USERPROJECT_ID, ExceptionMessages.NO_ROLES_FOR_THIS_USERPROJECT_ID);
		return criteria.list();
=======
	public List<UserProjectRoles> getUserProjectRolesByUserProjectId(
			long userProjectId) throws Exception {
		try {
			session = SessionFactoryUtil.getInstance().getNewSession();
			session.beginTransaction();
//			session = SessionFactoryUtil.getNewSession();
//			SessionFactoryUtil.getNewTransaction(transaction);
			Criteria criteria = session.createCriteria(UserProjectRoles.class)
					.add(Restrictions.eq("userProjectId", userProjectId));
			if (criteria.list().isEmpty())
				throw new RolesException(
						ExceptionCodes.NO_ROLES_FOR_THIS_USERPROJECT_ID,
						ExceptionMessages.NO_ROLES_FOR_THIS_USERPROJECT_ID);
			return criteria.list();
		} finally {
			session.close();
		}
	}
>>>>>>> 7719fa5f39d1939b4b46fc46756ffa075dd00157

	public boolean deletUserProjectRoleByUserProjectId(UserProject userProject)
			throws Exception {
		session = SessionFactoryUtil.getInstance().getNewSession();
		session.beginTransaction();
//		session = SessionFactoryUtil.getNewSession();
		try {
			//SessionFactoryUtil.getNewTransaction(transaction);
			UserProjectRoles userProjectRoles = (UserProjectRoles) session
					.createCriteria(UserProjectRoles.class)
					.add(Restrictions.eq("userProjectId", userProject.getId()))
					.uniqueResult();
			session.delete(userProjectRoles);
			return true;
		} catch (IllegalArgumentException e) {
			return true;
		}finally{
			session.getTransaction().commit();
		}
	}
<<<<<<< HEAD
	private boolean checkRoleAlreadyExists(List<Long> userProjectId) {
		return true;
=======

	@Override
	public BaseObject getObjectById(long id) throws ObjectNotFoundException {
		session = SessionFactoryUtil.getInstance().getNewSession();
		//SessionFactoryUtil.getNewTransaction();
		if (session.createQuery("from UserProjectRoles where id=" + id).list()
				.isEmpty())
			throw new ObjectNotFoundException(
					ExceptionCodes.OBJECT_NOT_FOUND, "Invalid role id");
		return (Roles) session.createQuery("from UserProjectRoles where id=" + id)
				.list().iterator().next();
>>>>>>> 7719fa5f39d1939b4b46fc46756ffa075dd00157
	}
public boolean deletUserProjectRoleByUserProjectId(UserProject userProject)
	   throws Exception {
	  session=SessionFactoryUtil.getInstance().getNewSession();
	  try{
	   session.beginTransaction();
	   UserProjectRoles userProjectRoles = (UserProjectRoles) session.createCriteria(UserProjectRoles.class).add(Restrictions.eq("userProjectId",userProject.getId())).uniqueResult();
	   session.delete(userProjectRoles);
	   session.getTransaction().commit();
	   return true;
	  }catch(IllegalArgumentException e){
	   session.getTransaction().commit();
	   return true;
	  }
	 }
}
