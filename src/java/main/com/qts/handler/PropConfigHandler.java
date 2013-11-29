package com.qts.handler;

import com.qts.common.Utils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Vamsi Kuchi
 * Date: 2/19/13
 * Time: 6:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropConfigHandler implements PropFileConstants{

    private static PropConfigHandler INSTANCE;
    private static Map<String, ResourceBundle> resouceBundleMap = null;

    private PropConfigHandler() {
               loadProperties();
    }

    public static PropConfigHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PropConfigHandler();
        }
        return INSTANCE;
    }

    private void loadProperties() {
        try {
            resouceBundleMap = new HashMap<String, ResourceBundle>();
            Class<? extends PropConfigHandler> loader = this.getClass();
            final ClassLoader classLoader = loader.getClassLoader();
            InputStream resourceAsStream  = null;
            if(Utils.isWindows()){
                resourceAsStream = classLoader.getResourceAsStream("propertiesFilesList_windows.properties");
            }else if(Utils.isUnix()){
                resourceAsStream = classLoader.getResourceAsStream("propertiesFilesList.properties");
            }

            ResourceBundle propertyResourceBundle = new PropertyResourceBundle(resourceAsStream);
            Enumeration<String> propertyFileKey = propertyResourceBundle.getKeys();
            while (propertyFileKey.hasMoreElements()) {
                String key = propertyFileKey.nextElement();
                String propertyFile = propertyResourceBundle.getString(key);
                InputStream stream = classLoader.getResourceAsStream(propertyFile);
                ResourceBundle propResBundle = new PropertyResourceBundle(stream);
                resouceBundleMap.put(key, propResBundle);
                System.out.println("Loading configuration for " + key + " is completed..");
                stream = null;
            }
            resourceAsStream = null;
        } catch (IOException e) {
            System.exit(0);
            e.printStackTrace();
        }
    }

    public String getValue(String fileName, String key) {
        ResourceBundle resourceBundle = resouceBundleMap.get(fileName);
        if (null == resourceBundle) {
            System.out.println(" No Resource found with given Property key :" + fileName);
            return null;
        }
        String value = resourceBundle.getString(key);
        return StringUtils.isBlank(value) ? "" : value;
    }


}
