package com.qts.persistence.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import com.qts.model.LoginBean;
import com.qts.model.User;
import com.qts.model.UserBean;

public class UserDAOImpl extends BaseDAOImpl implements UserDAO {

	private static UserDAO INSTANCE = null;

	private UserDAOImpl() {

	}

	public static UserDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new UserDAOImpl();
		}
		return INSTANCE;
	}

	@SuppressWarnings("unchecked")
	public List<User> getListOfUsersObjects() {
		Session session = DAOConnection.openSession();
		List<User> list = session.createQuery("from User").list();
		DAOConnection.closeSession(session);
		return list;
	}

	public List<User> addUser(UserBean userBean) {
		Session session = DAOConnection.openSession();
		return null;// ----

	}

	// ----
	public User getUserByUserId(String userId) {
		if (userId == null)
			throw new NullPointerException();
		Session session = DAOConnection.openSession();
		// Query query = session.createQuery(arg0)
		List<User> list = session.createQuery(
				"from User where userId = " + userId).list();
		User user = null;
		// for (Iterator<User> iterator = list.iterator(); iterator.hasNext();)
		// {
		// user = (User) iterator.next();
		// }
		return list.iterator().next();
	}

	// -----
	public User getUserByEmail(String email) {
		if (email == null)
			throw new NullPointerException();
		Session session = DAOConnection.openSession();
		// Query query = session.createQuery(arg0)
		List<User> list = session.createQuery(
				"from User where emailId = " + email).list();
		User user = null;
		// for (Iterator<User> iterator = list.iterator(); iterator.hasNext();)
		// {
		// user = (User) iterator.next();
		// }
		return list.iterator().next();
	}

	public boolean deleteUser(long id) {
		boolean isDeleted = false;
		Query query = null;
		Session session = null;
		try {
			session = DAOConnection.openSession();
			query = session.createQuery("update user set isDeleted = ");
		} catch (HibernateException e) {

		}

		return isDeleted;
	}

	public String getUserName(long id) {
		Session session = null;
		session = DAOConnection.openSession();
		Criteria searchUserCriteria = session.createCriteria(User.class);
		searchUserCriteria.add(Restrictions.eq("id", id));
		List<User> list = searchUserCriteria.list(); 
//		List<User> list = session.createQuery("from user where id = " + id)
//				.list();
		return list.iterator().next().getFirstName();
	}

	@SuppressWarnings("unchecked")
	public List<User> searchUser(UserBean bean) {
		Session session = DAOConnection.openSession();
		Criteria searchUserCriteria = session.createCriteria(User.class);
		if (null != bean) {
			String nickName = bean.getNickName();
			String email = bean.getEmail();
			String employeeId = bean.getEmployeeId();
			String designation = bean.getDesignation();

			if (nickName != null && nickName.trim().length() > 0) {
				searchUserCriteria.add(Restrictions.eq("nickName", nickName));

			}
			if (email != null && email.trim().length() > 0) {
				searchUserCriteria.add(Restrictions.eq("email", email));

			}
			if (designation != null && designation.trim().length() > 0) {
				searchUserCriteria.add(Restrictions.eq("designation",
						designation));

			}
			if (employeeId != null && employeeId.trim().length() > 0) {
				searchUserCriteria.add(Restrictions
						.eq("employeeId", employeeId));
			}
		}
		List<User> list = searchUserCriteria.list();
		return list;
		// Set<User> set = new HashSet<User>();
		// List<User> list = new ArrayList<User>();
		//
		// List<User> listNickName = session.createQuery(
		// "from User where nickName = " + bean.getNickName()).list();
		//
		// set.addAll(listNickName);
		// List<User> listEmail = session.createQuery(
		// "from User where email = " + bean.getEmail()).list();
		// set.addAll(listEmail);
		// List<User> listEmployeeId = session.createQuery(
		// "from User where employeeId = " + bean.getEmployeeId()).list();
		// set.addAll(listEmployeeId);
		// List<User> listDesignation = session.createQuery(
		// "from User where designation = " + bean.getDesignation())
		// .list();
		// set.addAll(listDesignation);
		// DAOConnection.closeSession(session);
		// list.addAll(set);
		// return list;

	}

	@Override
	public List<User> getUserLogin(LoginBean bean) {
		Session session = DAOConnection.openSession();
		Criteria searchUserCriteria = session.createCriteria(User.class);
		String email = bean.getEmail();
		String password = bean.getPassword();
		searchUserCriteria.add(Restrictions.eq("email", email));
		searchUserCriteria.add(Restrictions.eq("password", password));
		@SuppressWarnings("unchecked")
		List<User> list = searchUserCriteria.list();
		return list;
	}

	@Override
	public long addUser(UserBean bean, long id, long cts, long mts,
			String createdBy, String modifiedBy, boolean isDeleted,
			long photoFileId) {
		long userId = 0;
		boolean gender = bean.getGender().equals("male")?true:false;		
		Session session = DAOConnection.openSession();
		Transaction transaction = session.beginTransaction();
		User user = new User(bean.getEmail(),
				bean.getPassword(),
				bean.getEmployeeId(), 
				bean.getFirstName(), 
				bean.getLastName(), 
				bean.getNickName(),
				bean.getLocation(), 
				gender,
				bean.getDesignation(),
				cts,
				mts, 
				createdBy, 
				modifiedBy,
				isDeleted, 
				bean.getUserId(),
				photoFileId);		 
		 userId = (Integer)session.save(user);
		 transaction.commit();
		 		 session.close();
		return userId;

		
	}

}
