package com.jn.kiku.ttp.analysis;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (友盟统计管理)
 * @create by: chenwei
 * @date 2018/1/22 14:04
 */
public class UMManage {

    private static UMManage instance = null;

    private UMManage() {
    }

    public static synchronized UMManage getInstance() {
        if (instance == null)
            instance = new UMManage();
        return instance;
    }

    /**
     * 初始化，在Application中使用
     *
     * @param context     上下文，不能为空
     * @param isLogEnable boolean 默认为false，如需查看LOG设置为true
     */
    public void init(Context context, boolean isLogEnable) {
        init(context, UMConfigure.DEVICE_TYPE_PHONE, null);
        setEncryptEnabled(true);
        setLogEnabled(isLogEnable);
        openActivityDurationTrack(false);// 禁止默认的页面统计方式，这样将不会再自动统计Activity。
    }

    /**
     * 设置场景类型为普通统计场景类型
     *
     * @param context
     */
    public void setScenarioType(Context context) {
        setScenarioType(context, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }

    /**
     * 初始化，在Application中使用
     * <p>
     * 新版本中即使已经在AndroidManifest.xml中配置appkey和channel值，也需要在App代码中调用初始化接口
     * （如需要使用AndroidManifest.xml中配置好的appkey和channel值，UMConfigure.init调用中appkey和channel参数请置为null）：
     * </P>
     *
     * @param context    上下文，不能为空
     * @param deviceType 设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
     * @param pushSecret Push推送业务的secret，需要集成Push功能时必须传入Push的secret，否则传空
     */
    public void init(Context context, int deviceType, String pushSecret) {
        UMConfigure.init(context, deviceType, pushSecret);
    }

    /**
     * 初始化，在Application中使用
     * <p>
     * 新版本中即使已经在AndroidManifest.xml中配置appkey和channel值，也需要在App代码中调用初始化接口
     * （如需要使用AndroidManifest.xml中配置好的appkey和channel值，UMConfigure.init调用中appkey和channel参数请置为null）：
     * </P>
     *
     * @param context    上下文，不能为空
     * @param appKey     友盟 AppKey
     * @param channel    :友盟 Channel
     * @param deviceType 设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
     * @param pushSecret Push推送业务的secret，需要集成Push功能时必须传入Push的secret，否则传空
     */
    public void init(Context context, String appKey, String channel, int deviceType, String pushSecret) {
        UMConfigure.init(context, appKey, channel, deviceType, pushSecret);
    }

    /**
     * 场景类型设置接口
     *
     * @param context
     * @param etype   EScenarioType.E_UM_NORMAL 普通统计场景类型  EScenarioType.E_UM_GAME 游戏场景类型
     */
    public void setScenarioType(Context context, MobclickAgent.EScenarioType etype) {
        MobclickAgent.setScenarioType(context, etype);
    }

    /**
     * 设置组件化的Log开关
     * 参数: boolean 默认为false，如需查看LOG设置为true
     */
    public void setLogEnabled(boolean isLogEnable) {
        UMConfigure.setLogEnabled(isLogEnable);
    }


    /**
     * 设置日志加密
     * 参数：boolean 默认为false（不加密）
     */
    public void setEncryptEnabled(boolean isEncryptEnable) {
        UMConfigure.setEncryptEnabled(isEncryptEnable);
    }

    /**
     * 设置Secret Key
     * 新增secret Key接口,防止appkey被盗用,secretkey网站申请
     *
     * @param context
     * @param secretkey
     */
    public void setSecret(Context context, String secretkey) {
        MobclickAgent.setSecret(context, secretkey);
    }

    /**
     * 统计应用时长的(也就是Session时长,当然还包括一些其他功能)
     * <p>
     * 必须调用 MobclickAgent.onResume() 和MobclickAgent.onPause()方法，
     * 才能够保证获取正确的新增用户、活跃用户、启动次数、使用时长等基本数据。
     *
     * @param context
     */
    public void onResume(Context context) {
        MobclickAgent.onResume(context);
    }

    /**
     * 统计应用时长的(也就是Session时长,当然还包括一些其他功能)
     * <p>
     * 必须调用 MobclickAgent.onResume() 和MobclickAgent.onPause()方法，
     * 才能够保证获取正确的新增用户、活跃用户、启动次数、使用时长等基本数据。
     *
     * @param context
     */
    public void onPause(Context context) {
        MobclickAgent.onPause(context);
    }

    /**
     * 统计页面信息
     * <p>
     * 在onResume中调用
     * </P>
     *
     * @param className
     */
    public void onPageStart(String className) {
        MobclickAgent.onPageStart(className);
    }

    /**
     * 统计页面信息
     * <p>
     * 在onPause中调用
     * </p>
     *
     * @param className
     */
    public void onPageEnd(String className) {
        MobclickAgent.onPageEnd(className);
    }

    public void onResumeActivity(Context context, String className) {
        UMManage.getInstance().onPageStart(className);// [统计页面(仅有Activity的应用中SDK自动调用,不需要单独写。参数为页面名称,可自定义)]
        UMManage.getInstance().onResume(context);//友盟统计，所有Activity中添加，父类添加后子类不用重复添加
    }

    public void onPauseActivity(Context context, String className) {
        UMManage.getInstance().onPageEnd(className);// [统计页面(仅有Activity的应用中SDK自动调用,不需要单独写。参数为页面名称,可自定义)]
        UMManage.getInstance().onPause(context);//友盟统计，所有Activity中添加，父类添加后子类不用重复添加
    }

    public void onResumeFragment(String className) {
        UMManage.getInstance().onPageStart(className);// [统计页面(仅有Activity的应用中SDK自动调用,不需要单独写。参数为页面名称,可自定义)]
    }

    public void onPauseFragment(String className) {
        UMManage.getInstance().onPageEnd(className);// [统计页面(仅有Activity的应用中SDK自动调用,不需要单独写。参数为页面名称,可自定义)]
    }

    /**
     * <p>
     * 如果开发者调用kill或者exit之类的方法杀死进程，请务必在此之前调用onKillProcess(Context context)方法，用来保存统计数据。
     * </p>
     *
     * @param context
     */
    public void onKillProcess(Context context) {
        MobclickAgent.onKillProcess(context);
    }

    /**
     * 是否开启默认的页面统计方式
     *
     * @param isOpen
     */
    public void openActivityDurationTrack(boolean isOpen) {
        MobclickAgent.openActivityDurationTrack(isOpen);
    }

    /**
     * 获取测试设备信息
     *
     * @param context
     * @return
     */
    public String[] getTestDeviceInfo(Context context) {
        return UMConfigure.getTestDeviceInfo(context);
    }
}
