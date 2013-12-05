/**
 * 
 */
package com.qts.persistence.dao;

import java.util.List;

import com.qts.model.LoginBean;
import com.qts.model.User;
import com.qts.model.UserBean;

/**
 *AnilRam
 *
 */
public interface UserDAO {	
	public boolean deleteUser(long id);
	public User getUserByUserId(String userId);
	public List<User> searchUser(UserBean bean);
	public List<User> getUserLogin(LoginBean bean);
	public User getUserByEmail(String email);
	public String getUserName(long id);
	public long addUser(UserBean bean, long id, long cts, long mts,
			String createdBy, String modifiedBy, boolean isDeleted, long photoFileId);
}
