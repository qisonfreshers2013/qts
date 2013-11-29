package com.qts.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Base64;

import com.qts.exception.EncryptionException;
import com.qts.exception.ExceptionCodes;

/**
 * @author RAMMOHAN
 * 
 */
public class MD5EncryptionUtil implements EncryptionUtil{

	public static final String MD5_ENCRYPTION_ALGORITHM = "MD5";
	public static final String UTF_8_ENCODING = "UTF-8";
	public static MD5EncryptionUtil INSTANCE = null;

	public void initialize(String[] args) throws EncryptionException {
	}

	public synchronized String encrypt(String value) throws EncryptionException {

		MessageDigest md = null;
		try {
			md = MessageDigest
					.getInstance(MD5EncryptionUtil.MD5_ENCRYPTION_ALGORITHM);
			md.update(value.getBytes(MD5EncryptionUtil.UTF_8_ENCODING));
			byte raw[] = md.digest();
			return new String((new Base64()).encode(raw));
		} catch (UnsupportedEncodingException e) {
			throw new EncryptionException(ExceptionCodes.ENCRYPTION_FAILED);
		} catch (NoSuchAlgorithmException e) {
			throw new EncryptionException(ExceptionCodes.ENCRYPTION_FAILED);
		}
	}

	public String encryptUsingPKCS5(String valueToEncrypt) throws EncryptionException {
		return valueToEncrypt;
	}

	public String decrypt(String value) throws EncryptionException {
		return value;
	}

	public String decryptUsingPKCS5(String value) throws EncryptionException {
		return value;
	}
	
}
