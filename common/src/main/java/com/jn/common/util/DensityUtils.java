package com.jn.common.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Author：Stevie.Chen Time：2019/8/12
 * Class Comment：常用单位转换的辅助类
 */
public class DensityUtils {

    private DensityUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * dp转px
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     *
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     */
    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     */
    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    /**
     * 获取dp
     */
    public static int getIntDip(Context context, float i) {
        return (int) getFloatDip(context, i);
    }

    /**
     * 获取dp
     */
    public static float getFloatDip(Context context, float i) {
        return context.getResources().getDisplayMetrics().density * i;
    }

    /**
     * 根据收集的分辨率指定字体的大小
     */
    public static int pixelsToDip(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        screenWidth = screenWidth > screenHeight ? screenWidth : screenHeight;
        /*
         * 1. 在视图的 onsizechanged里获取视图宽度，一般情况下默认宽度是320，所以计算一个缩放比率
         rate = (float) w/320   w是实际宽度
         2.然后在设置字体尺寸时 paint.setTextSize((int)(8*rate));   8是在分辨率宽为320 下需要设置的字体大小
         实际字体大小 = 默认字体大小 x  rate
         */
        int rate = (int) (3 * (float) screenWidth / 320); //我自己测试这个倍数比较适合，当然你可以测试后再修改
        return rate < 15 ? 15 : rate; //字体太小也不好看的
    }

    /**
     * 数值转换
     * <p>
     * 一般用于显示数值和实际数值不一样的情况
     * </P>
     */
    public static double calculateRate(String str) {
        double value = Double.valueOf(str);
        if (value <= 200000)
            value = value / 10;
        else if (value <= 220000)
            value = value / 9.7;
        else if (value <= 240000)
            value = value / 9.5;
        else if (value <= 250000)
            value = value / 9.3;
        else if (value <= 260000)
            value = value / 9;
        else if (value <= 270000)
            value = value / 8.8;
        else if (value <= 280000)
            value = value / 8.5;
        else if (value <= 300000)
            value = value / 8;
        else if (value <= 320000)
            value = value / 7.8;
        else if (value <= 340000)
            value = value / 7.5;
        else if (value <= 370000)
            value = value / 7.3;
        else if (value <= 380000)
            value = value / 7.2;
        else if (value <= 400000)
            value = value / 7;
        else if (value <= 410000)
            value = value / 6.8;
        else if (value <= 420000)
            value = value / 6.2;
        else if (value <= 430000)
            value = value / 6;
        else if (value <= 435000)
            value = value / 5.8;
        else if (value <= 440000)
            value = value / 5.5;
        else if (value <= 445000)
            value = value / 5.3;
        else if (value <= 450000)
            value = value / 5.1;
        else if (value <= 452000)
            value = value / 5;
        else if (value <= 453000)
            value = value / 4.53;
        else if (value <= 455000)
            value = value / 4.5;
        else if (value <= 460000)
            value = value / 4;
        else if (value <= 465000)
            value = value / 3.5;
        else if (value <= 468000)
            value = value / 3.2;
        else if (value <= 470000)
            value = value / 3;
        else if (value <= 473000)
            value = value / 2.8;
        else if (value <= 475000)
            value = value / 2.5;
        else if (value <= 478000)
            value = value / 2.2;
        else if (value <= 480000)
            value = value / 2;
        else if (value <= 482000)
            value = value / 1.8;
        else if (value <= 483000)
            value = value / 1.61;
        else if (value <= 485000)
            value = value / 1.5;
        else if (value <= 488000)
            value = value / 1.3;
        else if (value <= 490000)
            value = value / 1.2;
        else if (value <= 495000)
            value = value / 1.1;
        else if (value <= 498000)
            value = value / 1;
        return value;
    }
}
