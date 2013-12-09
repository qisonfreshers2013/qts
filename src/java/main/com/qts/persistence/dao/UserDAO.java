/**
 * 
 */
package com.qts.persistence.dao;

import java.util.List;

import com.qts.exception.UserException;
import com.qts.model.LoginBean;
import com.qts.model.User;
import com.qts.model.UserBean;

/**
 *AnilRam
 *
 */
public interface UserDAO {	
	public boolean deleteUser(long id) throws UserException; //deleteUser
	public User getUserByUserId(long userId) throws UserException;
	public List<User> searchUser(UserBean bean); //search User
	public User getUserLogin(LoginBean bean) throws UserException;//login
	public User getUserByEmail(String email) throws UserException;
	public String getUserName(long id);

	public long addUser(User user);//addUser
	public User updateUser(UserBean bean) throws UserException;
	
	boolean changePassword(String password, long id) throws UserException;
	public boolean isUserDeleted(long id) throws Exception;
//	public long addUser(UserBean bean, long id, long cts, long mts,
//	String createdBy, String modifiedBy, boolean isDeleted, long photoFileId);
	//public boolean changePassword(String password);
}
