/**
 * Project Name:tool-common
 * File Name:DateUtils.java
 * Package Name:com.luotuo.tool.common.utils
 * Date:2018年3月1日下午5:15:18
 * Copyright (c) 2018, 鲁济良 All Rights Reserved.
 *
*/

package com.luotuo.tool.common.utils.date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName:DateUtils <br/>
 * Function: 时间格式转换、时间点获取. <br/>
 * Date: 2018年3月1日 下午5:15:18 <br/>
 * 
 * @author 鲁济良
 * @version 1.0
 * @since JDK 1.8
 * @see
 */
public final class DateUtils {

    /**
     * DEFAULT_DATE_FMT: yyyy/MM/dd
     */
    public static final String DEFAULT_DATE_FMT = "yyyy/MM/dd";
    /**
     * DEFAULT_DATE_FMT: yyyy-MM-dd.
     */
    public static final String DEFAULT_DATE1_FMT = "yyyy-MM-dd";
    /**
     * DEFAULT_DATE_FMT: yyyyMMdd.
     */
    public static final String DEFAULT_DATE2_FMT = "yyyyMMdd";
    /**
     * DEFAULT_DATE3_FMT:ddMMyyyy.
     */
    public static final String DEFAULT_DATE3_FMT = "ddMMyyyy";

    /**
     * DEFAULT_TIME_FMT: yyyy/MM/dd HH:mm:ss.
     */
    public static final String DEFAULT_TIME_FMT = "yyyy/MM/dd HH:mm:ss";

    /**
     * DEFAULT_DATETIME1_FMT: yyyyMMddHHmmss.
     */
    public static final String DEFAULT_DATETIME1_FMT = "yyyyMMddHHmmss";
    /**
     * DEFAULT_DATETIME2_FMT: yyyy-MM-dd HH:mm:ss.
     */
    public static final String DEFAULT_DATETIME2_FMT = "yyyy-MM-dd HH:mm:ss";

    /**
     * DEFAULT_DATETIME3_FMT: yyyyMMdd HH:mm:ss.
     */
    public static final String DEFAULT_DATETIME3_FMT = "yyyyMMdd HH:mm:ss";

    /**
     * DEFAULT_DATETIME4_FMT: yyyyMMdd HH:mm:ss:ms.
     */
    public static final String DEFAULT_DATETIME4_FMT = "yyyyMMdd HH:mm:ss:ms";

    /**
     * DEFAULT_DATE_FMT: yyyy-MM-dd.
     */
    public static final String DEFAULT_MONTH_FMT = "yyyyMM";

    /**
     * TIMEZONEIDS:全部时区名字. TimeZone: 表示时区偏移量，也可以计算夏令时 Africa/Abidjan,
     * 结果：Africa/Accra, Africa/Addis_Ababa....
     */
    private static final List<String> TIMEZONEIDS = Arrays.asList(TimeZone.getAvailableIDs());

    /**
     * TimeZoneCache:自定义时区缓存.
     */
    private static final Map TIMEZONECACHE = new HashMap();

    /**
     * timeZone Beijing:北京时区.用 getTimeZone 及时区 ID 获取 TimeZone
     */
    public static final TimeZone TIMEZONEBEIJING = TimeZone.getTimeZone("Asia/Shanghai");

    /**
     * date2String:(日期毫秒数转换为日期格式字符串（yyyy/MM/dd）). <br/>
     *
     * @author 鲁济良
     * @param date
     *            长整型（毫秒）日期
     * @return yyyy/MM/dd
     * @since JDK 1.8
     */
    public static String date2String(long date) {
        return date2String(new Date(date), DEFAULT_DATE_FMT, null);
    }

    /**
     * date2String:(日期毫秒数转换为指定时区日期格式字符串（yyyy/MM/dd）). <br/>
     *
     * @author 鲁济良
     * @param date
     *            长整型（毫秒）日期
     * @param timeZone
     *            时区
     * @return yyyy/MM/dd
     * @since JDK 1.8
     */
    public static String date2String(long date, TimeZone timeZone) {
        return date2String(new Date(date), DEFAULT_DATE_FMT, timeZone);
    }

    /**
     * date2String:转换日期为缺省日期格式字符串. <br/>
     * 
     * @param date
     *            date
     * @return String
     * @since JDK 1.6
     */
    public static String date2String(Date date) {
        return date2String(date, DEFAULT_DATE_FMT, null);
    }

    /**
     * date2String:转换日期为缺省日期格式字符串 yyyyMMdd. <br/>
     * 
     * @param date
     *            date
     * @return String
     * @since JDK 1.6
     */
    public static String date2String8(Date date) {
        return date2String(date, DEFAULT_DATE2_FMT, null);
    }

    /**
     * 
     * date2String:转换日期为缺省日期格式字符串. <br/>
     * 
     * @param date
     *            date
     * @param timeZone
     *            格式
     * @return String
     * @since JDK 1.6
     */
    public static String date2String(Date date, TimeZone timeZone) {
        return date2String(date, DEFAULT_DATE_FMT, timeZone);
    }

    /**
     * time2String:转换日期毫秒数为缺省日期格式字符串. <br/>
     * 
     * @param date
     *            date
     * @return String
     * @since JDK 1.6
     */
    public static String time2String(long date) {
        return date2String(new Date(date), DEFAULT_TIME_FMT, null);
    }

    /**
     * 
     * time2String:转换日期毫秒数为缺省日期格式字符串. <br/>
     * 
     * @param date
     *            date
     * @param timeZone
     *            格式
     * @return String
     * @since JDK 1.6
     */
    public static String time2String(long date, TimeZone timeZone) {
        return date2String(new Date(date), DEFAULT_TIME_FMT, timeZone);
    }

    /**
     * time2String:转换日期为缺省日期格式字符串. <br/>
     * 
     * @param date
     *            date
     * @return String
     * @since JDK 1.6
     */
    public static String time2String(Date date) {
        return date2String(date, DEFAULT_TIME_FMT, null);
    }

    /**
     * time2String:转换日期为缺省日期格式字符串. <br/>
     * 
     * @param date
     *            date
     * @param timeZone
     *            格式
     * @return String
     * @since JDK 1.6
     */
    public static String time2String(Date date, TimeZone timeZone) {
        return date2String(date, DEFAULT_TIME_FMT, timeZone);
    }

    /**
     * date2String:转换日期为指定格式字符串. <br/>
     * 
     * @param date
     *            date
     * @param format
     *            格式
     * @return String
     * @since JDK 1.6
     */
    public static String date2String(Date date, String format) {
        return date2String(date, format, null);

    }

    /**
     * date2String:(转换日期为指定格式字符串). <br/>
     *
     * @author 鲁济良
     * @param date
     *            转换日期
     * @param format
     *            字符串格式
     * @param timeZone
     *            时区
     * @return 指定时间字符串
     * @since JDK 1.8
     */
    public static String date2String(Date date, String format, TimeZone timeZone) {
        if (date == null || format == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (timeZone != null) {
            sdf.setTimeZone(timeZone);
        }
        return sdf.format(date);

    }

    /**
     * string2Date:( string2Date:解析日期时间字符串,支持 yyMMdd,yyyyMMdd, yyyy-MM-dd,
     * yyyy/MM/dd, yyyyMMddHHmm, yyyyMMddHHmmss, yyyyMMddHHmmssSSS, yyyy-MM-dd
     * HH:mm:ss, yyyy-MM-dd HH:mm:ss.SSS 格式, 其它方式结果不保证正确.). <br/>
     *
     * @author 鲁济良
     * @param dateStr
     *            时间字符串
     * @return date
     * @since JDK 1.8
     */
    public static Date string2Date(String dateStr) {
        return string2Date(dateStr, (TimeZone) null);
    }

    /**
     * string2Date:解析日期时间字符串,支持 yyMMdd,yyyyMMdd, yyyy-MM-dd, yyyy/MM/dd,
     * yyyyMMddHHmm, yyyyMMddHHmmss, yyyyMMddHHmmssSSS, yyyy-MM-dd HH:mm:ss,
     * yyyy-MM-dd HH:mm:ss.SSS, yyyyMMdd HH:mm:ss(add by zhongcf 20120220)格式,
     * 其它方式结果不保证正确. <br/>
     * 
     * @author 鲁济良
     * @param dateStr
     *            时间字符串
     * @param timeZone
     *            时区
     * @return Date
     * @since JDK 1.8
     */
    public static Date string2Date(String dateStr, TimeZone timeZone) {

        if (dateStr == null) {
            return null;
        }

        dateStr = dateStr.trim();

        if (dateStr.length() == 6) {
            return string2Date(dateStr, "yyMMdd", timeZone);
        }

        if (dateStr.length() == 8) {
            return string2Date(dateStr, "yyyyMMdd", timeZone);
        }

        if (dateStr.length() == 10) {
            if (dateStr.indexOf("-") != -1) {
                return string2Date(dateStr, "yyyy-MM-dd", timeZone);
            }

            if (dateStr.indexOf("/") != -1) {
                return string2Date(dateStr, "yyyy/MM/dd", timeZone);
            }
        }

        if (dateStr.length() == 12) {
            return string2Date(dateStr, "yyyyMMddHHmm", timeZone);
        }

        if (dateStr.length() == 14) {
            return string2Date(dateStr, "yyyyMMddHHmmss", timeZone);
        }

        if (dateStr.length() == 17) {
            if (dateStr.indexOf(":") != -1) {
                return string2Date(dateStr, "yyyyMMdd HH:mm:ss", timeZone);
            } else {
                return string2Date(dateStr, "yyyyMMddHHmmssSSS", timeZone);
            }
        }

        if (dateStr.length() == 19) {
            if (dateStr.indexOf("-") != -1) {
                return string2Date(dateStr, "yyyy-MM-dd HH:mm:ss", timeZone);
            }
            if (dateStr.indexOf("/") != -1) {
                return string2Date(dateStr, "yyyy/MM/dd HH:mm:ss", timeZone);
            }
        }

        if (dateStr.length() == 23) {
            if (dateStr.indexOf("-") != -1) {
                return string2Date(dateStr, "yyyy-MM-dd HH:mm:ss.SSS", timeZone);
            }
            if (dateStr.indexOf("/") != -1) {
                return string2Date(dateStr, "yyyy/MM/dd HH:mm:ss.SSS", timeZone);
            }
        }

        try {
            //兼容其它时间串格式转换为时间
            return new SimpleDateFormat().parse(dateStr);
        } catch (ParseException e) {
            //TODO 加入日志
        }
        return null;
    }

    /**
     * 按指定方式解析日期时间. <br/>
     * 
     * @param str
     *            date
     * @param format
     *            格式
     * @return Date
     */
    public static Date string2Date(String str, String format) {
        return string2Date(str, format, null);
    }

    /**
     * string2Date:(将指定时间字符串，解析日期时间). <br/>
     *
     * @author 鲁济良
     * @param str
     *            时间字符串
     * @param format
     *            时间格式
     * @param timeZone
     *            时区
     * @return Date时间
     * @since JDK 1.8
     */
    public static Date string2Date(String str, String format, TimeZone timeZone) {

        if (str == null) {
            return null;
        }

        if (format == null) {
            format = DEFAULT_DATE_FMT;
        }

        SimpleDateFormat fmt = new SimpleDateFormat(format);

        if (timeZone != null) {
            fmt.setTimeZone(timeZone);
        }

        try {
            return fmt.parse(str);
        } catch (ParseException e) {
            //TODO 加入日志
        }
        return null;
    }

    /**
     * 循环调整时间. <br/>
     * 
     * @param date
     *            时间
     * @param field
     *            格式
     * @param amount
     *            格式
     * @return Date
     */
    public static Date rollDate(Date date, int field, int amount) {
        return rollDate(date, field, amount, null);
    }

    /**
     * rollDate:(循环调整时间). <br/>
     *
     * @author 鲁济良
     * @param date
     *            被修改目标时间
     * @param field
     *            日历字段（年、月、日、时......）
     * @param amount
     *            有符号时间量
     * @param timeZone
     *            时区
     * @return 修改过的时间
     * @since JDK 1.8
     */
    public static Date rollDate(Date date, int field, int amount, TimeZone timeZone) {
        Calendar cal = null;
        if (timeZone == null) {
            cal = Calendar.getInstance();
        } else {
            cal = Calendar.getInstance(timeZone);
        }

        cal.setTime(date);
        //向指定日历字段添加指定（有符号的）时间量，不更改更大的字段
        cal.roll(field, amount);
        return cal.getTime();
    }

    /**
     * addDate:(调整时间). <br/>
     *
     * @author 鲁济良
     * @param date
     *            被修改目标时间
     * @param field
     *            日历字段（年、月、日、时......）
     * @param amount
     *            有符号时间量
     * @return 修改过的时间
     * @since JDK 1.8
     */
    public static Date addDate(Date date, int field, int amount) {
        return addDate(date, field, amount, null);
    }

    /**
     * addDate:(调整时间). <br/>
     *
     * @author 鲁济良
     * @param date
     *            被修改目标时间
     * @param field
     *            日历字段（年、月、日、时......）
     * @param amount
     *            有符号时间量
     * @param timeZone
     *            时区
     * @return 修改过的时间
     * @since JDK 1.8
     */
    public static Date addDate(Date date, int field, int amount, TimeZone timeZone) {
        Calendar cal = null;
        if (timeZone == null) {
            cal = Calendar.getInstance();
        } else {
            cal = Calendar.getInstance(timeZone);
        }
        cal.setTime(date);
        cal.add(field, amount);
        return cal.getTime();
    }

    /**
     * compare:(比较2个同时区时间先后，). <br/>
     *
     * @author 鲁济良
     * @param date1
     *            时间字符串1
     * @param date2
     *            时间字符串2
     * @return 如果时间1等于时间2，返回0，如果时间1小于时间2，返回负值，如果时间1大于时间2，返回正值
     * @since JDK 1.8
     * @see 注意:时间的格式必须在string2Date支持的格式范围内
     */
    public static int compare(String date1, String date2) {
        return string2Date(date1).compareTo(string2Date(date2));
    }

    /**
     * compare:(比较2个同时区时间先后，). <br/>
     *
     * @author 鲁济良
     * @param date1
     *            时间1
     * @param date2
     *            时间2
     * @return 如果时间1等于时间2，返回0，如果时间1小于时间2，返回负值，如果时间1大于时间2，返回正值
     * @since JDK 1.8
     */
    public static int compare(Date date1, Date date2) {
        return date1.compareTo(date2);
    }

    /**
     * getTimeZone:(根据时区名字取得时区 ，如果非java已知标准名字，则必须为 GMT[+-]hh:mm 格式). <br/>
     *
     * @author 鲁济良
     * @param id
     *            时区名字
     * @return 时区对象
     * @since JDK 1.8
     */
    public static TimeZone getTimeZone(String id) {
        if (id == null) {
            return null;
        }
        if (TIMEZONEIDS.contains(id)) {
            return TimeZone.getTimeZone(id);
        }
        if (TIMEZONECACHE.containsKey(id)) {
            return (TimeZone) TIMEZONECACHE.get(id);
        }
        //检测自定义时区字符串规则
        Pattern p = Pattern.compile("^GMT[+-](0[0-9]|1[01]):([0-5][0-9])$");
        Matcher m = p.matcher("id");
        if (!m.matches()) {
            return null;
        }
        int hh = Integer.parseInt(id.substring(4, 6));
        int mm = Integer.parseInt(id.substring(7));

        int sign = 0;
        if (id.charAt(3) == '-') {
            sign = -1;
        } else {
            sign = 1;
        }
        TimeZone tz = new SimpleTimeZone((hh * 60 + mm) * 60000 * sign, id);
        TIMEZONECACHE.put(id, tz);
        return tz;
    }

    /**
     * string2TimeStamp:(字符毫秒数转化为时间戳). <br/>
     *
     * @author 鲁济良
     * @param time
     *            毫秒字符数字
     * @param nanos
     *            小数秒
     * @return 时间戳对象
     * @since JDK 1.8
     */
    public static Timestamp string2TimeStamp(String time, String nanos) {
        //使用毫秒时间值构造 Timestamp 对象,
        //整数秒存储在底层日期值中；小数秒存储在 Timestamp 对象的 nanos 字段中
        //millions 表示自 1970 年 1 月 1 日 00:00:00 GMT 以来的毫秒数。负数表示 1970 年 1 月 1 日 00:00:00 GMT 之前的毫秒数
        Timestamp res = new Timestamp(Long.parseLong(time));

        //将此 Timestamp 对象的 nanos 字段设置为给定值
        res.setNanos(Integer.parseInt(nanos));
        return res;
    }

    /**
     * date2Timestamp:(date2Timestamp:把Date转为Timestamp). <br/>
     *
     * @author 鲁济良
     * @param adate
     *            被转换日期
     * @return 时间戳对象
     * @since JDK 1.8
     */
    public static Timestamp date2Timestamp(Date adate) {
        return new Timestamp(adate.getTime());
    }

    /**
     * 把时间转成用户当地时间. <br/>
     * 
     * @param dateSrc
     *            待转换的时间。
     * @param dest
     *            用户所在时区。
     * @return 转换后的时间。
     */
    public static Date transformDate(Date dateSrc, TimeZone dest) {
        Calendar cal = Calendar.getInstance(dest);
        cal.setTimeInMillis(dateSrc.getTime());
        int yy = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int dd = cal.get(Calendar.DATE);
        int hours = cal.get(Calendar.HOUR_OF_DAY);
        int mm = cal.get(Calendar.MINUTE);
        int ss = cal.get(Calendar.SECOND);
        int sss = cal.get(Calendar.MILLISECOND);
        Calendar calBJ = Calendar.getInstance(TIMEZONEBEIJING);
        calBJ.set(Calendar.YEAR, yy);
        calBJ.set(Calendar.MONTH, month);
        calBJ.set(Calendar.DATE, dd);
        calBJ.set(Calendar.HOUR_OF_DAY, hours);
        calBJ.set(Calendar.MINUTE, mm);
        calBJ.set(Calendar.SECOND, ss);
        calBJ.set(Calendar.MILLISECOND, sss);
        return calBJ.getTime();
    }

    /**
     * 校验起始日期和结束日期的合法性
     * <p>
     * 例如：起始日期距当前日期不超过12个月，起始结束日期间隔不超过3个月，调用<br>
     * validateDateRange(startDate, endDate, currentDate, 3, 12). <br/>
     * 
     * @param startDate
     *            起始日期
     * @param endDate
     *            结束日期
     * @param currentDate
     *            当前日期
     * @param maxInterval
     *            起始日期和结束日期的最大距离（单位为月）
     * @param amount
     *            起始日期和当前日期的最大距离（单位为月）
     * @return boolean
     */
    public static boolean validateDateRange(Date startDate, Date endDate, Date currentDate, int maxInterval,
            int amount) {
        if (startDate.after(endDate)) {
            return false;
        }

        if (currentDate.after(addDate(startDate, Calendar.MONTH, amount))) {
            return false;
        }

        if (endDate.after(addDate(startDate, Calendar.MONTH, maxInterval))) {
            return false;
        }

        return true;
    }

    /**
     * startDayDate:获取一天的开始时间. <br/>
     * 
     * @param date
     *            日期
     * @return 日期 + 00:00:00
     */
    public static Date startDayDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        cal.set(Calendar.MILLISECOND, 000);
        return cal.getTime();
    }

    /**
     * fundsTranDate:日终对账获取订单信息的时间点. <br/>
     * 
     * @return 获取时间信息的时间点
     */
    public static Date fundsTranDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 15);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        return cal.getTime();
    }

    /**
     * endDayDate:获取一天的结束时间. <br/>
     * 
     * @param date
     *            日期
     * @return 日期 + 23:59:59
     */
    public static Date endDayDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    /**
     * startDayOfMonth:获取上月第一天. <br/>
     * 生成交易明细报表时抓取数据使用.<br/>
     * 交易明细报表本月3号生成上月1号至月末的报表.<br/>
     * 
     * @param date
     *            当前系统时间
     * @return 上月第一天
     */
    public static Date startDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        cal.set(Calendar.MILLISECOND, 000);
        return cal.getTime();
    }

    /**
     * endDayOfMonth:获取上月最后一天. <br/>
     * 生成交易明细报表时抓取数据使用.<br/>
     * 交易明细报表本月3号生成上月1号至月末的报表.<br/>
     * 
     * @param date
     *            当前系统时间
     * @return 上月最后一天
     */
    public static Date endDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_YEAR, -1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    /**
     * startDayOfCurrentMonth:获取当月的第一天. <br/>
     *
     * @param date
     *            系统时间
     * @return 当月第一天
     */
    public static Date startDayOfCurrentMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        cal.set(Calendar.MILLISECOND, 000);
        return cal.getTime();
    }

    /**
     * lastMonth:当前月上月日期“YYYYMM”. <br/>
     * 
     * @return .
     */
    public static String lastMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        int month = c.get(Calendar.MONTH) + 1;

        if (month < 10) {
            return c.get(Calendar.YEAR) + "0" + month;
        } else {
            return c.get(Calendar.YEAR) + "" + month;
        }
    }

    /**
     * theMonthBeforeLast:上上月日期. <br/>
     *
     * @param pattern
     *            pattern
     * @return String
     */
    public static String theMonthBeforeLast(String pattern) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -2);
        Date date = c.getTime();
        SimpleDateFormat s = new SimpleDateFormat(pattern);
        String lastmonth = s.format(date);
        return lastmonth;

    }

    /**
     * startDayOfYear:当前时间所在年的开始日期. <br/>
     * 
     * @param date
     *            date
     * @return Date
     */
    public static Date startDayOfYear(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        cal.set(Calendar.MILLISECOND, 000);
        return cal.getTime();

    }

    /**
     * 
     * lastMonth:获取当前时间的上个月. <br/>
     * 
     * @param pattern
     *            时间模板
     * @return 上个月
     */
    public static String lastMonth(String pattern) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        Date date = c.getTime();
        SimpleDateFormat s = new SimpleDateFormat(pattern);
        String lastmonth = s.format(date);
        return lastmonth;
    }

    /**
     * initDate:(这里用一句话描述这个方法的作用). <br/>
     *
     * @param initdate
     *            initdate
     * @return Date
     */
    public static Date initDate(String initdate) {
        int hour = Integer.parseInt(initdate);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, 00);
        c.set(Calendar.SECOND, 00);
        c.set(Calendar.MILLISECOND, 000);

        return c.getTime();
    }

    /**
     * getCurrentMonthFirstDay:(这里用一句话描述这个方法的作用). <br/>
     *
     * @param yearString
     *            yearString
     * @param monthString
     *            monthString
     * @return Date
     */
    public static Date getCurrentMonthFirstDay(String yearString, String monthString) {
        int year = Integer.parseInt(yearString);
        int month = Integer.parseInt(monthString);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.DAY_OF_MONTH, 01);
        c.set(Calendar.HOUR_OF_DAY, 00);
        c.set(Calendar.MINUTE, 00);
        c.set(Calendar.SECOND, 00);
        c.set(Calendar.MILLISECOND, 000);
        return c.getTime();
    }

    /**
     * getCurrentYearFirstDay:(这里用一句话描述这个方法的作用). <br/>
     *
     * @param yearString
     *            yearString
     * @return Date
     */
    public static Date getCurrentYearFirstDay(String yearString) {
        int year = Integer.parseInt(yearString);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 01);
        c.set(Calendar.HOUR_OF_DAY, 00);
        c.set(Calendar.MINUTE, 00);
        c.set(Calendar.SECOND, 00);
        c.set(Calendar.MILLISECOND, 000);
        return c.getTime();
    }

    /**
     * getCurrentMonthLastDay:(这里用一句话描述这个方法的作用). <br/>
     *
     * @param date
     *            date
     * @return Date
     */
    public static Date getCurrentMonthLastDay(Date date) {
        Calendar cDay1 = Calendar.getInstance();
        cDay1.setTime(date);
        int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
        cDay1.set(Calendar.DAY_OF_MONTH, lastDay);
        cDay1.set(Calendar.HOUR_OF_DAY, 23);
        cDay1.set(Calendar.MINUTE, 59);
        cDay1.set(Calendar.SECOND, 59);
        cDay1.set(Calendar.MILLISECOND, 999);
        return cDay1.getTime();

    }

    /**
     * getNextMonthFirstDay:(这里用一句话描述这个方法的作用). <br/>
     *
     * @param yearString
     *            yearString
     * @param monthString
     *            monthString
     * @return Date
     */
    public static Date getNextMonthFirstDay(String yearString, String monthString) {
        int year = Integer.parseInt(yearString);
        int month = Integer.parseInt(monthString);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, 01);
        c.set(Calendar.HOUR_OF_DAY, 00);
        c.set(Calendar.MINUTE, 00);
        c.set(Calendar.SECOND, 00);
        c.set(Calendar.MILLISECOND, 000);
        return c.getTime();

    }

    /**
     * getCurrentTime:(这里用一句话描述这个方法的作用). <br/>
     *
     * @return String
     */
    public static String getCurrentTime() {
        SimpleDateFormat s = new SimpleDateFormat("yyyyMMdd");
        String currenttime = s.format(new Date());

        return currenttime;
    }

    /**
     * getCurrentTime1:(这里用一句话描述这个方法的作用). <br/>
     * 
     * @return String
     */
    public static String getCurrentTime1() {
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        String currenttime = s.format(new Date());

        return currenttime;
    }

    /**
     * theCurrentBeforeNday:当前日期上N天的日期. <br/>
     *
     * @param dayNum
     *            dayNum
     * @return String
     * @since JDK 1.6
     */
    public static String theCurrentBeforeNday(int dayNum) {
        Calendar c = Calendar.getInstance();

        c.setTime(new Date());
        c.add(Calendar.DAY_OF_YEAR, -dayNum);
        Date date = c.getTime();
        SimpleDateFormat s = new SimpleDateFormat(DateUtils.DEFAULT_DATE1_FMT);
        String lastmonth = s.format(date);
        return lastmonth;

    }

    /**
     * theCurrentBeforeNday:当前日期上N天的日期. <br/>
     * 
     * @param fnt
     *            :日期格式
     * @param dayNum
     *            dayNum
     * @return String
     * @since JDK 1.6
     */
    public static String theCurrentBeforeNdayWithFnt(int dayNum, String fnt) {
        Calendar c = Calendar.getInstance();

        c.setTime(new Date());
        c.add(Calendar.DAY_OF_YEAR, -dayNum);
        Date date = c.getTime();
        SimpleDateFormat s = new SimpleDateFormat(fnt);
        String lastmonth = s.format(date);
        return lastmonth;
    }

    /**
     * theCurrentBeforeNYear:当前日期上N年前的日期. <br/>
     * 
     * @param yearNum
     *            yearNum
     * @return Date
     * @since JDK 1.6
     */
    public static Date theCurrentBeforeNYear(int yearNum) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, c.get(Calendar.YEAR) - yearNum);
        return c.getTime();
    }

    /**
     * lastStartDay:获取上一天开始. <br/>
     * 生成交易明细报表时抓取数据使用.<br/>
     * 
     * @param date
     *            时间
     * @return 上月第一天
     * @since JDK 1.6
     */
    public static Date lastDayStart(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        cal.set(Calendar.MILLISECOND, 000);
        return cal.getTime();
    }

    /**
     * lastendDay:上一天的最后. <br/>
     * 生成交易明细报表时抓取数据使用.<br/>
     * 
     * @param date
     *            时间
     * @return 上一天的最后
     * @since JDK 1.6
     */
    public static Date lastDayEnd(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    /**
     * 
     * @param startDate
     *            yyyymmdd
     * @param endDate
     *            yyyymmdd
     * @return endDate - startDate =天数
     */
    public static int daysBetween(String startDate, String endDate) {
        Date start = string2Date(startDate, DEFAULT_DATE2_FMT);
        Date end = string2Date(endDate, DEFAULT_DATE2_FMT);
        long betweendays = (end.getTime() - start.getTime()) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(betweendays));
    }

    /**
     * dateToString:转换日期为缺省日期格式字符串. <br/>
     * 
     * @param date
     *            date
     * @return String
     * @since JDK 1.6
     */
    public static String dateToString(Date date) {
        return date2String(date, DEFAULT_DATE2_FMT, null);
    }

    /**
     * dateToString2:转换日期为缺省日期格式字符串. <br/>
     * 
     * @param date
     *            date
     * @return String
     * @since JDK 1.6
     */
    public static String dateToString2(Date date) {
        return date2String(date, DEFAULT_DATETIME3_FMT, null);
    }

    /**
     * dateToString3:转换日期为缺省日期格式字符串. <br/>
     * 
     * @param date
     *            date
     * @return String
     * @since JDK 1.6
     */
    public static String dateToString3(Date date) {
        return date2String(date, DEFAULT_DATE1_FMT, null);
    }

    //时间的格式必须为YYYYMMDD类型
    /**
     * dateFormatValidate:日期格式必须为YYYYMMDD格式. <br/>
     *
     * @param date
     *            date
     * @return Boolean
     * @since JDK 1.6
     */
    public static Boolean dateFormatValidate(String date) {
        Boolean flag = true;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.DEFAULT_DATE2_FMT);
            sdf.setLenient(false);
            sdf.parse(date);
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * getHour:获取当前时间的小时. <br/>
     *
     * @return int
     * @since JDK 1.6
     */
    public static int getHour() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.HOUR_OF_DAY);
    }
}
