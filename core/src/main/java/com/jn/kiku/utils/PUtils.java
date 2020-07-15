package com.jn.kiku.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (Android P适配)
 * @create by: chenwei
 * @date 2018/6/11 16:38
 */
public class PUtils {

    private static final int VIVO_NOTCH = 0x00000020;//是否有刘海
    private static final int VIVO_FILLET = 0x00000008;//是否有圆角

    /**
     * @param activity Activity
     *                 <p>
     *                 LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT：
     *                 只有当DisplayCutout完全包含在系统栏中时，才允许窗口延伸到DisplayCutout区域。
     *                 否则，窗口布局不与DisplayCutout区域重叠。
     *                 </p>
     *                 <p>
     *                 LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER：
     *                 该窗口决不允许与DisplayCutout区域重叠。
     *                 </p>
     *                 <p>
     *                 LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES：
     *                 该窗口始终允许延伸到屏幕短边上的DisplayCutout区域。
     *                 </P>
     *                 <p>
     *                 LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT：模式在全屏显示下跟LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER一样。
     *                 LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER：模式不会让屏幕到延申刘海区域中，会留出一片黑色区域。
     *                 LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES：模式会让屏幕到延申刘海区域中。
     *                 </P>
     */
    public static void setNotchScreen(Activity activity) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT;
        //lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER;
        //lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        window.setAttributes(lp);
    }

    public static void setDisplayCutoutInfoListener(Activity activity) {
        Window window = activity.getWindow();
        final View decorView = window.getDecorView();
        decorView.post(new Runnable() {
            @Override
            public void run() {
                DisplayCutout displayCutout = null;
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    displayCutout = decorView.getRootWindowInsets().getDisplayCutout();
                    Log.e("TAG", "安全区域距离屏幕左边的距离 SafeInsetLeft:" + displayCutout.getSafeInsetLeft());
                    Log.e("TAG", "安全区域距离屏幕右部的距离 SafeInsetRight:" + displayCutout.getSafeInsetRight());
                    Log.e("TAG", "安全区域距离屏幕顶部的距离 SafeInsetTop:" + displayCutout.getSafeInsetTop());
                    Log.e("TAG", "安全区域距离屏幕底部的距离 SafeInsetBottom:" + displayCutout.getSafeInsetBottom());
                    List<Rect> rectList = displayCutout.getBoundingRects();
                    if (rectList != null && rectList.size() > 0) {
                        Rect rect = rectList.get(0);
                        if (rect != null)
                            Log.e(getClass().getSimpleName(), "刘海屏区域：" + rect);
                    }
                }
            }
        });
    }

    /**
     * 华为手机是否有刘海屏
     *
     * @param context
     * @return true为有刘海，false则没有
     */
    public static boolean hasNotchAtHuawei(Context context) {
        boolean ret = false;
        try {
            ClassLoader classLoader = context.getClassLoader();
            Class HwNotchSizeUtil = classLoader.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            ret = (boolean) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {
            Log.e("Notch", "hasNotchAtHuawei ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e("Notch", "hasNotchAtHuawei NoSuchMethodException");
        } catch (Exception e) {
            Log.e("Notch", "hasNotchAtHuawei Exception");
        } finally {
            return ret;
        }
    }

    /**
     * 华为手机获取刘海尺寸：width、height
     *
     * @param context
     * @return int[0]值为刘海宽度 int[1]值为刘海高度
     */
    public static int[] getNotchSizeAtHuawei(Context context) {
        int[] ret = new int[]{0, 0};
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("getNotchSize");
            ret = (int[]) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {
            Log.e("Notch", "getNotchSizeAtHuawei ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e("Notch", "getNotchSizeAtHuawei NoSuchMethodException");
        } catch (Exception e) {
            Log.e("Notch", "getNotchSizeAtHuawei Exception");
        } finally {
            return ret;
        }
    }

    /**
     * ViVo手机是否有刘海屏
     *
     * @param context
     * @return true为有刘海，false则没有
     */
    public static boolean hasNotchAtViVo(Context context) {
        boolean ret = false;
        try {
            ClassLoader classLoader = context.getClassLoader();
            Class FtFeature = classLoader.loadClass("android.util.FtFeature");
            Method method = FtFeature.getMethod("isFeatureSupport", int.class);
            ret = (boolean) method.invoke(FtFeature, VIVO_NOTCH);
        } catch (ClassNotFoundException e) {
            Log.e("Notch", "hasNotchAtVoio ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e("Notch", "hasNotchAtVoio NoSuchMethodException");
        } catch (Exception e) {
            Log.e("Notch", "hasNotchAtVoio Exception");
        } finally {
            return ret;
        }
    }

    /**
     * ViVo手机获取刘海尺寸：width、height
     * vivo不提供接口获取刘海尺寸，目前vivo的刘海宽为100dp,高为27dp。
     *
     * @return int[0]值为刘海宽度 int[1]值为刘海高度
     */
    public static int[] getNotchSizeAtViVo() {
        int[] ret = new int[]{100, 20};
        return ret;
    }

    /**
     * OPPO手机是否有刘海屏
     *
     * @param context
     * @return true为有刘海，false则没有
     */
    public static boolean hasNotchInScreenAtOPPO(Context context) {
        return context.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
    }

    /**
     * 获取OPPO手机刘海尺寸：width、height
     * OPPO不提供接口获取刘海尺寸，目前OPPO的刘海区域则都是宽度为324px, 高度为80px
     *
     * @return int[0]值为刘海宽度 int[1]值为刘海高度
     */
    public static int[] getNotchSizeAtOPPO() {
        int[] ret = new int[]{324, 80};
        return ret;
    }

}
