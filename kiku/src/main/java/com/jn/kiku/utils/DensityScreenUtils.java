package com.jn.kiku.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (屏幕适配 ， 通过修改Density中的值)
 * @create by: chenwei
 * @date 2018/6/22 11:13
 * <p>
 * densityDpi ：像素密度
 * density：dpi / 160;
 * scaledDensity：字体的缩放因子
 * </P>
 */
public class DensityScreenUtils {

    private static float sNonCompatDensity;
    private static float sNonCompatScaledDensity;

    public static void setCustomDensity(@NonNull Activity activity, @NonNull final Application application, float screenWidthDp) {
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();

        if (sNonCompatDensity == 0) {
            sNonCompatDensity = appDisplayMetrics.density;
            sNonCompatScaledDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    //用户修改了系统字体大小
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNonCompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }

        //appDisplayMetrics.widthPixels：屏幕宽度(px)
        final float targetDensity = (appDisplayMetrics.widthPixels / screenWidthDp);
        final float targetScaledDensity = targetDensity * (sNonCompatScaledDensity / sNonCompatDensity);
        final int targetDensityDpi = (int) (160 * targetDensity);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaledDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }

    /**
     * 获取屏幕宽度(dp)
     *
     * @param screenWidth  屏幕宽度(px)
     * @param screenHeight 屏幕高度(px)
     * @param screenSize   屏幕尺寸(英寸)
     * @return
     */
    public static float getScreenWidthDp(int screenWidth, int screenHeight, float screenSize) {
        float densityDpi = (float) (Math.sqrt(screenWidth * screenWidth + screenHeight * screenHeight) / screenSize);
        float density = densityDpi / 160;
        return screenWidth / density;
    }

}
