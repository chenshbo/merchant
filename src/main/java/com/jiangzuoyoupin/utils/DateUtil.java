package com.jiangzuoyoupin.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 有关日期处理的工具类。
 * 
 * @author xutiantian
 *
 */
public class DateUtil extends DateUtils {
	private static final ThreadLocal<DateFormats> dateFormats = new ThreadLocal<DateFormats>() {
		protected DateFormats initialValue() {
			return new DateFormats();
		}
	};

	private static final ThreadLocal<DateTimeFormatters> dateTimeFormatters = new ThreadLocal<DateTimeFormatters>() {
		protected DateTimeFormatters initialValue() {
			return new DateTimeFormatters();
		}
	};

    /*
     * ========================================================================== ==
     */
    /* 定义时间常量，毫秒为单位。 */
    /*
     * ========================================================================== ==
     */
	/**
	 * 一天的起始时间
	 */
	public static final String TIME_BEGIN = " 00:00:00";

	/**
	 * 一天的结束时间
	 */
	public static final String TIME_END = " 23:59:59";

	public static final int HOUR_MIN = 60;

	public static final int DAY_MI_SECOND = 24 * 60 * 60 * 1000;

	/*
     * ========================================================================== ==
     */
    /* String 与 Date 之间相互转化。 */
    /*
     * ========================================================================== ==
     */

	public static String formatYMD(Date date) {
		if(date == null){
			return null;
		}
		return dateFormats.get().ymd.format(date);
	}

	public static String formatYM(Date date) {
		if(date == null){
			return null;
		}
		return dateFormats.get().ym.format(date);
	}

	public static String formatHMS(Date date) {
		if(date == null){
			return null;
		}
		return dateFormats.get().hms.format(date);
	}

	public static String formatHM(Date date) {
		if(date == null){
			return null;
		}
		return dateFormats.get().hm.format(date);
	}

	public static String formatYMDHM(Date date) {
		if(date == null){
			return null;
		}
		return dateFormats.get().ymdhm.format(date);
	}

	public static String formatYMDHMS(Date date) {
		if(date == null){
			return null;
		}
		return dateFormats.get().ymdhms.format(date);
	}

	public static String formatYMDHMSSS(Date date) {
		if(date == null){
			return null;
		}
		return dateFormats.get().ymdhmsss.format(date);
	}

    public static String format(Date date, String format) {
        SimpleDateFormat _format = new SimpleDateFormat(format);
        return _format.format(date);
    }

	public static String formatYMDChinese(Date date) {
		if(date == null){
			return null;
		}
		return dateFormats.get().ymdChinese.format(date);
	}

	public static String formatYMDSlash(Date date) {
		if(date == null){
			return null;
		}
		return dateFormats.get().ymdSlash.format(date);
	}

	public static Date parseYMD(String dateStr) {
		if(dateStr == null){
			return null;
		}
		return parse(dateFormats.get().ymd, dateStr);
	}

	public static Date parseYM(String dateStr) {
		if(dateStr == null){
			return null;
		}
		return parse(dateFormats.get().ym, dateStr);
	}

	public static Date parseYMDHMS(String dateStr) {
		if(dateStr == null){
			return null;
		}
		return parse(dateFormats.get().ymdhms, dateStr);
	}

    public static Date parseYMDHMS_strict(String dateStr) {
        if (dateStr == null) {
            return null;
        }
        SimpleDateFormat s = dateFormats.get().ymdhms;
        s.setLenient(false);
        return parse(s, dateStr);
    }

	public static Date parseTodayHMS(String dateStr) {
		String today = formatYMD(new Date());
		String todayDateStr = String.format("%s %s", today, dateStr);
		return parse(dateFormats.get().ymdhms, todayDateStr);
	}

	public static Date parse(SimpleDateFormat format, String dateStr) {
		try {
            Date d = format.parse(dateStr);
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            int year = c.get(Calendar.YEAR);
            if (year >= 1000 && year <= 9999) {
                return d;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }


	/*
     * ========================================================================== ==
     */
    /* String 与 LocalDateTime 之间相互转化。 */
    /*
     * ========================================================================== ==
     */
	public static String formatYMD(LocalDateTime localDateTime) {
		if(localDateTime == null){
			return null;
		}
		return localDateTime.format(dateTimeFormatters.get().ymd);
	}

	public static String formatYM(LocalDateTime localDateTime) {
		if(localDateTime == null){
			return null;
		}
		return localDateTime.format(dateTimeFormatters.get().ym);
	}

	public static String formatHMS(LocalDateTime localDateTime) {
		if(localDateTime == null){
			return null;
		}
		return localDateTime.format(dateTimeFormatters.get().hms);
	}

	public static String formatHM(LocalDateTime localDateTime) {
		if(localDateTime == null){
			return null;
		}

		return localDateTime.format(dateTimeFormatters.get().hm);
	}

	public static String formatYMDHM(LocalDateTime localDateTime) {
		if(localDateTime == null){
			return null;
		}
		return localDateTime.format(dateTimeFormatters.get().ymdhm);
	}

	public static String formatYMDHMS(LocalDateTime localDateTime) {
		if(localDateTime == null){
			return null;
		}
		return localDateTime.format(dateTimeFormatters.get().ymdhms);
	}

	public static String formatYMDChinese(LocalDateTime localDateTime) {
		if(localDateTime == null){
			return null;
		}

		return localDateTime.format(dateTimeFormatters.get().ymdChinese);
	}

	public static String formatYMDSlash(LocalDateTime localDateTime) {
		if(localDateTime == null){
			return null;
		}

		return localDateTime.format(dateTimeFormatters.get().ymdSlash);
	}

	public static LocalDateTime parseYMDTLocalDateTime(String dateStr) {
		if(dateStr == null){
			return null;
		}
		return parse(dateTimeFormatters.get().ymd, dateStr);
	}

	public static LocalDateTime parseYMTLocalDateTime(String dateStr) {
		if(dateStr == null){
			return null;
		}
		return parse(dateTimeFormatters.get().ym, dateStr);
	}

	public static LocalDateTime parseYMDHMSTLocalDateTime(String dateStr) {
		if(dateStr == null){
			return null;
		}
		return parse(dateTimeFormatters.get().ymdhms, dateStr);
	}

	public static LocalDateTime parseTodayHMSTLocalDateTime(String dateStr) {
		String today = formatYMD(new Date());
		String todayDateStr = String.format("%s %s", today, dateStr);
		return parse(dateTimeFormatters.get().ymdhms, todayDateStr);
	}

	public static LocalDateTime parse(DateTimeFormatter formatter, String dateStr) {
		try {
			return LocalDateTime.parse(dateStr, formatter);
		} catch (Exception ex) {
			return null;
		}
	}

	/*
     * ========================================================================== ==
     */
    /* Date 与 LocalDateTime 之间相互转化。 */
    /*
     * ========================================================================== ==
     */

	/**
	 * 01. java.util.Date --> java.time.LocalDateTime
	 */
	public static LocalDateTime dateToLocalDateTime(Date date) {
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		return LocalDateTime.ofInstant(instant, zone);
	}

	/**
	 * 02. java.util.Date --> java.time.LocalDate
	 */
	public static LocalDate dateToLocalDate(Date date) {
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
		return localDateTime.toLocalDate();
	}

	/**
	 * 03. java.util.Date --> java.time.LocalTime
	 */
	public static LocalTime dateToLocalTime(Date date) {
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
		return localDateTime.toLocalTime();
	}

	/**
	 * 04. java.time.LocalDateTime --> java.util.Date
	 */
	public static Date localDateTimeToUdate(LocalDateTime localDateTime) {
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = localDateTime.atZone(zone).toInstant();
		return Date.from(instant);
	}

	/**
	 * 05. java.time.LocalDate --> java.util.Date
	 */
	public static Date localDateToUdate(LocalDate localDate) {
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
		return Date.from(instant);
	}

	/**
	 * 06. java.time.LocalTime --> java.util.Date
	 */
	public static Date localTimeToUdate(LocalTime localTime) {
		LocalDate localDate = LocalDate.now();
		LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = localDateTime.atZone(zone).toInstant();
		return Date.from(instant);
	}


	/**
	 * 根据日期获取年份
	 *
	 * @param date 日期 @see Date
	 * @return 年份，如果<code>date</code>为<code>null</code>,返回<code>-1</code>
	 */
	public static final int getYear(Date date) {
		if (date == null) {
			return -1;
		}

		LocalDateTime localDateTime = dateToLocalDateTime(date);
		return localDateTime.getYear();
	}

	/**
	 * 根据日期获取月份
	 *
	 * @param date 日期 @see Date
	 * @return 月份，如果<code>date</code>为<code>null</code>,返回<code>-1</code>
	 */
	public static final int getMonth(Date date) {
		if (date == null) {
			return -1;
		}

		LocalDateTime localDateTime = dateToLocalDateTime(date);
		return localDateTime.getMonthValue();
	}

	/**
	 * 根据时间获取日
	 *
	 * @param date 日期 @see Date
	 * @return 年月日中的日，如果<code>date</code>为<code>null</code>,返回<code>-1</code>
	 */
	public static final int getDay(Date date) {
		if (date == null) {
			return -1;
		}

		LocalDateTime localDateTime = dateToLocalDateTime(date);
		return localDateTime.getDayOfMonth();
	}

	/**
	 * 根据日期获取小时
	 *
	 * @param date 日期 @see Date
	 * @return 小时，如果<code>date</code>为<code>null</code>,返回<code>-1</code>
	 */
	public static final int getHour(Date date) {
		if (date == null) {
			return -1;
		}

		LocalDateTime localDateTime = dateToLocalDateTime(date);
		return localDateTime.getHour();
	}

	/**
	 * 比较两个日期的先后顺序
	 *
	 * @param first 第一个日期 @see Date
	 * @param second 第二个日期 @see Date
	 *
	 * @return 如果<code>first</code>==<code>second</code>，返回<code>0</code>;
	 *         <p>
	 *         如果<code>first</code>&lt;<code>second</code>，返回<code>-1</code>;
	 *         <p>
	 *         如果<code>first</code>&gt;<code>second</code>，返回<code>1</code>
	 */
	public static int compareDate(Date first, Date second) {
		if ((first == null) && (second == null)) {
			return 0;
		}

		if (first == null) {
			return -1;
		}

		if (second == null) {
			return 1;
		}

		if (first.before(second)) {
			return -1;
		}

		if (first.after(second)) {
			return 1;
		}

		return 0;
	}

	/**
	 * 返回给定日期时间所在月份的第一天
	 *
	 * @param date 给定的日期对象 @see Date
	 * @return 给定日期时间所在月份的第一天
	 */
	public static Date getFirstOfMonth(final Date date) {
		if (date == null) {
			return null;
		}

		LocalDate today = dateToLocalDate(date);
		LocalDate firstDayOfThisMonth = today.with(TemporalAdjusters.firstDayOfMonth());
		return localDateToUdate(firstDayOfThisMonth);
	}

	/**
	 * 返回给定日期时间所在月份的最后一天
	 *
	 * @param date 给定的日期对象 @see Date
	 * @return 给定日期时间所在月份的最后一天
	 */
	public static Date getEndOfMonth(final Date date) {
		if (date == null) {
			return null;
		}
		LocalDate today = dateToLocalDate(date);
		LocalDate lastDayOfThisMonth = today.with(TemporalAdjusters.lastDayOfMonth());
		return localDateToUdate(lastDayOfThisMonth);
	}

	/**
	 * 判断当前时间是否在某段时间内 参数不区分先后顺序
	 */
	public static boolean isDuringTwoDate(Date date, Date another) {
		long dateTime = date.getTime();
		long anotherTime = another.getTime();
		long currentTime = new Date().getTime();

		if (currentTime > dateTime && currentTime < anotherTime) {
			return true;
		} else if (currentTime > anotherTime && currentTime < dateTime) {
			return true;
		} else {
			return false;
		}
	}



	/**
	 * 判断一个日期是否是今天
	 *
	 * @param date
	 * @return
     */
	public static boolean isTodaytDay(Date date) {
		LocalDate today = LocalDate.now();
		LocalDate dateTime = dateToLocalDate(date);
		return today.equals(dateTime);
	}

	public static long daysOffset(Date date1, Date date2) {
		date1 = parseYMD(formatYMD(date1));
		date2 = parseYMD(formatYMD(date2));
		return (date1.getTime() - date2.getTime()) / DAY_MI_SECOND;
	}

    public static Date offSet(Date data, int offSet, TimeUnit timeUnit) {
        long offSetTime = data.getTime() + timeUnit.toMillis(offSet);

        Date offSetData = new Date();
        offSetData.setTime(offSetTime);
        return offSetData;
    }

    public static long secondsTillEndOfDay() {
        Calendar cal = Calendar.getInstance();  // current date and time
        cal.add(Calendar.DAY_OF_MONTH, 1);      // add a day
        cal.set(Calendar.HOUR_OF_DAY, 0);       // set hour to last hour
        cal.set(Calendar.MINUTE, 0);            // set minutes to last minute
        cal.set(Calendar.SECOND, 0);            // set seconds to last second
        cal.set(Calendar.MILLISECOND, 0);       // set milliseconds to last millisecond

        return (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
    }

    public static Date startOfToday() {
        Calendar cal = Calendar.getInstance();  // current date and time
        cal.add(Calendar.DAY_OF_MONTH, 0);      // add a day
        cal.set(Calendar.HOUR_OF_DAY, 0);       // set hour to last hour
        cal.set(Calendar.MINUTE, 0);            // set minutes to last minute
        cal.set(Calendar.SECOND, 0);            // set seconds to last second
        cal.set(Calendar.MILLISECOND, 0);       // set milliseconds to last millisecond

        return cal.getTime();
    }

	/**
	 * 今天是星期几 , 7表示星期日
	 *
	 * @return
	 */
	public static int getTodayDayOfWeek() {
		LocalDateTime localDateTime = LocalDateTime.now();
		return localDateTime.getDayOfWeek().getValue();
	}

	public static void main(String[] args) throws ParseException {
        Date d = startOfToday();
        System.out.println(d.toString());

        Date data = new Date();
        System.out.println(offSet(data, 10, TimeUnit.DAYS));


        System.out.println((int) secondsTillEndOfDay());

		System.out.println(getTodayDayOfWeek());

        Date date = parseYMDHMS("2017-01-10 12:00:00");
        System.out.println(isTodaytDay(date));

        System.out.println(getMonth(new Date()));

        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println(localDateTime.toString());

        LocalDateTime localDateTime1 = parseYMDHMSTLocalDateTime("2017-01-10 12:00:10");
        System.out.println(localDateTime1.toString());


        String dataFormat = format(new Date(), "MMdd");
        System.out.println(dataFormat);


		System.out.println(DateUtil.parseDate("20141030133525","yyyyMMddHHmmss"));

    }

}

class DateFormats {
	public final SimpleDateFormat hms = new SimpleDateFormat("HH:mm:ss");
	public final SimpleDateFormat hm = new SimpleDateFormat("HH:mm");
	public final SimpleDateFormat ymdhm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public final SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
	public final SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM");
	public final SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public final SimpleDateFormat ymdChinese = new SimpleDateFormat("yyyy年MM月dd");
	public final SimpleDateFormat ymdSlash = new SimpleDateFormat("yyyy/MM/dd");
	public final SimpleDateFormat ymdhmsss = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	public final SimpleDateFormat ymdhms2 = new SimpleDateFormat("yyyyMMddHHmmss");
}

class DateTimeFormatters{
	public final DateTimeFormatter hms = DateTimeFormatter.ofPattern("HH:mm:ss");
	public final DateTimeFormatter hm = DateTimeFormatter.ofPattern("HH:mm");
	public final DateTimeFormatter ymdhm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	public final DateTimeFormatter ymd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public final DateTimeFormatter ym = DateTimeFormatter.ofPattern("yyyy-MM");
	public final DateTimeFormatter ymdhms = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public final DateTimeFormatter ymdChinese = DateTimeFormatter.ofPattern("yyyy年MM月dd");
	public final DateTimeFormatter ymdSlash = DateTimeFormatter.ofPattern("yyyy/MM/dd");
}
