package com.jn.kiku.utils.manager;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.jn.kiku.activity.ProcessAliveActivity;
import com.jn.kiku.receiver.ProcessAliveReceiver;
import com.jn.kiku.service.ProcessAliveService;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (Android组件管理)
 * @create by: chenwei
 * @date 2018/6/12 15:33
 */
public class ComponentManager {

    /**
     * 注册进程保活需要用到的系统广播
     * <p>
     * 此广播只能动态注册
     * </p>
     *
     * @param context Context
     */
    public static void registerProcessAliveReceiver(Context context, ProcessAliveReceiver receiver) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);//时间变化
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);//亮屏
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);//息屏
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);//
        if (receiver != null)
            context.registerReceiver(receiver, intentFilter);
    }

    /**
     * 开启进程保活服务
     *
     * @param context Context
     * @param service ProcessAliveService
     */
    public static void startProcessAliveService(Context context, Class<? extends ProcessAliveService> service) {
        Intent intent = new Intent(context, service);
        context.startService(intent);
    }

    /**
     * 停止进程保活服务
     *
     * @param context Context
     * @param service ProcessAliveService
     */
    public static void stopProcessAliveService(Context context, Class<? extends ProcessAliveService> service) {
        Intent intent = new Intent(context, service);
        context.stopService(intent);
    }
}
