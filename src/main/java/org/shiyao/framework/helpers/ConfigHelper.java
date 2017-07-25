package org.shiyao.framework.helpers;

import org.shiyao.framework.ConfigConstant;
import org.shiyao.framework.utils.PropsUtil;

import java.util.Properties;

public final class ConfigHelper {
    private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);
}
