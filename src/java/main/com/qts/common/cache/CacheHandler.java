package com.qts.common.cache;

import java.io.IOException;
import java.util.Properties;

import com.qts.common.Constants;
import com.qts.common.ConfigReader;
import com.qts.common.cache.memcached.MemcachedInitializer;


/**
 * 
 * @author Vinay Thandra
 *
 */
public class CacheHandler {

	private static CacheHandler INSTANCE = null;
	
	public static CacheHandler getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new CacheHandler();
        }
        return INSTANCE;
	}
	
	public void initService() throws IOException{
		Properties props = ConfigReader.getProperties(Constants.MEMCACHED_FILE);
		String servers = props.getProperty(Constants.MEMCACHED_SERVERS);
		MemcachedInitializer.getInstance().initialize(servers);
	}
}
