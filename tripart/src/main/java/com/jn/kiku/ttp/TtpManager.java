package com.jn.kiku.ttp;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import cn.jpush.android.api.JPushInterface;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (第三方平台管理)
 * @create by: chenwei
 * @date 2018/5/30 10:12
 */
public class TtpManager {

    /**
     * 初始化百度地图SDK
     *
     * @param context Context
     */
    public static void initBaiduMap(Context context) {
        //SDKInitializer.initialize(context);
    }

    /**
     * 初始化极光推送SDK
     *
     * @param context   Context
     * @param LOG_DEBUG 是否开启日志，建议debug下才开启
     */
    public static void initJPush(Context context, boolean LOG_DEBUG) {
        JPushInterface.setDebugMode(LOG_DEBUG);
        JPushInterface.init(context);
    }

    /**
     * 初始化新浪回调地址
     *
     * @param redirectUrl 回调地址
     */
    public static void initSina(String redirectUrl) {
        TtpConstants.SINA_REDIRECT_URL = redirectUrl;
    }

    /**
     * 初始化友盟
     */
    public static void initUM(Context context, String appKey) {
        UMConfigure.init(context, appKey, "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null);
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
    }
}
