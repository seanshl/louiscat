package org.shiyao.framework.utils;

import java.util.Map;
import java.util.Set;

public final class BeanUtil {

    private static final Map<Class<?>, Object> BEAN_MAP;

    static {
        Set<Class<?>> beanClassSet = ClassUtil.getClassSet();
    }
}
