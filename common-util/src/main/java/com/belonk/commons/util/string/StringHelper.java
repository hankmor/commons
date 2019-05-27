package com.belonk.commons.util.string;

import org.apache.commons.lang.ArrayUtils;
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

    // ~ 1.2版本添加

    /**
     * 进行字符串格式化
     *
     * @param target 目标字符串
     * @param params format 参数
     * @return format 后的
     */
    public static String format(String target, Object... params) {
        if (target.contains("%s") && ArrayUtils.isNotEmpty(params)) {
            return String.format(target, params);
        }
        return target;
    }

    /**
     * 判断字符串是不是驼峰命名
     * <li> 包含 '_' 不算 </li>
     * <li> 首字母大写的不算 </li>
     *
     * @param str 字符串
     * @return 结果
     */
    public static boolean isCamel(String str) {
        if (str.contains(StringPool.UNDERSCORE)) {
            return false;
        }
        return Character.isLowerCase(str.charAt(0));
    }

    /**
     * 字符串驼峰转下划线格式
     *
     * @param param 需要转换的字符串
     * @return 转换后的字符串
     */
    public static String camelToUnderscore(String param) {
        if (isEmpty(param)) {
            return EMPTY;
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c) && i > 0) {
                sb.append(StringPool.UNDERSCORE);
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    /**
     * 字符串下划线转驼峰格式
     *
     * @param param 需要转换的字符串
     * @return 转换好的字符串
     */
    public static String underscoreToCamel(String param) {
        if (isEmpty(param)) {
            return EMPTY;
        }
        String temp = param.toLowerCase();
        int len = temp.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = temp.charAt(i);
            if (c == StringPool.UNDERSCORE.charAt(0)) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(temp.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
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

        String str = "this_is_underscore";
        System.out.println(StringHelper.underscoreToCamel(str));
        System.out.println(StringHelper.camelToUnderscore(StringHelper.underscoreToCamel(str)));
    }
}
