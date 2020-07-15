package com.jn.kiku.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (版本更新广播)
 * @create by: chenwei
 * @date 2018/5/21 16:45
 */
public class VersionUpdateReceiver extends BroadcastReceiver {

    public static final String VERSION_UPDATE_ACTION = "com.jn.kiku.VersionUpdate";//版本更新广播Action
    private IReceiverListener mIReceiverListener;

    public VersionUpdateReceiver(IReceiverListener IReceiverListener) {
        mIReceiverListener = IReceiverListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (mIReceiverListener != null)
            mIReceiverListener.onReceive(context, intent);
    }
}
