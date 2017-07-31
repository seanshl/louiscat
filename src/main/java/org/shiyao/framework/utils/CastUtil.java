package org.shiyao.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CastUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CastUtil.class);

    public static Long castLong(Object v) {
        Long res = null;
        try {
            if (v instanceof Long) {
                res = (Long)v;
            } else if (v instanceof String) {
                res = Long.valueOf((String)v);
            }
        } catch (ClassCastException e) {
            LOGGER.error("Can not cast type to Long", e.getMessage());
        }

        return res;
    }
}
