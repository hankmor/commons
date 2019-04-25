package com.belonk.commons.util.date;

import org.junit.Assert;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Created by sun on 2019/4/17.
 *
 * @author sunfuchang03@126.com
 * @version 1.0
 * @since 1.2
 */
public class DateHelperTest {
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

    public static void main(String[] args) throws ParseException {
        LocalDate start = LocalDate.of(2019, 4, 1);
        LocalDate stop = LocalDate.of(2019, 4, 30);
        LocalDate.of(2019, 4, 30);
        List<LocalDate> localDates = DateHelper.splitEachDay(start, stop);
        Assert.assertEquals(localDates.size(), 30);
        Assert.assertEquals(localDates.get(0), start);
        Assert.assertEquals(localDates.get(localDates.size() - 1), stop);

        LocalDateTime localDateTime = LocalDateTime.of(2019, 4, 1, 15, 20, 20);
        LocalDate localDate = LocalDate.of(2019, 4, 1);

        String str = DateHelper.format(localDate, DateFormatEnum.YYYY_MM_DD);
        Assert.assertEquals("2019-04-01", str);
        str = DateHelper.format(localDateTime, DateFormatEnum.YYYY_MM_DD_HH_MM_SS);
        Assert.assertEquals(localDateTime, DateHelper.fromDatetimeStr(str, DateFormatEnum.YYYY_MM_DD_HH_MM_SS));
        Assert.assertEquals("2019-04-01 15:20:20", str);
        str = DateHelper.format(localDateTime, DateFormatEnum.YYYY_MM_DD);
        Assert.assertEquals(localDate, DateHelper.fromDateStr(str, DateFormatEnum.YYYY_MM_DD));
        Assert.assertEquals("2019-04-01", str);
        str = DateHelper.format(localDateTime, DateFormatEnum.YYYY_MM_DD_ZN);
        Assert.assertEquals("2019年04月01日", str);
        Assert.assertEquals(localDate, DateHelper.fromDateStr(str, DateFormatEnum.YYYY_MM_DD_ZN));
        str = DateHelper.format(localDateTime, DateFormatEnum.YYYY_MM_DD_SLASH);
        Assert.assertEquals("2019/04/01", str);
        Assert.assertEquals(localDate, DateHelper.fromDateStr(str, DateFormatEnum.YYYY_MM_DD_SLASH));

        str = DateHelper.formatDate(localDate);
        Assert.assertEquals("2019-04-01", str);
        str = DateHelper.formatDate(localDateTime);
        Assert.assertEquals("2019-04-01", str);
        str = DateHelper.formatDatetime(localDateTime);
        Assert.assertEquals("2019-04-01 15:20:20", str);
        str = DateHelper.formatDatetime(localDateTime);
        Assert.assertEquals("2019-04-01 15:20:20", str);
        str = DateHelper.formatTime(localDateTime);
        Assert.assertEquals("15:20:20", str);

        DateHelperTest DateHelperTest = new DateHelperTest();
        Date date = DateHelper.from("2019-04-19", "yyyy-MM-dd");
        Date[] dates;
        dates = DateHelperTest.startAndStopOfRecentWeek(date, 0);
        Assert.assertEquals("2019-04-15 00:00:00", DateHelper.format(dates[0], "yyyy-MM-dd HH:mm:ss"));
        Assert.assertEquals("2019-04-21 23:59:59", DateHelper.format(dates[1], "yyyy-MM-dd HH:mm:ss"));
        dates = DateHelperTest.startAndStopOfRecentWeek(date, 1);
        Assert.assertEquals("2019-04-22 00:00:00", DateHelper.format(dates[0], "yyyy-MM-dd HH:mm:ss"));
        Assert.assertEquals("2019-04-28 23:59:59", DateHelper.format(dates[1], "yyyy-MM-dd HH:mm:ss"));
        dates = DateHelperTest.startAndStopOfRecentWeek(date, 2);
        Assert.assertEquals("2019-04-29 00:00:00", DateHelper.format(dates[0], "yyyy-MM-dd HH:mm:ss"));
        Assert.assertEquals("2019-05-05 23:59:59", DateHelper.format(dates[1], "yyyy-MM-dd HH:mm:ss"));
        dates = DateHelperTest.startAndStopOfRecentWeek(date, -1);
        Assert.assertEquals("2019-04-08 00:00:00", DateHelper.format(dates[0], "yyyy-MM-dd HH:mm:ss"));
        Assert.assertEquals("2019-04-14 23:59:59", DateHelper.format(dates[1], "yyyy-MM-dd HH:mm:ss"));
        dates = DateHelperTest.startAndStopOfRecentWeek(date, -2);
        Assert.assertEquals("2019-04-01 00:00:00", DateHelper.format(dates[0], "yyyy-MM-dd HH:mm:ss"));
        Assert.assertEquals("2019-04-07 23:59:59", DateHelper.format(dates[1], "yyyy-MM-dd HH:mm:ss"));
        dates = DateHelperTest.startAndStopOfRecentWeek(date, -3);
        Assert.assertEquals("2019-03-25 00:00:00", DateHelper.format(dates[0], "yyyy-MM-dd HH:mm:ss"));
        Assert.assertEquals("2019-03-31 23:59:59", DateHelper.format(dates[1], "yyyy-MM-dd HH:mm:ss"));
        dates = DateHelperTest.startAndStopDateOfWeek(date);
        Assert.assertEquals("2019-04-15 00:00:00", DateHelper.format(dates[0], "yyyy-MM-dd HH:mm:ss"));
        Assert.assertEquals("2019-04-21 23:59:59", DateHelper.format(dates[1], "yyyy-MM-dd HH:mm:ss"));

        start = LocalDate.of(2018, 4, 1);
        stop = LocalDate.of(2019, 4, 30);
        List<String> months = DateHelper.splitEachMonth(start, stop);
        Assert.assertEquals(13, months.size());
        Assert.assertEquals("2018-04", months.get(0));
        months = DateHelper.splitEachMonth(start, stop, "/");
        Assert.assertEquals(13, months.size());
        Assert.assertEquals("2018/04", months.get(0));

        List<Integer> years = DateHelper.splitEachYear(start, stop);
        Assert.assertEquals(2, years.size());
        Assert.assertEquals(2018, (int) years.get(0));
    }

    public Date[] startAndStopOfRecentWeek(Date date, int week) {
        return DateHelper.startAndStopOfRecentWeek(date, week);
    }

    public Date[] startAndStopDateOfWeek(Date date) {
        return DateHelper.startAndStopDateOfWeek(date);
    }

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Protected Methods
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */



    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Property accessors
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */



    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Inner classes
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */


}