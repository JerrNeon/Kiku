package com.jn.kiku.ttp.pay.unionpay;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.jn.common.api.ILogToastView;
import com.unionpay.UPPayAssistEx;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


/**
 * 银联支付
 */
public class UnionPayManage implements Runnable, ILogToastView {

    //Mode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
    private String mMode = UnionPayConstants.UNION_OFFICIAL_CONNECT;

    private static UnionPayManage instance = null;
    private Handler mHandler = null;
    private UnionPayResultListener mUnionPayResultListener = null;//支付结果监听
    private UnionPayLoadListener mUnionPayLoadListener = null;//支付加载监听

    private UnionPayManage() {
    }

    public static synchronized UnionPayManage getInstance() {
        if (instance == null)
            instance = new UnionPayManage();
        return instance;
    }

    private void init(Activity activity) {
        this.mHandler = new MyHandler(activity);
    }

    /**
     * 网络获取交易流水号后进行支付(TN)
     */
    public void startPay(@NonNull Activity activity, @NonNull UnionPayResultListener listener, @Nullable UnionPayLoadListener loadListener) {
        init(activity);
        mUnionPayResultListener = listener;
        mUnionPayLoadListener = loadListener;
        if (mUnionPayLoadListener != null)
            mUnionPayLoadListener.showProgress();
        new Thread(UnionPayManage.this).start();
    }

    /**
     * 外部获取流水号后进行支付
     *
     * @param tn 流水号
     */
    public void setTnAndStartPay(Activity activity, String tn, UnionPayResultListener listener, @Nullable UnionPayLoadListener loadListener) {
        init(activity);
        mUnionPayResultListener = listener;
        mUnionPayLoadListener = loadListener;
        if (mUnionPayLoadListener != null)
            mUnionPayLoadListener.showProgress();
        Message msg = mHandler.obtainMessage();
        msg.obj = tn;
        mHandler.sendMessage(msg);
    }

    /**
     * 测试的时候采用此方法，从指定网站获取流水号
     */
    @Override
    public void run() {
        String tn = null;
        InputStream is;
        try {
            URL myURL = new URL(UnionPayConstants.GET_TN_URL);
            URLConnection ucon = myURL.openConnection();
            ucon.setConnectTimeout(120000);
            is = ucon.getInputStream();
            int i = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((i = is.read()) != -1) {
                baos.write(i);
            }
            tn = baos.toString();
            is.close();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Message msg = mHandler.obtainMessage();
        msg.obj = tn;
        mHandler.sendMessage(msg);
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

    }

    @Override
    public void showToast(String message, int duration) {

    }

    private class MyHandler extends Handler {
        private Activity mActivity;

        public MyHandler(Activity activity) {
            this.mActivity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            logI("UnionPayTn: " + msg.obj);
            if (mUnionPayLoadListener != null)
                mUnionPayLoadListener.dismissProgress();
            if (msg.obj == null || ((String) msg.obj).length() == 0) {
                new AlertDialog.Builder(mActivity)
                        .setTitle("错误提示")
                        .setMessage("网络连接失败,请重试!")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();
            } else {
                //通过银联工具类启动支付插件
                doStartUnionPayPlugin(mActivity, (String) msg.obj, mMode);
            }
        }
    }

    /**
     * @param tn
     * @param mode 0 - 启动银联正式环境,1 - 连接银联测试环境
     */
    private void doStartUnionPayPlugin(final Activity activity, String tn, String mode) {
        int ret = UPPayAssistEx.startPay(activity, null, null, tn, mode);
        logI("UnionPayResult: " + ret);
        if (!UPPayAssistEx.checkWalletInstalled(activity))//是否安装了银联Apk
            return;
        if (ret == UnionPayConstants.PLUGIN_NEED_UPGRADE || ret == UnionPayConstants.PLUGIN_NOT_INSTALLED) {
            // 需要重新安装控件(更新)
            logI(" plugin not found or need upgrade!!!");
            new AlertDialog.Builder(activity)
                    .setTitle("提示")
                    .setMessage("完成购买需要安装银联支付控件，是否安装？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 目前使用的内置在assets文件夹中的apk，如果不考虑版本问题，应该使用下载链接
                            UPPayAssistEx.installUPPayPlugin(activity);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .create().show();
        }
    }

    /**
     * 支付界面onActivityResult()方法中实现此方法
     * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) return;
        String str = data.getExtras().getString("pay_result");
        if (str == null) return;
        if (str.equalsIgnoreCase("success")) {
            // 如果想对结果数据验签，可使用下面这段代码，但建议不验签，直接去商户后台查询交易结果
            if (data.hasExtra("result_data")) {
                String result = data.getExtras().getString("result_data");
                try {
                    JSONObject resultJson = new JSONObject(result);
                    String sign = resultJson.getString("sign");
                    String dataOrg = resultJson.getString("data");
                    // 此处的verify建议送去商户后台做验签
                    // 如要放在手机端验，则代码必须支持更新证书
                    boolean ret = RSAUtil.verify(dataOrg, sign, mMode);
                    if (ret) {
                        // 验签成功，显示支付结果
                        logI("onActivityResult: 验签后支付成功");
                        if (mUnionPayResultListener != null)
                            mUnionPayResultListener.onSuccess();
                    } else {
                        // 验签失败
                        logE("onActivityResult: 验签后支付失败");
                    }
                } catch (JSONException e) {
                }
            }
            // 结果result_data为成功时，去商户后台查询一下再展示成功
            logI("onActivityResult: 验签前支付成功");
        } else if (str.equalsIgnoreCase("fail")) {
            logE("onActivityResult: 支付失败");
            if (mUnionPayResultListener != null)
                mUnionPayResultListener.onFailure();
        } else if (str.equalsIgnoreCase("cancel")) {
            logI("onActivityResult: 用户取消了支付");
        }
    }

    public void onDestroy() {
        if (mUnionPayResultListener != null)
            mUnionPayResultListener = null;
        if (mUnionPayLoadListener != null)
            mUnionPayLoadListener = null;
        if (mHandler != null)
            mHandler = null;
    }

    public interface UnionPayResultListener {
        void onSuccess();

        void onFailure();
    }

    public interface UnionPayLoadListener {
        void showProgress();

        void dismissProgress();
    }

}
