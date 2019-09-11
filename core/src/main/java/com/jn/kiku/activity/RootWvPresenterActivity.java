package com.jn.kiku.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.jn.kiku.mvp.IBPresenter;
import com.jn.kiku.mvp.IBView;

/**
 * Author：Stevie.Chen Time：2019/8/20
 * Class Comment：RootWvActivity
 */
public abstract class RootWvPresenterActivity<P extends IBPresenter<V>, V extends IBView> extends RootWvActivity {

    protected P mPresenter;

    protected abstract P createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mPresenter == null)
            mPresenter = createPresenter();
        if (mPresenter != null) {
            //noinspection unchecked
            mPresenter.attachView((V) this);
            getLifecycle().addObserver(mPresenter);
        }
    }

}
