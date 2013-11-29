package com.qts.common.cache;

import com.qts.common.cache.memcached.MemcachedCacheFactory;
import com.qts.exception.CacheException;

public abstract class AbstractCacheFactory implements CacheFactory {

	/**
	 * Gets the factory based on type of cache
	 * @param cacheType
	 * @return
	 */
	public static CacheFactory getCacheFactory(int cacheType) {
		
		if(cacheType == CacheType.CENTRAL_CACHE){
			return MemcachedCacheFactory.getInstance();
		}
		
		throw new CacheException("Factory not found for the type " + cacheType);	
	}

	@Override
	public Cache getCache(String cacheName) {
		return getCache(cacheName, 0, 0);
	}

	@Override
	public Cache getCache(String cacheName, int timeToLiveSeconds,
			int maxElementsInCache) {
		return getCache(cacheName, timeToLiveSeconds, maxElementsInCache);
	}	
}
