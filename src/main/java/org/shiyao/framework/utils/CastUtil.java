package org.shiyao.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CastUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CastUtil.class);

    public Long castLong(String v) {
        Long res = null;
        try {
            res = Long.valueOf(v);
        } catch (ClassCastException e) {
            LOGGER.error("Can not cast type to Long", e.getMessage());
        }

        return res;
    }
}
