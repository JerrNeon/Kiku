package com.jn.kiku.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.jn.kiku.R;
import com.jn.kiku.utils.AppUtils;

/**
 * Author：Stevie.Chen Time：2019/8/20
 * Class Comment：1像素的Activity, 用于息屏时打开此Activity提升优先级达到进程保活
 * <p>
 * 需要使用时请在manifest中注册
 * </P>
 */
public class ProcessAliveActivity extends AppCompatActivity {

    /**
     * 用于发送关闭此Activity的广播Action
     */
    public static final String KIKU_FINISH_ACTION = "com.jn.kiku.processalive.finish";
    protected BroadcastReceiver mFinishReceiver;//结束此Activity的广播

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.NoTitleTranslucentTheme);
        setSize();
        registerFinishReceiver();
        checkScreenStatus();
    }

    @Override
    protected void onResume() {
        checkScreenStatus();
        super.onResume();
    }

    /**
     * 设置此界面的尺寸
     */
    protected void setSize() {
        Window window = getWindow();
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 0;
        params.y = 0;
        params.height = 1;
        params.width = 1;
        window.setAttributes(params);
    }

    /**
     * 注册结束此Activity的广播
     */
    protected void registerFinishReceiver() {
        mFinishReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                finish();
            }
        };
        registerReceiver(mFinishReceiver, new IntentFilter(KIKU_FINISH_ACTION));
    }

    /**
     * 检查屏幕状态
     */
    protected void checkScreenStatus() {
        if (AppUtils.isScreenOn(this)) {
            finish();
        }
    }
}
