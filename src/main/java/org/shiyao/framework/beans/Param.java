package org.shiyao.framework.beans;

import org.shiyao.framework.utils.CastUtil;

import java.util.Map;

public class Param {

    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public Long getLong(String name) {
        return CastUtil.castLong(paramMap.get(name));
    }
}
