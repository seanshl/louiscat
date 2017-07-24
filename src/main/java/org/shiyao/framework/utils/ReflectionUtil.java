package org.shiyao.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ReflectionUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    public static Object createNewInstance(Class<?> cls) {
        Object instance;
        try {
            instance = cls.newInstance();
        } catch (Exception e) {
            LOGGER.error("Failed creating new instance", e.getMessage());
            throw new RuntimeException(e);
        }

        return instance;
    }


}
