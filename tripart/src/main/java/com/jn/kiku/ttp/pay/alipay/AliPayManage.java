package com.jn.kiku.ttp.pay.alipay;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.jn.common.api.ILogToastView;
import com.jn.kiku.ttp.TtpConstants;

import java.util.Map;

/**
 * Author：Stevie.Chen Time：2019/8/6
 * Class Comment：支付宝支付管理, 真实App里, privateKey等数据严禁放在客户端, 加签过程务必要放在服务端完成
 */
public class AliPayManage implements Handler.Callback, ILogToastView, DefaultLifecycleObserver {

    private static final int SDK_PAY_FLAG = 1;//支付
    private static final int SDK_AUTH_FLAG = 2;//授权

    private Handler mHandler = null;
    private AliPayResultListener mAliPayResultListener = null;

    private AliPayManage() {
    }

    private static class INSTANCE {
        private static AliPayManage instance = new AliPayManage();
    }

    public static AliPayManage getInstance() {
        return INSTANCE.instance;
    }

    /**
     * 支付
     *
     * @param activity activity
     * @param listener 结果监听
     *                 <p>
     *                 App组装参数和加密信息
     *                 </P>
     */
    @SuppressWarnings("ConstantConditions")
    public void pay(@NonNull final Activity activity, @Nullable AliPayResultListener listener) {
        if (mHandler == null)
            mHandler = new Handler(this);
        mAliPayResultListener = listener;
        boolean isRsa2 = TtpConstants.ALIPAY_RSA2_PRIVATE.length() > 0;//是否使用rsa2秘钥
        String privateKey = isRsa2 ? TtpConstants.ALIPAY_RSA2_PRIVATE : TtpConstants.ALIPAY_RSA_PRIVATE;
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(TtpConstants.ALIPAY_APPID, isRsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, isRsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = () -> {
            PayTask alipay = new PayTask(activity);
            Map<String, String> result = alipay.payV2(orderInfo, true);
            logI("AliPayResult：" + result.toString());

            Message msg = new Message();
            msg.what = SDK_PAY_FLAG;
            msg.obj = result;
            mHandler.sendMessage(msg);
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 支付
     *
     * @param activity  activity
     * @param orderInfo 订单信息
     * @param listener  结果监听
     *                  <p>
     *                  服务器组装参数和加密信息
     *                  </p>
     */
    public void pay(@NonNull final Activity activity, final String orderInfo, @Nullable AliPayResultListener listener) {
        if (mHandler == null)
            mHandler = new Handler(this);
        mAliPayResultListener = listener;
        Runnable payRunnable = () -> {
            PayTask alipay = new PayTask(activity);
            Map<String, String> result = alipay.payV2(orderInfo, true);
            logI("AliPayResult：" + result.toString());

            Message msg = new Message();
            msg.what = SDK_PAY_FLAG;
            msg.obj = result;
            mHandler.sendMessage(msg);
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 授权
     *
     * @param activity activity
     * @param listener 结果监听
     */
    @SuppressWarnings("ConstantConditions")
    public void authV2(@NonNull final Activity activity, @Nullable AliPayResultListener listener) {
        if (mHandler == null)
            mHandler = new Handler(this);
        mAliPayResultListener = listener;
        boolean isRsa2 = TtpConstants.ALIPAY_RSA2_PRIVATE.length() > 0;//是否使用rsa2秘钥
        String privateKey = isRsa2 ? TtpConstants.ALIPAY_RSA2_PRIVATE : TtpConstants.ALIPAY_RSA_PRIVATE;
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(TtpConstants.ALIPAY_PID, TtpConstants.ALIPAY_APPID, TtpConstants.ALIPAY_TARGET_ID, isRsa2);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, isRsa2);
        final String authInfo = info + "&" + sign;
        Runnable authRunnable = () -> {
            // 构造AuthTask 对象
            AuthTask authTask = new AuthTask(activity);
            // 调用授权接口，获取授权结果
            Map<String, String> result = authTask.authV2(authInfo, true);

            Message msg = new Message();
            msg.what = SDK_AUTH_FLAG;
            msg.obj = result;
            mHandler.sendMessage(msg);
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    /**
     * 授权
     *
     * @param activity activity
     * @param authInfo 授权信息
     * @param listener 结果监听
     *                 <p>
     *                 服务器组装参数和加密信息
     *                 </p>
     */
    public void authV2(@NonNull final Activity activity, String authInfo, @Nullable AliPayResultListener listener) {
        if (mHandler == null)
            mHandler = new Handler(this);
        mAliPayResultListener = listener;
        Runnable authRunnable = () -> {
            // 构造AuthTask 对象
            AuthTask authTask = new AuthTask(activity);
            // 调用授权接口，获取授权结果
            Map<String, String> result = authTask.authV2(authInfo, true);

            Message msg = new Message();
            msg.what = SDK_AUTH_FLAG;
            msg.obj = result;
            mHandler.sendMessage(msg);
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case SDK_PAY_FLAG: {
                @SuppressWarnings("unchecked")
                AliPayResult aliPayResult = new AliPayResult((Map<String, String>) msg.obj);
                /*
                 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 9000 	订单支付成功
                 8000 	正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
                 4000 	订单支付失败
                 5000 	重复请求
                 6001 	用户中途取消
                 6002 	网络连接出错
                 6004 	支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
                 其它 	其它支付错误
                 */
                //String resultInfo = aliPayResult.getResult();// 同步返回需要验证的信息
                String resultStatus = aliPayResult.getResultStatus();
                // 判断resultStatus 为9000则代表支付成功
                if (TextUtils.equals(resultStatus, "9000")) {
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    showToast("支付成功");
                    if (mAliPayResultListener != null)
                        mAliPayResultListener.onSuccess();
                } else if (TextUtils.equals(resultStatus, "6001")) {//取消支付
                    showToast("取消支付");
                } else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    showToast("支付失败");
                    if (mAliPayResultListener != null)
                        mAliPayResultListener.onFailure(resultStatus);
                }
                break;
            }
            case SDK_AUTH_FLAG: {
                @SuppressWarnings("unchecked")
                AliAuthResult aliAuthResult = new AliAuthResult((Map<String, String>) msg.obj, true);
                String resultStatus = aliAuthResult.getResultStatus();

                // 判断resultStatus 为“9000”且result_code
                // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(aliAuthResult.getResultCode(), "200")) {
                    // 获取alipay_open_id，调支付时作为参数extern_token 的value
                    // 传入，则支付账户为该授权账户
                    showToast("授权成功");
                    if (mAliPayResultListener != null)
                        mAliPayResultListener.onSuccess();
                } else {
                    // 其他状态值则为授权失败
                    showToast("授权失败");
                    if (mAliPayResultListener != null)
                        mAliPayResultListener.onFailure(resultStatus);
                }
                logI(String.format("authCode:%s", aliAuthResult.getAuthCode()));
                break;
            }
            default:
                break;
        }
        return false;
    }

    private void onDestroy() {
        if (mAliPayResultListener != null)
            mAliPayResultListener = null;
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

    public interface AliPayResultListener {
        void onSuccess();

        void onFailure(String errorCode);
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
        onDestroy();
    }
}
