package com.jn.kiku.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jn.common.api.ILogToastView;
import com.jn.kiku.common.api.IBaseView;
import com.jn.kiku.common.api.IDisposableView;
import com.jn.kiku.common.api.IImageView;
import com.jn.kiku.common.api.IMvpView;
import com.jn.kiku.common.api.IUtilsView;
import com.jn.kiku.mvp.IBPresenter;
import com.jn.kiku.mvp.IBView;
import com.jn.kiku.utils.manager.BaseManager;

/**
 * Author：Stevie.Chen Time：2019/7/31
 * Class Comment：RootActivity
 */
public class RootActivity<P extends IBPresenter> extends AppCompatActivity implements IUtilsView, IImageView, IBaseView,
        ILogToastView, IDisposableView, IMvpView<P>, View.OnClickListener {

    protected Activity mActivity;
    protected AppCompatActivity mAppCompatActivity;
    protected Context mContext;
    protected BaseManager mBaseManager;

    protected P mPresenter;

    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mAppCompatActivity = this;
        mContext = getApplicationContext();
        mBaseManager = new BaseManager(this);
        getLifecycle().addObserver(mBaseManager);
        initPresenter();
    }

    @Override
    public BaseManager getBaseManager() {
        return mBaseManager;
    }

    @Override
    public void initPresenter() {
        if (mPresenter == null)
            mPresenter = createPresenter();
        if (mPresenter != null && this instanceof IBView) {
            mPresenter.attachView((IBView) this);
            getLifecycle().addObserver(mPresenter);
        }
    }

    @Override
    public void onClick(View view) {

    }
}
