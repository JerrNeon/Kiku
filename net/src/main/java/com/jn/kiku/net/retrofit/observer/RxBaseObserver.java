package com.jn.kiku.net.retrofit.observer;

import android.content.Context;
import android.support.annotation.IntDef;

import com.jn.common.util.ContextUtils;
import com.jn.common.util.ToastUtils;
import com.jn.kiku.net.retrofit.exception.OkHttpErrorHelper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Author：Stevie.Chen Time：2019/4/8
 * Class Comment：RxBaseObserver
 */
public abstract class RxBaseObserver<T, V> implements Observer<T> {

    /**
     * 不显示任何错误信息
     */
    public static final int NONE = 1;
    /**
     * 仅显示异常类信息
     */
    public static final int EXCEPTION = 2;
    /**
     * 显示所有错误信息
     */
    public static final int ALL = 3;

    @IntDef({NONE, EXCEPTION, ALL})
    @Retention(RetentionPolicy.SOURCE)
    @interface ErrorType {
    }

    @ErrorType
    protected int mErrorType;

    /**
     * @param errorType toast显示的错误类型
     */
    public RxBaseObserver(@ErrorType int errorType) {
        mErrorType = errorType;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T tXaResult) {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        String errorMsg = "";
        if (mErrorType != NONE)
            errorMsg = OkHttpErrorHelper.getMessage(e);
        onFailure(e, errorMsg);
    }

    @Override
    public void onComplete() {

    }

    /**
     * 请求成功
     */
    public abstract void onSuccess(V v);

    public void onResponse(T t) {

    }


    protected Context getContext(){
        return ContextUtils.getInstance().getContext();
    }

    /**
     * 请求失败
     *
     * @param e        异常信息
     * @param errorMsg 错误提示信息
     */
    public void onFailure(Throwable e, String errorMsg) {
        if (null != errorMsg && !"".equals(errorMsg)) {
            ToastUtils.showToast(errorMsg);
        }
    }

}
