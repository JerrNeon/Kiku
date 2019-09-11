package com.jn.kiku.mvp;

import androidx.lifecycle.DefaultLifecycleObserver;

import io.reactivex.disposables.Disposable;

/**
 * Author：Stevie.Chen Time：2019/4/8
 * Class Comment：IPresenter
 */
public interface IBPresenter extends DefaultLifecycleObserver {

    void attachView(IBView view);

    void detachView();

    void addDisposable(Disposable disposable);

    void dispose();

}
