package com.jn.kiku.net.retrofit.observer;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.IntDef;
import androidx.appcompat.app.AppCompatActivity;

import com.jn.common.util.ToastUtils;
import com.jn.kiku.net.retrofit.exception.OkHttpErrorHelper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Author：Stevie.Chen Time：2019/8/6
 * Class Comment：网络请求Response数据处理封装
 */
public abstract class BaseObserver<T, V> implements Observer<T> {

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

    protected Activity mActivity;
    protected AppCompatActivity mAppCompatActivity = null;
    protected Context mContext;
    protected int mErrorType = ALL;
    private boolean isShowProgressDialog;

    /**
     * @param activity activity
     */
    public BaseObserver(Activity activity) {
        mActivity = activity;
        if (mActivity instanceof AppCompatActivity)
            mAppCompatActivity = (AppCompatActivity) mActivity;
        mContext = activity.getApplicationContext();
    }

    /**
     * @param activity             activity
     * @param isShowProgressDialog 是否显示加载框，默认显示
     */
    public BaseObserver(Activity activity, boolean isShowProgressDialog) {
        this(activity);
        this.isShowProgressDialog = isShowProgressDialog;
    }

    /**
     * @param activity  activity
     * @param errorType toast显示的错误类型
     */
    public BaseObserver(Activity activity, @ErrorType int errorType) {
        this(activity);
        mErrorType = errorType;
    }

    /**
     * @param activity             activity
     * @param errorType            toast显示的错误类型
     * @param isShowProgressDialog 是否显示加载框，默认显示
     */
    public BaseObserver(Activity activity, @ErrorType int errorType, boolean isShowProgressDialog) {
        this(activity, errorType);
        this.isShowProgressDialog = isShowProgressDialog;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (isShowProgressDialog)
            ShowProgressDialog();
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
        dismissProgressDialog();
    }

    /**
     * 请求成功
     */
    public abstract void onSuccess(V v) throws Exception;

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
        dismissProgressDialog();
    }

    /**
     * 显示对话框
     */
    protected void ShowProgressDialog() {

    }

    /**
     * 隐藏对话框
     */
    protected void dismissProgressDialog() {

    }
}
