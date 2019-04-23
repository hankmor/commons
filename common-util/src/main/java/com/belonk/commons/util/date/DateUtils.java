package com.belonk.commons.util.date;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * Created by sun on 2018/6/27.
 *
 * @author sunfuchang03@126.com
 * @version 1.2
 * @since 1.0
 */
public class DateUtils {
    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Static fields/constants/initializer
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */



    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Instance fields
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */



    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Constructors
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */



    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Public Methods
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    public static int weekOfMonth(LocalDateTime localDateTime) {
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        return dayOfWeek.getValue();
    }

    public static boolean isAfter(LocalDateTime srcDate, LocalDateTime destDate) {
        return srcDate.isAfter(destDate);
    }

    public static LocalDateTime of(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static Date of(LocalDateTime localDateTime) {
        OffsetDateTime odt = OffsetDateTime.now();
        return Date.from(localDateTime.toInstant(odt.getOffset()));
    }

    public static Date of(LocalDate localDate) {
        return of(localDate.atTime(LocalTime.now()));
    }

    public static Date of(LocalTime localTime) {
        return of(localTime.atDate(LocalDate.now()));
    }

    /**
     * 获取某一段日期的开始时间和结束时间。
     *
     * @param currentDay 日期字符串
     * @param separator  日期字符串的分隔符
     * @return 包含开始日期毫秒数和结束日期毫秒的map，开始key为<code>beg</code>，结束key<code>end</code>
     */
    public static Map<String, Long> getCurrentDayBeginAndEnd(String currentDay, String separator) {
        Long beg = null;
        Long end = null;
        Map<String, Long> timeMap = new HashMap<>();
        if (StringUtils.isNotBlank(currentDay) && StringUtils.isNotBlank(separator)) {
            try {
                String[] dates = currentDay.split(separator);
                beg = parseToMills(dates[0] + " 00:00:00:000");
                end = parseToMills(dates[1] + " 23:59:59:999");
                timeMap.put("beg", beg);
                timeMap.put("end", end);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return timeMap;
        }
        return null;
    }

    /**
     * 将字符串日期格式解析为日期毫秒数。
     *
     * @param formatDate 日期字符串
     * @return 日期毫秒数
     * @throws ParseException throw <code>ParseException</code> when parse date failed
     */
    public static Long parseToMills(String formatDate) throws ParseException {
        Date dt = parseToDate(formatDate);
        return dt.getTime();
    }

    /**
     * 计算开始日期和结束日期之间相差的小时数。
     *
     * @param begin 开始日期字符串
     * @param end   结束日期字符串
     * @return 小时数
     * @throws ParseException hrow <code>ParseException</code> when parse date failed
     */
    public static long betweenHour(String begin, String end) throws ParseException {
        long deltaMillis = parseToDateHour(end).getTime() - parseToDateHour(begin).getTime();
        return Duration.ofMillis(deltaMillis).toHours();
    }

    /**
     * 计算开始日期和结束日期之间相差的小时数。
     *
     * @param begin 开始日期毫秒数
     * @param end   结束日期毫秒数
     * @return 小时数
     */
    public static long betweenHour(long begin, long end) {
        long deltaMillis = end - begin;
        return Duration.ofMillis(deltaMillis).toHours();
    }

    /**
     * 将完整日期解析为包含年月日时分的日期，不包括秒。
     *
     * @param formatDate 原始完整日期字符串
     * @return 包含年月日时分的日期，秒为0
     * @throws ParseException hrow <code>ParseException</code> when parse date failed
     */
    public static Date parseToDateHour(String formatDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (formatDate.contains("年") || formatDate.contains("月") || formatDate.contains("日")) {
            sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
            return sdf.parse(formatDate);
        }
        if (formatDate.contains("/")) {
            sdf = new SimpleDateFormat("yyyy/MM/dd HH/mm");
            return sdf.parse(formatDate);
        }
        return sdf.parse(formatDate);
    }

    /**
     * 将字符串日期格式化为日期对象。
     *
     * @param formatDate 字符串日期
     * @return 日期对象
     * @throws ParseException throw <code>ParseException</code> when parse date failed
     */
    public static Date parseToDateMilis(String formatDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (formatDate.contains("年") || formatDate.contains("月") || formatDate.contains("日")) {
            sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
            return sdf.parse(formatDate);
        }
        if (formatDate.contains("/")) {
            sdf = new SimpleDateFormat("yyyy/MM/dd HH/mm/ss");
            return sdf.parse(formatDate);
        }
        return sdf.parse(formatDate);
    }

    /**
     * 将字符串日期格式化为包含年月日的日期，时分秒为0.
     *
     * @param formatDate 字符串日期
     * @return 日期对象
     * @throws ParseException throw <code>ParseException</code> when parse date failed
     */
    public static Date parseToDate(String formatDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (formatDate.contains("年") || formatDate.contains("月") || formatDate.contains("日")) {
            sdf = new SimpleDateFormat("yyyy年MM月dd日");
            return sdf.parse(formatDate);
        }
        if (formatDate.contains("/")) {
            sdf = new SimpleDateFormat("yyyy/MM/dd");
            return sdf.parse(formatDate);
        }
        return sdf.parse(formatDate);
    }

    /**
     * 获取某一日期是当月的第几天，从1开始。
     *
     * @param instant 日期毫秒数
     * @return 当月第几天
     */
    public static int getDayOfMonth(long instant) {
        return Instant.ofEpochMilli(instant).atZone(ZoneId.systemDefault()).getDayOfMonth();
    }

    public static String getBeginOfCurrentMonth(long instant, SimpleDateFormat format) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        // 设置为1号,当前日期既为本月第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        synchronized (DateUtils.class) {
            return format.format(c.getTime());
        }
    }

    public static String getEndOfCurrentMonth(long instant, SimpleDateFormat format) {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        synchronized (DateUtils.class) {
            return format.format(ca.getTime());
        }
    }

    public static long betweenDays(String begin, String end) throws ParseException {
        long beginTime = parseToMills(begin);
        long endTime = parseToMills(end);
        return countDays(endTime - beginTime);
    }


    public static long countDays(long deltaMillis) {
        return Duration.ofMillis(deltaMillis).toDays();
    }

    public static long plusDays(long instant, long days) {
        return Instant.ofEpochMilli(instant).atZone(ZoneId.systemDefault()).plusDays(days).toInstant().toEpochMilli();
    }

    public static Long plusDays(String currentDay, long days) throws ParseException {
        long current = parseToMills(currentDay);
        return plusDays(current, days);
    }

    public static Date plusYear(String currentDay, int year) throws ParseException {
        return new Date(plusDays(currentDay, year * 365));
    }

    public static String plusDaysToString(String currentDay, long day, String pattern) throws ParseException {
        long millis = plusDays(currentDay, day);
        return millisToDateTime(millis, pattern);
    }

    public static Date plusDaysToDate(String currentDay, long days) throws ParseException {
        long millis = plusDays(currentDay, days);
        return new Date(millis);
    }

    /**
     * 计算日期加上某天数后的日期。
     *
     * @param currentDay 原始日期
     * @param days       天数
     * @return 计算后的日期
     */
    public static Date plusDaysToDate(Date currentDay, long days) {
        long millis = plusDays(currentDay.getTime(), days);
        return new Date(millis);
    }

    /**
     * 日期的毫秒数加上秒数，得到的日期毫秒。
     *
     * @param instant 日期的毫秒数
     * @param seconds 描述
     * @return 计算后的日期毫秒数
     */
    public static long plusSeconds(long instant, long seconds) {
        return Instant.ofEpochMilli(instant).atZone(ZoneId.systemDefault()).plusSeconds(seconds).toInstant().toEpochMilli();
    }

    /**
     * 日期的毫秒数加上分钟数，得到的日期毫秒。
     *
     * @param instant 日期的毫秒数
     * @param minutes 分钟数
     * @return 计算后的日期毫秒数
     */
    public static long plusMinutes(long instant, long minutes) {
        return Instant.ofEpochMilli(instant).atZone(ZoneId.systemDefault()).plusMinutes(minutes).toInstant().toEpochMilli();
    }

    /**
     * 计算日期加上某小时数后的日期。
     *
     * @param srcDate 原始日期
     * @param hours   小时数
     * @return 计算后日期
     */
    public static Date plusHoursToDate(Date srcDate, int hours) {
        long milli = Instant.ofEpochMilli(srcDate.getTime()).atZone(ZoneId.systemDefault()).plusHours(hours).toInstant().toEpochMilli();
        return new Date(milli);
    }

    public static String millisToDateTime(Long millis, String pattern) {
        LocalDateTime time = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDateTime();
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String getBeginOfTheMouth(long instant) {
        return Instant.ofEpochMilli(instant).atZone(ZoneId.systemDefault()).with(TemporalAdjusters.firstDayOfMonth()).toString();
    }

    public static boolean isBeforeThanToday(Date day) throws ParseException {
        long now = System.currentTimeMillis();
        return day.getTime() < now;
    }

    /**
     * 判断某一时间是否在一个区间内
     *
     * @param sourceTime 时间区间,半闭合,如[10:00-20:00)
     * @param curTime    需要判断的时间 如10:00
     * @return 如果在，返回{@code true}，否则返回{@code false}
     */
    public static boolean isInTime(String sourceTime, String curTime) {
        if (sourceTime == null || !sourceTime.contains("-") || !sourceTime.contains(":")) {
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
        }
        if (curTime == null || !curTime.contains(":")) {
            throw new IllegalArgumentException("Illegal Argument arg:" + curTime);
        }
        String[] args = sourceTime.split("-");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            long now = sdf.parse(curTime).getTime();
            long start = sdf.parse(args[0]).getTime();
            long end = sdf.parse(args[1]).getTime();
            if ("00:00".equals(args[1])) {
                args[1] = "24:00";
            }
            if (end < start) {
                return now < end || now >= start;
            } else {
                return now >= start && now < end;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
        }

    }

    /**
     * 计算某一个日期{@code date2}比{@code date1}多的天数。
     *
     * @param date1 被比较日期
     * @param date2 基础日期
     * @return 天数
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        // 不同一年
        if (year1 != year2) {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                // 闰年
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                    timeDistance += 366;
                    // 不是闰年
                } else {
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2 - day1);
            // 同年
        } else {
            return day2 - day1;
        }
    }

    /**
     * 获取两个日期之间的所有日期（yyyy-MM-dd）
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return 所有日期列表
     */
    public static List<Date> getBetweenDates(Date begin, Date end) {
        List<Date> result = new ArrayList<Date>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(begin);
        while (begin.getTime() < end.getTime()) {
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
            begin = tempStart.getTime();
        }
        return result;
    }

    /**
     * 判断某一日期是否在给定的时间段内
     *
     * @param nowTime   基础日期
     * @param beginTime 开始日期
     * @param endTime   结束日期
     * @return 如果在返回{@code true}，否则返回{@code false}.
     */
    public static boolean belongPeriod(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.compareTo(begin) == 0 || date.compareTo(end) == 0) {
            return true;
        }
        return date.after(begin) && date.before(end);
    }

    /**
     * 根据传入时间获取传入时间的天的起点时间字符串（date为空时默认取当前系统时间）
     *
     * @param date   日期
     * @param format 格式
     * @return 日期字符串
     */
    public static String getBeginOfDay(Date date, String format) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        SimpleDateFormat simple = new SimpleDateFormat(format);
        return simple.format(zero);
    }


    /**
     * 由出生日期获得年龄
     *
     * @param birthDayStr 出生日期字符串
     * @return 年龄
     * @throws ParseException 日期解析失败，则抛出异常
     */
    public static int getAge(String birthDayStr) throws ParseException {
        Date birthDay = parseToDate(birthDayStr);
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                age--;
            }
        }
        return age;
    }

    /**
     * 年份减运算，计算少多少年之前的某一个日期。
     *
     * @param src  原日期
     * @param year 多少年之前
     * @return 最终日期
     */
    public static Date beforeYear(Date src, int year) {
        if (src == null) {
            return null;
        }
        if (year <= 0) {
            return src;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(src);
        calendar.add(Calendar.YEAR, -year);
        return calendar.getTime();
    }

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

        return (int) ((bigMills - smallMills) / 24 / 3600 / 1000);
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
        if (date == null) {
            return null;
        }
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
     * @param date 目标日期
     * @return 后一天的日期
     */
    public static Date nextDate(Date date) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, 1);
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
        if (dest == null || start == null || end == null) {
            return false;
        }
        long destMilliseconds = dest.getTime();
        long startMilliseconds = start.getTime();
        long endMilliseconds = end.getTime();
        if (endMilliseconds < startMilliseconds) {
            return false;
        }
        return startMilliseconds <= destMilliseconds && destMilliseconds <= endMilliseconds;
    }

    /**
     * 取给定日期的前一天。
     *
     * @param date 目标日期
     * @return 目标前一天
     */
    public static Date prevDate(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    /**
     * 天数加运算，计算某一日期多少天后的日期。
     *
     * @param src 原日期
     * @param day 多少天后
     * @return 多少天后日期
     */
    public static Date after(Date src, int day) {
        if (src == null) {
            return null;
        }
        if (day <= 0) {
            return src;
        }
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
        if (src == null) {
            return null;
        }
        if (day <= 0) {
            return src;
        }
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
        if (src == null) {
            return null;
        }
        if (month <= 0) {
            return src;
        }
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
        if (src == null) {
            return null;
        }
        if (month <= 0) {
            return src;
        }
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
        if (src == null) {
            return null;
        }
        if (year <= 0) {
            return src;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(src);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Private Methods
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */


}