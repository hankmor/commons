package com.belonk.commons.util.date;

/**
 * 日期格式枚举类，提供基础的格式化枚举值
 *
 * @author sunfuchang03@126.com
 * @version 1.2
 * @since 1.0
 */
public enum DateFormatEnum {

    /**
     * 日期格式化为(年-月-日):yyyy-MM-dd
     */
    YYYY_MM_DD("yyyy-MM-dd"),

    /**
     * 日期格式化为(年月日):yyyyMMdd
     */
    YYYYMMDD("yyyyMMdd"),

    /**
     * 日期格式化为(时:分:秒):HH:mm:ss
     */
    HH_MM_SS("HH:mm:ss"),

    /**
     * 日期格式化为(时分秒):HHmmss
     */
    HHMMSS("HHmmss"),

    /**
     * 日期格式化为(年-月-日 时:分:秒):yyyy-MM-dd HH:mm:ss
     */
    YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),

    /**
     * 日期格式化为(年月日时分秒):yyyyMMddHHmmss
     */
    YYYYMMDDHHMMSS("yyyyMMddHHmmss"),

    /**
     * 日期格式化为（月-日）:MM-dd
     */
    MM_DD("MM-dd"),

    /**
     * 日期格式化为（时:分）:HH:mm
     */
    HH_MM("HH:mm"),

    // since 1.2 add

    /**
     * 日期格式化为(年/月/日):yyyy/MM/dd
     */
    YYYY_MM_DD_SLASH("yyyy/MM/dd"),

    /**
     * 日期格式化为(年-月-日 时:分:秒):yyyy-MM-dd HH:mm:ss
     */
    YYYY_MM_DD_HH_MM_SS_S("yyyy-MM-dd HH:mm:ss.S"),

    /**
     * 日期格式化为(年月日时分秒):yyyyMMddHHmmssS
     */
    YYYYMMDDHHMMSSS("yyyyMMddHHmmssS"),

    /**
     * 日期格式化为(年/月/日 时:分:秒):yyyy/MM/dd HH:mm:ss
     */
    YYYY_MM_DD_HH_MM_SS_SLASH("yyyy/MM/dd HH:mm:ss"),

    YYYY_MM_DD_HH_MM_SS_S_SLASH("yyyy/MM/dd HH:mm:ss.S"),

    MM_DD_SLASH("MM/dd"),

    /**
     * 日期格式化为:yyyy年MM月dd日
     */
    YYYY_MM_DD_ZN("yyyy年MM月dd日"),

    /**
     * 日期格式化为(年/月/日 时:分:秒):yyyy/MM/dd HH:mm:ss
     */
    YYYY_MM_DD_HH_MM_SS_ZN("yyyy年MM月dd日 HH:mm:ss"),

    YYYY_MM_DD_HH_MM_SS_S_ZN("yyyy年MM月dd日 HH:mm:ss.S"),

    MM_DD_ZN("MM月dd日"),

    YYYYMM("yyyyMM"),

    YYYY_MM("yyyy-MM-dd"),

    YYYY_MM_ZN("yyyy年MM月"),

    YYYY_MM_SLASH("yyyy/MM"),

    ;

    public static final String DATE_TIME_SPLITTER = " ";

    private String value;

    private DateFormatEnum(String value) {
        this.value = value;
    }

    /**
     * 获取格式化字符串
     *
     * @return 枚举定义的格式化字符串
     */
    public String getValue() {
        return value;
    }

}
