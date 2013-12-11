package com.qts.handler.user;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.TimeZone;

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
import com.qts.model.User;
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
		boolean userValidity = passwordFromDB.equals(password);//anil

		if (!userValidity) {
			throw new BusinessException(ExceptionCodes.INVALID_PASSWORD,
<<<<<<< HEAD
					ExceptionMessages.INVALID_PASSWORD);
		}//anil
=======
					ExceptionMessages.INVALID_PASSWORD);//anil
		}
>>>>>>> 1d587748b60786fcc68b6d96e67c4674b59bea17

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
				sessionToken, authStatus, user);
		return authenticationOutput;
	}

}
