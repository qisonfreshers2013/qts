package com.qts.handler.user;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;




import org.apache.commons.collections.CollectionUtils;

import com.qts.common.EncryptionFactory;
import com.qts.common.cache.Cache;
import com.qts.common.cache.CacheManager;
import com.qts.common.cache.CacheRegionType;
import com.qts.exception.BusinessException;
import com.qts.exception.EncryptionException;
import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.ObjectNotFoundException;
import com.qts.exception.SystemException;
import com.qts.model.Roles;
import com.qts.model.User;
import com.qts.model.UserProject;
import com.qts.model.UserProjectsRoles;
import com.qts.model.UserSessionToken;
import com.qts.model.user.AuthenticationInput;
import com.qts.model.user.AuthenticationOutput;
import com.qts.model.user.DefaultAuthenticationInput;
import com.qts.persistence.dao.DAOFactory;

/**
 * @author RAMMOHAN
 * 
 */
public class DefaultAuthenticationHandler implements AuthenticationHandler {

	@Override
	public AuthenticationOutput authenticate(AuthenticationInput input)
			throws ObjectNotFoundException, BusinessException,
			EncryptionException {

		DefaultAuthenticationInput daInput = (DefaultAuthenticationInput) input;
		int authStatus = User.AUTH_STATUS_NONE;
		String email = daInput.getEmail();
		String password = daInput.getPassword();
		if (email != null) {
			email = email.trim().toLowerCase();
		}
		if (password != null) {
			password = password.trim();
		}
		User user = null;
		DAOFactory daoFactory = DAOFactory.getInstance();
		// TODO handle email doesn't exists case.
		user = daoFactory.getUserDAO().getUserByEmail(email);
		//String encryptedPassword = Utils.encrypt(password);
		String passwordFromDB = user.getPassword();
		
		
		/////////////////////////////////////////////////////////////////////////////////
		long userId = user.getId();
		List<UserProject> userProjects = daoFactory.getUserProjectDAOImplInstance().getUserProjectsByUserId(userId);
		Set<String> roleNames = new HashSet<>();
		if(CollectionUtils.isNotEmpty(userProjects)) {
			for(UserProject project : userProjects) {
				List<UserProjectsRoles> userProjectsRoles = new LinkedList<>();
				try {
					userProjectsRoles = daoFactory.getUserProjectsRolesDAOInstance().getUserProjectsRolesByUserProject(project.getId());
				} catch (Exception e) {
				}
				if(CollectionUtils.isNotEmpty(userProjectsRoles)) {
					for(UserProjectsRoles userProjectsRole: userProjectsRoles) {
						Roles r=(Roles)daoFactory.getRoleDAOImplInstance().getObjectById(userProjectsRole.getRoleId());
						
						roleNames.add(r.getName());
					}
				}
			}
		}
		if(user.getIsAdmin()){
			roleNames.add("ADMIN");
		}
		
		
	////////////////////////////////////////////////////////////
		
		boolean userValidity = passwordFromDB.equals(password);//anil

		if (!userValidity) {
			throw new BusinessException(ExceptionCodes.INVALID_PASSWORD,

					ExceptionMessages.INVALID_PASSWORD);
		}//anil

		authStatus = User.AUTH_STATUS_EXISTING;

		String sessionToken = null;
		try {
			TimeZone.setDefault(TimeZone.getDefault());
			sessionToken = URLEncoder
					.encode(EncryptionFactory.getEncryption(true).encrypt(
							user.getEmail()
									+ Calendar.getInstance().getTimeInMillis()),
							"UTF-8");
		} catch (EncryptionException ee) {
			throw new SystemException(ExceptionCodes.INTERNAL_ERROR,
					ExceptionMessages.INTERNAL_ERROR);
		} catch (UnsupportedEncodingException uee) {
			throw new SystemException(ExceptionCodes.INTERNAL_ERROR,
					ExceptionMessages.INTERNAL_ERROR);
		}

		UserSessionToken userSessionToken = new UserSessionToken();
		userSessionToken.setUserEmail(user.getEmail());
		userSessionToken.setUserId(user.getId());
		userSessionToken.setUserSessionId(sessionToken);
		Cache cache = CacheManager.getInstance().getCache(CacheRegionType.USER_SESSION_CACHE);
		cache.put(sessionToken, userSessionToken);
		System.out.println("Session Token : "+sessionToken);		
		System.out.println("Cached : "+cache.getValue(sessionToken));
		AuthenticationOutput authenticationOutput = new AuthenticationOutput(
				sessionToken, authStatus, user,roleNames);
		return authenticationOutput;
	}

}
