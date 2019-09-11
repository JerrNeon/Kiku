package com.jn.kiku.ttp.pay.pingpp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.jn.common.api.ILogToastView;
import com.pingplusplus.android.Pingpp;

/**
 * Author：Stevie.Chen Time：2019/9/11
 * Class Comment：Ping + + 管理类
 */
public class PingPpManage implements ILogToastView {

    private static PingPpManage instance = null;
    private PingPpResultListener mPingPpResultListener = null;//支付结果回调

    private PingPpManage() {
    }

    public static synchronized PingPpManage getInstance() {
        if (instance == null)
            instance = new PingPpManage();
        return instance;
    }

    /**
     * 发起支付（除QQ钱包外）
     *
     * @param activity 表示当前调起支付的Activity
     * @param data     表示获取到的charge或order的JSON字符串
     */
    public void pay(Activity activity, String data, PingPpResultListener listener) {
        mPingPpResultListener = listener;
        Pingpp.createPayment(activity, data);
    }

    /**
     * @param fragment 表示当前调起支付的Fragment
     * @param data     表示获取到的charge或order的JSON字符串
     */
    public void pay(Fragment fragment, String data, PingPpResultListener listener) {
        mPingPpResultListener = listener;
        Pingpp.createPayment(fragment, data);
    }

    /**
     * 是否开启日志
     *
     * @param isDebugEnable true:开启 false:关闭
     */
    public void setDebugEnable(boolean isDebugEnable) {
        //开启调试模式
        Pingpp.DEBUG = isDebugEnable;
    }

    /**
     * 支付回调
     *
     * @param data        <p>
     *                    pay_result ->处理返回值
     *                    "success" - 支付成功
     *                    "fail"    - 支付失败
     *                    "cancel"  - 取消支付
     *                    "invalid" - 支付插件未安装（一般是微信客户端未安装的情况）
     *                    "unknown" - app进程异常被杀死(一般是低内存状态下,app进程被杀死)
     *                    </p>
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT && resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            String result = null;
            if (bundle != null) {
                result = bundle.getString("pay_result");
            }
            if (result != null && !"".equals(result)) {
                switch (result) {
                    case "success":
                        showToast("支付成功");
                        if (mPingPpResultListener != null)
                            mPingPpResultListener.onSuccess();
                        break;
                    case "fail":
                        showToast("支付失败");
                        if (mPingPpResultListener != null)
                            mPingPpResultListener.onFailure();
                        break;
                    case "cancel":
                        showToast("取消支付");
                        break;
                    case "invalid":
                        logE("支付插件未安装");
                        showToast("支付失败");
                        if (mPingPpResultListener != null)
                            mPingPpResultListener.onFailure();
                        break;
                    case "unknown":
                        logE("app进程异常被杀死");
                        showToast("支付失败");
                        if (mPingPpResultListener != null)
                            mPingPpResultListener.onFailure();
                        break;
                    default:
                        break;
                }
            }
            if (bundle != null) {
                String errorMsg = bundle.getString("error_msg"); // 错误信息
                String extraMsg = bundle.getString("extra_msg"); // 错误信息
            }
        }
    }

    public void onDestroy() {
        if (mPingPpResultListener != null)
            mPingPpResultListener = null;
    }

    public interface PingPpResultListener {
        void onSuccess();

        void onFailure();
    }
}
