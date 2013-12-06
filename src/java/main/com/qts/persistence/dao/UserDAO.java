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
	public boolean deleteUser(long id); //deleteUser
	public User getUserByUserId(String userId);
	public List<User> searchUser(UserBean bean); //search User
	public User getUserLogin(LoginBean bean) throws UserException;//login
	public User getUserByEmail(String email);
	public String getUserName(long id);
//	public long addUser(UserBean bean, long id, long cts, long mts,
//			String createdBy, String modifiedBy, boolean isDeleted, long photoFileId);
	public long addUser(User user);//addUser
	public boolean updateUser(UserBean bean);
	//public boolean changePassword(String password);
	boolean changePassword(String password, long id);
}
