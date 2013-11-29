package com.qts.common.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.SystemException;

/**
 * Singleton class used to get handle to the individual cache objects. 
 * @author Vinay Thandra
 *
 */
public class CacheManager {

	private static CacheManager cacheManager;
	//initialize manager
	static
	{
		cacheManager = new CacheManager();
	}

	private CacheManager(){
		
	}
	
	public static CacheManager getInstance(){
		return cacheManager;
	}
	
	public static int DEFAULT_CACHE_TYPE = CacheType.CENTRAL_CACHE;
	
	/**
	 * Used to store the caches based on type of cache. Each instance of Cache represents a 
	 * central cache implemented using memcached 
	 * 
	 */
	private Map<String, Cache> registeredCaches = new ConcurrentHashMap<String, Cache>();

	public Cache getCache(String cacheName) {
		Cache cache = registeredCaches.get(cacheName);
		if(cache == null){
			CacheRegionType type = CacheRegionType.getCacheRegionType(cacheName);
			if(type == null){
				throw new SystemException(ExceptionCodes.CACHE_REGION_NOT_FOUND, ExceptionMessages.CACHE_REGION_NOT_FOUND);
			}else{
				return getCache(type);
			}
		}
		return cache;
	}
	
	
	/**
	 * Gets the cache for the given cache region type. If not found then registers the one with default value. 
	 */
	public Cache getCache(CacheRegionType cacheRegionType) {
		Cache cache = registeredCaches.get(cacheRegionType.getId());
		if(cache == null){
			cache = addCache(cacheRegionType.getCacheType(), cacheRegionType.getId(), cacheRegionType.getTimeToLiveSeconds());
		}
		return cache;
	}

	public String[] getCacheNames() {
		String[] cacheNames = new String [this.registeredCaches.keySet().size()];
		this.registeredCaches.keySet().toArray(cacheNames);
		return cacheNames;
	}


	private Cache addCache(int cacheType, String cacheName, int timeToLiveSeconds) {
		Cache aCache = AbstractCacheFactory.getCacheFactory(cacheType).getCache(cacheName, timeToLiveSeconds, 0);
		registeredCaches.put(cacheName, aCache);		
		return aCache;		
	}
	
}
