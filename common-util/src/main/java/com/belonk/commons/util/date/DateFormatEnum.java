package com.belonk.commons.util.date;

/**
 * 日期格式枚举类，提供基础的格式化枚举值。
 *
 * @author sunfuchang03@126.com
 * @version 1.3
 * @since 1.0
 */
public enum DateFormatEnum {

    /**
     * 日期格式化为(年-月-日):yyyy-MM-dd
     */
    YYYY_MM_DD("yyyy-MM-dd", DateFormatType.DATE),

    /**
     * 日期格式化为(年-月-日 时:分:秒):yyyy-MM-dd HH:mm:ss
     */
    YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss", DateFormatType.DATE_TIME),

    /**
     * 日期格式化为(年月日时分秒):yyyyMMddHHmmss
     */
    YYYYMMDDHHMMSS("yyyyMMddHHmmss", DateFormatType.DATE_TIME),

    /**
     * 日期格式化为(年月日):yyyyMMdd
     */
    YYYYMMDD("yyyyMMdd", DateFormatType.DATE),

    /**
     * 日期格式化为(时:分:秒):HH:mm:ss
     */
    HH_MM_SS("HH:mm:ss", DateFormatType.TIME),

    /**
     * 日期格式化为(时分秒):HHmmss
     */
    HHMMSS("HHmmss", DateFormatType.TIME),

    /**
     * 日期格式化为（月-日）:MM-dd
     */
    MM_DD("MM-dd", DateFormatType.DATE_MONTH_DAY),

    /**
     * 日期格式化为（时:分）:HH:mm
     */
    HH_MM("HH:mm", DateFormatType.TIME_HOUR_MINUTE),

    // since 1.2 add

    /**
     * 日期格式化为(年/月/日):yyyy/MM/dd
     */
    YYYY_MM_DD_SLASH("yyyy/MM/dd", DateFormatType.DATE),

    /**
     * 日期格式化为(年-月-日 时:分:秒):yyyy-MM-dd HH:mm:ss
     */
    YYYY_MM_DD_HH_MM_SS_S("yyyy-MM-dd HH:mm:ss.SSS", DateFormatType.DATE_TIME),

    /**
     * 日期格式化为(年月日时分秒):yyyyMMddHHmmssS
     */
    YYYYMMDDHHMMSSS("yyyyMMddHHmmssSSS", DateFormatType.DATE_TIME),

    /**
     * 日期格式化为(年/月/日 时:分:秒):yyyy/MM/dd HH:mm:ss
     */
    YYYY_MM_DD_HH_MM_SS_SLASH("yyyy/MM/dd HH:mm:ss", DateFormatType.DATE_TIME),

    YYYY_MM_DD_HH_MM_SLASH("yyyy/MM/dd HH:mm", DateFormatType.DATE_TIME_HOUR_MINUTE),

    YYYY_MM_DD_HH_MM_SS_S_SLASH("yyyy/MM/dd HH:mm:ss.SSS", DateFormatType.DATE_TIME),

    MM_DD_SLASH("MM/dd", DateFormatType.DATE_MONTH_DAY),

    /**
     * 日期格式化为:yyyy年MM月dd日
     */
    YYYY_MM_DD_ZN("yyyy年MM月dd日", DateFormatType.DATE),

    /**
     * 日期格式化为(年/月/日 时:分:秒):yyyy/MM/dd HH:mm:ss
     */
    YYYY_MM_DD_HH_MM_SS_ZN("yyyy年MM月dd日 HH:mm:ss", DateFormatType.DATE_TIME),

    YYYY_MM_DD_HH_MM_ZN("yyyy年MM月dd日 HH:mm", DateFormatType.DATE_TIME_HOUR_MINUTE),

    YYYY_MM_DD_HH_MM_SS_S_ZN("yyyy年MM月dd日 HH:mm:ss.SSS", DateFormatType.DATE_TIME),

    MM_DD_ZN("MM月dd日", DateFormatType.DATE_MONTH_DAY),

    YYYYMM("yyyyMM", DateFormatType.DATE_YEAR_MONTH),

    YYYY_MM("yyyy-MM", DateFormatType.DATE_YEAR_MONTH),

    YYYY_MM_ZN("yyyy年MM月", DateFormatType.DATE_YEAR_MONTH),

    YYYY_MM_SLASH("yyyy/MM", DateFormatType.DATE_YEAR_MONTH),

    /**
     * 日期格式化为(年-月-日 时:分):yyyy-MM-dd HH:mm
     */
    YYYY_MM_DD_HH_MM("yyyy-MM-dd HH:mm", DateFormatType.DATE_TIME_HOUR_MINUTE),

    YYYYMMDDHHMM("yyyyMMddHHmm", DateFormatType.DATE_TIME_HOUR_MINUTE),

    ;

    public static final String DATE_TIME_SPLITTER = " ";

    private String value;

    /**
     * @since 1.3
     */
    private int formatType;

    DateFormatEnum(String value) {
        this.value = value;
    }

    DateFormatEnum(String value, int formatType) {
        this.value = value;
        this.formatType = formatType;
    }

    /**
     * 获取格式化字符串
     *
     * @return 枚举定义的格式化字符串
     */
    public String getValue() {
        return value;
    }

    public int formatType() {
        return formatType;
    }

    /**
     * Date format type.
     *
     * @since 1.3
     */
    public static class DateFormatType {
        /**
         * Date
         * <p>
         * Only contains year,month and day.
         */
        public static final int DATE = 1;

        /**
         * Month_day of date.
         * <p>
         * Only contains month and day.
         */
        public static final int DATE_MONTH_DAY = DATE + 1;

        /**
         * Date_year_month
         * <p>
         * Contains year,month only.
         */
        public static final int DATE_YEAR_MONTH = DATE_MONTH_DAY + 1;

        /**
         * Datetime
         * <p>
         * Contains year,onth,day and hour,minute,second.
         */
        public static final int DATE_TIME = DATE_YEAR_MONTH + 1;

        /**
         * Date_time_hour_minute
         * <p>
         * Contains date and hour,minute.
         */
        public static final int DATE_TIME_HOUR_MINUTE = DATE_TIME + 1;

        /**
         * Time
         * <p>
         * Only contains hour,minute,second.
         */
        public static final int TIME = DATE_TIME_HOUR_MINUTE + 1;

        /**
         * Hour_minute of time.
         * <p>
         * Only contains hour and minute.
         */
        public static final int TIME_HOUR_MINUTE = TIME + 1;
    }
}
