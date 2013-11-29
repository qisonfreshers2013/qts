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
public interface AuthenticationHandler {
	public AuthenticationOutput authenticate(AuthenticationInput input)
			throws ObjectNotFoundException, BusinessException, EncryptionException;
}
