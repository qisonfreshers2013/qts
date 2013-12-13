package com.qts.persistence.dao;

import java.util.List;
import java.util.Set;

import com.qts.model.Roles;

/**
 * 
 * @author Jagadish
 * 
 */
public interface RoleDAO {

	List<Roles> getRoles() throws Exception;

	boolean validateRoleId(long roleId) throws Exception;
}
