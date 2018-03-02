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
     * date2String:(日期毫秒数转换为指定日期格式字符串). <br/>
     *
     * @author 鲁济良
     * @param date
     *            长整型（毫秒）日期
     * @param format
     *            字符串格式
     * @return 日期字符串
     * @since JDK 1.8
     */
    public static String date2String(long date, String format) {
        return date2String(new Date(date), format, null);
    }

    /**
     * date2String:(日期毫秒数转换为：指定时区、指定日期格式字符串). <br/>
     *
     * @author 鲁济良
     * @param date
     *            长整型（毫秒）日期
     * @param format
     *            字符串格式
     * @param timeZone
     *            时区
     * @return 日期格式字符串
     * @since JDK 1.8
     */
    public static String date2String(long date, String format, TimeZone timeZone) {
        return date2String(new Date(date), format, timeZone);
    }

    /**
     * date2String:(日期转化为指定格式字符串). <br/>
     *
     * @author 鲁济良
     * @param date
     *            被转化日期
     * @param format
     *            字符串格式
     * @return 日期字符串
     * @since JDK 1.8
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
     * addDate:(按时区调整时间). <br/>
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
     * transformDate:(将日期转化为指定时区日期). <br/>
     * TODO(描述这个方法的注意事项 – 可选).<br/>
     *
     * @author 鲁济良
     * @param dateSrc
     * @param dest
     * @return
     * @since JDK 1.8
     */
    public static Date transformDateTimeZone(Date dateSrc, TimeZone dest) {
        Calendar cal = Calendar.getInstance(dest);
        cal.setTimeInMillis(dateSrc.getTime());
        /*        int yy = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int dd = cal.get(Calendar.DATE);
        int hours = cal.get(Calendar.HOUR_OF_DAY);
        int mm = cal.get(Calendar.MINUTE);
        int ss = cal.get(Calendar.SECOND);
        int sss = cal.get(Calendar.MILLISECOND);*/
        /*        Calendar calBJ = Calendar.getInstance(TIMEZONEBEIJING);
        calBJ.set(Calendar.YEAR, yy);
        calBJ.set(Calendar.MONTH, month);
        calBJ.set(Calendar.DATE, dd);
        calBJ.set(Calendar.HOUR_OF_DAY, hours);
        calBJ.set(Calendar.MINUTE, mm);
        calBJ.set(Calendar.SECOND, ss);
        calBJ.set(Calendar.MILLISECOND, sss);*/
        cal.setTimeZone(TIMEZONEBEIJING);
        return cal.getTime();
    }

    /**
     * startDayDate:(获取当前时间所在天的开始时间). <br/>
     *
     * @author 鲁济良
     * @param date
     *            转换日期
     * @return 日期 + 00:00:00
     * @since JDK 1.8
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
     * endDayDate:(获取当前时间所在天的结束时间). <br/>
     *
     * @author 鲁济良
     * @param date
     *            日期
     * @return 日期 + 23:59:59
     * @since JDK 1.8
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
     * startDayOfMonth:(获取当前时间所在月第一天). <br/>
     * TODO(描述这个方法的注意事项 – 可选).<br/>
     *
     * @author 鲁济良
     * @param date
     *            时间
     * @return 当前时间本月第一天
     * @since JDK 1.8
     */
    public static Date startDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        cal.set(Calendar.MILLISECOND, 000);
        return cal.getTime();
    }

    /**
     * startDayOfMonth:(获取当前时间所在月最后一天). <br/>
     *
     * @author 鲁济良
     * @param date
     *            时间
     * @return 当前时间本月最后一天
     * @since JDK 1.8
     */
    public static Date endDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    /**
     * startDayOfYear:(获取当前时间所在年的开始日期). <br/>
     *
     * @author 鲁济良
     * @param date
     *            时间
     * @return 当前时间所在年的开始日期
     * @since JDK 1.8
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
     * theCurrentBeforeNdayWithFormat:(获取当前日期前N天的日期，指定时间字符串). <br/>
     *
     * @author 鲁济良
     * @param dayNum
     *            天数单位
     * @param format
     *            时间字符串格式
     * @return 时间字符串
     * @since JDK 1.8
     */
    public static String theCurrentBeforeNdayWithFormat(int dayNum, String format) {
        
        Calendar c = Calendar.getInstance();

        c.setTime(new Date());
        c.add(Calendar.DAY_OF_YEAR, -dayNum);
        Date date = c.getTime();
        SimpleDateFormat s = new SimpleDateFormat(format);
        String lastmonth = s.format(date);
        return lastmonth;
    }


    /**
     * daysBetween:(获取两个时间间隔天数). <br/>
     *
     * @author 鲁济良
     * @param startDate 时间1（yyyyMMdd）
     * @param endDate 时间2（yyyyMMdd）
     * @return 天数间隔
     * @since JDK 1.8
     */
    public static int daysBetween(String startDate, String endDate) {
        Date start = string2Date(startDate, DEFAULT_DATE2_FMT);
        Date end = string2Date(endDate, DEFAULT_DATE2_FMT);
        long betweendays = (end.getTime() - start.getTime()) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(betweendays));
    }

}
