package com.qts.common;

/**
 * @author Vinay Thandra
 *
 */
public class EncryptionFactory {
	public static EncryptionUtil getEncryption(String type) {
		if ("MD5".equals(type)) { return new MD5EncryptionUtil(); }
		return null;
	}


	public static EncryptionUtil getEncryption(boolean isOneWayEncryption) {
		if (isOneWayEncryption) { return getEncryption("MD5"); }
		return null;
	}
}
