package com.jn.kiku.fragment;

import android.content.Context;

import com.jn.kiku.mvp.IBPresenter;
import com.jn.kiku.mvp.IBView;

/**
 * Author：Stevie.Chen Time：2019/4/8
 * Class Comment：RootRvPresenterFragment
 */
public abstract class RootRvPresenterFragment<P extends IBPresenter<V>, V extends IBView, T> extends RootRvFragment<T> {

    protected P mPresenter;

    protected abstract P createPresenter();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (mPresenter == null)
            mPresenter = createPresenter();
        if (mPresenter != null) {
            //noinspection unchecked
            mPresenter.attachView((V) this);
            getLifecycle().addObserver(mPresenter);
        }
    }
}
