/**
 * 
 */
package com.qts.common.cache.memcached;

import com.danga.MemCached.SockIOPool;

/**
 * Instance of this class used to initialize memcached cache configuration
 * @author Vinay Thandra
 *
 */
public class MemcachedInitializer {

	private static MemcachedInitializer memcachedInitializer = null;
	
	/**
	 * Get the instance of memed cache initializer
	 * @return
	 */
	public static synchronized MemcachedInitializer getInstance() {
		if(memcachedInitializer == null) {
			memcachedInitializer = new MemcachedInitializer();
		}		
		return memcachedInitializer;
	}
	
	public void initialize(String serversListAsString) {
		
		// server list and weights
		String[] servers = serversListAsString.split(",");

		// grab an instance of our connection pool
		SockIOPool pool = SockIOPool.getInstance();

		// set the servers and the weights
		pool.setServers( servers );

		// set some basic pool settings
		// 5 initial, 5 min, and 250 max conns
		// and set the max idle time for a conn
		// to 6 hours
		pool.setInitConn( 5 );
		pool.setMinConn( 5 );
		pool.setMaxConn( 250 );
		pool.setMaxIdle( 1000 * 60 * 60 * 6 );
		pool.setHashingAlg(SockIOPool.CONSISTENT_HASH);

		// set the sleep for the main thread
		// it will wake up every x seconds and
		// maintain the pool size
		pool.setMaintSleep( 30 );

		// set some TCP settings
		// disable nagle
		// set the read timeout to 3 secs
		// and don't set a connect timeout
		pool.setNagle( false );
		pool.setSocketTO( 3000 );
		pool.setSocketConnectTO( 0 );

		// initialize the connection pool
		if(!pool.isInitialized()) {
			pool.initialize();		
		}
		
	}
}
