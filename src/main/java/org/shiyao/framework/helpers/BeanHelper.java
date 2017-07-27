package org.shiyao.framework.helpers;

import org.shiyao.framework.utils.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class BeanHelper {
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();
    private static Logger LOGGER = LoggerFactory.getLogger(BeanHelper.class);

    static {
        LOGGER.info("Begin loading BeanHelper...");

        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> beanClass : beanClassSet) {
            Object obj = ReflectionUtil.createNewInstance(beanClass);
            BEAN_MAP.put(beanClass, obj);
        }

        LOGGER.info("Finish loading BeanHelper...");
    }

    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    public static <T> T getBean(Class<T> cls) {
        if (!BEAN_MAP.containsKey(cls)) {
            throw new RuntimeException("Can not get bean by class: " + cls.getName());
        }

        return (T) BEAN_MAP.get(cls);
    }
}
