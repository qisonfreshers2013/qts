package com.qts.handler;

import java.util.ArrayList;
//import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.hibernate.HibernateException;

import com.qts.common.Utils;
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
import com.qts.persistence.dao.DAOFactory;
import com.qts.persistence.dao.UserDAO;
import com.qts.persistence.dao.UserDAOImpl;

public class UserHandler extends AbstractHandler {
 private static UserHandler INSTANCE = null;

 private UserHandler() {

 }

 public static UserHandler getInstance() {
  if (INSTANCE == null)
   INSTANCE = new UserHandler();
  return INSTANCE;
 }

 public SearchUserRecords searchUsers(UserBean bean) throws UserException {
  
  List<User> list = null;
  SearchUserRecords searchUserRecords = null;
  // validations of the input provided // saperate method // should return
  // true or false
  List<SearchUserRecord> records = null;
   if (bean==null || searchUserValidations(bean)) {
    try {
     
//     if (bean.getNickName() == null) {
//      bean.setNickName("Not");
//     }
//     if (bean.getEmail() == null) {
//      bean.setEmail("Not");
//     }
//     if (bean.getEmployeeId() == null) {
//      bean.setEmployeeId("Not");
//     }
//     if (bean.getDesignation() == null) {
//      bean.setDesignation("Not");
//     }

     list = DAOFactory.getInstance().getUserDAO().searchUser(bean);
    // List<SearchUserRecord> record = new LinkedList<SearchUserRecord>();
        records = new LinkedList<SearchUserRecord>();
     for(User user : list){
      // each iteration will create an instance of SearchUser Record
      SearchUserRecord searchUserRecord = new SearchUserRecord(user);
      List<Project> projects = new ArrayList<Project>();//List<Project> projects = UserProjectHandler.getInstance().getProjectsById(user.getId());      
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

 private boolean searchUserValidations(UserBean bean) throws UserException {
  boolean isValidated = false;
  
  
  
//   String name = bean.getNickName();
//   String designation = bean.getDesignation();
  if(bean.getNickName()!=null){
  try {
   Utils.validateName(bean.getNickName());
   isValidated = true;
  } catch (BusinessException be) {
   throw new UserException(ExceptionCodes.USER_NAME_FORMAT,
     ExceptionMessages.USER_NAME_FORMAT);

  }
  }
  if(bean.getEmail()!=null){
  try {
   
   boolean isEmailPatternValid = Pattern.compile(Utils.EMAIL_PATTERN)
     .matcher(bean.getEmail()).matches();
   if (!isEmailPatternValid) {
    throw new BusinessException(
      ExceptionCodes.INVALID_EMAIL_PATTERN,
      ExceptionMessages.INVALID_EMAIL_PATTERN);

   }
   isValidated = true;
  } catch (BusinessException be) {
   throw new UserException(ExceptionCodes.USER_EMAIL_FORMAT,
     ExceptionMessages.USER_EMAIL_FORMAT);
  }
  }
  if(bean.getEmployeeId()!=null){
  try {
   Utils.validateName(bean.getEmployeeId());
   isValidated = true;
  } catch (BusinessException be) {
   throw new UserException(ExceptionCodes.EMP_ID_FORMAT,
     ExceptionMessages.EMP_ID_FORMAT);
  }
  }
  if(bean.getDesignation()!=null){
  try {
   boolean isDesignationPatternValid = Pattern
     .compile(Utils.DESIGNATION).matcher(bean.getDesignation()).matches();
   if (!isDesignationPatternValid) {
    throw new BusinessException();      
   }
   isValidated = true;
  } catch (BusinessException be) {
   throw new UserException(ExceptionCodes.INVALID_DESIGNATION_PATTERN ,
     ExceptionMessages.INVALID_DESIGNATION_PATTERN );
  }
  }
   
  return isValidated;
 }
 

 //
// public List<User> getListOfUsers() {
//
//  //StringWriter userInfo = new StringWriter();
//
//  // DAOFactory daoFactory = DAOFactory.getInstance();
//  UserDAOImpl userDAOImpl = DAOFactory.getUserDAOImpl();
//  List<User> list = userDAOImpl.getListOfUsersObjects();
//  // Iterator<User1> itr = list.iterator();
//
////  for (User user : list) {
////   try {
////    userInfo.append(user.getPhotoFile().getPath() + "\t");
////   } catch (NullPointerException n) {
////    userInfo.append(null);
////   }
////   userInfo.append(user.getEmail() + "\t" + user.getEmployeeId()
////     + "\t" + user.getDesignation() + "\t");
////   Set<Project> set = user.getProject();
////   for (Project project : set) {
////
////    userInfo.append(project.getName() + "\n");
////   }
////  }
//
//  return list;
// }

 public long addUser(UserBean bean) throws UserException {
  String userId = null;
  long id = 0;
  
  if(isaddUserIsValidated(bean)){
  UserDAO userDAOImpl = DAOFactory.getInstance().getUserDAO();   
     id = 11;    
  Date date = new Date();
  long cts = date.getTime();
  long mts = cts;
  boolean isDeleted = false;
  long photoFileId = 7;
     String createdBy = userDAOImpl.getUserName(id); 
     String modifiedBy = createdBy;
     boolean gender = bean.getGender().equalsIgnoreCase("male")?true:false; 
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
   try {
    id = userDAOImpl.addUser(user);
    
   } catch (HibernateException he) {
    he.printStackTrace();
   }
  }
  return id;
  }
  
 

 private boolean isaddUserIsValidated(UserBean bean) throws UserException {
  boolean isValidated = false;
  if(null == bean){
   throw new UserException(ExceptionCodes.USER_DETAILS_NULL,ExceptionMessages.USER_DETAILS_NULL);
   }
//  if(bean.getPassword()==null){
//   isValidated = false;
//   throw new UserException(ExceptionCodes.PASSWORD_NULL,ExceptionMessages.PASSWORD_NULL );   
//  }
//  try {
//   Utils.validatePassword(bean.getPassword());
//   isValidated = true;
//  } catch (Exception be) {
//   isValidated = false;
//   throw new UserException(ExceptionCodes.PASSWORD_FORMAT,
//     ExceptionMessages.PASSWORD_FORMAT);
//  }
  isValidated = validatePassword(bean.getPassword());
  isValidated = validateConfirmPassword(bean.getPassword(),bean.getConfirmPassword());
  isValidated = validateFirstName(bean.getFirstName());
  isValidated = validateLastName(bean.getLastName());
  
//  if(bean.getConfirmPassword()==null){
//   isValidated = false;
//   throw new UserException(ExceptionCodes.CONFIRM_PASSWORD_NULL,ExceptionMessages.CONFIRM_PASSWORD_NULL );
//   
//  }
//  if(!bean.getPassword().equals(bean.getConfirmPassword())){
//   isValidated = false;
//   throw new UserException(ExceptionCodes.CONFIRM_PASSWORD_NOT_EQUAL,ExceptionMessages.CONFIRM_PASSWORD_NOT_EQUAL);
//  }
  
//  if(bean.getFirstName()==null||bean.getFirstName().trim().length()==0..