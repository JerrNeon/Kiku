package com.jn.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


/**
 * Author：Stevie.Chen Time：2019/8/12
 * Class Comment：日期管理
 */
public class DateUtils {

    /*短日期格式*/
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    /*长时间格式*/
    public static final String TIME_FORMAT = "HH:mm:ss";
    /*短时间格式*/
    public static final String SMALL_TIME_FORMAT = "HH:mm:ss";
    /*长日期时间格式*/
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /*短日期时间格式*/
    public static final String SMALL_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    /**
     * 获取当前时间.(yyyy-MM-dd HH:mm:ss)
     */
    public static String getToDayStr() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT, Locale.CHINA);
        return sdf.format(new Date());
    }

    /**
     * 获取当前时间.(yyyy-MM-dd)
     */
    public static String getToDayStrSmall() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.CHINA);
        return sdf.format(new Date());
    }

    /**
     * 格式化时间.
     *
     * @param date the date
     * @return the string
     */
    public static String fomatDate(String date) {
        if (QMUtil.isNotEmpty(date)) {
            return date.substring(0, 4) + "-" + date.substring(4, 6) + "-"
                    + date.substring(6, 8);
        }
        return null;
    }

    /**
     * 格式化时间.
     *
     * @param date the date
     * @return the string
     */
    public static String fomatLongDate(String date) {
        if (QMUtil.isNotEmpty(date)) {
            return date.substring(0, 4) + "-" + date.substring(4, 6) + "-"
                    + date.substring(6, 8) + " " + date.substring(8, 10) + ":"
                    + date.substring(10, 12) + ":" + date.substring(12, 14);
        }
        return null;
    }

    /**
     * 格式化时间.
     *
     * @param date the date
     * @return the string
     */
    public static String fomatDateTime2String(String date) {
        if (QMUtil.isNotEmpty(date)) {
            return date.replace("-", "").replace("T", "").replace(":", "")
                    .replace(" ", "");
        }
        return null;
    }

    /**
     * 将时间字符串格式化成一个日期(java.util.Date)
     *
     * @param dateStr   要格式化的日期字符串，如"2014-06-15 12:30:12"
     * @param formatStr 格式化模板，如"yyyy-MM-dd HH:mm:ss"
     * @return the string
     */
    public static Date formatDateString2Date(String dateStr, String formatStr) {
        DateFormat dateFormat = new SimpleDateFormat(formatStr, Locale.CHINA);
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将时间字符串格式化成一个日期(java.util.Date)
     *
     * @param date      要格式化的日期字符串，如"2014-06-15 12:30:12"
     * @param formatStr 格式化模板，如"yyyy-MM-dd HH:mm:ss"
     * @return the string
     */
    public static String formatDate2String(Date date, String formatStr) {
        DateFormat dateFormat = new SimpleDateFormat(formatStr, Locale.CHINA);
        String result = null;
        try {
            result = dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将时间字符串规范化
     *
     * @param dateStr   要格式化的日期字符串，如"2014-06-15 12:30:12.0"
     * @param formatStr 格式化模板，如"yyyy-MM-dd HH:mm:ss"
     * @return the string
     */
    public static String formatDateString2String(String dateStr, String formatStr) {
        DateFormat dateFormat = new SimpleDateFormat(formatStr, Locale.CHINA);
        String result = null;
        try {
            result = dateFormat.format(dateFormat.parse(dateStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将一个毫秒数时间转换为相应的时间格式（yyyy-MM-dd hh:mm:ss）
     */
    public static String formateDateLongToString(long longSecond) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(longSecond);
        SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT, Locale.CHINA);
        return format.format(gc.getTime());
    }

    /**
     * 将一个毫秒数时间转换为相应的时间格式（yyyy-MM-dd hh:mm:ss）
     */
    public static String formateDateLongToString2(long longSecond) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault());
        return format.format(longSecond * 1000);
    }


    /**
     * 将一个毫秒数时间转换为相应的时间格式（yyyy-MM-dd）
     */
    public static String formateDateLongToStringSmall(long longSecond) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(longSecond);
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.CHINA);
        return format.format(gc.getTime());
    }

    /**
     * 将一个毫秒数时间转换为相应的时间格式（yyyy-MM-dd）
     */
    public static String formateDateLongToStringSmall2(long longSecond) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        return format.format(longSecond * 1000);
    }

    /**
     * 时间取出月日显示
     */
    public static String fomatDateString2MonthDayString(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.CHINA);
        try {
            // 用parse方法，可能会异常，所以要try-catch
            Date date = format.parse(dateStr);
            // 获取日期实例
            Calendar calendar = Calendar.getInstance();
            // 将日历设置为指定的时间
            calendar.setTime(date);
            // 获取年
            int year = calendar.get(Calendar.YEAR);
            // 这里要注意，月份是从0开始。
            int month = calendar.get(Calendar.MONTH);
            // 获取天
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            String monthStr = "", dayStr = "";
            if (month < 9)
                monthStr = "0" + (month + 1);
            else
                monthStr = (month + 1) + "";
            if (day < 10)
                dayStr = "0" + day;
            else
                dayStr = day + "";
            return monthStr + "-" + dayStr;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 将日期格式的字符串转换为长整型
     */
    public static long convert2long(String date, String format) {
        try {
            if (QMUtil.isNotEmpty(date)) {
                if (QMUtil.isEmpty(format))
                    format = DATE_TIME_FORMAT;
                SimpleDateFormat sf = new SimpleDateFormat(format, Locale.CHINA);
                return sf.parse(date).getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0l;
    }

    /**
     * 获取下一天.
     *
     * @param currentDate the current date
     * @return the next date str
     * @throws ParseException the parse exception
     */
    public static String getNextDateStr(String currentDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        Date date = sdf.parse(currentDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return sdf.format(calendar.getTime());
    }

    /**
     * 获取上一天.
     *
     * @param currentDate the current date
     * @return the next date str
     * @throws ParseException the parse exception
     */
    public static String getYesterdayStr(String currentDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        Date date = sdf.parse(currentDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        return sdf.format(calendar.getTime());
    }

    /**
     * 根据日期获取星期
     *
     * @param forMat 20150101 ....
     */
    public static String getWeekDayByDate(String strdate, String forMat) {
        final String[] dayNames = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
                "星期六"};
        SimpleDateFormat sdfInput = new SimpleDateFormat(forMat, Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        try {
            date = sdfInput.parse(strdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek < 0)
            dayOfWeek = 0;
        return dayNames[dayOfWeek];
    }

    public static Date getDateByDayNumber(Integer day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }

    public static String getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return String.valueOf(calendar.get(Calendar.YEAR));

    }

    public static String getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        return month < 10 ? ("0" + month) : String.valueOf(month);
    }

    public static String getCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return day < 10 ? ("0" + day) : String.valueOf(day);
    }

    /**
     * 秒转时时分秒
     */
    public static String getTimeFromSecond(int second) {
        if (second < 10) {
            return "00:0" + second;
        }
        if (second < 60) {
            return "00:" + second;
        }
        if (second < 3600) {
            int minute = second / 60;
            second = second - minute * 60;
            if (minute < 10) {
                if (second < 10) {
                    return "0" + minute + ":0" + second;
                }
                return "0" + minute + ":" + second;
            }
            if (second < 10) {
                return minute + ":0" + second;
            }
            return minute + ":" + second;
        }
        int hour = second / 3600;
        int minute = (second - hour * 3600) / 60;
        second = second - hour * 3600 - minute * 60;
        if (hour < 10) {
            if (minute < 10) {
                if (second < 10) {
                    return "0" + hour + ":0" + minute + ":0" + second;
                }
                return "0" + hour + ":0" + minute + ":" + second;
            }
            if (second < 10) {
                return "0" + hour + minute + ":0" + second;
            }
            return "0" + hour + minute + ":" + second;
        }
        if (minute < 10) {
            if (second < 10) {
                return hour + ":0" + minute + ":0" + second;
            }
            return hour + ":0" + minute + ":" + second;
        }
        if (second < 10) {
            return hour + minute + ":0" + second;
        }
        return hour + minute + ":" + second;
    }
}
