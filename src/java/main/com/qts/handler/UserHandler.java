package com.qts.handler;



import java.util.ArrayList;
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

import org.hibernate.HibernateException;

import com.qts.common.Utils;
import com.qts.common.cache.CacheManager;
import com.qts.common.cache.CacheRegionType;
import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.ObjectNotFoundException;
import com.qts.exception.ProjectException;
import com.qts.exception.UserException;
import com.qts.handler.annotations.AuthorizeEntity;
import com.qts.model.ChangePasswordBean;
import com.qts.model.LoginBean;
import com.qts.model.Project;
import com.qts.model.Roles;
import com.qts.model.SearchUserRecord;
import com.qts.model.SearchUserRecords;
import com.qts.model.User;
import com.qts.model.UserBean;
import com.qts.model.UserProject;
import com.qts.persistence.dao.DAOFactory;
import com.qts.persistence.dao.UserDAO;
import com.qts.service.common.ServiceRequestContextHolder;

/**
 * @author AnilRam
 *
 */

public class UserHandler extends AbstractHandler {
	private static UserHandler INSTANCE = null;

	private UserHandler() {
	}

	/**
	 * @return instance of UserHandler
	 */
	public static UserHandler getInstance() {
		if (INSTANCE == null)
			INSTANCE = new UserHandler();
		return INSTANCE;
	}

	// search user

	/**
	 * @param bean
	 * @return SearchUserRecords contains Photofile Id,Email,EmployeeId,Designation
	 * @throws Exception
	 * @throws UserException
	 */
	public SearchUserRecords searchUsers(UserBean bean) throws Exception ,UserException,ObjectNotFoundException{

		List<User> list = null;
		SearchUserRecords searchUserRecords = null;
		// validations of the input provided  separate method  which return true or false
		List<SearchUserRecord> records = null;
		if (bean == null || searchUserValidations(bean) || bean.toString().trim().isEmpty()) {
			try {
				
				list = DAOFactory.getInstance().getUserDAO().searchUser(bean);	// for list of users			
				records = new LinkedList<SearchUserRecord>(); // for storing all User Records of needed fields
				for (User user : list) {	
					 SearchUserRecord searchUserRecord = new SearchUserRecord(user);

					//get project Names associated with particular Users Id
					 List<UserProject> userProjects =
					 UserProjectHandler.getInstance().getUserProjectsByUserId(user.getId());//for getting all userProject Ids
					 List<String> projectNames = new ArrayList<String>();
					 for(UserProject userProject : userProjects){
					 long projectId= userProject.getProjectId();
					 Project project = ProjectHandler.getInstance().getObjectById(projectId); //Getting project by project Id
					 projectNames.add(project.getName());//adding names to project Names list
				      }			
					searchUserRecord.setProjects(projectNames);
					// after that insert into List of records
					records.add(searchUserRecord);
				}
				searchUserRecords = new SearchUserRecords();
				searchUserRecords.setRecords(records);//saving list Of records 
			} catch (HibernateException he) {
				he.printStackTrace();
				throw he;
			}
		}
		return searchUserRecords;
		}
	@AuthorizeEntity(roles = {Roles.ROLE_ADMIN}, entity = "User.java")
	public long addUserAOP(UserBean bean) throws UserException {		
		long id = 0;
		long newId = 0;
		User user = null;
		if (isaddUserIsValidated(bean)) {			
			UserDAO userDAOImpl = DAOFactory.getInstance().getUserDAO();			
			id = ServiceRequestContextHolder.getContext().getUserSessionToken()
					.getUserId();// id of admin
			Date date = new Date();
			long cts = date.getTime();
			long mts = cts;// default cts = mts
			boolean isDeleted = false;
			long photoFileId = 7;// default value
			String createdBy = userDAOImpl.getUserName(id);
			String modifiedBy = createdBy;// default modified By = created By 
			//storing gender by converting String to Boolean
			boolean gender = bean.getGender().equalsIgnoreCase("male") ? true
					: false;
			boolean isAdmin = false;
			user = new User(bean.getEmail(), bean.getPassword(),
					bean.getEmployeeId(), bean.getFirstName(),
					bean.getLastName(), bean.getNickName(), bean.getLocation(),
					gender, bean.getDesignation(), cts, mts, createdBy,
					modifiedBy, isDeleted, bean.getUserId(), photoFileId,isAdmin);			
			newId = userDAOImpl.addUser(user);
		
		 }
	   return newId;
		
	}
/*
 * delete User 
 */
	@AuthorizeEntity(roles = {Roles.ROLE_ADMIN}, entity = "User.java")
	public boolean deleteUserAOP(UserBean bean) throws UserException,ProjectException {   //exception thrown by userProject
		boolean isDeleted = false;
		if (bean == null || bean.toString().trim().isEmpty()) {
			throw new UserException(ExceptionCodes.DELETE_ID_ZERO,
					ExceptionMessages.DELETE_ID_ZERO);
		}
		if (bean.getId() == 0) {
			throw new UserException(ExceptionCodes.DELETE_ID_ZERO,
					ExceptionMessages.DELETE_ID_ZERO);
			
		}
		UserProjectHandler userProjectHandler = UserProjectHandler.getInstance();
		List<UserProject> userProjectList = userProjectHandler.getUserProjectsByUserId(bean.getId());//get user project list for Specified users Id
		//if user is associated with projects 
		if(!(userProjectList.isEmpty())){
			for(UserProject userProject: userProjectList){
				//first delete role of user associated with specified project
				boolean isRoleDeleted = UserProjectsRolesHandler.getInstance().deleteUserProjectsRolesByUserProject(userProject);
				//next deallocate the project for the user
				boolean isProjectDeallocated = userProjectHandler.deAllocateUsersFromProject(userProject);		
			}
		}
		//if user not associated with projects delete user logically
		//	try{
	    //deleteUserTimeEntriesByUserId(long id);
	    //}catch(TimeEntryException){}
		UserDAO userDAOImpl = DAOFactory.getInstance().getUserDAO();		
		isDeleted = userDAOImpl.deleteUser(bean.getId());
		return isDeleted;
	}

	public boolean changePassword(ChangePasswordBean bean) throws UserException {
		boolean isPasswordChanged = false;
		UserDAO userDAOImpl = DAOFactory.getInstance().getUserDAO();
		isPasswordChanged = validatePassword(bean.getOldPassword());
		User user = userDAOImpl.getUserById(ServiceRequestContextHolder.getContext().getUserSessionToken().getUserId());
		if (!user.getPassword().equals( bean.getOldPassword())){
			throw new UserException(ExceptionCodes.OLD_PASSWORD_INVALID,ExceptionMessages.OLD_PASSWORD_INVALID);
		}
		isPasswordChanged = validatePassword(bean.getPassword());
		isPasswordChanged = validateConfirmPassword(bean.getPassword(),
				bean.getConfirmPassword());	
		isPasswordChanged = userDAOImpl.changePassword(bean);
		return isPasswordChanged;
	}

	public boolean forgotPassword(UserBean bean) throws UserException {
		boolean isValidated = false;
		if (bean == null || bean.toString().trim().isEmpty())
			throw new UserException(ExceptionCodes.EMAIL_CANNOT_BE_EMPTY,
					ExceptionMessages.EMAIL_CANNOT_BE_EMPTY);
		isValidated = validateEmail(bean.getEmail());
		User user = DAOFactory.getInstance().getUserDAO()
				.getUserByEmail(bean.getEmail());
	    if(user == null)
	    	throw new UserException(ExceptionCodes.DELETED_ALREADY,
					ExceptionMessages.DELETED_ALREADY);
	    //if user email is validated sentMail  
		sentMail(user);
		return isValidated;
	}

	// SearchUserRecords
	public User getLoginUser(LoginBean bean) throws UserException {
		User user = null;
		if (isLoginValidated(bean)) {
			try {
				user = DAOFactory.getInstance().getUserDAO().getLoginUser(bean);
			} catch (HibernateException he) {
				he.printStackTrace();
			}
		}
		return user;
	}
	@AuthorizeEntity(roles = {Roles.ROLE_ADMIN}, entity = "User.java")
	public User updateUserAOP(UserBean bean) throws UserException {
		User user = null;
		if(bean == null || bean.toString().trim().isEmpty())
		{
			throw new UserException(ExceptionCodes.USER_DETAILS_NULL,ExceptionMessages.USER_DETAILS_NULL);
		}
		if (isUpdateUserIsValidated(bean)) {
			UserDAO userDAOImpl = DAOFactory.getInstance().getUserDAO();
			
			user = userDAOImpl.updateUser(bean);
		}
		if(user == null)
		{
			throw new UserException(ExceptionCodes.UPDATE_HANDLAER_EXCEPTION,ExceptionMessages.UPDATE_HANDLAER_EXCEPTION);
		}
		return user;
	}

	public boolean logout() {
		boolean isLogout = false;
		String userSessionId = ServiceRequestContextHolder.getContext()
				.getUserSessionToken().getUserSessionId();
		
		isLogout = CacheManager.getInstance()
				.getCache(CacheRegionType.USER_SESSION_CACHE).remove(userSessionId);
		return isLogout;
	}

	public User getUserByUserId(long userId) throws UserException {
		User user = null;
		UserDAO userDAOImpl = DAOFactory.getInstance().getUserDAO();
		user = userDAOImpl.getUserById(userId);
		return user;
	}

	public User getUserByEmail(String email) throws UserException {
		User user = null;
		UserDAO userDAOImpl = DAOFactory.getInstance().getUserDAO();
		user = userDAOImpl.getUserByEmail(email);

		return user;
	}

	public boolean isUserDeleted(long id){
		return DAOFactory.getInstance().getUserDAO().isUserDeleted(id);

	}

	// sent Mail

	private boolean sentMail(User user) throws UserException {
		boolean isSent = false;
		String host = "smtp.gmail.com";
		int port = 465;
		final String username = "anilram295@gmail.com";
		final String password = "9848054549";
		String factory = "javax.net.ssl.SSLSocketFactory";
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "false");
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.socketFactory.port", port);
		props.put("mail.smtp.socketFactory.class", factory);
		props.put("mail.smtp.socketFactory.fallback", "false");
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
			msg.setRecipients(Message.RecipientType.TO, user.getEmail());
			msg.setSubject("Password Reset");
			msg.setSentDate(new Date());
			msg.setText(":)\n Your Password is : " + user.getPassword());
			Transport.send(msg);
			isSent = true;
		} catch (MessagingException mex) {
			throw new UserException(ExceptionCodes.SEND_MAIL_FAILED,
					ExceptionMessages.SEND_MAIL_FAILED);
		}
		return isSent;
	}

	/*
	 * searchUser  Validations 
	 */

	private boolean searchUserValidations(UserBean bean) throws UserException {
		boolean isValidated = false;
		if (bean.getNickName() != null ) {		
			boolean isNickNameValidated = Pattern.compile(Utils.USER_NAME_PATTERN).matcher(bean.getNickName()).matches(); 
		     if(!isNickNameValidated)
				throw new UserException(ExceptionCodes.NICKNAME_INVALID,
						ExceptionMessages.NICKNAME_INVALID);			
		}
		if (bean.getEmail() != null) {
			
			boolean isEmailPatternValid = Pattern.compile(Utils.EMAIL_PATTERN)
					.matcher(bean.getEmail()).matches();
			if (!isEmailPatternValid) {
				throw new UserException(ExceptionCodes.USER_EMAIL_FORMAT,
						ExceptionMessages.USER_EMAIL_FORMAT);				
			}
			isValidated = true;			
		}
		
			if (bean.getEmployeeId() != null) {
				boolean isEmployeeIdValid = Pattern.compile(Utils.EMPLOYEE_ID_PATTERN).matcher(bean.getEmployeeId())
	            .matches();
	            if (!isEmployeeIdValid) {
	        throw new UserException(ExceptionCodes.EMPLOYEE_ID_INVALID_FORMAT,
	                ExceptionMessages.EMPLOYEE_ID_INVALID_FORMAT);
			}
		}
		if (bean.getDesignation() != null) {
		
			boolean isDesignationPatternValid = Pattern
					.compile(Utils.DESIGNATION).matcher(bean.getDesignation())
					.matches();
			if (!isDesignationPatternValid) {
				
				throw new UserException(
						ExceptionCodes.DESIGNATION_PATTERN_INVALID,
						ExceptionMessages.DESIGNATION_PATTERN_INVALID);
			}
		
			
		}
		isValidated = true;
		return isValidated;
	}

	

	/*
	 *  addUserValidations
	 */

	private boolean isaddUserIsValidated(UserBean bean) throws UserException {
		boolean isValidated = false;
		if (null == bean || bean.toString().trim().length() == 0) {
			throw new UserException(ExceptionCodes.USER_DETAILS_NULL,
					ExceptionMessages.USER_DETAILS_NULL);
		}		
		isValidated = validateEmail(bean.getEmail());
		isValidated = validatePassword(bean.getPassword());
		isValidated = validateConfirmPassword(bean.getPassword(),
				bean.getConfirmPassword());
		isValidated = validateFirstName(bean.getFirstName());
		isValidated = validateLastName(bean.getLastName());
		if (bean.getNickName() == null) {
			bean.setNickName(bean.getLastName());
		}
		isValidated = validateNickName(bean.getNickName());
		isValidated = validateGender(bean.getGender());
		isValidated = validateEmployeeId(bean.getEmployeeId());
		isValidated = validateUserId(bean.getUserId());
		isValidated = validateDesignation(bean.getDesignation());
		if(!(bean.getUserId().equals(bean.getEmail()))){
			isValidated = false;
			throw new UserException(ExceptionCodes.EMAIL_USERID_NOT_EQUAL,
					ExceptionMessages.EMAIL_USERID_NOT_EQUAL);
		}
		isValidated = true;
		return isValidated;
		

		
		
	}

	// login validations

	private boolean isLoginValidated(LoginBean bean) throws UserException {
		boolean isValidated = false;		
		if (null == bean) {
			throw new UserException(ExceptionCodes.USER_ID_AND_PASSWORD_NULL,
					ExceptionMessages.USER_ID_AND_PASSWORD_NULL);
		}
		isValidated = validateEmail(bean.getEmail());
		isValidated = validatePassword(bean.getPassword());		
		return isValidated;

	}

	// update Validations

	private boolean isUpdateUserIsValidated(UserBean bean) throws UserException {
		boolean isValidated = false;
		isValidated = validateFirstName(bean.getFirstName());
		isValidated = validateLastName(bean.getLastName());
		isValidated = validateNickName(bean.getNickName());
		if (bean.getNickName() == null) {
			bean.setNickName(bean.getLastName());
		}
		isValidated = validateLocation(bean.getLocation());
		isValidated = validateGender(bean.getGender());
		isValidated = validateEmail(bean.getEmail());
		isValidated = validateEmployeeId(bean.getEmployeeId());
		isValidated = validateDesignation(bean.getDesignation());
		isValidated = validateUserId(bean.getUserId());
		isValidated = validatePassword(bean.getPassword());
		if(!(bean.getPassword().equals(bean.getConfirmPassword()))){
			isValidated = false;
			throw new UserException(ExceptionCodes.CONFIRM_PASSWORD_NOT_EQUAL,
					ExceptionMessages.CONFIRM_PASSWORD_NOT_EQUAL);
		}
		if(!(bean.getUserId().equals(bean.getEmail()))){
			isValidated = false;
			throw new UserException(ExceptionCodes.EMAIL_USERID_NOT_EQUAL,
					ExceptionMessages.EMAIL_USERID_NOT_EQUAL);
		}
		
		return isValidated;
	}

	private boolean validateUserId(String userId) throws UserException {
		boolean isValidated = false;
		if (userId == null || userId.trim().length() == 0 ) { // changes
			isValidated = false;
			throw new UserException(ExceptionCodes.USER_ID_NULL,
					ExceptionMessages.USER_ID_NULL);
		} 
		
		isValidated = true;
		return isValidated;
		
	}

	private boolean validateDesignation(String designation) throws UserException {
		boolean isValidated = false;
		if (designation == null || designation.trim().isEmpty()) {
			isValidated = false;
			throw new UserException(ExceptionCodes.DESIGNATION_NULL,ExceptionMessages.DESIGNATION_NULL);
			
		}
			boolean isDesignationPatternValid = Pattern
					.compile(Utils.DESIGNATION).matcher(designation)
					.matches();

			isValidated = true;
			if (!isDesignationPatternValid) {
				isValidated = false;
				throw new UserException(
						ExceptionCodes.DESIGNATION_PATTERN_INVALID,
						ExceptionMessages.DESIGNATION_PATTERN_INVALID);
			}
		return isValidated;
	}

	private boolean validateEmployeeId(String employeeId) throws UserException {
		boolean isValidated = false;
		if (employeeId == null
				|| employeeId.trim().length() == 0) {
			isValidated = false;
			throw new UserException(ExceptionCodes.EMPLOYEE_ID_NULL,
					ExceptionMessages.EMPLOYEE_ID_NULL);
		} else {
			boolean isEmployeeIdValid = Pattern
					.compile(Utils.EMPLOYEE_ID_PATTERN)
					.matcher(employeeId).matches();
			isValidated = true;

			if (!isEmployeeIdValid) {
				isValidated = false;
				throw new UserException(ExceptionCodes.EMPLOYEE_ID_INVALID,
						ExceptionMessages.EMPLOYEE_ID_INVALID);
			}
		}
		
		return isValidated;
	}

	private boolean validateGender(String gender) throws UserException {
		boolean isValidated = false;
		if (gender == null || gender.trim().length() == 0) {
			isValidated = false;
			throw new UserException(ExceptionCodes.GENDER_NOT_NULL,
					ExceptionMessages.GENDER_NOT_NULL);
		} else {
			boolean isGenderPatternValid = Pattern.compile(Utils.GENDER)
					.matcher(gender).matches();
			isValidated = true;
			if (!isGenderPatternValid) {
				isValidated = false;
				throw new UserException(ExceptionCodes.GENDER_INVALID,
						ExceptionMessages.GENDER_INVALID);
			}
		}
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
		if (location != null ) {
			boolean isNamePatternValid = Pattern
					.compile(Utils.LOCATION_PATTERN).matcher(location)
					.matches();
			isValidated = true;
			if (!isNamePatternValid) {
				isValidated = false;
				throw new UserException(ExceptionCodes.LOCATION_INVALID,
						ExceptionMessages.LOCATION_INVALID);
			}
			isValidated = true;
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

	// validate Password

	private boolean validatePassword(String password) throws UserException {
		boolean isValidated = false;
		if (password == null || password.trim().isEmpty()) {
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

	private static boolean validateEmail(String email) throws UserException {
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




public List<User> getUsersOtherThanTheseIds(List<Long> userIds){
	 return DAOFactory.getInstance().getUserDAO().getUsersOtherThanTheseIds(userIds);
	}


public User getUserById(Long id) throws UserException {
	if(id == null) {       
		throw new UserException(ExceptionCodes.USER_ID_INVALID,ExceptionMessages.USER_ID_INVALID);
	}
	User user = DAOFactory.getInstance().getUserDAO().getUserById(id);
	if(user == null)
		throw new UserException(ExceptionCodes.USER_DOESNOT_EXIST,ExceptionMessages.USER_DOESNOT_EXIST);
	return user;
}

	public User getLoginUserDetails() throws UserException {
		User user = DAOFactory
				.getInstance()
				.getUserDAO()
				.getUserById(
						ServiceRequestContextHolder.getContext()
								.getUserSessionToken().getUserId());
		return user;
	}

	public User updateLoginUser(UserBean bean) throws UserException {
		User user =  null;
		if(bean == null || bean.toString().trim().isEmpty()){
			throw new UserException(ExceptionCodes.USER_DETAILS_NULL,ExceptionMessages.USER_DETAILS_NULL);
		}		
		validateFirstName(bean.getFirstName());
		validateLastName(bean.getLastName());
		validateNickName(bean.getNickName());
		if (bean.getNickName() == null) {
			bean.setNickName(bean.getLastName());
		}
		validateLocation(bean.getLocation());		
		UserDAO userDAOImpl = DAOFactory.getInstance().getUserDAO();
		user = userDAOImpl.updateLoginUser(bean);	
		return user;
	}

	public List<String> getEmployeeIds() {
		UserDAO userDAOImpl = DAOFactory.getInstance().getUserDAO();
		List<String> employeeIds = userDAOImpl.getEmployeeIds();
		return employeeIds;
	}
	
	public List<User> getUsersByIds(List<Long> userIds) {
		UserDAO userDAOImpl = DAOFactory.getInstance().getUserDAO();
		List<User> UsersList = userDAOImpl.getUsersByIds(userIds);
		return UsersList;
	}
	public String getUserName(long id) {
		  UserDAO userDAOImpl = DAOFactory.getInstance().getUserDAO();
		  String userFirstName = userDAOImpl.getUserName(id);
		  return userFirstName;
		 }

	
	@AuthorizeEntity(roles = {Roles.ROLE_ADMIN}, entity = "User.java")
	public User getUserByIdAOP(Long id) throws UserException{
		if(id == null) {       
			throw new UserException(ExceptionCodes.USER_ID_INVALID,ExceptionMessages.USER_ID_INVALID);
		}
		User user = DAOFactory.getInstance().getUserDAO().getUserById(id);
		if(user == null)
			throw new UserException(ExceptionCodes.USER_DOESNOT_EXIST,ExceptionMessages.USER_DOESNOT_EXIST);
		return user;		
	}

	
}