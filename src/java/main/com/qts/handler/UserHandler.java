package com.qts.handler;

//import java.sql.SQLIntegrityConstraintViolationException;
//import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
//import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
//import javax.mail.search.SentDateTerm;

import org.hibernate.HibernateException;
//import org.hibernate.usertype.UserType;

//import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.qts.common.Utils;
import com.qts.common.cache.Cache;
import com.qts.common.cache.CacheManager;
import com.qts.common.cache.CacheRegionType;
import com.qts.exception.BusinessException;
import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.UserException;
import com.qts.model.LoginBean;
import com.qts.model.Project;
import com.qts.model.SearchUserRecord;
import com.qts.model.SearchUserRecords;
import com.qts.model.User;
import com.qts.model.UserBean;
import com.qts.model.UserSessionToken;
import com.qts.persistence.dao.DAOFactory;
import com.qts.persistence.dao.UserDAO;
//import com.qts.persistence.dao.UserDAOImpl;
import com.qts.service.common.ServiceRequestContextHolder;

public class UserHandler extends AbstractHandler {
	private static UserHandler INSTANCE = null;

	private UserHandler() {
	}

	public static UserHandler getInstance() {
		if (INSTANCE == null)
			INSTANCE = new UserHandler();
		return INSTANCE;
	}

//search user
	
	public SearchUserRecords searchUsers(UserBean bean) throws UserException {

		List<User> list = null;
		SearchUserRecords searchUserRecords = null;
		// validations of the input provided // saperate method // should return
		// true or false
		List<SearchUserRecord> records = null;
		if (bean == null || searchUserValidations(bean)) {
			try {

				// if (bean.getNickName() == null) {
				// bean.setNickName("Not");
				// }
				// if (bean.getEmail() == null) {
				// bean.setEmail("Not");
				// }
				// if (bean.getEmployeeId() == null) {
				// bean.setEmployeeId("Not");
				// }
				// if (bean.getDesignation() == null) {
				// bean.setDesignation("Not");
				// }

				list = DAOFactory.getInstance().getUserDAO().searchUser(bean);
				// List<SearchUserRecord> record = new
				// LinkedList<SearchUserRecord>();
				records = new LinkedList<SearchUserRecord>();
				for (User user : list) {
					// each iteration will create an instance of SearchUser
					// Record
					SearchUserRecord searchUserRecord = new SearchUserRecord(
							user);
					List<Project> projects = new ArrayList<Project>();
					// List<UserProject> userProject = UserProjectHandler.getInstance().getProjectsById(user.getId());
					// long id = userProject.getProjectId();
					
					//
					searchUserRecord.setProjects(projects);
					// after that insert into List of records
					records.add(searchUserRecord);

				}
				searchUserRecords = new SearchUserRecords();
				searchUserRecords.setRecords(records);

			} catch (HibernateException he) {
				he.printStackTrace();
				throw he;
			}
		}

		return searchUserRecords;

		// pass tUserBean to DAOImpl

		// pass tUserBean to DAOImpl
		// get the data , return the data using Output Descriptor

	}
	

	


	public long addUser(UserBean bean) throws UserException{
		String userId = null;
		long id = 0;		
		if (isaddUserIsValidated(bean)) {
			UserDAO userDAOImpl = DAOFactory.getInstance().getUserDAO();
			id =  ServiceRequestContextHolder.getContext().getUserSessionToken().getUserId();//id of admin
			Date date = new Date();
			long cts = date.getTime();
			long mts = cts;//default cts = mts
			boolean isDeleted = false;
			long photoFileId = 7;//default value
			String createdBy = userDAOImpl.getUserName(id);
			String modifiedBy = createdBy;
			boolean gender = bean.getGender().equalsIgnoreCase("male") ? true
					: false;
			User user = new User(bean.getEmail(), bean.getPassword(),
					bean.getEmployeeId(), bean.getFirstName(),
					bean.getLastName(), bean.getNickName(), bean.getLocation(),
					gender, bean.getDesignation(), cts, mts, createdBy,
					modifiedBy, isDeleted, bean.getUserId(), photoFileId);
			try {
				id = userDAOImpl.addUser(user);
			}
			
			catch (HibernateException he) {
				he.printStackTrace();				
				throw new UserException(ExceptionCodes.USER_CAN_NOT_ADDED,ExceptionMessages.USER_CAN_NOT_ADDED);
			}
		}
		return id;
	}	
    private static boolean validateEmail(String email)
            throws UserException {
    	boolean isValidated = false;
        if (email == null || email.isEmpty() || email.trim().isEmpty()) {
            throw new UserException(ExceptionCodes.EMAIL_CANNOT_BE_EMPTY,
                    ExceptionMessages.EMAIL_CANNOT_BE_EMPTY);
        }
        boolean isEmailPatternValid = Pattern.compile(Utils.EMAIL_PATTERN)
                .matcher(email).matches();
        isValidated = true;
        if (!isEmailPatternValid) {
        	isValidated = false;
            throw new UserException(ExceptionCodes.EMAIL_FORMAT,
                    ExceptionMessages.EMAIL_FORMAT);
        }
        return isValidated;        
    }

	private boolean validateConfirmPassword(String password,
			String confirmPassword) throws UserException {
		boolean isValidated = true;
		if (password == null) {
			isValidated = false;
			throw new UserException(ExceptionCodes.CONFIRM_PASSWORD_NULL,
					ExceptionMessages.CONFIRM_PASSWORD_NULL);

		}
		if (!password.equals(confirmPassword)) {
			isValidated = false;
			throw new UserException(ExceptionCodes.CONFIRM_PASSWORD_NOT_EQUAL,
					ExceptionMessages.CONFIRM_PASSWORD_NOT_EQUAL);
		}

		return isValidated;
	}

	public boolean deleteUser(UserBean bean) throws UserException {
		boolean isDeleted = false;
		UserDAO userDAOImpl = DAOFactory.getInstance().getUserDAO();
		if(bean == null || bean.toString().trim().isEmpty()){
			throw new UserException(ExceptionCodes.DELETE_ID_ZERO,ExceptionMessages.DELETE_ID_ZERO);
		}
		if(bean.getId()==0){
			throw new UserException(ExceptionCodes.DELETE_ID_ZERO,ExceptionMessages.DELETE_ID_ZERO);
		}
		isDeleted = userDAOImpl.deleteUser(bean.getId());
		
		return isDeleted;
	}

	
	public boolean changePassword(UserBean bean) throws UserException {
		boolean isPasswordChanged = false;
		isPasswordChanged = validatePassword(bean.getPassword());
		isPasswordChanged = validateConfirmPassword(bean.getPassword(),
				bean.getConfirmPassword());
		// if(bean.getConfirmPassword()==null){
		// throw new UserException(ExceptionCodes.CONFIRM_PASSWORD_NOT_EQUAL,
		// ExceptionMessages.CONFIRM_PASSWORD_NOT_EQUAL);
		// }
		// if(!bean.getPassword().equals(bean.getConfirmPassword())){
		// throw new UserException(ExceptionCodes.CONFIRM_PASSWORD_NOT_EQUAL,
		// ExceptionMessages.CONFIRM_PASSWORD_NOT_EQUAL);
		// }
		UserDAO userDAOImpl = DAOFactory.getInstance().getUserDAO();
		isPasswordChanged = userDAOImpl.changePassword(bean.getPassword(),
				bean.getId());
		return isPasswordChanged;
	}

	public boolean forgotPassword(UserBean bean) throws UserException {
		boolean isValidated = false;
		if(bean == null || bean.toString().trim().isEmpty())
			throw new UserException(ExceptionCodes.EMAIL_CANNOT_BE_EMPTY, ExceptionMessages.EMAIL_CANNOT_BE_EMPTY);
		isValidated = validateEmail(bean.getEmail());		
		User user = DAOFactory.getInstance().getUserDAO().getUserByEmail(bean.getEmail());		
	    sentMail(user);
		
		
		return isValidated;
	}

	
	

	// SearchUserRecords
	public User login(LoginBean bean) throws UserException {
		User user = null;
		if (isSearchLoginValidated(bean)) {
			try {
				user = DAOFactory.getInstance().getUserDAO().getUserLogin(bean);
			} catch (HibernateException he) {
				he.printStackTrace();
			}
		}
		return user;
	}

	

	public User updateUser(UserBean bean) throws UserException {
	
		User user = null;
		
		if (isUpdateUserIsValidated(bean)) {
			
			UserDAO userDAOImpl = DAOFactory.getInstance().getUserDAO();
			user = userDAOImpl.updateUser(bean);
		}
		return user;

	}

	public boolean logout(UserBean bean) {
		boolean isLogout = false;
		 String userSessionId = ServiceRequestContextHolder.getContext().getUserSessionToken().getUserSessionId();
		
    

  

     //check if session is valid or not
     Cache cache = CacheManager.getInstance().getCache(CacheRegionType.USER_SESSION_CACHE);
     System.out.println("Cached : "+cache.getValue(userSessionId));
     
      return isLogout;
	}


	public User getUserByUserId(long userId) throws UserException {
		User user = null;
		UserDAO userDAOImpl = DAOFactory.getInstance().getUserDAO();
		user = userDAOImpl.getUserByUserId(userId);
		return user;
	}

	public User getUserByEmail(String email) throws UserException {
		User user = null;
		UserDAO userDAOImpl = DAOFactory.getInstance().getUserDAO();
		user = userDAOImpl.getUserByEmail(email);
		
		return user;
	}
	
	public boolean isUserDeleted(long id) throws Exception{
		  return DAOFactory.getInstance().getUserDAO().isUserDeleted(id);
		  
		 }
  // sent Mail

	private boolean sentMail(User user) throws UserException {
		boolean isSent = false;
		String host="smtp.gmail.com";
		int port=465;
		final String username="anilram295@gmail.com";
		final String password="9848054549";
		String factory="javax.net.ssl.SSLSocketFactory";
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "false");
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.socketFactory.port", port);
		props.put("mail.smtp.socketFactory.class", factory);
		props.put("mail.smtp.socketFactory.fallback","false");
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		session.setDebug(true);
		
		try {
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(username));
			msg.setRecipients(Message.RecipientType.TO,
					user.getEmail());
			msg.setSubject("Password Reset");
			msg.setSentDate(new Date());
			msg.setText(":)\n Your Password is : "+user.getPassword());
			Transport.send(msg); 
			isSent = true;			
		} catch (MessagingException mex) {
			throw new  UserException(ExceptionCodes.SEND_MAIL_FAILED,ExceptionMessages.SEND_MAIL_FAILED);
		}		
		return isSent;		
	}


//validations
	
private boolean searchUserValidations(UserBean bean) throws UserException {
	boolean isValidated = false;

	// String name = bean.getNickName();
	// String designation = bean.getDesignation();
	if (bean.getNickName() != null) {
		try {
			Utils.validateName(bean.getNickName());
			isValidated = true;
		} catch (BusinessException be) {
			throw new UserException(ExceptionCodes.NICKNAME_INVALID,
					ExceptionMessages.NICKNAME_INVALID);
		}
	}
	if (bean.getEmail() != null) {
		//try {
			boolean isEmailPatternValid = Pattern
					.compile(Utils.EMAIL_PATTERN).matcher(bean.getEmail())
					.matches();
			if (!isEmailPatternValid) {					
					throw new UserException(ExceptionCodes.USER_EMAIL_FORMAT,
							ExceptionMessages.USER_EMAIL_FORMAT);					
//				throw new BusinessException(
//						ExceptionCodes.INVALID_EMAIL_PATTERN,
//						ExceptionMessages.INVALID_EMAIL_PATTERN);
			}
			isValidated = true;
		//} catch (BusinessException be) {
		//	throw new UserException(ExceptionCodes.USER_EMAIL_FORMAT,
		//			ExceptionMessages.USER_EMAIL_FORMAT);
		//}
	}
	if (bean.getEmployeeId() != null) {
		try {
			Utils.validateName(bean.getEmployeeId());
			isValidated = true;
		} catch (BusinessException be) {
			throw new UserException(ExceptionCodes.EMP_ID_FORMAT,
					ExceptionMessages.EMP_ID_FORMAT);
		}
	}
	if (bean.getDesignation() != null) {
		//try {
			boolean isDesignationPatternValid = Pattern
					.compile(Utils.DESIGNATION)
					.matcher(bean.getDesignation()).matches();
			if (!isDesignationPatternValid) {
				//throw new BusinessException();
				throw new UserException(
						ExceptionCodes.INVALID_DESIGNATION_PATTERN,
						ExceptionMessages.INVALID_DESIGNATION_PATTERN);
			}
			isValidated = true;
		//} catch (BusinessException be) {
		//	throw new UserException(
			//		ExceptionCodes.INVALID_DESIGNATION_PATTERN,
			//		ExceptionMessages.INVALID_DESIGNATION_PATTERN);
		//}
	}
	return isValidated;
}



//
//
// public List<User> getListOfUsers() {
//
// //StringWriter userInfo = new StringWriter();
//
// // DAOFactory daoFactory = DAOFactory.getInstance();
// UserDAOImpl userDAOImpl = DAOFactory.getUserDAOImpl();
// List<User> list = userDAOImpl.getListOfUsersObjects();
// // Iterator<User1> itr = list.iterator();
//
// // for (User user : list) {
// // try {
// // userInfo.append(user.getPhotoFile().getPath() + "\t");
// // } catch (NullPointerException n) {
// // userInfo.append(null);
// // }
// // userInfo.append(user.getEmail() + "\t" + user.getEmployeeId()
// // + "\t" + user.getDesignation() + "\t");
// // Set<Project> set = user.getProject();
// // for (Project project : set) {
// //
// // userInfo.append(project.getName() + "\n");
// // }
// // }
//
// return list;
// }

//addUserValidations

private boolean isaddUserIsValidated(UserBean bean) throws UserException {
	boolean isValidated = false;
	if (null == bean || bean.toString().trim().length() == 0) {
		throw new UserException(ExceptionCodes.USER_DETAILS_NULL,
				ExceptionMessages.USER_DETAILS_NULL);
	}
	// if(bean.getPassword()==null){
	// isValidated = false;
	// throw new
	// UserException(ExceptionCodes.PASSWORD_NULL,ExceptionMessages.PASSWORD_NULL
	// );
	// }
	// try {
	// Utils.validatePassword(bean.getPassword());
	// isValidated = true;
	// } catch (Exception be) {
	// isValidated = false;
	// throw new UserException(ExceptionCodes.PASSWORD_FORMAT,
	// ExceptionMessages.PASSWORD_FORMAT);
	// }
	isValidated = validateEmail(bean.getEmail());
	isValidated = validatePassword(bean.getPassword());
	isValidated = validateConfirmPassword(bean.getPassword(),
			bean.getConfirmPassword());
	isValidated = validateFirstName(bean.getFirstName());
	isValidated = validateLastName(bean.getLastName());
	if(bean.getNickName()==null){
		bean.setNickName(bean.getLastName());
	}
	isValidated = validateNickName(bean.getNickName());

	// if(bean.getConfirmPassword()==null){
	// isValidated = false;
	// throw new
	// UserException(ExceptionCodes.CONFIRM_PASSWORD_NULL,ExceptionMessages.CONFIRM_PASSWORD_NULL
	// );
	//
	// }
	// if(!bean.getPassword().equals(bean.getConfirmPassword())){
	// isValidated = false;
	// throw new
	// UserException(ExceptionCodes.CONFIRM_PASSWORD_NOT_EQUAL,ExceptionMessages.CONFIRM_PASSWORD_NOT_EQUAL);
	// }

	// if(bean.getFirstName()==null||bean.getFirstName().trim().length()==0){
	// isValidated = false;
	// throw new
	// UserException(ExceptionCodes.FIRST_NAME_SHOULD_NOT_NULL,ExceptionMessages.FIRST_NAME_SHOULD_NOT_NULL);
	// }
	// else{
	// boolean isNamePatternValid = Pattern.compile(Utils.USER_NAME_PATTERN)
	// .matcher(bean.getFirstName()).matches();
	// isValidated = true;
	// if (!isNamePatternValid) {
	// isValidated = false;
	// throw new UserException(ExceptionCodes.FIRST_NAME_INVALID,
	// ExceptionMessages.FIRST_NAME_INVALID);
	// }
	// }
	// if(bean.getLastName()==null||bean.getLastName().trim().length()==0){
	// isValidated = false;
	// throw new
	// UserException(ExceptionCodes.LAST_NAME_SHOULD_NOT_NULL,ExceptionMessages.LAST_NAME_SHOULD_NOT_NULL);
	// }
	// else{
	// boolean isNamePatternValid = Pattern.compile(Utils.USER_NAME_PATTERN)
	// .matcher(bean.getLastName()).matches();
	// isValidated = true;
	// if (!isNamePatternValid) {
	// isValidated = false;
	// throw new UserException(ExceptionCodes.LAST_NAME_INVALID,
	// ExceptionMessages.LAST_NAME_INVALID);
	// }
	// }

	// if(bean.getNickName() != null){
	// isValidated = false;
	// boolean isNamePatternValid = Pattern.compile(Utils.USER_NAME_PATTERN)
	// .matcher(bean.getNickName()).matches();
	// isValidated = true;
	// if (!isNamePatternValid) {
	// isValidated = false;
	// throw new UserException(ExceptionCodes.NICKNAME_INVALID,
	// ExceptionMessages.NICKNAME_INVALID);
	// }
	// }
	if (bean.getGender() == null || bean.getGender().trim().length() == 0) {
		isValidated = false;
		throw new UserException(ExceptionCodes.GENDER_NOT_NULL,
				ExceptionMessages.GENDER_NOT_NULL);
	} else {
		boolean isGenderPatternValid = Pattern.compile(Utils.GENDER)
				.matcher(bean.getGender()).matches();
		isValidated = true;
		if (!isGenderPatternValid) {
			isValidated = false;
			throw new UserException(ExceptionCodes.GENDER_INVALID,
					ExceptionMessages.GENDER_INVALID);
		}
	}
//	if(bean.getEmail() == null || bean.getEmail().trim().length() == 0)
//	{
//		throw new UserException(ExceptionCodes.EMAIL_CANNOT_BE_EMPTY,ExceptionMessages.EMAIL_CANNOT_BE_EMPTY);
//	}
//	try {
//		Utils.validateEmail(bean.getEmail());
//		isValidated = true;
//	} catch (Exception e) {
//		isValidated = false;
//		throw new UserException(ExceptionCodes.EMAIL_FORMAT,
//				ExceptionMessages.EMAIL_FORMAT);
//
//	}
	if (bean.getEmployeeId() == null
			|| bean.getEmployeeId().trim().length() == 0) {
		isValidated = false;
		throw new UserException(ExceptionCodes.EMPLOYEE_ID_NULL,
				ExceptionMessages.EMPLOYEE_ID_NULL);
	} else {
		boolean isEmployeeIdValid = Pattern
				.compile(Utils.EMPLOYEE_ID_PATTERN)
				.matcher(bean.getEmployeeId()).matches();
		isValidated = true;
		
		if (!isEmployeeIdValid) {
			isValidated = false;
			throw new UserException(ExceptionCodes.EMPLOYEE_ID_INVALID,
					ExceptionMessages.EMPLOYEE_ID_INVALID);
		}
	}
	if (bean.getDesignation() != null) {
		boolean isDesignationPatternValid = Pattern
				.compile(Utils.DESIGNATION).matcher(bean.getDesignation())
				.matches();
		isValidated = true;
		if (!isDesignationPatternValid) {
			isValidated = false;
			throw new UserException(
					ExceptionCodes.INVALID_DESIGNATION_PATTERN,
					ExceptionMessages.INVALID_DESIGNATION_PATTERN);
		}
	}
	isValidated = true;       //---
	if (bean.getLocation() != null) {
		boolean isDesignationPatternValid = Pattern
				.compile(Utils.USER_NAME_PATTERN)
				.matcher(bean.getLocation()).matches();
		isValidated = true;
		if (!isDesignationPatternValid) {
			isValidated = false;
			throw new UserException(
					ExceptionCodes.INVALID_DESIGNATION_PATTERN,
					ExceptionMessages.INVALID_DESIGNATION_PATTERN);
		}
	}

	if (bean.getUserId() == null || bean.getUserId().trim().length() == 0
			|| !(bean.getUserId().equalsIgnoreCase(bean.getEmail()))) {       // changes
		isValidated = false;
		throw new UserException(ExceptionCodes.USER_ID_NULL,
				ExceptionMessages.USER_ID_NULL);
	} else {
		boolean isUserIdValid = Pattern.compile(Utils.EMAIL_PATTERN)
				.matcher(bean.getUserId()).matches();
		isValidated = true;
		if (!isUserIdValid) {
			isValidated = false;
			throw new UserException(ExceptionCodes.USER_ID_FORMAT,
					ExceptionMessages.USER_ID_FORMAT);
		}
	}

	return isValidated;
}
//login validations

private boolean isSearchLoginValidated(LoginBean bean) throws UserException {
	boolean isValidated = false;
	// String email = null;
	// String password = null;
	// boolean isValidated;
	if (null == bean) {
		throw new UserException(ExceptionCodes.USER_ID_AND_PASSWORD_NULL,
				ExceptionMessages.USER_ID_AND_PASSWORD_NULL);	}
	isValidated = validateEmail(bean.getEmail());
	isValidated = validatePassword(bean.getPassword());
//	try {
//		Utils.validatePassword(bean.getPassword());
//		isValidated = true;
//	} catch (Exception be) {
//		isValidated = false;
//		throw new UserException(ExceptionCodes.PASSWORD_FORMAT,
//				ExceptionMessages.PASSWORD_FORMAT);
//	}

//	try {
//		Utils.validateEmail(bean.getEmail());
//		isValidated = true;
//	} catch (Exception e) {
//		isValidated = false;
//		throw new UserException(ExceptionCodes.USER_ID_FORMAT,
//				ExceptionMessages.USER_ID_FORMAT);
//
//	}
//	try {
//		Utils.validatePassword(bean.getPassword());
//		isValidated = true;
//	} catch (Exception be) {
//		isValidated = false;
//		throw new UserException(ExceptionCodes.PASSWORD_FORMAT,
//				ExceptionMessages.PASSWORD_FORMAT);
//	}
//
	return isValidated;

}

// update Validations

private boolean isUpdateUserIsValidated(UserBean bean) throws UserException {
	boolean isValidated = false;
	isValidated = validateFirstName(bean.getFirstName());
	isValidated = validateLastName(bean.getLastName());
	isValidated = validateNickName(bean.getNickName());
	isValidated = validateLocation(bean.getLocation());
	if(bean.getNickName()==null){
		bean.setNickName(bean.getLastName());
	}
	isValidated = validateLocation(bean.getLocation());
	return isValidated;
}
private boolean validateNickName(String nickName) throws UserException {

	boolean isValidated = false;
	if (nickName != null) {
		boolean isNamePatternValid = Pattern
				.compile(Utils.USER_NAME_PATTERN).matcher(nickName)
				.matches();
		isValidated = true;
		if (!isNamePatternValid) {
			isValidated = false;
			throw new UserException(ExceptionCodes.NICKNAME_INVALID,
					ExceptionMessages.NICKNAME_INVALID);
		}
	}
	
	return isValidated;
}

private boolean validateLocation(String location) throws UserException {
	boolean isValidated = false;
	if(location!=null){
		boolean isNamePatternValid = Pattern
				.compile(Utils.LOCATION_PATTERN).matcher(location)
				.matches();
		isValidated = true;
		if (!isNamePatternValid) {
			isValidated = false;
			throw new UserException(ExceptionCodes.LOCATION_INVALID,
					ExceptionMessages.LOCATION_INVALID);
		
	}
	}
	return isValidated;
}

private boolean validateLastName(String lastName) throws UserException {
	boolean isValidated = false;
	if (lastName == null || lastName.trim().length() == 0) {
		isValidated = false;
		throw new UserException(ExceptionCodes.LAST_NAME_SHOULD_NOT_NULL,
				ExceptionMessages.LAST_NAME_SHOULD_NOT_NULL);
	} else {
		boolean isNamePatternValid = Pattern
				.compile(Utils.USER_NAME_PATTERN).matcher(lastName)
				.matches();
		isValidated = true;
		if (!isNamePatternValid) {
			isValidated = false;
			throw new UserException(ExceptionCodes.LAST_NAME_INVALID,
					ExceptionMessages.LAST_NAME_INVALID);
		}
	}

	return isValidated;
}

private boolean validateFirstName(String firstName) throws UserException {
	boolean isValidated = false;
	if (firstName == null || firstName.trim().length() == 0) {
		isValidated = false;
		throw new UserException(ExceptionCodes.FIRST_NAME_SHOULD_NOT_NULL,
				ExceptionMessages.FIRST_NAME_SHOULD_NOT_NULL);
	} else {
		boolean isNamePatternValid = Pattern
				.compile(Utils.USER_NAME_PATTERN).matcher(firstName)
				.matches();
		isValidated = true;
		if (!isNamePatternValid) {
			isValidated = false;
			throw new UserException(ExceptionCodes.FIRST_NAME_INVALID,
					ExceptionMessages.FIRST_NAME_INVALID);
		}
	}

	return isValidated;
}

//validate Password 

private boolean validatePassword(String password) throws UserException {
	boolean isValidated = false;
	if (password == null) {
		isValidated = false;
		throw new UserException(ExceptionCodes.PASSWORD_NULL,
				ExceptionMessages.PASSWORD_NULL);
	}
	try {
		Utils.validatePassword(password);
		isValidated = true;
	} catch (Exception be) {
		isValidated = false;
		throw new UserException(ExceptionCodes.PASSWORD_FORMAT,
				ExceptionMessages.PASSWORD_FORMAT);
	}
	return isValidated;
}



}

