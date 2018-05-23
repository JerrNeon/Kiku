package com.jn.kiku.ttp.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jn.kiku.common.api.ILogToastView;
import com.jn.kiku.utils.LogUtils;

import cn.jpush.android.api.JPushInterface;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (Jpush消息接收)
 * @create by: chenwei
 * @date 2017/3/14 9:50
 */
public class JPushMessageReceiver extends BroadcastReceiver implements ILogToastView {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        logI("onReceive - " + intent.getAction());
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            logI("[MyReceiver] 接收Registration Id : " + regId);
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            logI("收到了自定义消息。消息内容是：" + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            // 自定义消息不会展示在通知栏，完全要开发者写代码去处理
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            logI("收到了通知");
            // 在这里可以做些统计，或者做些其他工作
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            logI("用户点击打开了通知");
            logI("[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            String extraJson = bundle.getString(JPushInterface.EXTRA_EXTRA);
            // 在这里可以自己写代码去定义用户点击后的行为
            onClickAndOpenNotication(extraJson);
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            String type = bundle.getString(JPushInterface.EXTRA_EXTRA);
            logI("[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + type);
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            logI("[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            logI("Unhandled intent - " + intent.getAction());
        }
    }

    /**
     * 用户点击打开了通知
     *
     * @param extraValue 传递过来的额外信息
     */
    protected void onClickAndOpenNotication(String extraValue) {
        
    }

    @Override
    public void logI(String message) {
        LogUtils.i(getClass().getSimpleName(), message);
    }

    @Override
    public void logE(String message) {
        LogUtils.e(getClass().getSimpleName(), message);
    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void showToast(String message, int duration) {

    }
}
