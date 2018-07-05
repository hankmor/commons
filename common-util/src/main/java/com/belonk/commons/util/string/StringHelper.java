package com.belonk.commons.util.string;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

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

    /**
     * 模糊一个字符串。
     *
     * @param start             开始模糊的位置，从0开始
     * @param length            模糊多少位长度
     * @param blurDisplayLength 模糊后的显示为多少长度，默认与length相同
     * @param blurKey           模糊的占位符，默认为“*”
     * @return 模糊后的手机号码
     */
    public static String blurStr(String str, int start, int length, Integer blurDisplayLength, String blurKey) {
        if (isBlank(str))
            return str;
        if (isBlank(blurKey)) {
            blurKey = "*";
        }
        if (blurDisplayLength == null || blurDisplayLength <= 0)
            blurDisplayLength = length;
        if (start < 0 || length <= 0 || length >= str.length()
                || blurDisplayLength >= str.length() || start + length > str.length())
            return str;
        String startStr = str.substring(0, start);
        String endStr = str.substring(start + length, str.length());
        return startStr + repeat(blurKey, blurDisplayLength) + endStr;
    }

    /**
     * 获取32为UUID字符串。
     * <p>
     * 该方法会去掉UUID中间所有的“-”。
     *
     * @param upper 是否返回大写，true - 大写，否则小写。
     * @return 32为uuid
     */
    public static String uuid32(boolean upper) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return upper ? uuid.toUpperCase() : uuid;
    }

    /**
     * string转换为unicode。
     *
     * @param string
     * @return
     */
    public static String string2Unicode(String string) {
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            // 取出每一个字符
            char c = string.charAt(i);
            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }
        return unicode.toString();
    }

    /**
     * unicode 转字符串
     */
    public static String unicode2String(String unicode) {
        StringBuffer string = new StringBuffer();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);
            // 追加成string
            string.append((char) data);
        }
        return string.toString();
    }

    public static void main(String[] args) {
        String s = "15100000000";
        System.out.println(s.replaceAll("(.{3})", "_"));
        String blurStr = StringHelper.blurStr(s, 7, 4, 4, null);
        System.out.println(blurStr);
        blurStr = StringHelper.blurStr(s, 7, 10, 4, null);
        System.out.println(blurStr);
        blurStr = StringHelper.blurStr(s, 0, 0, 3, null);
        System.out.println(blurStr);
        blurStr = StringHelper.blurStr(s, 0, 0, 0, null);
        System.out.println(blurStr);
        String[] ss = new String[3];
        ss[0] = "a";
        ss[1] = "bb";
        ss[2] = "cc";
        System.out.println(StringHelper.join(ss, ","));

        System.out.println(StringHelper.uuid32(false));
        System.out.println(StringHelper.uuid32(true));
    }
}
