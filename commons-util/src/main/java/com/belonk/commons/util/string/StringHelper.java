package com.belonk.commons.util.string;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通用字符串处理工具类。
 * <p/>
 * <p>Created by sun on 2016/5/30.
 *
 * @author sunfuchang03@126.com
 * @version 1.0
 * @since 1.1
 */
public class StringHelper extends StringUtils {
    //~ Static fields/initializers =====================================================================================
    private static final Logger LOG = LoggerFactory.getLogger(StringHelper.class);

    //~ Instance fields ================================================================================================

    //~ Methods ========================================================================================================

    private StringHelper() {
    }

    /**
     * 将给定字符串首字符大写。
     *
     * @param src
     * @return
     */
    public static String firstUpper(String src) {
        if (isBlank(src)) return "";
        return src.substring(0, 1).toUpperCase() + src.substring(1, src.length());
    }

    /**
     * 将给定字符串首字母小写。
     *
     * @param src
     * @return
     */
    public static String firstLower(String src) {
        if (isBlank(src)) return "";
        return src.substring(0, 1).toLowerCase() + src.substring(1, src.length());
    }
}
