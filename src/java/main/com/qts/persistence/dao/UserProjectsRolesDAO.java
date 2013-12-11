package com.qts.persistence.dao;

import java.util.List;

import com.qts.model.UserProject;
import com.qts.model.UserProjectRoles;
/**
 * 
 * @author Jagadish
 *
 */
public interface UserProjectsRolesDAO {
	//Gives UserProjectRoles that are available with the given userProjectId and roleId
	UserProjectRoles getUserProjectRoleByIds(long userProjectId, long roleId) throws Exception;	
	//lists all the Objects with given userProjetId
	List<UserProjectRoles> getUserProjectRolesByUserProjectId(long userProjectId) throws Exception;

	//deletes all the roles when user removed from the project
	boolean deletUserProjectRoleByUserProjectId(UserProject next) throws Exception;
	
}
