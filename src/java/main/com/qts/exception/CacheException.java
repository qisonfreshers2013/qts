package com.qts.exception;

/**
 * Throws this exception if there is any issue in accessing the cache
 * @author Vinay Thandra
 *
 */
public class CacheException extends SystemException {

	private static final long serialVersionUID = 8238208165723901223L;

	public CacheException(RuntimeException ex) {
		super(-1, ex);
	}

	public CacheException(Throwable ex) {
		super(-1, ex);
	}
	
	public CacheException(String string) {
		super(-1, string);
	}

	public CacheException(int exceptionCode) {
		super(exceptionCode);
	}

	public CacheException(int exceptionCode, Throwable ex) {
		super(exceptionCode, ex);
	}

	public CacheException(int exceptionCode, String[] args) {
		super(exceptionCode, args, null);
	}

	public CacheException(int exceptionCode, String[] args, Throwable ex) {
		super(exceptionCode, args, ex);
	}

}
