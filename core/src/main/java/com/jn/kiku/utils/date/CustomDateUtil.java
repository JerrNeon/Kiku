package com.jn.kiku.utils.date;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author tu
 * @version V1.0
 * @ClassName: DateUtil
 * @Description: 日期工具类
 * @date 2015年10月21日 下午5:58:02
 */
public class CustomDateUtil {

    public static String[] weekName = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    public static int getMonthDays(int year, int month) {
        if (month > 12) {
            month = 1;
            year += 1;
        } else if (month < 1) {
            month = 12;
            year -= 1;
        }
        int[] arr = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int days = 0;

        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            arr[1] = 29; // 闰年2月29天
        }

        try {
            days = arr[month - 1];
        } catch (Exception e) {
            e.getStackTrace();
        }

        return days;
    }

    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static int getDay() {
        return Calendar.getInstance().get(Calendar.DATE);
    }
    //日
    public static int getCurrentMonthDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }
    //星期
    public static int getWeekDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }
    //当年的第几周
    public static int getWeekOfYear() {
        return Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
    }
    //当年的第几周
    public static int getWeekOfMonth() {
        return Calendar.getInstance().get(Calendar.WEEK_OF_MONTH);
    }
    // 获取当前时间所在年的最大周数
    public static int getMaxWeekNumOfYear(int year) {
        Calendar c = new GregorianCalendar();
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);

        return getWeekOfYear(c.getTime());
    }

    // 获取当前时间所在年的周数
    public static int getWeekOfYear(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);

        return c.get(Calendar.WEEK_OF_YEAR);
    }



    public static int getHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }
    public static int getMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }
    public static int getSecond(){
        return Calendar.getInstance().get(Calendar.SECOND);
    }

    public static CustomDate getNextSunday() {

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 7 - getWeekDay() + 1);
        CustomDate date = new CustomDate(c.get(Calendar.YEAR),
                c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
        return date;
    }

    public static int[] getWeekSunday(int year, int month, int day, int pervious) {
        int[] time = new int[3];
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.add(Calendar.DAY_OF_MONTH, pervious);
        time[0] = c.get(Calendar.YEAR);
        time[1] = c.get(Calendar.MONTH) + 1;
        time[2] = c.get(Calendar.DAY_OF_MONTH);
        return time;

    }

    /**
     * 获得时间的星期
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String getWeek(int year, int month, int day){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.DAY_OF_MONTH, day);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek < 0)
            dayOfWeek = 0;
        return weekName[dayOfWeek];
    }

    public static int getWeekDayFromDate(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getDateFromString(year, month));
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return week_index;
    }

    /**
     * String 转date
     *
     * @param ymd
     * @return
     */
    public static Date string2Date(String ymd) {
        Date d = new Date();
        try {
            d = new SimpleDateFormat("yyyy-MM-dd").parse(ymd);
        } catch (Exception e) {
            System.out.println(e);
        }
        return d;
    }

    @SuppressLint("SimpleDateFormat")
    public static Date getDateFromString(int year, int month) {
        String dateString = year + "-" + (month > 9 ? month : ("0" + month))
                + "-01";
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return date;
    }

    /**
     * 获取两位格式时间 1-> 01; 13-> 13
     * @param time
     * @return
     */
    public static String getTwoTime(int time){
        return (time > 9 ? time : ("0" + time))+"";
    }

    public static boolean isToday(CustomDate date) {
        return (date.year == CustomDateUtil.getYear() &&
                date.month == CustomDateUtil.getMonth()
                && date.day == CustomDateUtil.getCurrentMonthDay());
    }

    public static boolean isCurrentMonth(CustomDate date) {
        return (date.year == CustomDateUtil.getYear() &&
                date.month == CustomDateUtil.getMonth());
    }

    /******************2015年10月26日***************************/

    /**
     * 根据年 月 获取对应的月份 天数
     */
    public static int getDaysByYearMonth(int year, int month) {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * @param year
     * @param month
     * @param day
     * @return 2015-09-12 14:57:19
     */
    public static String getStringFormat(int year, int month, int day, boolean isStart) {
        if (isStart)
            return year + "-" + (month > 9 ? month : ("0" + month)) + "-" +
                    (day > 9 ? day : ("0" + day)) + " " + "00:00:00";
        else
            return year + "-" + (month > 9 ? month : ("0" + month)) + "-" +
                    (day > 9 ? day : ("0" + day)) + " " + "23:59:59";
    }

    /**
     *
     * @param year
     * @param month
     * @param day
     * @return  20151023
     */
    public static String getStrFormatYMD(int year, int month, int day){
        return  year + "" + (month > 9 ? month : ("0" + month)) +
                (day > 9 ? day : ("0" + day));
    }

    /**
     * 获取月份第一堂时间戳
     */
    public static String getMonthFirst(CustomDate mDate) {
        //NLogUtil.logI("starttime", mDate.getYear()+"-"+mDate.getMonth()+"-"+"1");
        return getTime(CustomDateUtil.getStringFormat(mDate.getYear(),
                mDate.getMonth(), 1, true), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取月份最后一天堂时间戳
     */
    public static String getMonthFinally(CustomDate mDate) {
        //选择月份的最大日期
        int selectMonthMaxDay = CustomDateUtil.getMonthDays(mDate.getYear(), mDate.getMonth());
        //NLogUtil.logI("end", mDate.getYear()+"-"+mDate.getMonth()+"-"+selectMonthMaxDay);
        return getTime(CustomDateUtil.getStringFormat(mDate.getYear(),
                mDate.getMonth(), selectMonthMaxDay, false), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 比较当前月份
     *
     * @param mDate
     * @return 相等返回 0 ;小于返回 -1；大于返回1
     */
    public static int CompareCurrentMonth(CustomDate mDate) {
        if (mDate.getYear() < CustomDateUtil.getYear()) {  //判断年
            return -1;
        } else if (mDate.getYear() > CustomDateUtil.getYear()) {
            return 1;
        } else if (mDate.getMonth() < CustomDateUtil.getMonth()) {//判断月
            return -1;
        } else if (mDate.getMonth() > CustomDateUtil.getMonth()) {
            return 1;
        } else{
            return 0;
        }

    }
    /**
     * 比较当前日期
     *
     * @param mDate
     * @return 相等返回 0 ;小于返回 -1；大于返回1
     */
    public static int CompareCurrentDay(CustomDate mDate) {
        if (mDate.getYear() < CustomDateUtil.getYear()) {  //判断年
            return -1;
        } else if (mDate.getYear() > CustomDateUtil.getYear()) {
            return 1;
        } else if (mDate.getMonth() < CustomDateUtil.getMonth()) {//年份相等判断月
            return -1;
        } else if (mDate.getMonth() > CustomDateUtil.getMonth()) {
            return 1;
        } else if(mDate.getDay() > CustomDateUtil.getCurrentMonthDay()){//月份相等判断日
            return 1;
        }else if(mDate.getDay() < CustomDateUtil.getCurrentMonthDay()){
            return -1;
        }else{
            return 0;
        }
    }

    /**
     * mDate1 与 mDate2 比较
     * @param mDate1
     * @param mDate2
     * @return   相等返回 0 ;小于返回 -1；大于返回1
     */
    public static int CompareTwoDay(CustomDate mDate1,CustomDate mDate2){
        if(mDate1.getYear() <  mDate2.getYear()){
            return -1;
        }else if(mDate1.getYear() >  mDate2.getYear()){
            return 1;
        }else{
            if(mDate1.getMonth() <  mDate2.getMonth()){
                return -1;
            }else if(mDate1.getMonth() >  mDate2.getMonth()){
                return 1;
            }else{
                return 0;
            }
        }
    }
    /**
     * // 将字符串转为时间戳
     *
     * @param timeStr   时间字符串
     * @param dateStyle 转化格式
     * @return
     */
    public static String getTime(String timeStr, String dateStyle) {
        //NLogUtil.logI("日期", timeStr);
        String re_time = null;
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        SimpleDateFormat sdf = new SimpleDateFormat(dateStyle);
        Date d;
        try {
            d = sdf.parse(timeStr);
            Long l = d.getTime();
            re_time = l + "";
            //NLogUtil.logI("时间戳", re_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return re_time;
    }
    /**
     * // 将字符串转为时间戳
     *
     * @param timeStr   时间字符串
     * @param dateStyle 转化格式
     * @return
     */
    public static long getLongTime(String timeStr, String dateStyle) {
        long re_time = 0;
        SimpleDateFormat sdf = new SimpleDateFormat(dateStyle);
        Date d;
        try {
            d = sdf.parse(timeStr);
            re_time = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return re_time;
    }

    /**
     * // 将时间戳转为字符串
     *
     * @param cc_time
     * @param dateStyle
     * @return
     */
    public static String getStrTime(String cc_time, String dateStyle) {
        //NLogUtil.logI("时间戳转日期", cc_time);
        String re_StrTime = null;
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        SimpleDateFormat sdf = new SimpleDateFormat(dateStyle);
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time));
        //NLogUtil.logI("时间戳转日期", re_StrTime);
        return re_StrTime;

    }
}
