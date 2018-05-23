package com.jn.kiku.retrofit.exception;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.jn.kiku.R;
import com.jn.kiku.utils.LogUtils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (错误隐藏帮助类)
 * @create by: chenwei
 * @date 2018/5/9 15:56
 */
public class OkHttpErrorHelper {

    public static String getMessage(Throwable error, Context context) {
        if (!isNetworkConnected(context)) { // 无网络连接
            return context.getResources().getString(R.string.no_internet);
        } else if (error instanceof OkHttpException) {
            if (((OkHttpException) error).getErrorMsg() == null) {
                return ""; //
            } else {
                return ((OkHttpException) error).getErrorMsg();//服务器返回错误信息
            }
        } else if (error instanceof ConnectException) {
            return context.getResources().getString(R.string.generic_server_down);   //可能是连接服务器失败
        } else if (error instanceof SocketTimeoutException) {
            return context.getResources().getString(R.string.connect_time_out);  //连接超时
        } else if (error instanceof com.google.gson.JsonParseException) {
            LogUtils.e("OkHttpErrorHelper--->" + context.getResources().getString(R.string.json_paser_error));
            return "";
        } else if ("java.net.SocketException: Socket closed".equals(error.toString())
                || "java.io.IOException: Canceled".equals(error.toString())) {
            //这里应该是用户取消请求抛出的异常,其他情况可能也会抛出此异常，上面大多数判断可以处理
            LogUtils.e("OkHttpErrorHelper--->" + context.getResources().getString(R.string.user_cancel));
            return "";
        } else if (error instanceof IOException) {
            return "";
        }
        return context.getResources().getString(R.string.generic_error);
    }

    /**
     * 是否取消了请求
     *
     * @param error
     * @return
     */
    public static boolean isCancelRequest(Throwable error) {
        //这里应该是用户取消请求抛出的异常,其他情况可能也会抛出此异常，上面大多数判断可以处理
        if ("java.net.SocketException: Socket closed".equals(error.toString())
                || "java.io.IOException: Canceled".equals(error.toString()))
            return true;
        return false;
    }

    /**
     * 检测网络是否可用
     *
     * @return
     */
    private static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }
}
