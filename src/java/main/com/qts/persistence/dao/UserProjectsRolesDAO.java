package com.qts.persistence.dao;

import java.util.List;
import java.util.Set;

import com.qts.model.RoleBean;
import com.qts.model.UserProject;
import com.qts.model.UserProjectsRoles;

/**
 * 
 * @author Jagadish
 * 
 */
public interface UserProjectsRolesDAO {
	// Gives UserProjectRoles that are available with the given userProjectId
	// and roleId
	UserProjectsRoles getUserProjectsRolesByUserProjectAndRole(long userProjectId, long roleId)
			throws Exception;

	// lists all the Objects with given userProjetId
	List<UserProjectsRoles> getUserProjectsRolesByUserProject(long userProjectId)
			throws Exception;

	// lists all the roles of a particular user
	public RoleBean getUserRoles(RoleBean roleBean,
			List<UserProjectsRoles> listUserProjectRoles) throws Exception;

	// allocates user to specified set of roles
	RoleBean allocateRoles(RoleBean roleBean, UserProject userProject)
			throws Exception;

	// remove's particular role of a user
	RoleBean deallocateRoles(RoleBean rolebean, UserProject userProject)
			throws Exception;

	// deletes all the roles when user removed from the project
	boolean deleteUserProjectsRolesByUserProject(UserProject next);
}
