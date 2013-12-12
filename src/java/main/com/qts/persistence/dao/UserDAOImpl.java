package com.qts.persistence.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
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

	private UserDAOImpl(){
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
		Session session = null;
		
		try{
		session =getSession();		
		saveObject(user);			
		}catch(ConstraintViolationException  cve){
			 cve.printStackTrace();
			 	   	 throw new UserException (ExceptionCodes.DUPLICATE_ENTRY,ExceptionMessages.DUPLICATE_ENTRY);		
		}
		catch (HibernateException he) {
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
			session =getSession();			
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
		session =getSession();
		Criteria searchUserCriteria = session.createCriteria(User.class);
		searchUserCriteria.add(Restrictions.eq("id", id));
		List<User> list = searchUserCriteria.list();	
		session.close();
		return list.iterator().next().getNickName();
	}

	@SuppressWarnings("unchecked")
	public List<User> searchUser(UserBean bean) throws UserException {
		Session session =getSession();		
		Criteria searchUserCriteria = session.createCriteria(User.class);
		List<User> list = null;
		if (null != bean) {
			String nickName = bean.getNickName();
			String email = bean.getEmail();
			String employeeId = bean.getEmployeeId();
			String designation = bean.getDesignation();
			
			list = searchUserCriteria.list();
			if (nickName != null && nickName.trim().length() > 0) {
				searchUserCriteria.add(Restrictions.like("nickName",nickName,MatchMode.ANYWHERE));
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
		searchUserCriteria.add(Restrictions.like("isDeleted",false));
		list = searchUserCriteria.list();
		if(list.size() == 0){			
			throw new UserException(ExceptionCodes.SEARCH_RESULTS_NO_MATCH,ExceptionMessages.SEARCH_RESULTS_NO_MATCH);
		}		
		return list;
		}		
		return list;
	}

	@Override
	public User getUserLogin(LoginBean bean) throws UserException {
		Session session =getSession();
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
			session =getSession();			
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
			boolean gender = bean.getGender().equalsIgnoreCase("male") ? true:false;
			

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
			if(!(bean.getUserId().equals(bean.getUserId()))){
				user.setUserId(bean.getUserId());
			}
				
			

			user.setMts(new Date().getTime());// ---updating mts
			//user.setModifiedBy(ServiceRequestContextHolder.getContext().getUserSessionToken().getNickName());//ServiceRequestContextHolder.getContext().getUserSessionToken().getnickName()
			ServiceRequestContextHolder.getContext().getUserSessionToken().getUserId();
			user.setModifiedBy(getUserName(ServiceRequestContextHolder.getContext().getUserSessionToken().getUserId()));
			session.update(user);			
		} catch (HibernateException he) {			
			he.printStackTrace();
			throw new UserException (ExceptionCodes.DUPLICATE_ENTRY,ExceptionMessages.DUPLICATE_ENTRY);		
			}  
		return user;
	}

	@Override
	public boolean changePassword(ChangePasswordBean bean)
			throws UserException {
		boolean isChanged = false;
		Session session = null;	

		try {
			session =getSession();
			
			Criteria createCriteria = session.createCriteria(User.class);
			createCriteria.add(Restrictions.eq("id", ServiceRequestContextHolder.getContext().getUserSessionToken()
					.getUserId()));
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
			if(user.getPassword() == bean.getOldPassword()){
				throw new UserException(ExceptionCodes.OLD_PASSWORD_INVALID,ExceptionMessages.OLD_PASSWORD_INVALID);
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
	

	public User getUserById(long id) throws UserException {
		Session session =getSession();
		Criteria createCriteria = session.createCriteria(User.class);
		createCriteria.add(Restrictions.eq("id", id));		
		 createCriteria.add(Restrictions.eq("isDeleted",false));
		 List<User> list = createCriteria.list();
		 if (list.size() == 0) {
			 return null;
			}
		return list.get(0);
	}

	
	public User getUserByEmail(String email) throws UserException {
		Session session = null;
		List<User> list = null;
		Transaction tx = null;
		
		try{
		session =getSession();
		if(null==session){
			session = SessionFactoryUtil.getInstance().getCurrentSession();
			tx = SessionFactoryUtil.getInstance().beginTransaction(session);
		}
			
		Criteria createCriteria = session.createCriteria(User.class);
		createCriteria.add(Restrictions.eq("email", email));
   	    createCriteria.add(Restrictions.eq("isDeleted", false));
		list = createCriteria.list();
		if (list.size() == 0) {		
			throw new UserException(ExceptionCodes.DELETED_ALREADY,
					ExceptionMessages.DELETED_ALREADY);
		}
		
		}finally{
			
			try {
				if(tx!=null){
					tx.commit();
				if(session.isConnected())	
					session.close();
				}
			} catch (HibernateException e) {
				
				e.printStackTrace();
			}
		}
		return list.iterator().next();
		
	}

	public boolean isUserDeleted(long id) throws Exception {
		  Session session=getSession();
		  session.beginTransaction();
		  try{
		   Criteria userCriteria=session.createCriteria(User.class);
		   userCriteria.add(Restrictions.eq("id",id)).
		       add(Restrictions.eq("isDeleted",true));
		   List<User> list=userCriteria.list();
		   if(list.isEmpty())
		    return false;
		   return true;
		  }catch(Exception e){
			  session.close();
		   e.printStackTrace();
		   throw e;
		  }		  
		 }
	 public List<User> getUserByIds(List<Long> userIds) {
		  Session session=getSession();
		  try{
		   session.beginTransaction();
		   Iterator<Long> iterator=userIds.listIterator();
		   Criteria userCriteria=session.createCriteria(User.class);
		   userCriteria=userCriteria.add(Restrictions.conjunction());
		   while(iterator.hasNext()){
		    userCriteria.add(Restrictions.ne("id", iterator.next()));
		   }
		   userCriteria.add(Restrictions.ne("isDeleted",true));
		   return userCriteria.list();
		  }catch(Exception e){
		   e.printStackTrace();
		  }
		  return null;
		 }
	 

}

// @Override
// /addUserfrrom Bean
// public long addUser(UserBean bean, long id, long cts, long mts,
// String createdBy, String modifiedBy, boolean isDeleted,
// long photoFileId) {
// long userId = 0;
// boolean gender = bean.getGender().equals("male")?true:false;
// Session session =getSession();
// Transaction 
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
// 
// return userId;
//
//
// }

/*searchUser*/
//Set<User> set = new HashSet<User>();
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

