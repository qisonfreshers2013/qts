package com.qts.persistence.dao;

import java.util.List;

import com.qts.model.UserProjectRoles;

public interface UserProjectsRolesDAO {

	UserProjectRoles getUserProjectRoleByIds(long userProjectId, long roleId) throws Exception;	
	List<UserProjectRoles> getUserProjectRolesByUserProjectId(long userProjectId) throws Exception;
}
