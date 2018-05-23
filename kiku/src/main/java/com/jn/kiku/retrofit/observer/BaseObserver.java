package com.jn.kiku.retrofit.observer;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v7.app.AppCompatActivity;

import com.jn.kiku.dialog.ProgressDialogFragment;
import com.jn.kiku.retrofit.exception.OkHttpErrorHelper;
import com.jn.kiku.utils.ToastUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (网络请求Response数据处理封装)
 * @create by: chenwei
 * @date 2018/5/9 15:38
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

    protected Activity mActivity = null;
    protected AppCompatActivity mAppCompatActivity = null;
    protected Context mContext = null;
    protected ProgressDialogFragment mProgressDialog = null;
    protected int mErrorType = ALL;
    protected boolean isShowProgressDialog;

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
        onBefore();
    }

    @Override
    public void onNext(T tXaResult) {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        String errorMsg = "";
        if (mErrorType != NONE)
            errorMsg = OkHttpErrorHelper.getMessage(e, mContext);
        onFailure(e, errorMsg);
    }

    @Override
    public void onComplete() {
        onAfter();
    }

    /**
     * 请求开始
     */
    public void onBefore() {
        ShowProgressDialog();
    }

    /**
     * 请求成功
     *
     * @param v
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
            ToastUtil.showToast(mActivity, errorMsg);
        }
        dismissProgressDialog();
    }

    /**
     * 请求结束
     */
    public void onAfter() {
        dismissProgressDialog();
    }

    /**
     * 显示对话框
     */
    private void ShowProgressDialog() {
        if (isShowProgressDialog) {
            if (mProgressDialog == null)
                mProgressDialog = new ProgressDialogFragment();
            if (mAppCompatActivity != null)
                mProgressDialog.show(mAppCompatActivity.getSupportFragmentManager(), "");
        }
    }

    /**
     * 隐藏对话框
     */
    private void dismissProgressDialog() {
        if (mProgressDialog != null)
            mProgressDialog.cancel();
    }
}
