package com.jn.kiku.ttp.pay.cmblife;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.jn.common.api.ILogToastView;
import com.jn.common.util.ToastUtils;

import java.util.Map;

/**
 * Author：Stevie.Chen Time：2019/9/11
 * Class Comment：招行分期支付管理
 */
public class CmbLifeManage implements ILogToastView, ICmblifeListener {

    private static final String payRequestCode = "CmbLifePay";//发起支付请求code
    private static final String payResultCode = "respCode";//支付结果code
    private static final String paySuccessResultCode = "1000";//支付成功结果code
    private static final String payCancelResultCode = "2000";//支付取消结果code

    private Context mContext;
    private static CmbLifeManage instance = null;
    private CmbLifePayResultListener mCmbLifePayResultListener = null;

    private CmbLifeManage(Context context) {
        mContext = context.getApplicationContext();
    }

    public static synchronized CmbLifeManage getInstance(Context context) {
        if (instance == null)
            instance = new CmbLifeManage(context.getApplicationContext());
        return instance;
    }

    /**
     * 支付
     *
     * @param context  上下文
     * @param protocol 支付参数(掌上生活cmblife协议)
     */
    public void pay(Context context, String protocol, Class<?> callBackActivity, CmbLifePayResultListener listener) {
        boolean isInstall = CmblifeSDK.isInstall(context);
        if (isInstall) {
            mCmbLifePayResultListener = listener;
            CmblifeSDK.startCmblife(mContext, protocol, callBackActivity, payRequestCode);
        } else {
            showAppNotInstallToast();
        }
    }

    @Override
    public void onCmblifeCallBack(String requestCode, Map<String, String> resultMap) {
        if (payRequestCode.equals(requestCode)) {
            onCmblifeCallBack(resultMap);
        }
    }

    /**
     * 支付结果回调
     */
    public void onCmblifeCallBack(Map<String, String> resultMap) {
        String respCode = resultMap.get(payResultCode);
        if (paySuccessResultCode.equals(respCode)) {
            showToast("支付成功");
            if (mCmbLifePayResultListener != null)
                mCmbLifePayResultListener.onSuccess();
        } else if (payCancelResultCode.equals(respCode)) {
            showToast("取消支付");
        } else {
            showToast("支付失败");
            if (mCmbLifePayResultListener != null)
                mCmbLifePayResultListener.onFailure();
        }
    }

    /**
     * 处理回调
     */
    public void handleCallBack(Intent intent) {
        try {
            CmblifeSDK.handleCallBack(this, intent);
        } catch (Exception e) {
            e.printStackTrace();
            if (mCmbLifePayResultListener != null)
                mCmbLifePayResultListener.onFailure();
            showToast("支付失败");
        }
    }


    @Override
    public void logI(String message) {
        Log.i(getClass().getSimpleName(), String.format(messageFormat, getClass().getSimpleName(), message));
    }

    @Override
    public void logE(String message) {
        Log.e(getClass().getSimpleName(), String.format(messageFormat, getClass().getSimpleName(), message));
    }

    @Override
    public void showToast(String message) {
        ToastUtils.showToast(message);
    }

    @Override
    public void showToast(String message, int duration) {
        ToastUtils.showToast(message, duration);
    }

    /**
     * 显示掌上生活App未安装的提示
     */
    private void showAppNotInstallToast() {
        Toast.makeText(mContext, "您还未安装掌上生活,请安装掌上生活客户端", Toast.LENGTH_SHORT).show();
    }

    public void onDestroy() {
        if (mCmbLifePayResultListener != null)
            mCmbLifePayResultListener = null;
    }

    /**
     * 招行支付结果
     */
    public interface CmbLifePayResultListener {
        void onSuccess();

        void onFailure();
    }
}
