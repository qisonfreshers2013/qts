package com.qts.persistence.dao;

import java.util.List;

import com.qts.model.Roles;

/**
 * 
 * @author Jagadish
 * 
 */
public interface RoleDAO extends BaseDAO{

	List<Roles> getRoles() throws Exception;

	boolean validateRoleId(long roleId) throws Exception;
}
