package com.jn.kiku.utils;

import com.jn.common.util.DateUtils;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (计算目标时间与当前时间的时间差)
 * @create by: chenwei
 * @date 2016/8/8 20:12
 */
public class TimeTool {

    private final static long yearLevelValue = 365 * 24 * 60 * 60 * 1000L;
    private final static long monthLevelValue = 30 * 24 * 60 * 60 * 1000L;
    private final static long dayLevelValue = 24 * 60 * 60 * 1000L;
    private final static long hourLevelValue = 60 * 60 * 1000L;
    private final static long minuteLevelValue = 60 * 1000L;
    private final static long secondLevelValue = 1000L;

    /**
     * 计算出时间差中的年、月、日、时、分、秒
     *
     * @param nowTime    当前时间
     * @param targetTime 目标时间
     * @return 目标时间与当前时间差
     */
    public static String getDifference(long nowTime, long targetTime) {
        long period = targetTime - nowTime;
        return getDifference(period);
    }

    /**
     * 计算出时间差中的时、分
     */
    public static int[] getDifferenceHourAndMinute(long nowTime, long targetTime) {
        long period = targetTime - nowTime;
        int year = getYear(period);
        int month = getMonth(period - year * yearLevelValue);
        int day = getDay(period - year * yearLevelValue - month * monthLevelValue);
        int hour = getHour(period - year * yearLevelValue - month * monthLevelValue - day * dayLevelValue);
        int minute = getMinute(period - year * yearLevelValue - month * monthLevelValue - day * dayLevelValue - hour * hourLevelValue);
        return new int[]{hour, minute};
    }

    /**
     * 计算出时间差中的年、月、日、时、分、秒
     */
    private static String getDifference(long period) {//根据毫秒差计算时间差
        StringBuilder result = new StringBuilder();

        int year = getYear(period);
        int month = getMonth(period - year * yearLevelValue);
        int day = getDay(period - year * yearLevelValue - month * monthLevelValue);
        int hour = getHour(period - year * yearLevelValue - month * monthLevelValue - day * dayLevelValue);
        int minute = getMinute(period - year * yearLevelValue - month * monthLevelValue - day * dayLevelValue - hour * hourLevelValue);
        int second = getSecond(period - year * yearLevelValue - month * monthLevelValue - day * dayLevelValue - hour * hourLevelValue - minute * minuteLevelValue);

        if (year != 0)
            result.append("<font color=\"#ff0000\">").append(year).append("</font>年");
        if (month != 0)
            result.append("<font color=\"#ff0000\">").append(month).append("</font>月");
        if (day != 0)
            result.append("<font color=\"#ff0000\">").append(day).append("</font>天");
        if (hour != 0)
            result.append("<font color=\"#ff0000\">").append(hour).append("</font>时");
        if (minute != 0)
            result.append("<font color=\"#ff0000\">").append(minute).append("</font>分");
//        if (second != 0)
//            result.append("<font color=\"#ff0000\">" + second + "</font>秒");
        return result.toString();
    }

    /**
     * 计算出时间差中的年、月、日
     *
     * @param nowTime    当前时间
     * @param targetTime 目标时间
     * @return 目标时间与当前时间差 >1岁：xx岁xx个月 <1岁：xx月xx天 <1个月：xx天
     */
    public static String getDifferenceAge(long nowTime, long targetTime) {
        long period = targetTime - nowTime;
        return getDifferenceAge(period);
    }

    /**
     * 计算出时间差中的年、月、日
     *
     * @param period 时间差
     * @return >1岁：xx岁xx个月 <1岁：xx月xx天 <1个月：xx天
     */
    private static String getDifferenceAge(long period) {//根据毫秒差计算时间差
        StringBuilder result = new StringBuilder();

        int year = getYear(period);
        int month = getMonth(period - year * yearLevelValue);
        int day = getDay(period - year * yearLevelValue - month * monthLevelValue);

        if (year > 0) {
            result.append(year).append("岁");
            if (month > 0) {
                result.append(month).append("个月");
            }
        } else if (month > 0) {
            result.append(month).append("个月");
            if (day > 0) {
                result.append(day).append("天");
            }
        } else if (day > 0) {
            result.append(day).append("天");
        } else {
            result.append("");
        }
        return result.toString();
    }

    /**
     * 计算出时间差中的年、月、日、时、分
     *
     * @param targetTimeStr targetTimeStr
     * @return 刚刚、x分钟前、x小时前、x <= 3天前，时间日期
     */
    public static String getDifferenceTime(String targetTimeStr) {//根据毫秒差计算时间差
        long nowTime = DateUtils.convert2long(targetTimeStr, DateUtils.DATE_TIME_FORMAT);
        long targetTime = System.currentTimeMillis();
        return getDifferenceTime(nowTime, targetTime);
    }

    /**
     * 计算出时间差中的年、月、日、时、分
     *
     * @param nowTime    当前时间
     * @param targetTime targetTime
     * @return 刚刚、x分钟前、x小时前、x <= 3天前，时间日期
     */
    private static String getDifferenceTime(long nowTime, long targetTime) {//根据毫秒差计算时间差
        StringBuilder result = new StringBuilder();
        long period = targetTime - nowTime;
        int year = getYear(period);
        int month = getMonth(period - year * yearLevelValue);
        int day = getDay(period - year * yearLevelValue - month * monthLevelValue);
        int hour = getHour(period - year * yearLevelValue - month * monthLevelValue - day * dayLevelValue);
        int minute = getMinute(period - year * yearLevelValue - month * monthLevelValue - day * dayLevelValue - hour * hourLevelValue);

        if (year != 0 || month != 0 || day > 3) {
            result.append(DateUtils.formateDateLongToStringSmall(targetTime));
        } else if (day > 0) {
            result.append(day).append("天前");
        } else if (hour > 0) {
            result.append(hour).append("小时前");
        } else if (minute > 0) {
            result.append(minute).append("分钟前");
        } else {
            result.append("刚刚");
        }
        return result.toString();
    }

    private static int getYear(long period) {
        return (int) (period / yearLevelValue);
    }

    private static int getMonth(long period) {
        return (int) (period / monthLevelValue);
    }

    private static int getDay(long period) {
        return (int) (period / dayLevelValue);
    }

    private static int getHour(long period) {
        return (int) (period / hourLevelValue);
    }

    private static int getMinute(long period) {
        return (int) (period / minuteLevelValue);
    }

    private static int getSecond(long period) {
        return (int) (period / secondLevelValue);
    }
}
