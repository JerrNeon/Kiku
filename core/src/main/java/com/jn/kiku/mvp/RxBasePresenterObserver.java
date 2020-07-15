package com.jn.kiku.mvp;

import com.jn.kiku.net.retrofit.observer.RxBaseObserver;

import io.reactivex.disposables.Disposable;

/**
 * Author：Stevie.Chen Time：2019/8/6
 * Class Comment：RxBasePresenterObserver
 */
public abstract class RxBasePresenterObserver<T, V> extends RxBaseObserver<T, V> {

    private IBPresenter mIBPresenter;

    public RxBasePresenterObserver(IBPresenter mIBPresenter, int errorType) {
        super(errorType);
        this.mIBPresenter = mIBPresenter;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (mIBPresenter != null)
            mIBPresenter.addDisposable(d);
    }
}
