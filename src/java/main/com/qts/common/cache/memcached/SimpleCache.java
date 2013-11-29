package com.qts.common.cache.memcached;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.danga.MemCached.MemCachedClient;
import com.qts.exception.CacheException;
import com.qts.common.cache.Cache;
import com.qts.exception.SystemException;
import com.qts.common.EncryptionFactory;

/**
 * Instance of this class represents the simple cache region. This means that 
 * if object is found in the memcached server the same is returned to the caller 
 * else return null. 
 * 
 * Simple cache takes cache name and time to live values as inputs. Cache name is used to create 
 * the concept of region and time to live value is used to invalidate the cache object. 
 * 
 * @author Vinay Thandra
 *
 */

public class SimpleCache implements Cache {

	MemCachedClient memCachedClient = new MemCachedClient();
	long timeToLiveSeconds = 0;
	String name = null;

	public SimpleCache(int cacheId, long ttl) {
		timeToLiveSeconds = ttl;
		this.name = cacheId+"";
	}
	
	public SimpleCache(String name, long ttl) {
		timeToLiveSeconds = ttl;
		this.name = name;
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getKeys() {
		throw new SystemException("Get keys are not supported in memcached server");
	}

	@Override
	public int getMaxElementsInCache() {
		//not applicable for this type of cache
		return 0;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getSize() {
		return 0;
	}

	@Override
	public long getTimeToLiveSeconds() {
		return timeToLiveSeconds;
	}

	@Override
	public long getUpdateFrequency() {
		return 0;
	}

	@Override
	public Object getValue(Object key) {
		String modifiedKey = getUniqueKey(key);
		Object value = this.getMemCachedClient().get(modifiedKey);
		return value;
	}

	@Override
	public Map<String, Object> getValues(List<Object> keys) {
		Map<String, Object> cachedValues = new HashMap<String, Object>();
		if(keys != null && keys.size() > 0) {
			String[] modifiedKeys = new String[keys.size()];
			Map<String, String> keyMap = new HashMap<String, String>();
			for(int i=0; i < keys.size(); i++) {
				String modifiedKey = getUniqueKey(keys.get(i));
				keyMap.put(modifiedKey, keys.get(i).toString());
				modifiedKeys[i]= modifiedKey;
			}
			Map<String, Object> values =  this.getMemCachedClient().getMulti(modifiedKeys);
			for(String modifiedKey : values.keySet()){
				Object value = values.get(modifiedKey);
				if(value != null) {
					cachedValues.put(keyMap.get(modifiedKey), value);
				}
			}
		}
		return cachedValues;
	}
	
	/**
	 * Adds/Replaces the object in the cache. If found replaces the existing object with new one else
	 * simples adds the new object to the cache. 
	 */
	@Override
	public void put(Object key, Object value) {
		if(value != null) {
			String modifiedKey = getUniqueKey(key);
			if(getTimeToLiveSeconds() <= 0 ){
				this.getMemCachedClient().set(modifiedKey, value);
			}else{
				Date expiryDate = new Date();
				expiryDate.setTime(System.currentTimeMillis()+ (getTimeToLiveSeconds()*1000));
				this.getMemCachedClient().set(modifiedKey, value, expiryDate);			
			}		
		}
	}

	/**
	 * Adds the object to the cache if not found. If found then it does nothing, i.e. previously added 
	 * object continue to exist in the cache.
	 */
	@Override
	public boolean add(Object key, Object value) {
		boolean success = false;
		if (value != null) {
			String modifiedKey = getUniqueKey(key);
			if(getTimeToLiveSeconds() <= 0 ){
				success = this.getMemCachedClient().add(modifiedKey, value);
			}else{
				Date expiryDate = new Date();
				expiryDate.setTime(System.currentTimeMillis()+ (getTimeToLiveSeconds()*1000));
				success = this.getMemCachedClient().add(modifiedKey, value, expiryDate);			
			}		
		}
		return success;		
	}
	/**
	 * Removes the object from cache if found and if not found returns false. 
	 */
	@Override
	public boolean remove(Object key) {
		String modifiedKey = getUniqueKey(key);
		return this.getMemCachedClient().delete(modifiedKey);
	}

	@Override
	public boolean removeAll() {
		throw new CacheException("Remove all is not supported for memcached server");
	}
	
	@Override
	public void setMaxElementsInCache(int maxElementsInCache) {
		//no max limit for this cache
	}

	@Override
	public void setTimeToLiveSeconds(long seconds) {
		this.timeToLiveSeconds = seconds;
	}

		
	/**
	 * Memcached client responsible for interacting with memcached server. 
	 * @return
	 */
	protected MemCachedClient getMemCachedClient() {
		return memCachedClient;
	}

	/**
	 * Generates the key that is unique across all the instances of the cache 
	 * regions
	 * @param key
	 * @return
	 */
	protected String getUniqueKey(Object key) {
		String uniqueKey = this.getName()+"_"+key.toString();
		if(uniqueKey.length() > 255){
			try{
				uniqueKey = EncryptionFactory.getEncryption(true).encrypt(uniqueKey);
			}catch(Exception e){
				uniqueKey = uniqueKey.substring(0, 254);				
			}
		}
		return uniqueKey;
	}

	@Override
	public void put(Object key, Object value, int timeToLiveSeconds) {
		String modifiedKey = getUniqueKey(key);
		Date expiryDate = new Date();
		expiryDate.setTime(System.currentTimeMillis()+ (timeToLiveSeconds*1000));
		this.getMemCachedClient().set(modifiedKey, value, expiryDate);
	}

	@Override
	public boolean add(Object key, Object value, int timeToLiveSeconds) {
		boolean success = false;
		if (value != null) {
			String modifiedKey = getUniqueKey(key);
			Date expiryDate = new Date();
			expiryDate.setTime(System.currentTimeMillis()+ (timeToLiveSeconds*1000));
			success = this.getMemCachedClient().add(modifiedKey, value, expiryDate);			
		}
		return success;		
	}
}
