package com.jtexplorer.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * LOVService class
 *
 * @author 苏友朋
 * @date 2019/03/01 10:50
 */
public class TimeTools {
    /**
     * 根据传值转化日期格式（注日期参数也会被转化，请注意）
     *
     * @param dateTime   需要转格式的日期，该对象也会被转格式（默认为当前系统时间）
     * @param dateFormat 日期格式（yyyy-MM-dd HH:mm:ss）（yyyy年MM月dd日 HH:mm:ss）(默认yyyy-MM-dd HH:mm:ss)
     * @return String
     * @throws ParseException 异常
     */
    public static String transformDateFormat(Date dateTime, String dateFormat) throws ParseException {
        if (dateTime == null) {
            dateTime = new Date();
        }
        if (dateFormat == null) {
            dateFormat = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String returnStr = sdf.format(dateTime);
        dateTime = sdf.parse(returnStr);
        return returnStr;
    }

    /**
     * 根据传值转化日期格式（注日期参数也会被转化，请注意）
     *
     * @param dateTime   需要转格式的日期，该对象也会被转格式（默认为当前系统时间）
     * @param dateFormat 日期格式（yyyy-MM-dd HH:mm:ss）（yyyy年MM月dd日 HH:mm:ss）(默认yyyy-MM-dd HH:mm:ss)
     * @return String
     * @throws ParseException 异常
     */
    public static String transformDateFormatStringToString(String dateTime, String dateFormat) throws ParseException {
        if (StringUtil.isEmpty(dateTime)) {
            dateTime = transformDateFormat(new Date(), dateFormat);
        }
        if (dateFormat == null) {
            dateFormat = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(dateTime);
    }

    /**
     * 根据传值转化日期格式（注日期参数也会被转化，请注意）
     *
     * @param dateTime   需要转格式的日期，该对象也会被转格式（默认为当前系统时间）
     * @param dateFormat 日期格式（yyyy-MM-dd HH:mm:ss）（yyyy年MM月dd日 HH:mm:ss）(默认yyyy-MM-dd HH:mm:ss)
     * @return String
     * @throws ParseException 异常
     */
    public static Date transformDateFormatStringToDate(String dateTime, String dateFormat) throws ParseException {
        if (dateFormat == null) {
            dateFormat = "yyyy-MM-dd HH:mm:ss";
        }
        if (StringUtil.isEmpty(dateTime)) {
            dateTime = transformDateFormat(new Date(), dateFormat);
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.parse(dateTime);
    }

    /**
     * 根据传值转化日期格式（注日期参数也会被转化，请注意）,转化类型Mon Jun 17 00:00:00 CST 2019
     *
     * @param dateTime   需要转格式的日期，该对象也会被转格式（默认为当前系统时间）
     * @param dateFormat 日期格式(默认EEE MMM dd HH:mm:ss zzz yyyy)
     * @return String
     * @throws ParseException 异常
     */
    public static Date transformDateFormatStringToDateUS(String dateTime, String dateFormat) throws ParseException {
        if (dateFormat == null) {
            dateFormat = "EEE MMM dd HH:mm:ss zzz yyyy";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        return sdf.parse(dateTime);
    }

    /**
     * 根据传值转化日期格式（注日期参数也会被转化，请注意）
     *
     * @param dateTime   需要转格式的日期，该对象也会被转格式（默认为当前系统时间）
     * @param dateFormat 日期格式（yyyy-MM-dd HH:mm:ss）（yyyy年MM月dd日 HH:mm:ss）(默认yyyy-MM-dd HH:mm:ss)
     * @return String
     * @throws ParseException 异常
     */
    public static Date transformDateFormatDateToDate(Date dateTime, String dateFormat) throws ParseException {
        if (dateFormat == null) {
            dateFormat = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.parse(transformDateFormat(dateTime, dateFormat));
    }

    /**
     * 创建日历类
     *
     * @param dateTime 日期
     * @return long
     */
    public static Calendar newCalendar(Date dateTime) {
        Calendar cal = Calendar.getInstance();
        if (dateTime != null) {
            cal.setTime(dateTime);
        }
        return cal;
    }

    /**
     * 根据传值获取微秒数
     *
     * @param dateTime 日期(默认为当前系统时间)
     * @return long
     */
    public static Long getTimeInMillis(Date dateTime) {
        if (dateTime == null) {
            dateTime = new Date();
        }
        return newCalendar(dateTime).getTimeInMillis();
    }

    /**
     * 根据传值获取年或月
     *
     * @param dateTime 日期(默认为当前系统时间)
     * @param field    用于日期类get方法
     * @return long
     */
    public static int calendarGet(Date dateTime, int field) {
        if (dateTime == null) {
            dateTime = new Date();
        }
        return newCalendar(dateTime).get(field);
    }

    /**
     * 计算两个日期之间相差的时间
     *
     * @param smdate     较小的时间（默认为当前系统时间）
     * @param bdate      较大的时间（默认为当前系统时间）
     * @param divisor    除数（求相差天数：(1000*3600*24) ；求相差小时数：(1000*3600) ；求相差分钟数：(1000*60) ；求相差秒数：(1000)
     *                   求相差月数：0 ；求相差年数 -1）(默认1000*3600*24)
     * @param dateFormat 日期格式（yyyy-MM-dd HH:mm:ss）(默认yyyy-MM-dd)
     * @return 相差时间数
     * @throws ParseException 异常
     */
    public static Long dateBetween(Date smdate, Date bdate, Integer divisor, String dateFormat) throws ParseException {
        if (smdate == null || bdate == null) {
            Date now = new Date();
            if (smdate == null) {
                smdate = now;
            }
            if (bdate == null) {
                smdate = now;
            }
        }
        if (divisor == null || divisor.equals(0)) {
            divisor = 1000 * 3600 * 24;
        }
        if (dateFormat == null || "".equals(dateFormat)) {
            dateFormat = "yyyy-MM-dd";
        }
        transformDateFormat(smdate, dateFormat);
        transformDateFormat(bdate, dateFormat);
        return (getTimeInMillis(bdate) - getTimeInMillis(smdate)) / divisor;
    }

    /**
     * 判断时间是昨天
     *
     * @param timestamp 时间
     * @return boolean
     */
    public static boolean isYesterday(long timestamp) {
        Calendar c = Calendar.getInstance();
        clearCalendar(c, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND);
        c.add(Calendar.DAY_OF_MONTH, -1);
        // 昨天最早时间
        long firstOfDay = c.getTimeInMillis();
        c.setTimeInMillis(timestamp);
        // 指定时间戳当天最早时间
        clearCalendar(c, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND);
        return firstOfDay == c.getTimeInMillis();
    }

    private static void clearCalendar(Calendar c, int... fields) {
        for (int f : fields) {
            c.set(f, 0);
        }
    }

    /**
     * 计算两个日期相差年数
     *
     * @param startDate 较小日期
     * @param endDate   较大日期
     * @return int
     */
    public static int yearDateDiff(Date startDate, Date endDate) {
        return calendarGet(endDate, Calendar.YEAR) - calendarGet(startDate, Calendar.YEAR);
    }

    /**
     * 将日期加上相应分钟数
     *
     * @param date 日期
     * @param minute  要增加的分钟数(负数，则是减去对应分钟数)
     * @return Date
     */
    public static Date getDateByMoreMinute(Date date, Integer minute) {
        Calendar cal = newCalendar(date);
        cal.add(Calendar.MINUTE, minute);
        //这个时间就是日期往后推一天的结果
        date = cal.getTime();
        return date;
    }

    /**
     * 将日期加上相应天数
     *
     * @param date 日期
     * @param day  要增加的天数(负数，则是减去对应天数)
     * @return Date
     */
    public static Date getDateByMoreDay(Date date, Integer day) {
        Calendar cal = newCalendar(date);
        cal.add(Calendar.DATE, day);
        //这个时间就是日期往后推一天的结果
        date = cal.getTime();
        return date;
    }

    /**
     * 将日期加上相应月数
     *
     * @param date  日期
     * @param month 要增加的月数(负数，则是减去对应天数)
     * @return Date
     */
    public static Date getDateByMoreMonth(Date date, Integer month) {
        Calendar cal = newCalendar(date);
        cal.add(Calendar.MONTH, month);
        //这个时间就是日期往后推一天的结果
        date = cal.getTime();
        return date;
    }
    /**
     * 将日期加上相应年数
     *
     * @param date  日期
     * @param year 要增加的年数(负数，则是减去对应天数)
     * @return Date
     */
    public static Date getDateByMoreYear(Date date, Integer year) {
        Calendar cal = newCalendar(date);
        cal.add(Calendar.YEAR, year);
        //这个时间就是日期往后推一天的结果
        date = cal.getTime();
        return date;
    }

    /**
     * 获得某天最大时间 2017-10-15 23:59:59
     *
     * @param date 日期（默认当前系统时间）
     * @return Date
     */
    public static Date getEndOfDay(Date date) {
        if (date == null) {
            date = new Date();
        }
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

//    public static void main(String[] args) {
//        // 获取今日最晚点
//        Date endDate = TimeTools.getStartOfDay(TimeTools.getDateByMoreDay(new Date(),1));
//        // 获取七天前的日期
//        Date startDate = TimeTools.getStartOfDay(TimeTools.getDateByMoreDay(new Date(),-30));
//        System.out.println("1");
//    }

    /**
     * 获得某天最小时间 2017-10-15 00:00:00
     *
     * @param date 日期（默认当前系统时间）
     * @return Date
     */
    public static Date getStartOfDay(Date date) {
        if (date == null) {
            date = new Date();
        }
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获得某年某月第一天
     *
     * @param year       年（不可为空，为0时默认为当前系统年份）
     * @param month      月（不可为空，为0时默认为当前系统月份）
     * @param dateFormat 日期格式（yyyy-MM-dd HH:mm:ss）（yyyy年MM月dd日 HH:mm:ss）(默认yyyy-MM-dd HH:mm:ss)
     * @return Date
     */
    public static String getFirstDayOfMonth(int year, int month, String dateFormat) throws ParseException {
        if (year == 0) {
            year = Integer.parseInt(transformDateFormat(null, "yyyy"));
        }
        if (month == 0) {
            month = Integer.parseInt(transformDateFormat(null, "MM"));
        }
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最小天数
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        return transformDateFormat(cal.getTime(), dateFormat);
    }

    /**
     * 获得某年某月最后一天
     *
     * @param year       年（不可为空，为0时默认为当前系统年份）
     * @param month      月（不可为空，为0时默认为当前系统月份）
     * @param dateFormat 日期格式（yyyy-MM-dd HH:mm:ss）（yyyy年MM月dd日 HH:mm:ss）(默认yyyy-MM-dd HH:mm:ss)
     * @return Date
     */
    public static String getLastDayOfMonth(int year, int month, String dateFormat) throws ParseException {
        if (year == 0) {
            year = Integer.parseInt(transformDateFormat(null, "yyyy"));
        }
        if (month == 0) {
            month = Integer.parseInt(transformDateFormat(null, "MM"));
        }
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        return transformDateFormat(cal.getTime(), dateFormat);
    }

    /**
     * 获得某年某月第一天
     *
     * @param year       年（不可为空，为0时默认为当前系统年份）
     * @param month      月（不可为空，为0时默认为当前系统月份）
     * @param dateFormat 日期格式（yyyy-MM-dd HH:mm:ss）（yyyy年MM月dd日 HH:mm:ss）(默认yyyy-MM-dd HH:mm:ss)
     * @return Date
     */
    public static Date getFirstDayDateOfMonth(int year, int month, String dateFormat) throws ParseException {
        if (year == 0) {
            year = Integer.parseInt(transformDateFormat(null, "yyyy"));
        }
        if (month == 0) {
            month = Integer.parseInt(transformDateFormat(null, "MM"));
        }
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最小天数
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        return cal.getTime();
    }

    /**
     * 获得某年某月最后一天
     *
     * @param year       年（不可为空，为0时默认为当前系统年份）
     * @param month      月（不可为空，为0时默认为当前系统月份）
     * @param dateFormat 日期格式（yyyy-MM-dd HH:mm:ss）（yyyy年MM月dd日 HH:mm:ss）(默认yyyy-MM-dd HH:mm:ss)
     * @return Date
     */
    public static Date getLastDayDateOfMonth(int year, int month, String dateFormat) throws ParseException {
        if (year == 0) {
            year = Integer.parseInt(transformDateFormat(null, "yyyy"));
        }
        if (month == 0) {
            month = Integer.parseInt(transformDateFormat(null, "MM"));
        }
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        return cal.getTime();
    }

    /**
     * 比较时间，是否需要重新获取
     *
     * @return boolean
     */
    public static boolean timeComparison(Date time) {
        try {
            Date d1 = new Date();
            long diff = d1.getTime() - time.getTime();
            //需要重新获取
            return diff >= 7200000;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 根据具体年份周数获取周一日期
     *
     * @param year 年份
     * @param week 周数
     * @return Date
     */
    public static Date getMondayByWeek(Integer year, Integer week) {
        // 如果年份为空，则默认今年
        if(year == null){
            year = calendarGet(new Date(),Calendar.YEAR);
        }
        // 如果周数为空，则默认第一周
        if(week == null){
            week = 1;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        // 设置每周的开始日期
        cal.setFirstDayOfWeek(Calendar.MONDAY);

        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, week);

        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        return cal.getTime();
    }

    /**
     * 根据日期获取周几
     * @param date 日期
     */
    public static int getWeek(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w=cal.get(Calendar.DAY_OF_WEEK)-1;
        if(w==0) w=7;
        return w;
    }

    /**
     * 计算两个日期相差月数
     *
     * @param startDate 较小日期
     * @param endDate   较大日期
     * @return int
     */
    public static int monthDateDiff(Date startDate, Date endDate) {
        Temporal temporal1 = null;
        Temporal temporal2 = null;
        try {
            temporal1 = LocalDate.parse(transformDateFormat(startDate,"yyyy-MM-dd"));
            temporal2 = LocalDate.parse(transformDateFormat(endDate,"yyyy-MM-dd"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long month = ChronoUnit.MONTHS.between(temporal1, temporal2);
        return month.intValue();
    }

    /**
     * 获取日期特定值
     * @param date 日期
     * @return 月份
     */
    public static Integer getValueByDate(Date date,int field){
        if(date == null){
            date = new Date();
        }
        Calendar cal = newCalendar(date);
        return cal.get(field);
    }


    /**
     * 获取日期年份
     * @param date 日期
     * @return 年份
     */
    public static Integer getYear(Date date){
        return getValueByDate(date,Calendar.YEAR);
    }

    /**
     * 获取日期月份
     * @param date 日期
     * @return 月份
     */
    public static Integer getMonth(Date date){
        return getValueByDate(date,Calendar.MONTH)+1;
    }

    /**
     * 获取日期周份
     * @param date 日期
     * @return 月份
     */
    public static Integer getWeekCount(Date date){
        return getValueByDate(date,Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取日期日
     * @param date 日期
     * @return 月份
     */
    public static Integer getMonthDay(Date date){
        return getValueByDate(date,Calendar.DATE);
    }


    /***
     * convert Date to cron ,eg.  "0 07 10 15 1 ? 2016"
     * @param date  : 时间点
     * @return
     */
    public static String getCron(Date  date){
        String dateFormat="ss mm HH dd MM ? yyyy";
        try {
            return transformDateFormat(date, dateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String cron=getCron(new Date());
        System.out.println(cron);
    }


}
