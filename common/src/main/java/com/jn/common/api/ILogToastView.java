package com.jn.common.api;

import com.jn.common.util.LogUtils;
import com.jn.common.util.ToastUtils;

/**
 * Author：Stevie.Chen Time：2019/8/2
 * Class Comment：log和Toast
 */
public interface ILogToastView {

    String messageFormat = "%s---》%s";

    /**
     * 显示log
     */
    default void logI(String message) {
        LogUtils.i(String.format(messageFormat, getClass().getSimpleName(), message));
    }

    /**
     * 显示log
     */
    default void logE(String message) {
        LogUtils.e(String.format(messageFormat, getClass().getSimpleName(), message));
    }

    /**
     * 显示Toast(Toast.LENGTH_SHORT)
     */
    default void showToast(String message) {
        ToastUtils.showToast(message);
    }

    /**
     * 显示Toast
     *
     * @param duration (Toast.LENGTH_SHORT/Toast.LENGTH_LONG）
     */
    default void showToast(String message, int duration) {
        ToastUtils.showToast(message, duration);
    }
}
