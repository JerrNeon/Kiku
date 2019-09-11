package com.jn.example.request;

import android.app.Activity;

import com.jn.example.entiy.XaResult;
import com.jn.kiku.net.retrofit.exception.OkHttpErrorHelper;
import com.jn.kiku.net.retrofit.exception.OkHttpException;
import com.jn.kiku.net.retrofit.observer.BaseObserver;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (网络请求Response数据处理封装)
 * @create by: chenwei
 * @date 2018/5/9 15:38
 */
public abstract class BaseResponseObserver<T> extends BaseObserver<XaResult<T>, T> {

    public BaseResponseObserver(Activity activity) {
        super(activity);
    }

    public BaseResponseObserver(Activity activity, boolean isShowProgressDialog) {
        super(activity, isShowProgressDialog);
    }

    public BaseResponseObserver(Activity activity, int errorType) {
        super(activity, errorType);
    }

    public BaseResponseObserver(Activity activity, int errorType, boolean isShowProgressDialog) {
        super(activity, errorType, isShowProgressDialog);
    }

    @Override
    public void onNext(XaResult<T> tXaResult) {
        if (tXaResult.getCode() == 0) {
            OkHttpException okHttpException = new OkHttpException(String.valueOf(tXaResult.getCode()), tXaResult.getMsg());
            String errorMsg = "";
            if (mErrorType == ALL)
                errorMsg = OkHttpErrorHelper.getMessage(okHttpException);
            onFailure(okHttpException, errorMsg);
        } else {
            try {
                onSuccess(tXaResult.getData());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
