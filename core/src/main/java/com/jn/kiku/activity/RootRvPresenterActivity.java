package com.jn.kiku.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jn.kiku.mvp.IBPresenter;
import com.jn.kiku.mvp.IBView;

/**
 * Author：Stevie.Chen Time：2019/4/8
 * Class Comment：RootRvPresenterActivity
 */
public abstract class RootRvPresenterActivity<P extends IBPresenter<V>, V extends IBView, T> extends RootRvActivity<T> {

    protected P mPresenter;

    protected abstract P createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (mPresenter == null)
            mPresenter = createPresenter();
        if (mPresenter != null) {
            //noinspection unchecked
            mPresenter.attachView((V) this);
            getLifecycle().addObserver(mPresenter);
        }
        super.onCreate(savedInstanceState);
    }
}
