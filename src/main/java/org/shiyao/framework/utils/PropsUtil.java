package org.shiyao.framework.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * A util class help dealing with different properties.
 */
public class PropsUtil {
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


}
