/**
 * 
 */
package com.qts.common;

import com.qts.exception.EncryptionException;

/**
 * @author Vinay Thandra
 *
 */
public interface EncryptionUtil {
	public void initialize(String[] args) throws EncryptionException;
	public String encrypt(String valueToEncrypt) throws EncryptionException;
	public String encryptUsingPKCS5(String valueToEncrypt) throws EncryptionException;
	public String decrypt(String valueToEncrypt) throws EncryptionException;
	public String decryptUsingPKCS5(String value) throws EncryptionException;
}
