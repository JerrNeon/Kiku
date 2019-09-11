package com.jn.kiku.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.jn.common.SPManage;
import com.jn.kiku.R;
import com.jn.kiku.common.api.ISplashView;
import com.jn.kiku.utils.manager.BaseManager;

/**
 * Author：Stevie.Chen Time：2019/8/20
 * Class Comment：RootSplashActivity
 */
public abstract class RootSplashActivity extends RootActivity implements Handler.Callback, ISplashView {

    private static final int SKIP_WHAT = 0x01;
    protected int SKIP_TIME = 3000;//splash countDowner 3s
    protected Handler mHandler = new Handler(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.SplashTheme);
        if (getImgResourceId() != 0)
            getWindow().getDecorView().setBackgroundResource(getImgResourceId());
        initView();
    }

    @Override
    public boolean handleMessage(Message message) {
        if (message.what == SKIP_WHAT) {
            if (SPManage.getInstance().getFirstGuide())
                openGuideActivity();//open splash first
            else
                openMainActivity();
            finish();
        }
        return false;
    }

    @Override
    public void initView() {
        super.initView();
        if (SPManage.getInstance().getFirstGuide()) {
            requestAllPermission();//need check All permission first enter App
        } else {
            mHandler.sendEmptyMessageDelayed(SKIP_WHAT, SKIP_TIME);
        }
    }

    @Override
    public void requestAllPermission() {
        BaseManager manager = getBaseManager();
        if (manager != null)
            manager.requestAllPermissions(aBoolean -> mHandler.sendEmptyMessageDelayed(SKIP_WHAT, SKIP_TIME), getAllPermissions());
    }

    @Override
    protected void onDestroy() {
        if (mHandler != null)
            mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
