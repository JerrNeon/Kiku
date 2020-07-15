package com.jn.kiku.ttp.share;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.jn.common.util.ContextUtils;
import com.jn.kiku.net.RetrofitManage;
import com.jn.kiku.ttp.TtpConstants;
import com.jn.kiku.ttp.pay.wxpay.WxPayInfoVO;
import com.jn.kiku.ttp.share.wechat.WeChatAccessTokenVO;
import com.jn.kiku.ttp.share.wechat.WeChatApiService;
import com.jn.kiku.ttp.share.wechat.WeChatUserInfoVO;
import com.jn.kiku.ttp.wxapi.WXEntryCallbackActivity;
import com.jn.kiku.ttp.wxapi.WXPayEntryCallbackActivity;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Author：Stevie.Chen Time：2019/8/2
 * Class Comment：微信登录 、 分享 、 支付管理
 */
public class WeChatManage implements DefaultLifecycleObserver {

    public static final int WECHAT_FRIEND = 1;//微信好友
    public static final int WECHAT_CIRCLE = 2;//微信朋友圈
    private static final int requestCode1 = 0x01;
    private static final int requestCode2 = 0x02;

    private Application mContext;
    private IWXAPI mIWXAPI = null;
    private WeChatResultListener mWeChatResultListener = null;
    private WeChatResultListener mWeChatLoginResultListener = new WeChatResultListenerIml();
    private WeChatTokenResultListener mWeChatTokenResultListener = null;
    private WeChatUserInfoResultListener mWeChatUserInfoResultListener = null;
    private BroadcastReceiver mWeChatLaunchReceiver;//微信启动广播

    @IntDef({WECHAT_FRIEND, WECHAT_CIRCLE})
    @Retention(RetentionPolicy.SOURCE)
    private @interface ShareType {
    }

    private Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == requestCode1) {
                if (mWeChatResultListener != null)
                    mWeChatResultListener.onFailure(null);
                if (mWeChatTokenResultListener != null)
                    mWeChatTokenResultListener.onFailure();
                if (mWeChatUserInfoResultListener != null)
                    mWeChatUserInfoResultListener.onFailure();
                Toast.makeText(mContext, "您还未安装微信,请安装微信客户端", Toast.LENGTH_SHORT).show();
            } else if (msg.what == requestCode2) {
                if (mWeChatResultListener != null)
                    mWeChatResultListener.onFailure(null);
                if (mWeChatTokenResultListener != null)
                    mWeChatTokenResultListener.onFailure();
                if (mWeChatUserInfoResultListener != null)
                    mWeChatUserInfoResultListener.onFailure();
                Toast.makeText(mContext, "微信版本太低,不能分享到朋友圈", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    });

    private WeChatManage() {
        this.mContext = ContextUtils.getInstance().getApplication();
    }

    private static class INSTANCE {
        private static WeChatManage instance = new WeChatManage();
    }

    public static synchronized WeChatManage getInstance() {
        return INSTANCE.instance;
    }

    private void init(@NonNull Activity activity) {
        if (mIWXAPI == null)
            mIWXAPI = WXAPIFactory.createWXAPI(activity, TtpConstants.WECHAT_APP_ID, false);
    }

    /**
     * 登录
     *
     * @param listener 结果监听
     */
    public <T extends WXEntryCallbackActivity> void login(@NonNull Activity activity, @NonNull Class<T> tClass, WeChatResultListener listener) {
        init(activity);
        mWeChatResultListener = listener;
        if (mIWXAPI == null)
            return;
        if (isWXAppInstalled())
            return;
        try {
            Method method = tClass.getMethod("setWeChatResultListener", WeChatResultListener.class);
            if (listener != null)
                method.invoke(null, listener);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";//应用授权作用域
        Random random = new Random();
        req.state = "wx" + random.nextInt() + System.currentTimeMillis();//用于保持请求和回调的状态，授权请求后原样带回给第三方。可设置为简单的随机数加session进行校验
        mIWXAPI.sendReq(req);
    }

    /**
     * 登录
     *
     * @param listener 结果监听
     */
    public <T extends WXEntryCallbackActivity> void login(@NonNull Activity activity, @NonNull Class<T> tClass, WeChatTokenResultListener listener) {
        init(activity);
        mWeChatTokenResultListener = listener;
        if (mIWXAPI == null)
            return;
        if (isWXAppInstalled())
            return;
        try {
            Method method = tClass.getMethod("setWeChatResultListener", WeChatResultListener.class);
            if (listener != null)
                method.invoke(null, mWeChatLoginResultListener);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";//应用授权作用域
        Random random = new Random();
        req.state = "wx" + random.nextInt() + System.currentTimeMillis();//用于保持请求和回调的状态，授权请求后原样带回给第三方。可设置为简单的随机数加session进行校验
        mIWXAPI.sendReq(req);
    }

    /**
     * 登录
     *
     * @param listener 结果监听
     */
    public <T extends WXEntryCallbackActivity> void login(@NonNull Activity activity, @NonNull Class<T> tClass, WeChatUserInfoResultListener listener) {
        init(activity);
        mWeChatUserInfoResultListener = listener;
        if (mIWXAPI == null)
            return;
        if (isWXAppInstalled())
            return;
        try {
            Method method = tClass.getMethod("setWeChatResultListener", WeChatResultListener.class);
            if (listener != null)
                method.invoke(null, mWeChatLoginResultListener);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";//应用授权作用域
        Random random = new Random();
        req.state = "wx" + random.nextInt() + System.currentTimeMillis();//用于保持请求和回调的状态，授权请求后原样带回给第三方。可设置为简单的随机数加session进行校验
        mIWXAPI.sendReq(req);
    }

    /**
     * 微信分享
     *
     * @param type     WeiXinFriend: 好友分享,WeiXinCircle：朋友圈分享
     * @param listener 结果监听
     *                 1.
     *                 <p>
     *                 WXWebpageObject object = new WXWebpageObject();//网页地址分享
     *                 object.webpageUrl = "网页url";//限制长度不超过10KB
     *                 <p>
     *                 <P>
     *                 //初始化一个WXMusicObject，填写url
     *                 WXMusicObject object = new WXMusicObject();
     *                 object.musicUrl="音乐url";//限制长度不超过10KB
     *                 <p>
     *                 <p>
     *                 WXVideoObject object = new WXVideoObject();
     *                 object.videoUrl ="视频url";//限制长度不超过10KB
     *                 </p>
     *                 <p>
     *                 WXMiniProgramObject object = new WXMiniProgramObject();
     *                 object.webpageUrl = "http://www.qq.com"; // 兼容低版本的网页链接
     *                 object.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;// 正式版:0，测试版:1，体验版:2
     *                 object.userName = "gh_d43f693ca31f";     // 小程序原始id
     *                 object.path = "/pages/media";            //小程序页面路径
     *                 </p>
     *                 2.
     *                 </P>
     *                 WXMediaMessage wxMediaMessage = new WXMediaMessage();
     *                 wxMediaMessage.mediaObject = object;
     *                 wxMediaMessage.title = "标题";//限制长度不超过512Bytes
     *                 wxMediaMessage.description = "描述";//限制长度不超过1KB
     *                 Bitmap thumbBmp = BitmapFactory.decodeResource(getResources(), R.drawable.send_music_thumb);
     *                 wxMediaMessage.thumbData = Util.bmpToByteArray(thumbBmp, true);//限制内容大小不超过32KB
     *                 </p>
     */
    public <T extends WXEntryCallbackActivity> void share(@NonNull Activity activity, @ShareType int type, WXMediaMessage wxMediaMessage, @NonNull Class<T> tClass, WeChatResultListener listener) {
        init(activity);
        mWeChatResultListener = listener;
        if (mIWXAPI == null)
            return;
        if (isWXAppInstalled())
            return;
        try {
            Method method = tClass.getMethod("setWeChatResultListener", WeChatResultListener.class);
            if (listener != null)
                method.invoke(null, listener);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        if (wxMediaMessage != null) {
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            if (type == WECHAT_FRIEND)
                req.scene = SendMessageToWX.Req.WXSceneSession;//发送到微信好友(默认)
            else if (type == WECHAT_CIRCLE) {
                if (mIWXAPI.getWXAppSupportAPI() >= Build.TIMELINE_SUPPORTED_SDK_INT)
                    req.scene = SendMessageToWX.Req.WXSceneTimeline;//发送到朋友圈
                else
                    mHandler.sendEmptyMessage(requestCode2);
            }
            req.transaction = String.valueOf(System.currentTimeMillis());//用于唯一标识一个请求
            req.message = wxMediaMessage;

            mIWXAPI.sendReq(req);
        }
    }

    /**
     * 支付
     *
     * @param info     支付信息
     * @param listener 结果监听
     */
    public <T extends WXPayEntryCallbackActivity> void pay(@NonNull Activity activity, WxPayInfoVO info, @NonNull Class<T> tClass, WeChatResultListener listener) {
        init(activity);
        mWeChatResultListener = listener;
        if (mIWXAPI == null)
            return;
        if (isWXAppInstalled())
            return;
        try {
            Method method = tClass.getMethod("setWeChatResultListener", WeChatResultListener.class);
            if (listener != null)
                method.invoke(null, listener);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        PayReq request = new PayReq();
        request.appId = TtpConstants.WECHAT_APP_ID;
        request.partnerId = info.getPartnerid();//微信支付分配的商户号
        //微信返回的支付交易会话ID(服务器生成预付单，获取到prepay_id后将参数再次签名传输给APP发起支付)
        request.prepayId = info.getPrepayid();
        request.packageValue = "Sign=WXPay";
        request.nonceStr = info.getNoncestr();//随机字符串，不长于32位
        request.timeStamp = info.getTimestamp();
        request.sign = info.getSign();
        mIWXAPI.sendReq(request);
    }

    /**
     * 跳转到微信小程序
     *
     * @param req      请求参数
     *                 WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
     *                 req.userName = "gh_d43f693ca31f"; // 填小程序原始id
     *                 req.path = path;                  //拉起小程序页面的可带参路径，不填默认拉起小程序首页，对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"。
     *                 req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// 可选打开 开发版，体验版和正式版
     * @param listener 结果监听
     */
    public <T extends WXEntryCallbackActivity> void launch(@NonNull Activity activity, WXLaunchMiniProgram.Req req, @NonNull Class<T> tClass, WeChatResultListener listener) {
        init(activity);
        mWeChatResultListener = listener;
        if (mIWXAPI == null)
            return;
        if (isWXAppInstalled())
            return;
        try {
            Method method = tClass.getMethod("setWeChatResultListener", WeChatResultListener.class);
            if (listener != null)
                method.invoke(null, listener);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        mIWXAPI.sendReq(req);
    }

    public void handleIntent(Intent intent, IWXAPIEventHandler iwxapiEventHandler) {
        if (mIWXAPI == null)
            return;
        mIWXAPI.handleIntent(intent, iwxapiEventHandler);
    }

    /**
     * 检查微信是否安装
     *
     * @return true：未安装
     */
    private boolean isWXAppInstalled() {
        if (!mIWXAPI.isWXAppInstalled()) {
            mHandler.sendEmptyMessage(requestCode1);
            return true;
        }
        return false;
    }

    /**
     * 获取access_token
     */
    public void getAccessToken(BaseResp baseResp) {
        RetrofitManage.getInstance().create(WeChatApiService.WeChatBaseUrl, WeChatApiService.class)
                .getAccessToken(WeChatApiService.GET_ACCESS_TOKEN, TtpConstants.WECHAT_APP_ID, TtpConstants.WECHAT_SECRET, ((SendAuth.Resp) baseResp).code, "authorization_code")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeChatAccessTokenVO>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WeChatAccessTokenVO weChatAccessTokenVO) {
                        if (mWeChatTokenResultListener != null)
                            mWeChatTokenResultListener.onSuccess(weChatAccessTokenVO);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 刷新access_token
     */
    public void refreshAccessToken(WeChatAccessTokenVO weChatAccessTokenVO) {
        RetrofitManage.getInstance().create(WeChatApiService.WeChatBaseUrl, WeChatApiService.class)
                .refreshAccessToken(WeChatApiService.REFRESH_ACCESS_TOKEN, TtpConstants.WECHAT_APP_ID, weChatAccessTokenVO.getRefresh_token(), "refresh_token")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeChatAccessTokenVO>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WeChatAccessTokenVO qqUserInfoVO) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 检查access_token是否可用
     */
    public void checkAccessToken(WeChatAccessTokenVO weChatAccessTokenVO) {
        RetrofitManage.getInstance().create(WeChatApiService.WeChatBaseUrl, WeChatApiService.class)
                .checkAccessToken(WeChatApiService.CHECK_ACCESS_TOKEN, weChatAccessTokenVO.getAccess_token(), weChatAccessTokenVO.getOpenid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeChatAccessTokenVO>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WeChatAccessTokenVO qqUserInfoVO) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo(BaseResp baseResp) {
        RetrofitManage.getInstance().create(WeChatApiService.WeChatBaseUrl, WeChatApiService.class)
                .getAccessToken(WeChatApiService.GET_ACCESS_TOKEN, TtpConstants.WECHAT_APP_ID, TtpConstants.WECHAT_SECRET, ((SendAuth.Resp) baseResp).code, "authorization_code")
                .flatMap((Function<WeChatAccessTokenVO, ObservableSource<WeChatUserInfoVO>>) weChatAccessTokenVO -> RetrofitManage.getInstance().create(WeChatApiService.WeChatBaseUrl, WeChatApiService.class)
                        .getUserInfo(WeChatApiService.CHECK_ACCESS_TOKEN, weChatAccessTokenVO.getAccess_token(), weChatAccessTokenVO.getOpenid()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeChatUserInfoVO>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WeChatUserInfoVO weChatUserInfoVo) {
                        if (mWeChatUserInfoResultListener != null)
                            mWeChatUserInfoResultListener.onSuccess(weChatUserInfoVo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void onDestroy() {
        if (mIWXAPI != null)
            mIWXAPI = null;
        if (mWeChatResultListener != null)
            mWeChatResultListener = null;
        if (mWeChatTokenResultListener != null)
            mWeChatTokenResultListener = null;
        if (mWeChatUserInfoResultListener != null)
            mWeChatUserInfoResultListener = null;
    }

    /**
     * 实现回调结果
     */
    class WeChatResultListenerIml implements WeChatResultListener {

        @Override
        public void onSuccess(BaseResp resp) {
            if (mWeChatTokenResultListener != null)
                getAccessToken(resp);
            if (mWeChatUserInfoResultListener != null)
                getUserInfo(resp);//获取用户信息
        }

        @Override
        public void onFailure(BaseResp resp) {

        }
    }

    public interface WeChatResultListener {
        void onSuccess(BaseResp resp);

        void onFailure(BaseResp resp);
    }

    public interface WeChatTokenResultListener {
        void onSuccess(WeChatAccessTokenVO response);

        void onFailure();
    }

    public interface WeChatUserInfoResultListener {
        void onSuccess(WeChatUserInfoVO response);

        void onFailure();
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        if (null == mWeChatLaunchReceiver) {
            mWeChatLaunchReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (mIWXAPI != null)
                        mIWXAPI.registerApp(TtpConstants.WECHAT_APP_ID);
                }
            };
        }
        mContext.registerReceiver(mWeChatLaunchReceiver, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));
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
        if (mWeChatLaunchReceiver != null) {
            mContext.unregisterReceiver(mWeChatLaunchReceiver);
            mWeChatLaunchReceiver = null;
        }
        onDestroy();
    }
}
