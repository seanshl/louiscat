package org.shiyao.framework.helpers;

import org.shiyao.framework.annotation.Inject;
import org.shiyao.framework.utils.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;

public class DependencyInjectionHelper {
    private static Logger LOGGER = LoggerFactory.getLogger(DependencyInjectionHelper.class);
    static {
        LOGGER.info("Begin loading DependencyInjectionHelper and autowiring fields.");
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();

        for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
            Class<?> beanClass = beanEntry.getKey();
            Object beanInstance = beanEntry.getValue();

            Field[] beanFields = beanClass.getDeclaredFields();
            for (Field beanField : beanFields) {
                if (beanField.isAnnotationPresent(Inject.class)) {
                    Class<?> beanFieldClass = beanField.getType();
                    Object beanFieldInstance = beanMap.get(beanFieldClass);

                    if (beanFieldInstance == null) {
                        LOGGER.error("Can't find bean: " + beanFieldClass.getName());
                    } else {
                        ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                    }
                }
            }
        }
    }
}
