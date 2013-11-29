package com.qts.handler.user;

import com.qts.exception.BusinessException;
import com.qts.exception.EncryptionException;
import com.qts.exception.ObjectNotFoundException;
import com.qts.model.user.AuthenticationInput;
import com.qts.model.user.AuthenticationOutput;

/**
 * @author RAMMOHAN
 * 
 */
public class DefaultAuthenticationHandler implements AuthenticationHandler {

	@Override
	public AuthenticationOutput authenticate(AuthenticationInput input)
			throws ObjectNotFoundException, BusinessException, EncryptionException {
		
		/*DefaultAuthenticationInput daInput = (DefaultAuthenticationInput) input;
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
	try {
		user = daoFactory.getUserDAO().getUserByEmail(email);
	} catch (ObjectNotFoundException e) {
		throw new ObjectNotFoundException(
				ExceptionCodes.EMAIL_DOESNOT_EXIST,
				ExceptionMessages.EMAIL_DOESNOT_EXIST);
	}

	String encryptedPassword = Utils.encrypt(password);
	String passwordFromDB = user.getPassword();
	boolean userValidity = passwordFromDB.equals(encryptedPassword);

	if (!userValidity) {
		throw new BusinessException(ExceptionCodes.INVALID_PASSWORD,
				ExceptionMessages.INVALID_PASSWORD);
	} 
	
	authStatus = User.AUTH_STATUS_EXISTING; 

	String sessionToken = null;
	try {
		sessionToken = URLEncoder.encode(
				EncryptionFactory.getEncryption(true).encrypt(
						user.getEmail() + DateUtils.getCurrentTime()),
				"UTF-8");
	} catch (EncryptionException ee) {
		throw new SystemException(ExceptionCodes.INTERNAL_ERROR,
				ExceptionMessages.INTERNAL_ERROR);
	} catch (UnsupportedEncodingException uee) {
		throw new SystemException(ExceptionCodes.INTERNAL_ERROR,
				ExceptionMessages.INTERNAL_ERROR);
	}

	long affinityId = Utils.getAffinityId();
	Affinity affinity = DAOFactory.getInstance().getAffinityDAO().getAffinity(affinityId);
	
	String communityType = affinity.getCommunity_Type();
	String registrationType = affinity.getRegistration_Type();
	
	UserSessionToken userSessionToken = new UserSessionToken();
	userSessionToken.setUserEmail(user.getEmail());
	userSessionToken.setUserId(user.getId());
	userSessionToken.setUserSessionId(sessionToken);
	userSessionToken.setRoleId(user.getRoleId());
	userSessionToken.setCommunityType(communityType);
	userSessionToken.setRegistrationType(registrationType);
	CacheManager.getInstance().getCache(CacheRegionType.USER_SESSION_CACHE)
			.put(sessionToken, userSessionToken);
	
	long fileId = user.getFileId();
	File file = null;
	if(fileId != 0) {
		file = FileHandler.getInstance().getFile(fileId);
	}
	
	long roleId = user.getRoleId();
	Role role = null;
	if(roleId != 0) {
		role = daoFactory.getRoleDAO().getRole(roleId);
	}
	
	UserOutput userOutput = new UserOutput(user, file, role); 
	AuthenticationOutput authenticationOutput = new AuthenticationOutput(
			sessionToken, authStatus, userOutput);
	return authenticationOutput;*/
        return null;
}
		

}
