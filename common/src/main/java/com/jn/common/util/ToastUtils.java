/*
 *             				  _ooOoo_
 *                           o8888888o
 *                           88" . "88
 *                           (| -_- |)
 *                            O\ = /O
 *                        ____/`---'\____
 *                      .   ' \\| |// `.
 *                       / \\||| : |||// \
 *                     / _||||| -:- |||||- \
 *                       | | \\\ - /// | |
 *                     | \_| ''\---/'' | |
 *                      \ .-\__ `-` ___/-. /
 *                   ___`. .' /--.--\ `. . __
 *                ."" '< `.___\_<|>_/___.' >'"".
 *               | | : `- \`.;`\ _ /`;.`/ - ` : | |
 *                 \ \ `-. \_ __\ /__ _/ .-` / /
 *         ======`-.____`-.___\_____/___.-`____.-'======
 *                            `=---='
 *
 *         .............................................
 *                  	       佛祖保佑             永无BUG
 *         		佛曰:
 *                      写字楼里写字间，写字间里程序员；
 *                 	            程序人员写程序，又拿程序换酒钱。
 *                      酒醒只在网上坐，酒醉还来网下眠；
 *                      酒醉酒醒日复日，网上网下年复年。
 *                      但愿老死电脑间，不愿鞠躬老板前；
 *                      奔驰宝马贵者趣，公交自行程序员。
 *                      别人笑我忒疯癫，我笑自己命太贱；
 *                 	            不见满街漂亮妹，哪个归得程序员？
 *
 * Copyright (C) 2014
 * 版权所有
 *
 * 功能描述： 提示框工具类
 *
 * 创建标识： Xuxq 2014-11-19
 *
 * 修改标识：
 * 修改描述：
 */
package com.jn.common.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.DrawableRes;

/**
 * Author：Stevie.Chen Time：2019/8/2
 * Class Comment：ToastUtil
 */
public class ToastUtils {

    /******************* 多次触发Toast，只显示一次 *******************************/

    private static String oldMsg;
    private static long oneTime = 0;
    private static long twoTime = 0;
    private static Toast mToast = null;

    private static Context getContext() {
        return ContextUtils.getInstance().getContext();
    }

    /**
     * Toast.LENGTH_SHORT
     */
    public static void showToast(String s) {
        if (mToast == null) {
            mToast = Toast.makeText(getContext(), s, Toast.LENGTH_SHORT);
            mToast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (s.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    mToast.show();
                }
            } else {
                oldMsg = s;
                mToast.setText(s);
                mToast.show();
            }
        }
        oneTime = twoTime;
    }

    /**
     * 自定义时间
     */
    public static void showToast(String s, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(getContext().getApplicationContext(), s, duration);
            mToast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (s.equals(oldMsg)) {
                if (twoTime - oneTime > duration) {
                    mToast.show();
                }
            } else {
                oldMsg = s;
                mToast.setText(s);
                mToast.show();
            }
        }
        oneTime = twoTime;
    }

    /**
     * 多次触发Toast，只显示一次
     */
    public static void showToast(int resId) {
        showToast(getContext().getString(resId));
    }

    /**
     * 多次触发Toast，只显示一次
     */
    public static void showToast(int resId, int duration) {
        showToast(getContext().getString(resId), duration);
    }

    /**
     * 普通toast
     */
    public static void showMessage(String string) {
        Toast.makeText(getContext(), string, Toast.LENGTH_SHORT).show();
    }

    /**
     * 普通toast
     */
    public static void showMessage(String string, int duration) {
        Toast.makeText(getContext(), string, duration).show();
    }

    /**
     * 居中带图片的toast
     */
    public static void showToastByPic(String msg, @DrawableRes int resId, int duration) {
        Toast toast = Toast.makeText(getContext(), msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(getContext());
        imageCodeProject.setImageResource(resId);
        toastView.addView(imageCodeProject, 0);
        toast.show();
    }
}
