/**
 * 
 */
package com.qts.persistence.dao;

import java.util.List;

import com.qts.exception.UserException;
import com.qts.model.ChangePasswordBean;
import com.qts.model.LoginBean;
import com.qts.model.User;
import com.qts.model.UserBean;

/**
 * AnilRam
 * 
 */
public interface UserDAO extends BaseDAO{
	public boolean deleteUser(long id) throws UserException; // deleteUser
	public User getUserById(long userId) throws UserException;
	public List<User> searchUser(UserBean bean) throws UserException; // search User								
	public User getUserByEmail(String email) throws UserException;
	public String getUserName(long id);
	public long addUser(User user) throws UserException;// addUser
	public User updateUser(UserBean bean) throws UserException;
	public boolean changePassword(ChangePasswordBean bean) throws UserException;
	public boolean isUserDeleted(long id) ;
	public List<User> getUsersOtherThanTheseIds(List<Long> userIds);
	public User updateLoginUser(UserBean bean) throws UserException;
	public User getLoginUser(LoginBean bean) throws UserException;
	public List<String> getEmployeeIds();
	public List<User> getUsersByIds(List<Long> userIds);
	
}
