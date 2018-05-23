package com.jn.kiku.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static com.jn.kiku.utils.QMUtil.isNotEmpty;


/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (日期管理)
 * @create by: chenwei
 * @date 2016/10/10 18:26
 */
public class DateUtils {

    /**
     * 短日期格式
     */
    public static String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 长日期格式
     */
    public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 长日期格式
     */
    public static String SMALLTIME_FORMAT = "HH:mm:ss";

    /**
     * 获取当前时间.(yyyy-MM-dd HH:mm:ss)
     *
     * @return
     * @throws
     */
    public static String getToDayStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    /**
     * 获取当前时间.(yyyy-MM-dd)
     *
     * @return
     * @throws
     */
    public static String getToDayStrSmall() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    /**
     * 格式化时间.
     *
     * @param date the date
     * @return the string
     */
    public static String fomatDate(String date) {
        if (isNotEmpty(date)) {
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
        if (isNotEmpty(date)) {
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
        if (isNotEmpty(date)) {
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
        DateFormat dateFormat = new SimpleDateFormat(formatStr);
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
        DateFormat dateFormat = new SimpleDateFormat(formatStr);
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
        DateFormat dateFormat = new SimpleDateFormat(formatStr);
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
     *
     * @param longSecond
     * @return
     */
    public static String formateDateLongToString(long longSecond) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(longSecond);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(gc.getTime());
    }

    /**
     * 将一个毫秒数时间转换为相应的时间格式（yyyy-MM-dd hh:mm:ss）
     *
     * @param longSecond
     * @return
     */
    public static String formateDateLongToString2(long longSecond) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        return format.format(longSecond * 1000);
    }


    /**
     * 将一个毫秒数时间转换为相应的时间格式（yyyy-MM-dd）
     *
     * @param longSecond
     * @return
     */
    public static String formateDateLongToStringSmall(long longSecond) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(longSecond);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(gc.getTime());
    }

    /**
     * 将一个毫秒数时间转换为相应的时间格式（yyyy-MM-dd）
     *
     * @param longSecond
     * @return
     */
    public static String formateDateLongToStringSmall2(long longSecond) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return format.format(longSecond * 1000);
    }

    /**
     * 时间取出月日显示
     *
     * @param dateStr
     * @return
     */
    public static String fomatDateString2MonthDayString(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
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
     *
     * @param date
     * @param format
     * @return
     */
    public static long convert2long(String date, String format) {
        try {
            if (isNotEmpty(date)) {
                if (QMUtil.isEmpty(format))
                    format = TIME_FORMAT;
                SimpleDateFormat sf = new SimpleDateFormat(format);
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
    public static String getNextDateStr(String currentDate)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = sdf.parse(currentDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        String nextDate = sdf.format(calendar.getTime());
        return nextDate;
    }

    /**
     * 获取上一天.
     *
     * @param currentDate the current date
     * @return the next date str
     * @throws ParseException the parse exception
     */
    public static String getYesterdayStr(String currentDate)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = sdf.parse(currentDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        String nextDate = sdf.format(calendar.getTime());
        return nextDate;
    }

    /**
     * 根据日期获取星期
     *
     * @param strdate
     * @param forMat  20150101 ....
     * @return
     */
    public static String getWeekDayByDate(String strdate, String forMat) {
        final String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
                "星期六"};
        SimpleDateFormat sdfInput = new SimpleDateFormat(forMat);
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
}
