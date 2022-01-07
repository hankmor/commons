package com.belonk.commons.util.date;

import com.belonk.commons.util.asserts.Assert;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;
import java.util.*;

/**
 * 基于java8的日期处理工具类。
 * Created by sun on 2019/4/22.
 *
 * @author sunfuchang03@126.com
 * @version 1.0
 * @since 1.2
 */
public final class DateHelper {
	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *
	 * Static fields/constants/initializer
	 *
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

	private static final Map<String, DateTimeFormatter> FORMATTER_MAP = new HashMap<>();

	static {
		DateFormatEnum[] formatEnum = DateFormatEnum.values();
		for (DateFormatEnum dateFormatEnum : formatEnum) {
			FORMATTER_MAP.put(dateFormatEnum.getValue(), DateTimeFormatter.ofPattern(dateFormatEnum.getValue()));
		}
	}

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

	/**
	 * 获取给定日期处于本月的第几周。
	 *
	 * @param localDateTime 给定日期
	 * @return 第几周，从1开始
	 */
	public static int weekOfMonth(LocalDateTime localDateTime) {
		return localDateTime.getDayOfWeek().getValue();
	}

	/**
	 * 计算某一日期是一年中的第几周。
	 *
	 * @param localDateTime 日期时间
	 * @return 第几周，从1开始
	 */
	public static int weekOfYear(LocalDateTime localDateTime) {
		if (localDateTime == null) {
			throw new NullPointerException("LocalDateTime is null.");
		}
		return localDateTime.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
	}

	/**
	 * 计算某一日期是一年中的第几周。
	 *
	 * @param date 日期时间
	 * @return 第几周，从1开始
	 */
	public static int weekOfYear(Date date) {
		if (date == null) {
			throw new NullPointerException("LocalDateTime is null.");
		}
		LocalDateTime localDateTime = date.toInstant().atZone(OffsetTime.now().getOffset()).toLocalDateTime();
		return weekOfYear(localDateTime);
	}

	/**
	 * 判断原日期是否处于目标日期之后。
	 *
	 * @param srcDate  原日期
	 * @param destDate 目标日期
	 * @return 是则返回true，否则返回false
	 */
	public static boolean isAfter(LocalDateTime srcDate, LocalDateTime destDate) {
		return srcDate.isAfter(destDate);
	}

	/**
	 * 将{@link Date}转换为{@link LocalDateTime}.
	 *
	 * @param date 目标{@link Date}
	 * @return 转换后的{@link LocalDateTime}
	 */
	public static LocalDateTime of(Date date) {
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

	/**
	 * 将{@link LocalDateTime}转换为{@link Date}。
	 *
	 * @param localDateTime 目标{@link LocalDateTime}
	 * @return 转换后的{@link Date}
	 */
	public static Date of(LocalDateTime localDateTime) {
		OffsetDateTime odt = OffsetDateTime.now();
		return Date.from(localDateTime.toInstant(odt.getOffset()));
	}

	/**
	 * 将{@link LocalDate}转换为{@link Date}，时间使用当前时间。
	 *
	 * @param localDate 目标{@link LocalDate}
	 * @return 转换后的{@link Date}
	 */
	public static Date of(LocalDate localDate) {
		return of(localDate.atTime(LocalTime.now()));
	}

	@Deprecated
	public static Date ofStartOfDay(LocalDate localDate) {
		return of(localDate.atStartOfDay());
	}

	public static Date startOfDay(LocalDate localDate) {
		return of(localDate.atStartOfDay());
	}

	public static Date startOfDay(LocalDateTime localDateTime) {
		return of(localDateTime.toLocalDate().atStartOfDay());
	}

	public static Date startOfDay(Date date) {
		return of(of(date).toLocalDate().atStartOfDay());
	}

	public static Date endOfDay(LocalDate localDate) {
		return of(localDate.atTime(23, 59, 59));
	}

	public static Date endOfDay(LocalDateTime localDateTime) {
		return of(localDateTime.toLocalDate().atTime(23, 59, 59));
	}

	public static Date endOfDay(Date date) {
		return of(of(date).toLocalDate().atTime(23, 59, 59));
	}

	/**
	 * 将{@link LocalTime}转换为{@link Date}，日期使用当前日期。
	 *
	 * @param localTime 目标{@link LocalTime}
	 * @return 转换后的{@link Date}
	 */
	public static Date of(LocalTime localTime) {
		return of(localTime.atDate(LocalDate.now()));
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
	 * 获取某一日期最近几周的开始、结束时间，时间精确到秒。
	 * <p>
	 * 参数{@code week}有以下几种情况：
	 * <li>为0：则计算目标日期所在周的开始结束时间，此时同{@link #startAndStopDateOfWeek(Date)};
	 * <Li>大于0：则计算目标日期之后的第{@code week}周的开始、结束时间；
	 * <Li>小于0：则计算目标日期之前的第{@code week}周的开始、结束时间；
	 *
	 * @param date 目标日期
	 * @param week 最近第几周
	 * @return 开始、结束时间数组，下标为0时为开始时间，1是结束时间
	 */
	public static Date[] startAndStopOfRecentWeek(Date date, int week) {
		Date[] dates = startAndStopDateOfWeek(date);
		if (week == 0) {
			return dates;
		}
		int plusDay = week * 7;
		ZoneOffset zoneOffset = OffsetTime.now().getOffset();
		LocalDateTime start = dates[0].toInstant().atOffset(zoneOffset).toLocalDateTime();
		LocalDate localDate = start.toLocalDate();
		return new Date[]{
				Date.from(start.plusDays(plusDay).toInstant(zoneOffset)),
				Date.from(localDate.atTime(23, 59, 59).plusDays(plusDay).plusDays(7 - 1).toInstant(zoneOffset))
		};
	}

	public static Date[] startAndStopOfRecentWeek(LocalDate localDate, int week) {
		return startAndStopOfRecentWeek(of(localDate), week);
	}

	public static Date[] startAndStopOfRecentWeek(LocalDateTime localDateTime, int week) {
		return startAndStopOfRecentWeek(of(localDateTime), week);
	}

	/**
	 * 计算目标日期所在周的开始结束时间，时间精确到秒。
	 *
	 * @param date 目标日期
	 * @return 开始、结束时间数组，下标为0时为开始时间，1是结束时间
	 */
	public static Date[] startAndStopDateOfWeek(Date date) {
		if (date == null) {
			return null;
		}
		LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		TemporalField temporalField = WeekFields.of(Locale.CHINA).dayOfWeek();
		// 加一天，从周一到周日
		LocalDate localDate = localDateTime.toLocalDate();
		LocalDateTime start = localDate.atStartOfDay().with(temporalField, 1).plusDays(1);
		LocalDateTime end = localDate.atTime(23, 59, 59).with(temporalField, 7).plusDays(1);
		return new Date[]{
				Date.from(start.toInstant(ZonedDateTime.now().getOffset())),
				Date.from(end.toInstant(ZonedDateTime.now().getOffset()))
		};
	}

	public static Date[] startAndStopDateOfWeek(LocalDateTime localDateTime) {
		return startAndStopDateOfWeek(of(localDateTime));
	}

	public static Date[] startAndStopDateOfWeek(LocalDate localDate) {
		return startAndStopDateOfWeek(of(localDate));
	}

	/**
	 * 获取两个日期之间的所有日期，使用{@link LocalDate}。
	 *
	 * @param start 开始日期
	 * @param stop  结束日期
	 * @return 所有日期列表
	 */
	public static List<LocalDate> splitEachDay(LocalDate start, LocalDate stop) {
		List<LocalDate> result = new ArrayList<>();
		while (start.compareTo(stop) <= 0) {
			result.add(start);
			start = start.plusDays(1);
		}
		return result;
	}

	public static List<String> splitEachMonth(LocalDate start, LocalDate stop) {
		return splitEachMonth(start, stop, "-");
	}

	/**
	 * 获取两个日期之间的所有月份，使用{@link LocalDate}表示，格式为YYYY-MM。
	 *
	 * @param start    开始日期
	 * @param stop     结束日期
	 * @param splitter 年和月份间的间隔符
	 * @return 所有月份的字符串列表
	 */
	public static List<String> splitEachMonth(LocalDate start, LocalDate stop, String splitter) {
		List<String> result = new ArrayList<>();
		while (start.compareTo(stop) <= 0) {
			int month = start.getMonth().getValue();
			result.add(start.getYear() + splitter + (month < 10 ? "0" + month : start.getMonth().getValue()));
			start = start.plusMonths(1);
		}
		return result;
	}

	public static List<String> splitEachQuarter(LocalDate start, LocalDate stop, String splitter) {
		List<String> result = new ArrayList<>();
		while (start.compareTo(stop) <= 0) {
			int month = start.getMonth().getValue();
			result.add(start.getYear() + splitter + ((month + 2) / 3));
			start = start.plusMonths(3);
		}
		return result;
	}

	public static Map<String, DateTuple> splitEachQuarterWithDate(LocalDate start, LocalDate stop, String splitter) {
		Map<String, DateTuple> result = new HashMap<>();
		while (start.compareTo(stop) <= 0) {
			int month = start.getMonth().getValue();
			String key = (start.getYear() + splitter + ((month + 2) / 3));
			result.put(key, getDateTuple(start.getYear(), (month + 2) / 3));
			start = start.plusMonths(3);
		}
		return result;
	}

	/**
	 * 将时间端按照固定天数分隔为起止时间列表。
	 * <p>
	 * 自动将开始时间的时间点转换为当天0点，结束时间的时间点转换为23点59分59秒。
	 * <p>
	 * 示例代码：
	 * <pre>
	 * Date            start      = DateHelper.from("2020-01-01", DateFormatEnum.YYYY_MM_DD);
	 * Date            stop       = DateHelper.from("2020-01-07", DateFormatEnum.YYYY_MM_DD);
	 * int             period     = 7;
	 * List<DateTuple> dateTuples = DateHelper.splitDatePeriodByDays(start, stop, period);
	 * System.out.println(dateTuples.size());
	 * </pre>
	 * 上边的示例代码将会得到包含以下两个起止时间段的列表:
	 * <pre>
	 * start=2020-01-01T00:00, stop=2020-01-07T23:59:59
	 * start=2020-01-08T00:00, stop=2020-01-09T23:59:59
	 * </pre>
	 *
	 * @param start 起始时间
	 * @param stop  结束时间
	 * @param days  固定天数
	 * @return 时间列表
	 */
	public static List<DateTuple> splitDatePeriodByDays(Date start, Date stop, int days) {
		Assert.notNull(start, "Start date must not be null.");
		Assert.notNull(stop, "Stop date must not be null.");
		Assert.isTrue(start.compareTo(stop) <= 0, "Start date must be less then or equals stop date.");
		Assert.isTrue(days > 0, "Days must be great then zero.");

		start = DateHelper.startOfDay(start);
		stop = DateHelper.endOfDay(stop);

		List<DateTuple> dateTuples = new ArrayList<>();
		LocalDateTime begin = of(start);
		LocalDateTime end = of(stop);

		if (begin.plusDays(days).compareTo(end) >= 0) {
			dateTuples.add(new DateTuple(begin, end));
			return dateTuples;
		}

		LocalDateTime tmpBegin = null;
		LocalDateTime tmpEnd = null;
		while (begin.compareTo(end) < 0) {
			tmpBegin = begin;
			tmpEnd = begin.plusDays(days - 1).toLocalDate().atTime(23, 59, 59);
			begin = begin.plusDays(days);
			if (tmpEnd.compareTo(end) > 0) {
				dateTuples.add(new DateTuple(tmpBegin, end));
			} else {
				dateTuples.add(new DateTuple(tmpBegin, tmpEnd));
			}
		}
		return dateTuples;
	}

	/**
	 * 获取两个日期之间的所有年份列表。
	 *
	 * @param start 开始日期
	 * @param stop  结束日期
	 * @return 所有年份列表
	 */
	public static List<Integer> splitEachYear(LocalDate start, LocalDate stop) {
		List<Integer> result = new ArrayList<>();
		while (start.compareTo(stop) <= 0) {
			result.add(start.getYear());
			start = start.plusYears(1);
		}
		return result;
	}

	// date format

	/**
	 * 将{@link TemporalAccessor}实现格式化为指定格式的字符换，格式由{@link DateTimeFormatter}定义。
	 *
	 * @param temporalAccessor temporalAccessor实现
	 * @return 字符串
	 */
	public static String format(TemporalAccessor temporalAccessor, DateFormatEnum dateFormatEnum) {
		Assert.notNull(temporalAccessor);
		Assert.notNull(dateFormatEnum);
		return FORMATTER_MAP.get(dateFormatEnum.getValue()).format(temporalAccessor);
	}

	/**
	 * 将{@link TemporalAccessor}实现格式化为指定格式的字符换。
	 *
	 * @param temporalAccessor temporalAccessor实现
	 * @return 字符串
	 */
	public static String format(TemporalAccessor temporalAccessor, String format) {
		Assert.notNull(temporalAccessor);
		Assert.hasLength(format);
		DateTimeFormatter formatter = FORMATTER_MAP.get(format);
		if (formatter != null) {
			return formatter.format(temporalAccessor);
		} else {
			return DateTimeFormatter.ofPattern(format).format(temporalAccessor);
		}
	}

	public static String format(Date date, DateFormatEnum dateFormatEnum) {
		Assert.notNull(date);
		Assert.notNull(dateFormatEnum);
		return FORMATTER_MAP.get(dateFormatEnum.getValue()).format(of(date));
	}

	public static String format(Date date, String format) {
		Assert.notNull(date);
		Assert.hasLength(format);
		DateTimeFormatter formatter = FORMATTER_MAP.get(format);
		if (formatter != null) {
			return formatter.format(of(date));
		} else {
			return DateTimeFormatter.ofPattern(format).format(of(date));
		}
	}

	public static String formatDate(Date date) {
		return format(date, DateFormatEnum.YYYY_MM_DD);
	}

	public static String formatDate(LocalDate localDate) {
		return format(localDate, DateFormatEnum.YYYY_MM_DD);
	}

	public static String formatDate(LocalDateTime localDate) {
		return format(localDate, DateFormatEnum.YYYY_MM_DD);
	}

	public static String formatDatetime(LocalDateTime localDateTime) {
		return format(localDateTime, DateFormatEnum.YYYY_MM_DD_HH_MM_SS);
	}

	public static String formatTime(LocalDateTime localDateTime) {
		return format(localDateTime, DateFormatEnum.HH_MM_SS);
	}

	public static Date from(String dateString, DateFormatEnum dateFormatEnum) {
		switch (dateFormatEnum.formatType()) {
			case DateFormatEnum.DateFormatType.DATE:
				return ofStartOfDay(fromDateStr(dateString, dateFormatEnum));
			case DateFormatEnum.DateFormatType.DATE_TIME:
				return of(fromDatetimeStr(dateString, dateFormatEnum));
			case DateFormatEnum.DateFormatType.DATE_TIME_HOUR_MINUTE:
				return of(fromDatetimeStr(dateString, dateFormatEnum));
			// 以下格式暂不支持
			case DateFormatEnum.DateFormatType.DATE_MONTH_DAY:

			case DateFormatEnum.DateFormatType.DATE_YEAR_MONTH:

			case DateFormatEnum.DateFormatType.TIME:

			case DateFormatEnum.DateFormatType.TIME_HOUR_MINUTE:

			default:
				throw new UnsupportedOperationException(String.format("Date [%s] and format [%s] is not supported yet.", dateString, dateFormatEnum.getValue()));
		}
	}

	public static void main(String[] args) {
		Date from = DateHelper.from("2021-07-09 18:22", DateFormatEnum.YYYY_MM_DD_HH_MM);
		System.out.println(from);
	}

	public static Date from(String dateString, String format) {
		if (format.contains(DateFormatEnum.DATE_TIME_SPLITTER)) {
			return of((fromDatetimeStr(dateString, format)));
		} else {
			return ofStartOfDay((fromDateStr(dateString, format)));
		}
	}

	public static LocalDateTime fromDatetimeStr(String dateString, DateFormatEnum dateFormatEnum) {
		DateTimeFormatter formatter = FORMATTER_MAP.get(dateFormatEnum.getValue());
		if (formatter == null) {
			return LocalDateTime.from(DateTimeFormatter.ofPattern(dateFormatEnum.getValue()).parse(dateString));
		} else {
			return LocalDateTime.from(formatter.parse(dateString));
		}
	}

	public static LocalDateTime fromDatetimeStr(String dateString, String format) {
		DateTimeFormatter formatter = FORMATTER_MAP.get(format);
		if (formatter == null) {
			return LocalDateTime.from(DateTimeFormatter.ofPattern(format).parse(dateString));
		} else {
			return LocalDateTime.from(formatter.parse(dateString));
		}
	}

	public static LocalDate fromDateStr(String dateString, DateFormatEnum dateFormatEnum) {
		DateTimeFormatter formatter = FORMATTER_MAP.get(dateFormatEnum.getValue());
		if (formatter == null) {
			return LocalDate.from(DateTimeFormatter.ofPattern(dateFormatEnum.getValue()).parse(dateString));
		} else {
			return LocalDate.from(formatter.parse(dateString));
		}
	}

	public static LocalDate fromDateStr(String dateString, String format) {
		DateTimeFormatter formatter = FORMATTER_MAP.get(format);
		if (formatter == null) {
			return LocalDate.from(DateTimeFormatter.ofPattern(format).parse(dateString));
		} else {
			return LocalDate.from(formatter.parse(dateString));
		}
	}

	public static int yearIntervalInclude(Date start, Date stop) {
		return intervalInclude(start, stop, DateUnit.YEAR);
	}

	public static int monthIntervalInclude(Date start, Date stop) {
		return intervalInclude(start, stop, DateUnit.MONTH);
	}

	public static int quarterIntervalInclude(Date start, Date stop) {
		return intervalInclude(start, stop, DateUnit.QUARTER);
	}

	public static int dayIntervalInclude(Date start, Date stop) {
		return intervalInclude(start, stop, DateUnit.DAY);
	}

	public static int intervalInclude(Date start, Date stop, DateUnit dateUnit) {
		if (start == null || stop == null) {
			throw new IllegalArgumentException("Start and stop date must not be null.");
		}
		if (dateUnit == null) {
			throw new IllegalArgumentException("DateUnit must not be null.");
		}
		if (stop.compareTo(start) < 0) {
			return 0;
		}
		if (stop.compareTo(start) == 0) {
			return 1;
		}
		LocalDate startDate = of(start).toLocalDate();
		LocalDate stopDate = of(stop).toLocalDate();
		int stopYear = stopDate.getYear();
		int startYear = startDate.getYear();
		int startMonth = startDate.getMonth().getValue();
		int stopMonth = stopDate.getMonth().getValue();

		switch (dateUnit) {
			case DAY:
				return (int) (stopDate.toEpochDay() - startDate.toEpochDay() + 1);
			case YEAR:
				return stopYear - startYear + 1;
			case MONTH:
				if (stopYear == startYear) {
					return stopMonth - startMonth + 1;
				} else {
					return (stopYear - startYear) * 12 + (stopMonth - startMonth);
				}
			case QUARTER:
				int startQuarter = 0;
				if (startMonth <= 3) {
					startQuarter = 1;
				} else if (startMonth <= 6) {
					startQuarter = 2;
				} else if (startMonth <= 9) {
					startQuarter = 3;
				} else {
					startQuarter = 4;
				}
				int stopQuarter = 0;
				if (stopMonth <= 3) {
					stopQuarter = 1;
				} else if (stopMonth <= 6) {
					stopQuarter = 2;
				} else if (stopMonth <= 9) {
					stopQuarter = 3;
				} else {
					stopQuarter = 4;
				}

				int quarters = 0;
				if (stopYear == startYear) {
					quarters = stopQuarter - startQuarter + 1;
				} else {
					int yearTmp = stopDate.getYear() - startDate.getYear() - 1;
					quarters = yearTmp * 4 + (4 - startQuarter + 1) + stopQuarter;
				}
				return quarters;
			default:
		}
		return 0;
	}

	// 2022-1-7 09:57:24 add

	public static Date plus(Date src, long amount, ChronoUnit unit) {
		if (amount == 0) return src;
		LocalDateTime localDateTime = of(src);
		return of(localDateTime.plus(amount, unit));
	}

	public static Date minus(Date src, long amount, ChronoUnit unit) {
		if (amount == 0) return src;
		LocalDateTime localDateTime = of(src);
		return of(localDateTime.minus(amount, unit));
	}

	public static Date prevDays(Date date, int days) {
		return minus(date, days, ChronoUnit.DAYS);
	}

	public static Date nextDays(Date date, int days) {
		return plus(date, days, ChronoUnit.DAYS);
	}

	public static Date prevDay(Date date) {
		return prevDays(date, 1);
	}

	public static Date nextDay(Date date) {
		return nextDays(date, 1);
	}

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *
	 * Protected Methods
	 *
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

	private static DateTuple getDateTuple(int year, int quarter) {
		if (year <= 0 || (quarter > 4 || quarter <= 0)) {
			throw new IllegalArgumentException("Illegal arguments : " + year + ", " + quarter);
		}
		switch (quarter) {
			case 1:
				return new DateTuple(
						LocalDateTime.of(year, 1, 1, 0, 0, 0),
						LocalDateTime.of(year, 3, 30, 23, 59, 59)
				);
			case 2:
				return new DateTuple(
						LocalDateTime.of(year, 4, 1, 0, 0, 0),
						LocalDateTime.of(year, 6, 30, 23, 59, 59)
				);
			case 3:
				return new DateTuple(
						LocalDateTime.of(year, 7, 1, 0, 0, 0),
						LocalDateTime.of(year, 9, 30, 23, 59, 59)
				);
			case 4:
				return new DateTuple(
						LocalDateTime.of(year, 10, 1, 0, 0, 0),
						LocalDateTime.of(year, 12, 31, 23, 59, 59)
				);
			default:
				throw new IllegalArgumentException("Illegal arguments : " + year + ", " + quarter);
		}
	}

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
