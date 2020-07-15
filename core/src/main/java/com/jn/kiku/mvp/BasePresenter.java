package com.jn.kiku.mvp;

import androidx.lifecycle.LifecycleOwner;
import androidx.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Author：Stevie.Chen Time：2019/8/28
 * Class Comment：BasePresenter
 */
public abstract class BasePresenter<V extends IBView, M extends IBModel> implements IBPresenter {

    protected V mView;
    protected M mModel;
    private CompositeDisposable mCompositeDisposable;

    protected abstract M getModel();

    @SuppressWarnings("unchecked")
    @Override
    public void attachView(IBView view) {
        mView = (V) view;
        mModel = getModel();
    }

    @Override
    public void detachView() {
        mView = null;
        mModel = null;
    }

    @Override
    public void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void dispose() {
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        detachView();
        dispose();
    }
}