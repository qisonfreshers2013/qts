package com.qts.persistence.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.UserException;
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

	// @SuppressWarnings("unchecked")
	// public List<User> getListOfUsersObjects() {
	// Session session = SessionFactoryUtil.getInstance().getNewSession();
	// List<User> list = session.createQuery("from User").list();
	// DAOConnection.closeSession(session);
	// return list;
	// }

	/* -- addUser -- */
	public long addUser(User user) {
		// long id=0;
		Session session = SessionFactoryUtil.getInstance().getNewSession();
		Transaction transaction = session.beginTransaction();
		session.save(user);
		// long vid = id;
		transaction.commit();
		session.close();
		return user.getId();

	}


	public boolean deleteUser(long id) throws UserException {
		boolean isDeleted = false;
		Session session = null;
		Transaction transaction = null;

		try {
			session = SessionFactoryUtil.getInstance().getNewSession();
			transaction = session.beginTransaction();
			Criteria createCriteria = session.createCriteria(User.class);
			createCriteria.add(Restrictions.eq("id", id));
			List listId = createCriteria.list();
			if (listId.size() == 0)
				throw new UserException(ExceptionCodes.DELETE_INVALID,
						ExceptionMessages.DELETE_INVALID);
			createCriteria.add(Restrictions.eq("isDeleted", false));
			List list = createCriteria.list();
			if (list.size() == 0)
				throw new UserException(ExceptionCodes.DELETED_ALREADY,
						ExceptionMessages.DELETED_ALREADY);
			User user = (User) list.get(0);
			user.setIsDeleted(true);
			session.update(user);
			transaction.commit();
			isDeleted = true;
		} catch (HibernateException he) {
			isDeleted = false;
			if (transaction != null) {
				transaction.rollback();
			}
			he.printStackTrace();
			throw new UserException(ExceptionCodes.DELETE_INVALID,
					ExceptionMessages.DELETE_INVALID);
		} finally {
			session.close();
		}
		return isDeleted;
	}

	public String getUserName(long id) {
		Session session = null;
		session = SessionFactoryUtil.getInstance().getNewSession();
		Criteria searchUserCriteria = session.createCriteria(User.class);
		searchUserCriteria.add(Restrictions.eq("id", id));
		List<User> list = searchUserCriteria.list();
		// List<User> list = session.createQuery("from user where id = " + id)
		// .list();
		return list.iterator().next().getFirstName();
	}

	@SuppressWarnings("unchecked")
	public List<User> searchUser(UserBean bean) {
		Session session = SessionFactoryUtil.getInstance().getNewSession();
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
	public User getUserLogin(LoginBean bean) throws UserException {
		Session session = SessionFactoryUtil.getInstance().getNewSession();
		Criteria searchUserCriteria = session.createCriteria(User.class);
		String email = bean.getEmail();
		String password = bean.getPassword();
		searchUserCriteria.add(Restrictions.eq("email", email));
		searchUserCriteria.add(Restrictions.eq("password", password));
		@SuppressWarnings("unchecked")
		List<User> list = searchUserCriteria.list();
		if (list.isEmpty())
			throw new UserException(
					ExceptionCodes.USER_ID_AND_PASSWORD_INVALID,
					ExceptionMessages.USER_ID_AND_PASSWORD_INVALID);
		return list.get(0);
	}

	@Override
	public User updateUser(UserBean bean) throws UserException {
		// boolean isUpdated = false;
		// Query query = null;
		Session session = null;
		Transaction transaction = null;
		User user = null;
		try {
			session = SessionFactoryUtil.getInstance().getNewSession();
			transaction = session.beginTransaction();
			Criteria createCriteria = session.createCriteria(User.class);
			createCriteria.add(Restrictions.eq("id", bean.getId()));
			 List listUserById = createCriteria.list();
			 if(listUserById.size()==0)
			 throw new
			 UserException(ExceptionCodes.UPDATE_NOT_EXIST_INVALID,ExceptionMessages.UPDATE_NOT_EXIST_INVALID);
			
			createCriteria.add(Restrictions.eq("isDeleted", false));
			List list = createCriteria.list();
			if (list.size() == 0)
				throw new UserException(
						ExceptionCodes.DELETE_INVALID,
						ExceptionMessages.DELETE_INVALID);
			user = (User) list.get(0);

			if (!bean.getNickName().equals(user.getNickName())) {
				user.setNickName(bean.getNickName());
			}
			if (!(bean.getFirstName().equals(user.getFirstName()))) {
				user.setFirstName(bean.getFirstName());
			}
			if (!(bean.getLastName().equals(user.getLastName()))) {
				user.setLastName(bean.getLastName());
			}
			if (!(bean.getLocation().equals(user.getLastName()))) {
				user.setLocation(bean.getLocation());
			}
			// isUpdated = true;

			user.setMts(new Date().getTime());// ---updating mts
			//user.setModifiedBy(ServiceRequestContextHolder.getContext().getUserSessionToken().getNickName());//ServiceRequestContextHolder.getContext().getUserSessionToken().getnickName()
			user.setModifiedBy(user.getNickName());
			//ServiceRequestContextHolder.getContext().getUserSessionToken().getUserId();
			session.update(user);
			transaction.commit();
		} catch (HibernateException he) {
			// isUpdated = false;
			if (transaction != null) {
				transaction.rollback();
			}
			he.printStackTrace();
		} finally {
			session.close();
		}
		return user;
	}

	@Override
	public boolean changePassword(String password, long id)
			throws UserException {

		boolean isChanged = false;
		Session session = null;
		Transaction transaction = null;

		try {
			session = SessionFactoryUtil.getInstance().getNewSession();
			transaction = session.beginTransaction();
			Criteria createCriteria = session.createCriteria(User.class);
			createCriteria.add(Restrictions.eq("id", id));
			@SuppressWarnings("rawtypes")
			List list = createCriteria.list();
			if (list.size() == 0)
				throw new UserException(
						ExceptionCodes.UPDATE_NOT_EXIST_INVALID,
						ExceptionMessages.UPDATE_NOT_EXIST_INVALID);
			createCriteria.add(Restrictions.eq("isDeleted", false));
			list = createCriteria.list();
			if (list.size() == 0)
				throw new UserException(ExceptionCodes.DELETED_ALREADY,
						ExceptionMessages.DELETED_ALREADY);
			User user = (User) list.get(0);
			user.setPassword(password);
			session.update(user);
			transaction.commit();
			isChanged = true;
		} catch (HibernateException he) {
			isChanged = false;
			if (transaction != null) {
				transaction.rollback();
			}
			he.printStackTrace();
		} finally {
			session.close();
		}
		return isChanged;
	}
	
	// ----
	public User getUserByUserId(long userId) throws UserException {
		if (userId == 0)
			throw new UserException(ExceptionCodes.USER_DOESNOT_EXIST,
					ExceptionMessages.USER_DOESNOT_EXIST);
		Session session = SessionFactoryUtil.getInstance().getNewSession();
		List<User> list = session.createQuery(
				"from User where userId = " + userId).list();
		return list.iterator().next();
	}

	// -----
	public User getUserByEmail(String email) throws UserException {
		Session session = SessionFactoryUtil.getInstance().getNewSession();
		Criteria createCriteria = session.createCriteria(User.class);
		createCriteria.add(Restrictions.eq("email", email));
		List<User> list = createCriteria.list();
		if (list.size() == 0) {
			throw new UserException(ExceptionCodes.EMAIL_DOESNOT_EXIST,
					ExceptionMessages.EMAIL_DOESNOT_EXIST);
		}
		createCriteria.add(Restrictions.eq("isDeleted", false));
		list = createCriteria.list();
		if (list.size() == 0) {
			throw new UserException(ExceptionCodes.DELETED_ALREADY,
					ExceptionMessages.DELETED_ALREADY);
		}
		return list.iterator().next();
	}

	public boolean isUserDeleted(long id) throws Exception {
		  Session session=SessionFactoryUtil.getInstance().getNewSession();
		  session.beginTransaction();
		  try{
		   Criteria userCriteria=session.createCriteria(User.class);
		   userCriteria.add(Restrictions.eq("id",id)).
		       add(Restrictions.eq("isDeleted",1));
		   List<User> list=userCriteria.list();
		   if(list.isEmpty())
		    return false;
		   return true;
		  }catch(Exception e){
		   e.printStackTrace();
		   throw e;
		  }
		  
		 }

}

// @Override
// addUserfrrom Bean
// public long addUser(UserBean bean, long id, long cts, long mts,
// String createdBy, String modifiedBy, boolean isDeleted,
// long photoFileId) {
// long userId = 0;
// boolean gender = bean.getGender().equals("male")?true:false;
// Session session = SessionFactoryUtil.getInstance().getNewSession();
// Transaction transaction = session.beginTransaction();
// User user = new User(bean.getEmail(),
// bean.getPassword(),
// bean.getEmployeeId(),
// bean.getFirstName(),
// bean.getLastName(),
// bean.getNickName(),
// bean.getLocation(),
// gender,
// bean.getDesignation(),
// cts,
// mts,
// createdBy,
// modifiedBy,
// isDeleted,
// bean.getUserId(),
// photoFileId);
// userId = (Integer)session.save(user);
// transaction.commit();
// session.close();
// return userId;
//
//
// }

