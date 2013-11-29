package com.qts.common.cache;

import java.util.List;
import java.util.Map;

/**
 * Interface that provides the handle to store or get objects into single cache region.
 * @author Vinay Thandra
 *
 */
public interface Cache {

	public String getName();
	
	public Object getValue(java.lang.Object key);

	public Map<String, Object> getValues(List<Object> key);

	public java.util.List<Object> getKeys();
	
	public int getSize();
	
	/**
	 * Puts the element in cache and if the element is already in the cache 
	 * then this call updates the element. This will also make sure that
	 * the input object is wrapped in ICacheElement before setting into the cache
	 * @param key
	 * @param value
	 */
	public void put(java.lang.Object key, java.lang.Object value);

	/**
	 * Puts the element in cache and if the element is already in the cache 
	 * then this call updates the element. The element will expire after timeToLiveSeconds
	 * This will also make sure that
	 * the input object is wrapped in ICacheElement before setting into the cache
	 * @param key
	 * @param value
	 * @param timeToLiveSeconds
	 */
	public void put(java.lang.Object key, java.lang.Object value, int timeToLiveSeconds);
	
	/**
	 * add the element in cache and if the element is already in the cache 
	 * then ignores the add
	 * @param key
	 * @param value
	 * @return true if add is success else return false
	 */
	public boolean add(java.lang.Object key, java.lang.Object value);
	
	/**
	 * add the element in cache and if the element is already in the cache 
	 * then ignores the add
	 * @param key
	 * @param value
	 * @return true if add is success else return false
	 */
	public boolean add(java.lang.Object key, java.lang.Object value, int timToLiveSeconds);

	/**
	 * Remove the element from cache for the given name. 
	 * @param key
	 * @return false if the element is not in the cache
	 */
	public boolean remove(java.lang.Object key);

	/**
	 * Remove the element from cache for the given name. 
	 * @param key
	 * @return false if the element is not in the cache
	 */
	public boolean removeAll();
	
	/**
	 * Get the update frequency for the cache element, if set to -1 then it is never updated
	 * @return
	 */
	public long getUpdateFrequency();
	
	/**
	 * Specifies the maximum amount of time that the object should live in cache. After the specified time 
	 * object will be removed  from the cache. Default value is 2 minutes. If the value is <0 then the object 
	 * will live in the cache forever, however if the maximum number of objects exceed then the object will be 
	 * removed from cache  using LRU.	
	 */
	public void setTimeToLiveSeconds(long seconds);
	
	public long getTimeToLiveSeconds();
	
	/**
	 * Maximum element in the cache region.  If the number exceeds system uses the 
	 * LRU algorithm to replace the old one with new one. If not specified it takes the default value which 
	 * is currently set to 10K
	 * @return
	 */
	public int getMaxElementsInCache();

	public void setMaxElementsInCache(int maxElementsInCache);
}
