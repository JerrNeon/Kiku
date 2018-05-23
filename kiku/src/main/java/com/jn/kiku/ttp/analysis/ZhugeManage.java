package com.jn.kiku.ttp.analysis;

import android.content.Context;

import com.zhuge.analysis.stat.ZhugeSDK;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (诸葛IO管理)
 * @create by: chenwei
 * @date 2017/3/29 16:57
 */
public class ZhugeManage {

    private static ZhugeManage instance = null;

    private ZhugeManage() {
    }

    public static synchronized ZhugeManage getInstance() {
        if (instance == null)
            instance = new ZhugeManage();
        return instance;
    }

    public void init(Context context) {
        ZhugeSDK.getInstance().init(context.getApplicationContext());
    }

    public void flush(Context context) {
        ZhugeSDK.getInstance().flush(context.getApplicationContext());
    }

    /**
     * 设置数据上传服务器地址
     *
     * @param url
     */
    public void setUploadURL(String url) {
        ZhugeSDK.getInstance().setUploadURL(url, null);
    }

    public void openDebug() {
        ZhugeSDK.getInstance().openDebug();
    }

    public void openLog() {
        ZhugeSDK.getInstance().openLog();
    }

    /**
     * 设置日志级别
     *
     * @param logLevel Log.VERBOSE/Log.DEBUG/Log.ERROR/Log.ASSERT/Log.WARN
     */
    public void setLogLevel(int logLevel) {
        ZhugeSDK.getInstance().setLogLevel(logLevel);
    }

    /**
     * 标识用户
     *
     * @param context
     * @param userId     用户ID
     * @param jsonObject 用户属性
     */
    public void identify(Context context, String userId, JSONObject jsonObject) {
        ZhugeSDK.getInstance().identify(context.getApplicationContext(), userId, jsonObject);
    }

    /**
     * 标识用户
     *
     * @param context
     * @param userId  用户ID
     * @param hashMap 用户属性
     */
    public void identify(Context context, String userId, HashMap<String, Object> hashMap) {
        ZhugeSDK.getInstance().identify(context.getApplicationContext(), userId, hashMap);
    }

    /**
     * 记录事件
     * 只统计事件的次数
     *
     * @param context
     * @param trackName 事件名称
     */
    public void track(Context context, String trackName) {
        ZhugeSDK.getInstance().track(context.getApplicationContext(), trackName);
    }

    /**
     * 记录事件
     *
     * @param context
     * @param trackName  事件名称
     * @param jsonObject 事件属性
     */
    public void track(Context context, String trackName, JSONObject jsonObject) {
        ZhugeSDK.getInstance().track(context.getApplicationContext(), trackName, jsonObject);
    }

    /**
     * 记录事件
     *
     * @param context
     * @param trackName 事件名称
     * @param hashMap   事件属性
     */
    public void track(Context context, String trackName, HashMap<String, Object> hashMap) {
        ZhugeSDK.getInstance().track(context.getApplicationContext(), trackName, hashMap);
    }

    /**
     * 时长事件的统计(来开始一个事件的统计)
     * <p>
     * 若您希望统计⼀一个事件发生的时长，比如视频的播放，页面的停留，那么可以调用如下接口来进行
     * <p>
     * 注意： startTrack()与endTrack()必须成对出现（eventName⼀一致），单独调⽤用⼀一个接⼝口是⽆无效的
     *
     * @param eventName 事件的名称
     */
    public void startTrack(String eventName) {
        ZhugeSDK.getInstance().startTrack(eventName);
    }

    /**
     * 调⽤用 endTrack() 来记录事件的持续时长。调用 endTrack() 之前，相同eventName的事件必须
     * 已经调用过 startTrack() ，否则这个接口并不不会产生任何事件。
     * <p>
     * 注意： startTrack()与endTrack()必须成对出现（eventName⼀一致），单独调⽤用⼀一个接⼝口是⽆无效的
     *
     * @param eventName
     * @param jsonObject
     */
    public void endTrack(String eventName, JSONObject jsonObject) {
        ZhugeSDK.getInstance().endTrack(eventName, jsonObject);
    }

    /**
     * @return 获取当前设备在诸葛体系下的设备标识
     */
    public String getDid() {
        return ZhugeSDK.getInstance().getDid();
    }

    /**
     * @return 获得当前应用所属的会话ID
     */
    public long getSid() {
        return ZhugeSDK.getInstance().getSid();
    }

    /**
     * 事件自定义属性
     * <p>
     * 若有⼀一些属性对于您来说，每⼀一个事件都要拥有，那么您可以调用 setSuperProperty() 将它传⼊入。之
     * 后，每⼀一个经过track(),endTrack()传入的事件，都将自动获得这些属性。
     *
     * @param jsonObject
     */
    public void setSuperProperty(JSONObject jsonObject) {
        ZhugeSDK.getInstance().setSuperProperty(jsonObject);
    }

    /**
     * 设备⾃自定义属性
     * <p>
     * 诸葛默认展示的设备信息包含一些硬件信息，如系统版本，设备分辨率，设备制造商等。若您希望在展示设
     * 备信息时展示一些额外的信息，那么可以调⽤用 setPlatform() 传入，我们会将这些信息添加在设备信息中。
     *
     * @param jsonObject
     */
    public void setPlatform(JSONObject jsonObject) {
        ZhugeSDK.getInstance().setPlatform(jsonObject);
    }

    /**
     * 获得WebView中的ZhuGeJS
     *
     * @return
     */
    public ZhugeSDK.ZhugeJS getZhugeJS() {
        return new ZhugeSDK.ZhugeJS();
    }

}
