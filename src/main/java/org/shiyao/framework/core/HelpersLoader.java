package org.shiyao.framework.core;

import org.shiyao.framework.helpers.BeanHelper;
import org.shiyao.framework.helpers.ClassHelper;
import org.shiyao.framework.helpers.ControllerHelper;
import org.shiyao.framework.helpers.DependencyInjectionHelper;
import org.shiyao.framework.utils.ClassUtil;

/**
 * Load helpers when initializating
 */
public class HelpersLoader {
    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                DependencyInjectionHelper.class,
                ControllerHelper.class
        };

        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName());
        }
    }
}
