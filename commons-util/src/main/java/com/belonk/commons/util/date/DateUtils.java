package com.belonk.commons.util.date;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理工具类。
 *
 * @author sunfuchang03@126.com
 * @since v2.0
 */
public class DateUtils {

    /**
     * 将日期类型转换为指格式的字符串类型
     *
     * @param date       日期对象
     * @param formatEnum 格式化枚举对象
     * @return 格式化后的字符串日期
     */
    public static String dateToString(Date date, DateFormatEnum formatEnum) {
        if (null == formatEnum) {
            throw new NullPointerException("formatEnum值不能为Null");
        }
        if (null == date) {

            return "";
        }
        return dateToString(date, formatEnum.getValue());
    }

    /**
     * 将日期类型转换为指定格式的字符串类型
     *
     * @param date   日期对象
     * @param format 格式化字符串
     * @return 格式化后的字符串日期
     */
    public static String dateToString(Date date, String format) {
        if (null == date || StringUtils.isEmpty(format)) {
            throw new NullPointerException("date值对象或format值不能为Null");
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    /**
     * 将日期字符串根据指定的格式转换为日期对象
     *
     * @param date       日期字符串
     * @param formatEnum 格式化枚举对象
     * @return 格式化后的日期对象
     * @throws ParseException 抛出转换失败的异常
     */
    public static Date stringToDate(String date, DateFormatEnum formatEnum) throws ParseException {
        if (null == formatEnum) {
            throw new NullPointerException("formatEnum值不能为null");
        }
        return stringToDate(date, formatEnum.getValue());
    }

    /**
     * 将日期字符串根据指定的格式转换为日期对象
     *
     * @param date   日期字符串
     * @param format 格式化字符串
     * @return 格式化后的日期对象
     * @throws ParseException 抛出转换失败的异常
     */
    public static Date stringToDate(String date, String format) throws ParseException {
        if (null == date || StringUtils.isEmpty(format)) {
            throw new NullPointerException("date值或format值不能为Null");
        }

        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.parse(date);
    }

    /**
     * 比较两个日期相差多少天，单位（天）
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     */
    public static int compareDay(Date startDate, Date endDate) {
        Calendar small = Calendar.getInstance();
        small.setTime(startDate);
        Calendar big = Calendar.getInstance();
        big.setTime(endDate);

        small.set(Calendar.HOUR_OF_DAY, 0);
        small.set(Calendar.MINUTE, 0);
        small.set(Calendar.SECOND, 0);
        small.set(Calendar.MILLISECOND, 0);

        big.set(Calendar.HOUR_OF_DAY, 0);
        big.set(Calendar.MINUTE, 0);
        big.set(Calendar.SECOND, 0);
        big.set(Calendar.MILLISECOND, 0);

        Long smallMills = small.getTimeInMillis();
        Long bigMills = big.getTimeInMillis();

        int days = (int) ((bigMills - smallMills) / 24 / 3600 / 1000);

        return days;
    }

    /**
     * 将给定日期转换为该日期的某一时间（时/分/秒/毫秒）。
     * <p/>
     * <p>如：将2015-11-11 22:33:11 转为 2015-11-11 12:00:00</p>
     *
     * @param date        日期
     * @param hour        小时
     * @param minute      分钟
     * @param second      秒
     * @param millisecond 毫秒
     * @return 转换后日期
     */
    public static Date transToTimePointOfDate(Date date, int hour, int minute, int second, int millisecond) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        cal.set(Calendar.MILLISECOND, millisecond);
        return cal.getTime();
    }

    /**
     * 取给定日期的后一天。
     *
     * @param date
     * @return
     */
    public static Date nextDate(Date date) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * 取给定日期的前一天。
     *
     * @param date
     * @return
     */
    public static Date prevDate(Date date) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    /**
     * 判断目标日期是否在某段时间之间。
     *
     * @param dest  目标时间
     * @param start 开始时间
     * @param end   结束时间
     * @return true - 在， false - 没在
     */
    public static boolean between(Date dest, Date start, Date end) {
        if (dest == null || start == null || end == null)
            return false;
        long destMilliseconds = dest.getTime();
        long startMilliseconds = start.getTime();
        long endMilliseconds = end.getTime();
        if (endMilliseconds < startMilliseconds)
            return false;
        return startMilliseconds <= destMilliseconds && destMilliseconds <= endMilliseconds;
    }

    /**
     * 天数加运算，计算某一日期多少天后的日期。
     *
     * @param src 原日期
     * @param day 多少天后
     * @return 多少天后日期
     */
    public static Date after(Date src, int day) {
        if (src == null) return null;
        if (day <= 0) return src;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(src);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    /**
     * 天数减运算，计算某一日期多少天前的日期。
     *
     * @param src 原日期
     * @param day 多少天前
     * @return 多少天前日期
     */
    public static Date before(Date src, int day) {
        if (src == null) return null;
        if (day <= 0) return src;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(src);
        calendar.add(Calendar.DAY_OF_MONTH, -day);
        return calendar.getTime();
    }

    /**
     * 月份加运算，计算月份之后的某一个日期。
     *
     * @param src   原日期
     * @param month 多少月份之后
     * @return 最终日期
     */
    public static Date afterMonth(Date src, int month) {
        if (src == null) return null;
        if (month <= 0) return src;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(src);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    /**
     * 月份减运算，计算月份之前的某一个日期。
     *
     * @param src   原日期
     * @param month 多少月份之前
     * @return 最终日期
     */
    public static Date beforeMonth(Date src, int month) {
        if (src == null) return null;
        if (month <= 0) return src;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(src);
        calendar.add(Calendar.MONTH, -month);
        return calendar.getTime();
    }

    /**
     * 年份加运算，计算年少年后的某一个日期。
     *
     * @param src  原日期
     * @param year 多少年之后
     * @return 最终日期
     */
    public static Date afterYear(Date src, int year) {
        if (src == null) return null;
        if (year <= 0) return src;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(src);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 年份减运算，计算少多少年之前的某一个日期。
     *
     * @param src  原日期
     * @param year 多少年之前
     * @return 最终日期
     */
    public static Date beforeYear(Date src, int year) {
        if (src == null) return null;
        if (year <= 0) return src;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(src);
        calendar.add(Calendar.YEAR, -year);
        return calendar.getTime();
    }

    public static void main(String[] args) {
        Date date = DateUtils.after(new Date(), 10);
        System.out.println(date);
        date = DateUtils.before(new Date(), 10);
        System.out.println(date);

        date = DateUtils.afterMonth(new Date(), 3);
        System.out.println(date);
        date = DateUtils.beforeMonth(new Date(), 3);
        System.out.println(date);

        date = DateUtils.afterYear(new Date(), 3);
        System.out.println(date);
        date = DateUtils.beforeYear(new Date(), 3);
        System.out.println(date);
    }
}
