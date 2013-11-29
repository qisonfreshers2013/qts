package com.qts.common.logger;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.spi.LoggingEvent;

/**
 * @author RAMMOHAN
 *
 */
public class Logger {
    org.apache.log4j.Logger logger;
    static boolean disabled = false;

    //private static String LOG_CONFIG_PATH = ConfigConstants.getLoggerDir();

    static {
        //loadConfig();
    }

    /**
     * Use -Dlog4j.debug=true to debug log4j.properties loading problems.
     */
    /*public static void loadConfig() {
        if (LOG_CONFIG_PATH.equals("DEFAULT-CONFIG")) return;

        System.out.println("loading logger config from " + LOG_CONFIG_PATH);
        if (new File(LOG_CONFIG_PATH).exists()) {
            PropertyConfigurator.configure(LOG_CONFIG_PATH);
        } else {
            System.out.println("Warning: qts logging was not configured properly. Check home directory for qts-log4j.properties.");
        }
    }*/

    /*public static String getLogOutputFilePath() {
        PropertyConfigurator.configure(LOG_CONFIG_PATH);
        return null;
    }*/

    public Logger(org.apache.log4j.Logger logger) {
        this.logger = logger;
    }

    static Map<String, Logger> logs = Collections.synchronizedMap(new HashMap<String, Logger>());

    public static Logger getLogger(Class<?> cls) {
        return getLogger(cls.getName());
    }

    public static Logger getLogger(String name) {
        Logger result = logs.get(name);
        if (result == null) {
            result = new Logger(org.apache.log4j.Logger.getLogger(name));
            logs.put(name, result);
        }
        return result;
    }

    public void setAdditivity(boolean value) {
        if (logger != null) {
            logger.setAdditivity(value);
        }
    }

    // DEBUG level

    public boolean isDebug() {
        return !disabled && ((logger != null) && logger.isDebugEnabled());
    }

    public void debug(String message, Throwable t) {
        if (logger != null && !disabled) {
            logger.log(org.apache.log4j.Level.DEBUG, message, t);
        }
    }

    public void debug(String message, Object... args) {
        if (logger != null && !disabled) {
            logger.log(org.apache.log4j.Level.DEBUG, formatMessage(message, args));
        }
    }

    // INFO level

    public boolean isInfo() {
        return (logger != null) && logger.isInfoEnabled();
    }

    public void info(String message, Throwable t) {
        if (logger != null && !disabled) {
            logger.log(org.apache.log4j.Level.INFO, message, t);
        }
    }

    public void info(String message, Object... args) {
        if (logger != null && !disabled) {
            logger.log(org.apache.log4j.Level.INFO, formatMessage(message, args));
        }
    }

    // WARN level
    public void warn(String message, Throwable t) {
        if (logger != null && !disabled) {
            logger.log(org.apache.log4j.Level.WARN, message, t);
        }
    }

    public void warn(String message, Object... args) {
        if (logger != null && !disabled) {
            logger.log(org.apache.log4j.Level.WARN, formatMessage(message, args));
        }
    }

    // ERROR level
    public void error(String message, Throwable t) {
        if (logger != null && !disabled) {
            logger.log(org.apache.log4j.Level.ERROR, message, t);
        }
    }

    public void error(String message, Object... args) {
        if (logger != null && !disabled) {
            logger.log(org.apache.log4j.Level.ERROR, formatMessage(message, args));
        }
    }

    // FATAL level
    public void fatal(String message, Throwable t) {
        if (logger != null && !disabled) {
            logger.log(org.apache.log4j.Level.ERROR, message, t);
        }
    }

    public void fatal(String message, Object... args) {
        if (logger != null && !disabled) {
            logger.log(org.apache.log4j.Level.FATAL, formatMessage(message, args));
        }
    }

    public void log(LoggingEvent event) {
        if (logger != null && !disabled) {
            logger.callAppenders(event);
        }
    }

    public void disable() {
        disabled = true;
    }

    String formatMessage(String message, Object[] args) {
        // verify that there are no invalid format params in the message
        try {
            return MessageFormat.format(message, args);
        }
        catch (IllegalArgumentException ex) {
            return message;
        }
    }
    
    // BUSINESS level
    /*public void business(String message, Throwable t) {
        if (logger != null && !disabled) {
            logger.log(BusinessLogLevel.BUSINESS_LEVEL, message, t);
        }
    }

    public void business(String message, Object... args) {
        if (logger != null && !disabled) {
            logger.log(BusinessLogLevel.BUSINESS_LEVEL, formatMessage(message, args));
        }
    }
    
    public boolean isBusiness() {
        return !disabled && ((logger != null) && logger.getLevel().isGreaterOrEqual(BusinessLogLevel.BUSINESS_LEVEL));
    }*/
}