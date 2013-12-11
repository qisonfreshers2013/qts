package com.qts.persistence.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.RolesException;
<<<<<<< HEAD
<<<<<<< HEAD
=======
import com.qts.model.BaseObject;
import com.qts.model.Roles;
>>>>>>> 7719fa5f39d1939b4b46fc46756ffa075dd00157
import com.qts.model.UserProject;
=======
>>>>>>> aa6fb43f09ad3c0280514b8e976f1af9f568cf71
import com.qts.model.UserProjectRoles;
/**
 * 
 * @author Jagadish
 *
 */

<<<<<<< HEAD
public class UserProjectsRolesDAOImpl extends BaseDAOImpl implements
		UserProjectsRolesDAO {
	private static UserProjectsRolesDAO userProjectsRolesDAO = null;
	private static Session session = null;

=======
public class UserProjectsRolesDAOImpl implements UserProjectsRolesDAO{
	private static UserProjectsRolesDAO userProjectsRolesDAO=null;
	private static Session session=null;
>>>>>>> 1d587748b60786fcc68b6d96e67c4674b59bea17
	public static UserProjectsRolesDAO getInstance() {
		if(userProjectsRolesDAO==null){
			userProjectsRolesDAO=new UserProjectsRolesDAOImpl();
		}
		return userProjectsRolesDAO;
	}
<<<<<<< HEAD

	@SuppressWarnings("unchecked")
	public UserProjectRoles getUserProjectRoleByIds(long userProjectId,
			long roleId) throws RolesException {
		session = getSession();
		List<UserProjectRoles> listUserProjectRoles = session.createQuery(
				"from UserProjectRoles where user_project_Id=" + userProjectId
						+ " and role_id=" + roleId).list();
		return (UserProjectRoles) listUserProjectRoles.listIterator().next();
=======
	public UserProjectRoles getUserProjectRoleByIds(long userProjectId, long roleId) throws RolesException 
	{
//		Criteria criteria=Connection.openSession().createCriteria(UserProjectRoles.class)
//				.add(Restrictions.eq("userProjectId", userProjectId))
//				.add(Restrictions.eq("roleId", roleId))
//					;
		List<UserProjectRoles> UPRList=SessionFactoryUtil.getInstance().getNewSession().createQuery("from UserProjectRoles where user_project_Id="+userProjectId+" and role_id="+roleId).list();
		if(UPRList.isEmpty())
			return null;
		return (UserProjectRoles)UPRList.listIterator().next();
>>>>>>> 1d587748b60786fcc68b6d96e67c4674b59bea17
	}

	@SuppressWarnings("unchecked")
<<<<<<< HEAD
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
		session = getSession();
		Criteria criteria = session.createCriteria(UserProjectRoles.class).add(
				Restrictions.eq("userProjectId", userProjectId));
		if (criteria.list().isEmpty())
			throw new RolesException(
					ExceptionCodes.NO_ROLES_FOR_THIS_USERPROJECT_ID,
					ExceptionMessages.NO_ROLES_FOR_THIS_USERPROJECT_ID);
		return criteria.list();

	}
>>>>>>> 7719fa5f39d1939b4b46fc46756ffa075dd00157
=======
	public List<UserProjectRoles> getUserProjectRolesByUserProjectId(long userProjectId) throws Exception{
//		Connection.query(
//				"from UserProjectRoles where user_Project_Id=" + userProjectId).list();
		try{
		 session = SessionFactoryUtil.getInstance().getNewSession();
		   session.beginTransaction();
		Criteria criteria=session.createCriteria(UserProjectRoles.class)
				.add(Restrictions.eq("userProjectId", userProjectId));
		if(criteria.list().isEmpty())
			return null;
			//throw new RolesException(ExceptionCodes.NO_ROLES_FOR_THIS_USERPROJECT_ID, ExceptionMessages.NO_ROLES_FOR_THIS_USERPROJECT_ID);
		return criteria.list();
		}finally {
			   session.close();
		  }
>>>>>>> aa6fb43f09ad3c0280514b8e976f1af9f568cf71

<<<<<<< HEAD
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
=======
>>>>>>> 1d587748b60786fcc68b6d96e67c4674b59bea17
	}
<<<<<<< HEAD
<<<<<<< HEAD
	private boolean checkRoleAlreadyExists(List<Long> userProjectId) {
		return true;
=======

	@Override
	public BaseObject getObjectById(long id) throws ObjectNotFoundException {

		session = getSession();
		if (session.createQuery("from UserProjectRoles where id=" + id).list()
				.isEmpty())
<<<<<<< HEAD
			throw new ObjectNotFoundException(ExceptionCodes.OBJECT_NOT_FOUND,
					"Invalid role id");
		return (Roles) session
				.createQuery("from UserProjectRoles where id=" + id).list()
				.iterator().next();
=======
			throw new ObjectNotFoundException(
					ExceptionCodes.OBJECT_NOT_FOUND, "Invalid role id");
		return (Roles) session.createQuery("from UserProjectRoles where id=" + id)
				.list().iterator().next();
>>>>>>> 7719fa5f39d1939b4b46fc46756ffa075dd00157
=======
	private boolean checkRoleAlreadyExists(List<Long> userProjectId) {
		return true;
>>>>>>> aa6fb43f09ad3c0280514b8e976f1af9f568cf71
>>>>>>> 1d587748b60786fcc68b6d96e67c4674b59bea17
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
