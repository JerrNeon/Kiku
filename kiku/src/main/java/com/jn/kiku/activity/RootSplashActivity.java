package com.jn.kiku.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.jn.kiku.R;
import com.jn.kiku.common.api.ISplashView;
import com.jn.kiku.utils.manager.RxPermissionsManager;
import com.jn.kiku.utils.manager.SPManage;

import io.reactivex.functions.Consumer;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (RootSplashActivity)
 * @create by: chenwei
 * @date 2018/5/17 16:43
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
            getTheme().getDrawable(getImgResourceId());
        initView();
    }

    @Override
    public boolean handleMessage(Message message) {
        if (message.what == SKIP_WHAT) {
            if (SPManage.getInstance(mContext).getFirstGuide())
                openGuideActivity();//open splash first
            else
                openMainActivity();
        }
        return false;
    }

    @Override
    public void initView() {
        super.initView();
        if (SPManage.getInstance(mContext).getFirstGuide()) {
            requestAllPermission();//need check All permission first enter App
        } else {
            mHandler.sendEmptyMessageDelayed(SKIP_WHAT, SKIP_TIME);
        }
    }

    @Override
    public void requestAllPermission() {
        initRxPermissions();
        RxPermissionsManager.requestAllPermissions(mRxPermissions, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) {
                mHandler.sendEmptyMessageDelayed(SKIP_WHAT, SKIP_TIME);
            }
        }, getAllPermissions());
    }
}
