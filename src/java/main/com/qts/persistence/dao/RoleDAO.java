package com.qts.persistence.dao;


import java.util.List;

import com.qts.exception.ObjectNotFoundException;
import com.qts.model.RoleBean;
import com.qts.model.Roles;
import com.qts.model.UserProject;
import com.qts.model.UserProjectRoles;
/*
 * List of all role actions.
*/
/**
 * 
 * @author Jagadish
 *
 */
public interface RoleDAO 
{
	//lists all the current roles available
	 List<Roles> listRoles() throws Exception;
	 //lists all the roles of a particular user
	 public RoleBean listUserRoles(RoleBean roleBean,List<UserProjectRoles> listUserProjectRoles) throws Exception;
	 //allocates user to specified set of roles
	 RoleBean allocateRole(RoleBean roleBean,UserProject userProject) throws Exception;
	 //remove's all the current available roles of the user 
	// boolean deleteUserRole(UserProject userProject) throws Exception;
	 //remove's particular role of a user
	 RoleBean deallocateRole(RoleBean rolebean,UserProject userProject) throws Exception;
}