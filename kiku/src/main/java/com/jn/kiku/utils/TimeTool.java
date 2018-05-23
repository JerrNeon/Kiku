package com.jn.kiku.utils;

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


    public static String getDifference(long nowTime, long targetTime) {//目标时间与当前时间差
        long period = targetTime - nowTime;
        return getDifference(period);
    }

    public static int[] getDifferenceHourAndMinute(long nowTime, long targetTime) {//目标时间与当前时间差
        long period = targetTime - nowTime;
        /*******计算出时间差中的年、月、日、天、时、分、秒*******/
        int year = getYear(period);
        int month = getMonth(period - year * yearLevelValue);
        int day = getDay(period - year * yearLevelValue - month * monthLevelValue);
        int hour = getHour(period - year * yearLevelValue - month * monthLevelValue - day * dayLevelValue);
        int minute = getMinute(period - year * yearLevelValue - month * monthLevelValue - day * dayLevelValue - hour * hourLevelValue);
        return new int[]{hour, minute};
    }

    private static String getDifference(long period) {//根据毫秒差计算时间差
        StringBuilder result = new StringBuilder();

        /*******计算出时间差中的年、月、日、天、时、分、秒*******/
        int year = getYear(period);
        int month = getMonth(period - year * yearLevelValue);
        int day = getDay(period - year * yearLevelValue - month * monthLevelValue);
        int hour = getHour(period - year * yearLevelValue - month * monthLevelValue - day * dayLevelValue);
        int minute = getMinute(period - year * yearLevelValue - month * monthLevelValue - day * dayLevelValue - hour * hourLevelValue);
        int second = getSecond(period - year * yearLevelValue - month * monthLevelValue - day * dayLevelValue - hour * hourLevelValue - minute * minuteLevelValue);

        if (year != 0)
            result.append("<font color=\"#ff0000\">" + year + "</font>年");
        if (month != 0)
            result.append("<font color=\"#ff0000\">" + month + "</font>月");
        if (day != 0)
            result.append("<font color=\"#ff0000\">" + day + "</font>天");
        if (hour != 0)
            result.append("<font color=\"#ff0000\">" + hour + "</font>时");
        if (minute != 0)
            result.append("<font color=\"#ff0000\">" + minute + "</font>分");
//        if (second != 0)
//            result.append("<font color=\"#ff0000\">" + second + "</font>秒");
        return result.toString();
    }

    public static int getYear(long period) {
        return (int) (period / yearLevelValue);
    }

    public static int getMonth(long period) {
        return (int) (period / monthLevelValue);
    }

    public static int getDay(long period) {
        return (int) (period / dayLevelValue);
    }

    public static int getHour(long period) {
        return (int) (period / hourLevelValue);
    }

    public static int getMinute(long period) {
        return (int) (period / minuteLevelValue);
    }

    public static int getSecond(long period) {
        return (int) (period / secondLevelValue);
    }
}
