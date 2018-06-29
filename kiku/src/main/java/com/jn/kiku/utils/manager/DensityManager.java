package com.jn.kiku.utils.manager;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.DecimalFormat;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (屏幕适配, 每个界面只能以一个维度来进行适配 - 宽或高)
 * @create by: chenwei
 * @date 2018/6/29 14:24
 */
public class DensityManager {

    public static final int WIDTH = 1;//宽
    public static final int HEIGHT = 2;//高
    private int DESIGN_WIDTH = 375;//对应设计图屏幕宽度为750px
    private int DESIGN_HEIGHT = 667;//对应设计图屏幕高度为1334px
    private static DensityManager instance;

    private float appDensity;//像素密度比例
    private float appScaledDensity;//缩放因子
    private DisplayMetrics appDisplayMetrics;//屏幕密度尺寸转换类

    @IntDef({WIDTH, HEIGHT})
    @Retention(RetentionPolicy.SOURCE)
    @interface Orientation {
    }

    private DensityManager() {
    }

    public static synchronized DensityManager getInstance() {
        if (instance == null)
            instance = new DensityManager();
        return instance;
    }

    /**
     * 初始化Density
     * 默认以750 x 1334的设计图来
     *
     * @param application Application
     */
    public void initDensity(@NonNull final Application application) {
        initDensity(application, 0, 0);
    }

    /**
     * 初始化Density
     *
     * @param application  Application
     * @param designWidth  设计图宽度(单位：dp)
     * @param designHeight 设计图高度(单位：dp)
     */
    public void initDensity(@NonNull final Application application, int designWidth, int designHeight) {
        //获取application的DisplayMetrics
        appDisplayMetrics = application.getResources().getDisplayMetrics();

        if (appDensity == 0) {
            //初始化的时候赋值
            appDensity = appDisplayMetrics.density;
            appScaledDensity = appDisplayMetrics.scaledDensity;

            //添加字体变化的监听
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    //字体改变后,将appScaledDensity重新赋值
                    if (newConfig != null && newConfig.fontScale > 0) {
                        appScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {
                }
            });
        }
        if (designWidth > 0)
            DESIGN_WIDTH = designWidth;
        if (designHeight > 0)
            DESIGN_HEIGHT = designHeight;
    }

    /**
     * 设置Activity默认的适配(基于屏幕宽度)
     *
     * @param activity Activity
     */
    public void setDensity(Activity activity) {
        setAppOrientation(activity, WIDTH);
    }

    /**
     * 设置Activity基于某个方向的适配
     *
     * @param activity    Activity
     * @param orientation Orientation
     */
    public void setDensity(Activity activity, @Orientation int orientation) {
        setAppOrientation(activity, orientation);
    }

    /**
     * 设置Activity中的独立像素比、缩放因子及像素比例
     *
     * @param activity    Activity
     * @param orientation Orientation(WIDTH | HEIGHT)
     */
    private void setAppOrientation(@Nullable Activity activity, @Orientation int orientation) {
        float targetDensity = getTargetDensity(orientation);
        float targetScaledDensity = targetDensity * (appScaledDensity / appDensity);
        int targetDensityDpi = (int) (160 * targetDensity);

        /**
         *
         * 最后在这里将修改过后的值赋给系统参数
         *
         * 只修改Activity的density值
         */

        DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }

    /**
     * 获取最终的像素比例
     *
     * @param orientation Orientation(WIDTH | HEIGHT)
     * @return Float
     */
    private float getTargetDensity(@Orientation int orientation) {
        float targetDensity = 0;
        try {
            Double division;
            //根据带入参数选择不同的适配方向
            if (orientation == WIDTH) {
                division = division(appDisplayMetrics.widthPixels, DESIGN_WIDTH);
            } else {
                division = division(appDisplayMetrics.heightPixels, DESIGN_HEIGHT);
            }
            //由于手机的长宽不尽相同,肯定会有除不尽的情况,有失精度,所以在这里把所得结果做了一个保留两位小数的操作
            DecimalFormat df = new DecimalFormat("0.00");
            String s = df.format(division);
            targetDensity = Float.parseFloat(s);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return targetDensity;
    }

    /**
     * 获取两个数的商
     *
     * @param a 除数
     * @param b 被除数
     * @return
     */
    private double division(int a, int b) {
        double div = 0;
        if (b != 0) {
            div = a / b;
        }
        return div;
    }
}
