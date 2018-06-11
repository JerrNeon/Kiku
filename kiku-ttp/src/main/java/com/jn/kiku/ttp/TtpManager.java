package com.jn.kiku.ttp;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.baidu.mapapi.SDKInitializer;
import com.jn.kiku.ttp.analysis.BaiduMobManage;
import com.jn.kiku.ttp.analysis.UMManage;
import com.jn.kiku.ttp.analysis.ZhugeManage;
import com.jn.kiku.ttp.bug.BugtagsManage;
import com.jn.kiku.ttp.pay.pingpp.PingPpManage;
import com.jn.kiku.ttp.share.SinaManage;

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
     * 初始化第三方Id、Secret等信息
     *
     * @param builder
     */
    public static void initTtpConstants(@NonNull TtpConstants.Builder builder) {
        builder.builder();
    }

    /**
     * 初始化百度地图SDK
     *
     * @param context Context
     */
    public static void initBaiduMap(Context context) {
        SDKInitializer.initialize(context);
    }

    /**
     * 初始化极光推送SDK
     *
     * @param context   Context
     * @param LOG_DEBUG 是否开启日志，建议debug下才开启
     */
    public static void initJpush(Context context, boolean LOG_DEBUG) {
        JPushInterface.setDebugMode(LOG_DEBUG);
        JPushInterface.init(context);
    }

    /**
     * 诸葛IO,用于统计数据
     *
     * @param url       数据上传服务器地址
     * @param LOG_DEBUG 是否开启日志，建议debug下才开启
     */
    public static void initZhuGeIo(String url, boolean LOG_DEBUG) {
        ZhugeManage.getInstance().setUploadURL(url);
        if (LOG_DEBUG) {
            ZhugeManage.getInstance().openDebug();
            ZhugeManage.getInstance().openLog();
        }
    }

    /**
     * 初始化新浪微博SDK
     *
     * @param context Context
     */
    public static void initSina(Context context) {
        SinaManage.getInstance(context).install();
    }

    /**
     * 初始化Ping++
     *
     * @param LOG_DEBUG 是否开启日志，建议debug开启日志信息，release关闭日志信息
     */
    public static void initPingPp(boolean LOG_DEBUG) {
        PingPpManage.getInstance().setDebugEnable(LOG_DEBUG);
    }

    /**
     * 初始化百度统计
     *
     * @param LOG_DEBUG 是否开启日志，建议debug开启日志信息，release关闭日志信息
     */
    public static void initBaiduMob(boolean LOG_DEBUG) {
        BaiduMobManage.getInstance().setDebugOn(LOG_DEBUG);
    }

    /**
     * 初始化Bugtags
     *
     * @param application Application
     */
    public static void initBugtags(Application application) {
        BugtagsManage.getInstance().init(application);
    }

    /**
     * 初始化友盟统计
     *
     * @param context   Context
     * @param LOG_DEBUG 是否开启日志，建议debug下才开启
     */
    public static void initUM(Context context, boolean LOG_DEBUG) {
        UMManage.getInstance().init(context, LOG_DEBUG);
    }
}
