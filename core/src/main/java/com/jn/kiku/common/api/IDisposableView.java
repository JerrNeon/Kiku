package com.jn.kiku.common.api;

import com.jn.kiku.utils.manager.BaseManager;

import io.reactivex.disposables.Disposable;

/**
 * Author：Stevie.Chen Time：2019/4/8
 * Class Comment：IDisposableView
 */
public interface IDisposableView extends IRootView {

    default void addDisposable(Disposable disposable) {
        BaseManager manager = getBaseManager();
        if (manager != null)
            manager.addDisposable(disposable);
    }

    default void dispose() {
        BaseManager manager = getBaseManager();
        if (manager != null)
            manager.dispose();
    }
}
