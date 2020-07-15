package com.jn.kiku.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jn.kiku.activity.ProcessAliveActivity;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (进程保活需要用到的系统广播)
 * @create by: chenwei
 * @date 2018/6/12 15:30
 */
public class ProcessAliveReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //利用系统广播Intent.ACTION_TIME_TICK 每隔一分钟检测一次Service的运行状态
        if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
            checkProcessAliveService(context);
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {//屏幕亮
            context.sendBroadcast(new Intent(ProcessAliveActivity.KIKU_FINISH_ACTION));
            Intent it = new Intent(Intent.ACTION_MAIN);
            it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            it.addCategory(Intent.CATEGORY_HOME);
            context.startActivity(it);
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {//屏幕熄
            Intent it = new Intent(context, ProcessAliveActivity.class);
            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(it);
        }
    }

    /**
     * 检查进程保活服务
     *
     * @param context Context
     */
    protected void checkProcessAliveService(Context context) {

    }
}
