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
package com.jn.kiku.ttp.common.utils;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * @author tu
 * @since 2015/4/27
 */
public class ToastUtil {

    /******************* 多次触发Toast，只显示一次 *******************************/

    private static String oldMsg;
    private static long oneTime = 0;
    private static long twoTime = 0;
    private static Toast mToast = null;

    /**
     * Toast.LENGTH_SHORT
     *
     * @param context
     * @param s
     */
    public static void showToast(Context context, String s) {
        if (mToast == null) {
            mToast = Toast.makeText(context.getApplicationContext(), s, Toast.LENGTH_SHORT);
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
     *
     * @param context
     * @param s
     * @param duration
     */
    public static void showToast(Context context, String s, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context.getApplicationContext(), s, duration);
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
     *
     * @param context
     * @param resId
     */
    public static void showToast(Context context, int resId) {
        showToast(context, context.getString(resId));
    }

    /**
     * 多次触发Toast，只显示一次
     *
     * @param context
     * @param resId
     */
    public static void showToast(Context context, int resId, int duration) {
        showToast(context, context.getString(resId), duration);
    }

    /**
     * 普通toast
     *
     * @param ctx
     * @param string
     */
    public static void showMessage(Context ctx, String string) {
        Toast.makeText(ctx, string, Toast.LENGTH_SHORT).show();
    }

    /**
     * 普通toast
     *
     * @param ctx
     * @param string
     */
    public static void showMessage(Context ctx, String string, int duration) {
        Toast.makeText(ctx, string, duration).show();
    }

    /**
     * 居中带图片的toast
     *
     * @param context
     * @param duration 事件间隔
     */
    public static void showToastByPic(Context context, String msg, @DrawableRes int resId, int duration) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(context);
        imageCodeProject.setImageResource(resId);
        toastView.addView(imageCodeProject, 0);
        toast.show();
    }
}
