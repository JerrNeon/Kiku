package com.jn.kiku.common.api;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (log和Toast)
 * @create by: chenwei
 * @date 2018/5/16 17:14
 */
public interface ILogToastView {

    String messageFormat = "%s---》%s";

    /**
     * 显示log
     *
     * @param message
     */
    void logI(String message);

    /**
     * 显示log
     *
     * @param message
     */
    void logE(String message);

    /**
     * 显示Toast(Toast.LENGTH_SHORT)
     *
     * @param message
     */
    void showToast(String message);

    /**
     * 显示Toast
     *
     * @param message
     * @param duration (Toast.LENGTH_SHORT/Toast.LENGTH_LONG）
     */
    void showToast(String message, int duration);
}
