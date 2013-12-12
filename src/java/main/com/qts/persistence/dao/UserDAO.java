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
public interface UserDAO {
	public boolean deleteUser(long id) throws UserException; // deleteUser

	public User getUserById(long userId) throws UserException;

	public List<User> searchUser(UserBean bean) throws UserException; // search User																		

	public User getUserLogin(LoginBean bean) throws UserException;// login

	public User getUserByEmail(String email) throws UserException;

	public String getUserName(long id);

	public long addUser(User user) throws UserException;// addUser

	public User updateUser(UserBean bean) throws UserException;

	boolean changePassword(ChangePasswordBean bean) throws UserException;

	public boolean isUserDeleted(long id) throws Exception;

	public List<User> getUserByIds(List<Long> userIds);// tell to mani
	// public long addUser(UserBean bean, long id, long cts, long mts,
	// String createdBy, String modifiedBy, boolean isDeleted, long
	// photoFileId);
	// public boolean changePassword(String password);
}
