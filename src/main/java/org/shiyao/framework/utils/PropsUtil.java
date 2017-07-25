package org.shiyao.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * A util class help dealing with different properties.
 */
public class PropsUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    public static Properties loadProps(String fileName) {
        Properties props = null;

        InputStream is = null;
        try {{
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (is == null) {
                throw new FileNotFoundException(fileName + " not found");
            }

            props = new Properties();
            props.load(is);
        }} catch (Exception e) {
            //TODO Figure out the logger
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    //TODO log the error
                }
            }
        }

        return props;
    }

    /**
     * Obtain String property
     * @param props
     * @param key
     * @return
     */
    public static String getString(Properties props, String key) {
        return getString(props, "");
    }

    /**
     * Obtain String property with default value
     * @param props
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(Properties props, String key, String defaultValue) {
        if (props.contains(key)) {
            return props.getProperty(key);
        } else {
            return defaultValue;
        }
    }

    public static Integer getInteger(Properties props, String key) {
        return getInteger(props, key, null);
    }

    /**
     * Get property as Integer.
     * @param props
     * @param key
     * @param defaultValue
     * @return
     */
    public static Integer getInteger(Properties props, String key, Integer defaultValue) {
        if (props.contains(key)) {
            try {
                Integer value = Integer.parseInt(props.getProperty(key));
                return value;
            } catch (Exception e) {
                LOGGER.error("Error parsing property value: " + props.getProperty(key));
            }
        }

        return defaultValue;
    }

    public static Boolean getBoolean(Properties props, String key) {
        return getBoolean(props, key, false);
    }

    public static Boolean getBoolean(Properties props, String key, Boolean defaultValue) {
        if (props.contains(key)) {
            Boolean value = "true".equalsIgnoreCase(props.getProperty(key));
            return value;
        } else {
            return defaultValue;
        }
    }
}
