package com.qts.common;

import java.io.IOException;
import java.util.Properties;

/**
 * Reads a configuration file 
 * @author Vinay Thandra
 *
 */
public class ConfigReader {

	public static Properties getProperties(String propertiesFile) throws IOException{
		Properties properties = new Properties();
		properties.load(new ConfigReader().getClass().getClassLoader().getResourceAsStream(propertiesFile));
		return properties;
	}
	
}
