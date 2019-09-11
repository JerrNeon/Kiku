package com.jn.kiku.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (尺寸工具类)
 * @create by: chenwei
 * @date 2017/6/3 13:34
 */
public class SizeUtils {

    private SizeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * dp转px
     *
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param spValue sp值
     * @return px值
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转sp
     *
     * @param pxValue px值
     * @return sp值
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 各种单位转换
     * <p>该方法存在于TypedValue</p>
     *
     * @param unit    单位
     * @param value   值
     * @param metrics DisplayMetrics
     * @return 转换结果
     */
    public static float applyDimension(int unit, float value, DisplayMetrics metrics) {
        switch (unit) {
            case TypedValue.COMPLEX_UNIT_PX:
                return value;
            case TypedValue.COMPLEX_UNIT_DIP:
                return value * metrics.density;
            case TypedValue.COMPLEX_UNIT_SP:
                return value * metrics.scaledDensity;
            case TypedValue.COMPLEX_UNIT_PT:
                return value * metrics.xdpi * (1.0f / 72);
            case TypedValue.COMPLEX_UNIT_IN:
                return value * metrics.xdpi;
            case TypedValue.COMPLEX_UNIT_MM:
                return value * metrics.xdpi * (1.0f / 25.4f);
        }
        return 0;
    }

    /**
     * 在onCreate中获取视图的尺寸
     * <p>需回调onGetSizeListener接口，在onGetSize中获取view宽高</p>
     * <p>用法示例如下所示</p>
     * <pre>
     * SizeUtils.forceGetViewSize(view, new SizeUtils.onGetSizeListener() {
     *     Override
     *     public void onGetSize(View view) {
     *         view.getWidth();
     *     }
     * });
     * </pre>
     *
     * @param view     视图
     * @param listener 监听器
     */
    public static void forceGetViewSize(final View view, final onGetSizeListener listener) {
        view.post(new Runnable() {
            @Override
            public void run() {
                if (listener != null) {
                    listener.onGetSize(view);
                }
            }
        });
    }

    /**
     * 获取到View尺寸的监听
     */
    public interface onGetSizeListener {
        void onGetSize(View view);
    }

    /**
     * 测量视图尺寸
     *
     * @param view 视图
     * @return arr[0]: 视图宽度, arr[1]: 视图高度
     */
    public static int[] measureView(View view) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
        int widthSpec = ViewGroup.getChildMeasureSpec(0, 0, lp.width);
        int lpHeight = lp.height;
        int heightSpec;
        if (lpHeight > 0) {
            heightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY);
        } else {
            heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }
        view.measure(widthSpec, heightSpec);
        return new int[]{view.getMeasuredWidth(), view.getMeasuredHeight()};
    }

    /**
     * 获取测量视图宽度
     *
     * @param view 视图
     * @return 视图宽度
     */
    public static int getMeasuredWidth(View view) {
        return measureView(view)[0];
    }

    /**
     * 获取测量视图高度
     *
     * @param view 视图
     * @return 视图高度
     */
    public static int getMeasuredHeight(View view) {
        return measureView(view)[1];
    }

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

    /**
     * 显示的价格数据回显真实的数据
     *
     * @param str
     * @return
     */
    public static int calculateRealRate(String str) {
        double value = Double.valueOf(str);
        if (value <= 20000)
            value = value * 10;
        else if (value <= 22680)
            value = value * 9.7;
        else if (value <= 24742)
            value = value * 9.5;
        else if (value <= 26882)
            value = value * 9.3;
        else if (value <= 28889)
            value = value * 9;
        else if (value <= 30682)
            value = value * 8.8;
        else if (value <= 32941)
            value = value * 8.5;
        else if (value <= 37500)
            value = value * 8;
        else if (value <= 41026)
            value = value * 7.8;
        else if (value <= 45333)
            value = value * 7.5;
        else if (value <= 50685)
            value = value * 7.3;
        else if (value <= 52778)
            value = value * 7.2;
        else if (value <= 57143)
            value = value * 7;
        else if (value <= 60294)
            value = value * 6.8;
        else if (value <= 67742)
            value = value * 6.2;
        else if (value <= 71667)
            value = value * 6;
        else if (value <= 75000)
            value = value * 5.8;
        else if (value <= 80000)
            value = value * 5.5;
        else if (value <= 83962)
            value = value * 5.3;
        else if (value <= 88235)
            value = value * 5.1;
        else if (value <= 90400)
            value = value * 5;
        else if (value <= 100000)
            value = value * 4.53;
        else if (value <= 101111)
            value = value * 4.5;
        else if (value <= 115000)
            value = value * 4;
        else if (value <= 132857)
            value = value * 3.5;
        else if (value <= 146250)
            value = value * 3.2;
        else if (value <= 156667)
            value = value * 3;
        else if (value <= 168929)
            value = value * 2.8;
        else if (value <= 190000)
            value = value * 2.5;
        else if (value <= 217273)
            value = value * 2.2;
        else if (value <= 240000)
            value = value * 2;
        else if (value <= 267778)
            value = value * 1.8;
        else if (value <= 300000)
            value = value * 1.61;
        else if (value <= 323333)
            value = value * 1.5;
        else if (value <= 375385)
            value = value * 1.3;
        else if (value <= 408333)
            value = value * 1.2;
        else if (value <= 450000)
            value = value * 1.1;
        else if (value <= 49800)
            value = value / 1;
        return (int) value;
    }
}
