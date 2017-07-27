package org.shiyao.framework.helpers;

import org.shiyao.framework.annotation.Controller;
import org.shiyao.framework.annotation.Service;
import org.shiyao.framework.utils.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * ClassHelper provides a set of methods helping the client to fetch different types of class.
 * It is s layer built on ClassUtil
 */
public final class ClassHelper {
    private static final Set<Class<?>> CLASS_SET;
    private static Logger LOGGER = LoggerFactory.getLogger(ClassHelper.class);

    static {
        LOGGER.info("Begin loading ClassHelper..");
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
        LOGGER.info("End loading ClassHelper...");
    }

    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * Retrieve all Service classes under the base package.
     * @return
     */
    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Service.class)) {
                classSet.add(cls);
            }
        }

        return classSet;
    }

    /**
     * Retrieve all Controller classes under the base package.
     * @return
     */
    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Controller.class)) {
                classSet.add(cls);
            }
        }

        return classSet;
    }

    /**
     * Retrieve all classes under the base package.
     * @return
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> beanClassSet = new HashSet<>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());

        return beanClassSet;
    }
}
