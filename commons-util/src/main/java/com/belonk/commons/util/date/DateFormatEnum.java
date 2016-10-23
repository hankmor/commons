package com.belonk.commons.util.date;

/**
 * 日期格式枚举类，提供基础的格式化枚举值
 *
 * @author sunfuchang03@126.com
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
    HH_MM("HH:mm");

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
