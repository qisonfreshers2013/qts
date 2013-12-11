package com.qts.persistence.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.RolesException;
import com.qts.model.UserProject;
import com.qts.model.UserProjectRoles;

public class UserProjectsRolesDAOImpl implements UserProjectsRolesDAO{
	private static UserProjectsRolesDAO userProjectsRolesDAO=null;
	private static Session session=null;
	public static UserProjectsRolesDAO getInstance() {
		if(userProjectsRolesDAO==null){
			userProjectsRolesDAO=new UserProjectsRolesDAOImpl();
		}
		return userProjectsRolesDAO;
	}
	public UserProjectRoles getUserProjectRoleByIds(long userProjectId, long roleId) throws RolesException 
	{
//		Criteria criteria=Connection.openSession().createCriteria(UserProjectRoles.class)
//				.add(Restrictions.eq("userProjectId", userProjectId))
//				.add(Restrictions.eq("roleId", roleId))
//					;
		List<UserProjectRoles> UPRList=SessionFactoryUtil.query("from UserProjectRoles where user_project_Id="+userProjectId+" and role_id="+roleId).list();
		if(UPRList.isEmpty())
			return null;
		return (UserProjectRoles)UPRList.listIterator().next();
	}

	@SuppressWarnings("unchecked")
	public List<UserProjectRoles> getUserProjectRolesByUserProjectId(long userProjectId) throws Exception{
//		Connection.query(
//				"from UserProjectRoles where user_Project_Id=" + userProjectId).list();
		Criteria criteria=SessionFactoryUtil.getInstance().getNewSession().createCriteria(UserProjectRoles.class)
				.add(Restrictions.eq("userProjectId", userProjectId));
		if(criteria.list().isEmpty())
			throw new RolesException(ExceptionCodes.NO_ROLES_FOR_THIS_USERPROJECT_ID, ExceptionMessages.NO_ROLES_FOR_THIS_USERPROJECT_ID);
		return criteria.list();

	}
	private boolean checkRoleAlreadyExists(List<Long> userProjectId) {
		return true;
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
