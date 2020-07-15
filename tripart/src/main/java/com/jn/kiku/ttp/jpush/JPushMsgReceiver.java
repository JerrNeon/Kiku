package com.jn.kiku.ttp.jpush;

import android.app.Notification;
import android.content.Context;
import android.util.Log;

import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

/**
 * Author：Stevie.Chen Time：2019/8/2
 * Class Comment：极光推送消息处理
 */
public class JPushMsgReceiver extends JPushMessageReceiver {

    private static final String TAG = "JPushMsgReceiver";

    @Override
    public void onRegister(Context context, String s) {
        super.onRegister(context, s);
        logI("[MyReceiver] 接收Registration Id : " + s);
    }

    @Override
    public Notification getNotification(Context context, NotificationMessage notificationMessage) {
        logI("收到了通知");
        return super.getNotification(context, notificationMessage);
    }

    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        super.onMessage(context, customMessage);
        logI("收到了自定义消息。消息内容是：" + customMessage.toString());
    }

    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageOpened(context, notificationMessage);
        logI("用户点击打开了通知。消息内容是：" + notificationMessage.toString());
        // 在这里可以自己写代码去定义用户点击后的行为
    }

    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageArrived(context, notificationMessage);
        logI("通知已到达。消息内容是：" + notificationMessage.toString());
    }

    @Override
    public void onNotifyMessageDismiss(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageDismiss(context, notificationMessage);
        logI("通知已消失。消息内容是：" + notificationMessage.toString());
    }

    private void logI(String msg) {
        Log.i(TAG, msg);
    }
}
