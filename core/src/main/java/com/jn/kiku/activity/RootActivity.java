package com.jn.kiku.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jn.common.api.ILogToastView;
import com.jn.kiku.common.api.IBaseView;
import com.jn.kiku.common.api.IDisposableView;
import com.jn.kiku.common.api.IImageView;
import com.jn.kiku.common.api.IUtilsView;
import com.jn.kiku.utils.manager.BaseManager;

/**
 * Author：Stevie.Chen Time：2019/7/31
 * Class Comment：RootActivity
 */
public class RootActivity extends AppCompatActivity implements IUtilsView, IImageView, IBaseView,
        ILogToastView, IDisposableView, View.OnClickListener {

    protected Activity mActivity;
    protected AppCompatActivity mAppCompatActivity;
    protected Context mContext;
    protected BaseManager mBaseManager;

    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mAppCompatActivity = this;
        mContext = getApplicationContext();
        mBaseManager = new BaseManager(this);
        getLifecycle().addObserver(mBaseManager);
    }

    @Override
    public BaseManager getBaseManager() {
        return mBaseManager;
    }

    @Override
    public void onClick(View view) {

    }
}
