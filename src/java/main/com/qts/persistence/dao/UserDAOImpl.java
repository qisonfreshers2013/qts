package com.qts.persistence.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.UserException;
import com.qts.model.ChangePasswordBean;
import com.qts.model.LoginBean;
import com.qts.model.User;
import com.qts.model.UserBean;
import com.qts.service.common.ServiceRequestContextHolder;

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
	// List<User> list = session.createQuery("from User").list();
	// DAOConnection.closeSession(session);
	// return list;
	// }

	/** -- addUser -- */
	public long addUser(User user) throws UserException {
	//
		Session session = null;
		try {
			session = getSession();
			Criteria emailCreateCriteria = session.createCriteria(User.class);					
			emailCreateCriteria.add(Restrictions.eq("email", user.getEmail()));
			List list = emailCreateCriteria.list();
			if (list.size() != 0)
				throw new UserException(ExceptionCodes.DUPLICATE_ENTRY_EMAIL,
						ExceptionMessages.DUPLICATE_ENTRY_EMAIL);
			Criteria employeeIdCreateCriteria = session.createCriteria(User.class);					
			employeeIdCreateCriteria.add(Restrictions.eq("employeeId", user.getEmployeeId()));
			list = employeeIdCreateCriteria.list();
			if (list.size() != 0)
				throw new UserException(ExceptionCodes.DUPLICATE_ENTRY_EMPLOYEE_ID,
						ExceptionMessages.DUPLICATE_ENTRY_EMPLOYEE_ID);
			
			
			session.save(user);
		} catch (ConstraintViolationException cve) {
			cve.printStackTrace();
			throw new UserException(ExceptionCodes.DUPLICATE_ENTRY,
					ExceptionMessages.DUPLICATE_ENTRY);
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new UserException(ExceptionCodes.USER_CAN_NOT_ADDED,
					ExceptionMessages.USER_CAN_NOT_ADDED);
		}
		return user.getId();
	}

	/** -- deleteUser -- */
	public boolean deleteUser(long id) throws UserException {
		boolean isDeleted = false;
		Session session = null;
		try {
			session = getSession();
			Criteria createCriteria = session.createCriteria(User.class);
			createCriteria.add(Restrictions.eq("id", id));
			List list = createCriteria.list();
			if (list.size() == 0)
				throw new UserException(ExceptionCodes.DELETE_INVALID,
						ExceptionMessages.DELETE_INVALID);
			createCriteria.add(Restrictions.eq("isDeleted", false));
			list = createCriteria.list();
			if (list.size() == 0)
				throw new UserException(ExceptionCodes.DELETED_ALREADY,
						ExceptionMessages.DELETED_ALREADY);
			User user = (User) list.get(0);
			user.setIsDeleted(true);
			session.update(user);
			isDeleted = true;
		} catch (HibernateException he) {
			isDeleted = false;
			he.printStackTrace();
			throw new UserException(ExceptionCodes.DELETE_INVALID,
					ExceptionMessages.DELETE_INVALID);
		}
		return isDeleted;
	}

	/** -- getUserName -- */
	public String getUserName(long id) {
		Session session = null;
		session = getSession();
		Criteria userCriteria = session.createCriteria(User.class);
		userCriteria.add(Restrictions.eq("id", id));
		List<User> list = userCriteria.list();
		return list.iterator().next().getFirstName();
	}

	@SuppressWarnings("unchecked")
	public List<User> searchUser(UserBean bean) throws UserException {
		Session session = getSession();
		Criteria searchUserCriteria = session.createCriteria(User.class)
				.addOrder(Order.asc("email"));
		List<User> list = null;
		if (null != bean) {
			String nickName = bean.getNickName();
			String email = bean.getEmail();
			String employeeId = bean.getEmployeeId();
			String designation = bean.getDesignation();
			list = searchUserCriteria.list();
			if (nickName != null && nickName.trim().length() > 0) {
				searchUserCriteria.add(Restrictions.like("nickName", nickName,
						MatchMode.ANYWHERE));
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
		searchUserCriteria.add(Restrictions.eq("isDeleted", false));
		list = searchUserCriteria.list();
		if (list.size() == 0) {
			throw new UserException(ExceptionCodes.SEARCH_RESULTS_NO_MATCH,
					ExceptionMessages.SEARCH_RESULTS_NO_MATCH);
		}
		return list;
	}

	@Override
	public User getLoginUser(LoginBean bean) throws UserException {
		Session session = getSession();
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
		Session session = null;
		User user = null;
		try {
			session = getSession();
			Criteria createCriteria = session.createCriteria(User.class);
			createCriteria.add(Restrictions.eq("id", bean.getId()));
			List listUserById = createCriteria.list();
			if (listUserById.size() == 0)
				throw new UserException(ExceptionCodes.USER_DOESNOT_EXIST,
						ExceptionMessages.USER_DOESNOT_EXIST);
			createCriteria.add(Restrictions.eq("isDeleted", false));
			List list = createCriteria.list();
			if (list.size() == 0)
				throw new UserException(ExceptionCodes.DELETE_INVALID,
						ExceptionMessages.DELETE_INVALID);
			user = (User) list.get(0);
			boolean gender = bean.getGender().equalsIgnoreCase("male") ? true
					: false;

			if (!bean.getNickName().equals(user.getNickName())) {
				user.setNickName(bean.getNickName());
			}
			if (!(bean.getFirstName().equals(user.getFirstName()))) {
				user.setFirstName(bean.getFirstName());
			}
			if (!(bean.getLastName().equals(user.getLastName()))) {
				user.setLastName(bean.getLastName());
			}
			if (!(bean.getLocation().equals(user.getLocation()))) {
				user.setLocation(bean.getLocation());
			}
			if (gender != user.getGender()) {
				user.setGender(gender);
			}
			if (!(bean.getEmail().equals(user.getEmail()))) {
				user.setEmail(bean.getEmail());
			}
			if (!(bean.getEmployeeId().equals(user.getEmployeeId()))) {
				user.setEmployeeId(bean.getEmployeeId());
			}
			if (!(bean.getDesignation().equals(user.getDesignation()))) {
				user.setDesignation(bean.getDesignation());
			}
			if (!(bean.getUserId().equals(user.getUserId()))) {
				user.setUserId(bean.getUserId());
			}
			user.setMts(new Date().getTime());// ---updating mts
			// user.setModifiedBy(ServiceRequestContextHolder.getContext().getUserSessionToken().getNickName());//ServiceRequestContextHolder.getContext().getUserSessionToken().getnickName()
			user.setModifiedBy(getUserName(ServiceRequestContextHolder
					.getContext().getUserSessionToken().getUserId()));
			session.update(user);
		} catch (HibernateException he) {
			he.printStackTrace();
			throw new UserException(ExceptionCodes.DUPLICATE_ENTRY,
					ExceptionMessages.DUPLICATE_ENTRY);
		}
		return user;
	}

	@Override
	public boolean changePassword(ChangePasswordBean bean) throws UserException {
		boolean isChanged = false;
		Session session = null;
		try {
			session = getSession();
			Criteria createCriteria = session.createCriteria(User.class);
			createCriteria.add(Restrictions.eq("id",
					ServiceRequestContextHolder.getContext()
							.getUserSessionToken().getUserId()));
			@SuppressWarnings("rawtypes")
			List list = createCriteria.list();
			createCriteria.add(Restrictions.eq("isDeleted", false));
			list = createCriteria.list();
			if (list.size() == 0)
				throw new UserException(ExceptionCodes.DELETED_ALREADY,
						ExceptionMessages.DELETED_ALREADY);
			User user = (User) list.get(0);
			if (!user.getPassword().equals(bean.getOldPassword())) {
				throw new UserException(ExceptionCodes.OLD_PASSWORD_INVALID,
						ExceptionMessages.OLD_PASSWORD_INVALID);
			}
			user.setPassword(bean.getPassword());
			session.update(user);
			isChanged = true;
		} catch (HibernateException he) {
			isChanged = false;
			he.printStackTrace();
		}
		return isChanged;
	}

	public User getUserById(long id) {
		Session session = getSession();
		List<User> list = null;
		Criteria createCriteria = session.createCriteria(User.class);
		createCriteria.add(Restrictions.eq("id", id));
		createCriteria.add(Restrictions.eq("isDeleted", false));
		list = createCriteria.list();
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public User getUserByEmail(String email) throws UserException {
		Session session = null;
		List<User> list = null;
		Transaction tx = null;
		try {
			session = getSession();
			if (null == session) {
				session = SessionFactoryUtil.getInstance().openSession();
				tx = SessionFactoryUtil.getInstance().beginTransaction(session);
			}
			Criteria createCriteria = session.createCriteria(User.class);
			createCriteria.add(Restrictions.eq("email", email));
			createCriteria.add(Restrictions.eq("isDeleted", false));
			list = createCriteria.list();
			if (list.size() == 0) {
				throw new UserException(ExceptionCodes.USER_DOESNOT_EXIST, ExceptionMessages.USER_DOESNOT_EXIST);
			}

		} finally {

			try {
				if (tx != null) {
					tx.commit();
					if (session.isConnected())
						session.close();
				}
			} catch (HibernateException e) {

				e.printStackTrace();
			}
		}
		return list.iterator().next();

	}
	
	@Override
	public boolean isUserDeleted(long id) {
		Session session = getSession();
		boolean isDeleted = false;

		Criteria userCriteria = session.createCriteria(User.class);
		userCriteria = userCriteria.add(Restrictions.conjunction());
		userCriteria.add(Restrictions.eq("id", id));
		userCriteria.add(Restrictions.eq("isDeleted", false));
		List<User> list = userCriteria.list();
		if (list.isEmpty())
			isDeleted = true;
		return isDeleted;

	}

	@Override
	public User updateLoginUser(UserBean bean) throws UserException {
		Session session = null;
		long id = ServiceRequestContextHolder.getContext()
				.getUserSessionToken().getUserId();
		User user = null;

		session = getSession();
		Criteria createCriteria = session.createCriteria(User.class);
		createCriteria.add(Restrictions.eq("id", id));
		List listUserById = createCriteria.list();
		if (listUserById.size() == 0)
			throw new UserException(ExceptionCodes.USER_DOESNOT_EXIST,
					ExceptionMessages.USER_DOESNOT_EXIST);

		createCriteria.add(Restrictions.eq("isDeleted", false));
		List list = createCriteria.list();
		if (list.size() == 0)
			throw new UserException(ExceptionCodes.DELETE_INVALID,
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
		if (!(bean.getLocation().equals(user.getLocation()))) {
			user.setLocation(bean.getLocation());
		}

		user.setMts(new Date().getTime());// updating mts
		// user.setModifiedBy(ServiceRequestContextHolder.getContext().getUserSessionToken().getNickName());//ServiceRequestContextHolder.getContext().getUserSessionToken().getnickName()

		user.setModifiedBy(getUserName(ServiceRequestContextHolder.getContext()
				.getUserSessionToken().getUserId()));
		session.update(user);

		return user;
	}

	@Override
	public List<User> getUsersOtherThanTheseIds(List<Long> userIds) {
		Session session = getSession();
		Iterator<Long> iterator = userIds.listIterator();
		Criteria userCriteria = session.createCriteria(User.class);
		userCriteria.setProjection(
				Projections.projectionList().
						add(Projections.property("id")).
						add(Projections.property("email")).
						add(Projections.property("employeeId")).
						add(Projections.property("firstName")).
						add(Projections.property("lastName")).
						add(Projections.property("photoFileId")).
						add(Projections.property("nickName")).
						add(Projections.property("userId")).
						add(Projections.property("location")).
						add(Projections.property("designation"))
				);
		userCriteria = userCriteria.add(Restrictions.conjunction());
		while (iterator.hasNext()) {
			userCriteria.add(Restrictions.ne("id", iterator.next()));
		}
		userCriteria.add(Restrictions.ne("isDeleted", true));
		userCriteria.addOrder(Order.asc("email"));
		return userCriteria.list();
	}
	
	
	@Override
	public List<User> getUsersByIds(List<Long> userIds) {
		Session session = getSession();
		Criteria userCriteria = session.createCriteria(User.class);
		userCriteria = userCriteria.add(Restrictions.in("id",userIds));
		userCriteria.add(Restrictions.eq("isDeleted", false));
		userCriteria.addOrder(Order.asc("email"));
		return userCriteria.list();
	}


	@Override
	public List<String> getEmployeeIds() {
		Session session = getSession();
		List<User> list = null;
		List<String> employeeIdList = new ArrayList<String>();
		Criteria createCriteria = session.createCriteria(User.class).addOrder(Order.asc("employeeId"));		
		createCriteria.add(Restrictions.eq("isDeleted",false));
		list = createCriteria.list();
		for(User user:list){
			employeeIdList.add(user.getEmployeeId());
		}
		return employeeIdList;
	}

	

}
