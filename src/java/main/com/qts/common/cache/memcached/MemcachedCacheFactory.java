/**
 * 
 */
package com.qts.common.cache.memcached;

import com.qts.common.cache.AbstractCacheFactory;
import com.qts.common.cache.Cache;
import com.qts.exception.ExceptionCodes;
import com.qts.exception.SystemException;

public class MemcachedCacheFactory extends AbstractCacheFactory {

	private static MemcachedCacheFactory factory = null;
	
    public static synchronized MemcachedCacheFactory getInstance() {
    	if(factory == null){
    		factory = new MemcachedCacheFactory();
    	}
    	return factory;
    }
	
	@Override
	public Cache getCache(String cacheName, int timeToLiveSeconds,
			int maxElementsInCache) {
		
		Cache memedCache = null;		
		if(cacheName == null) {
			throw new SystemException(ExceptionCodes.CACHE_NAME_NOT_PROVIDED);
		}
		
		memedCache = new SimpleCache(cacheName, timeToLiveSeconds);
		
		return memedCache;
	}
}
