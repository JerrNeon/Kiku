package com.jn.kiku.ttp.share;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.IntDef;
import android.widget.Toast;

import com.jn.kiku.ttp.TtpConstants;
import com.jn.kiku.ttp.common.manage.RetrofitTtpManage;
import com.jn.kiku.ttp.pay.wxpay.WxPayInfoVO;
import com.jn.kiku.ttp.share.wechat.WeChatAccessTokenVO;
import com.jn.kiku.ttp.share.wechat.WeChatApiService;
import com.jn.kiku.ttp.share.wechat.WeChatMediaMessage;
import com.jn.kiku.ttp.share.wechat.WeChatUserInfoVo;
import com.jn.kiku.ttp.wxapi.WXEntryActivity;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.jn.kiku.ttp.share.WeChatManage.ShareType.WECHAT_CIRCLE;
import static com.jn.kiku.ttp.share.WeChatManage.ShareType.WECHAT_FRIEND;


/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (微信登录 、 分享 、 支付管理)
 * @create by: chenwei
 * @date 2017/3/9 13:55
 */
public class WeChatManage {

    private static final int requestCode1 = 0x01;
    private static final int requestCode2 = 0x02;

    private static WeChatManage sWeChatManage = null;
    private Context mContext;
    private IWXAPI mIWXAPI = null;
    private WeChatResultListener mWeChatResultListener = null;
    private WeChatResultListener mWeChatLoginResultListener = new WeChatResultListenerIml();
    private WeChatTokenResultListener mWeChatTokenResultListener = null;

    @IntDef({WECHAT_FRIEND, WECHAT_CIRCLE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ShareType {
        int WECHAT_FRIEND = 1;//微信好友
        int WECHAT_CIRCLE = 2;//微信朋友圈
    }

    private Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == requestCode1) {
                if (mWeChatResultListener != null)
                    mWeChatResultListener.onFailure(null);
                if (mWeChatTokenResultListener != null)
                    mWeChatTokenResultListener.onFailure();
                Toast.makeText(mContext, "您还未安装微信,请安装微信客户端", Toast.LENGTH_SHORT).show();
            } else if (msg.what == requestCode2) {
                if (mWeChatResultListener != null)
                    mWeChatResultListener.onFailure(null);
                if (mWeChatTokenResultListener != null)
                    mWeChatTokenResultListener.onFailure();
                Toast.makeText(mContext, "微信版本太低,不能分享到朋友圈", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    });

    public static synchronized WeChatManage getInstance(Context context) {
        if (sWeChatManage == null)
            sWeChatManage = new WeChatManage(context.getApplicationContext());
        return sWeChatManage;
    }

    private WeChatManage(Context context) {
        this.mContext = context;
    }

    private void init() {
        if (mIWXAPI == null)
            mIWXAPI = WXAPIFactory.createWXAPI(mContext, TtpConstants.WECHAT_APP_ID, false);
        mIWXAPI.registerApp(TtpConstants.WECHAT_APP_ID);
    }

    /**
     * 登录
     *
     * @param listener 结果监听
     */
    public void login(WeChatResultListener listener) {
        init();
        mWeChatResultListener = listener;
        if (mIWXAPI == null)
            return;
        if (!checkWXAppInstalled())
            return;
        if (listener != null)
            WXEntryActivity.setWeChatResultListener(listener);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";//应用授权作用域
        req.state = "censh" + System.currentTimeMillis();//用于保持请求和回调的状态，授权请求后原样带回给第三方。可设置为简单的随机数加session进行校验
        mIWXAPI.sendReq(req);
    }

    /**
     * 登录
     *
     * @param listener 结果监听
     */
    public void login(WeChatTokenResultListener listener) {
        init();
        mWeChatTokenResultListener = listener;
        if (mIWXAPI == null)
            return;
        if (!checkWXAppInstalled())
            return;
        if (listener != null) {
            WXEntryActivity.setWeChatResultListener(mWeChatLoginResultListener);
        }
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";//应用授权作用域
        req.state = "censh" + System.currentTimeMillis();//用于保持请求和回调的状态，授权请求后原样带回给第三方。可设置为简单的随机数加session进行校验
        mIWXAPI.sendReq(req);
    }

    /**
     * 微信分享
     *
     * @param type     WeiXinFriend: 好友分享,WeiXinCircle：朋友圈分享
     * @param listener 结果监听
     *                 <p>
     *                 WXWebpageObject wxWebpageObject = new WXWebpageObject();//网页地址分享
     *                 wxWebpageObject.webpageUrl = "url";
     *                 <p>
     *                 WXMediaMessage wxMediaMessage = new WXMediaMessage(wxWebpageObject);
     *                 wxMediaMessage.title = "标题";
     *                 wxMediaMessage.description = "描述";
     *                 wxMediaMessage.setThumbImage(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher));//不能超过32k
     *                 </p>
     */
    public void share(@ShareType int type, WXMediaMessage wxMediaMessage, WeChatResultListener listener) {
        init();
        mWeChatResultListener = listener;
        if (mIWXAPI == null)
            return;
        if (!checkWXAppInstalled())
            return;
        if (listener != null)
            WXEntryActivity.setWeChatResultListener(listener);
        if (wxMediaMessage != null) {
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            if (type == WECHAT_FRIEND)
                req.scene = SendMessageToWX.Req.WXSceneSession;//发送到微信好友(默认)
            else if (type == WECHAT_CIRCLE) {
                if (mIWXAPI.getWXAppSupportAPI() >= 0x21020001)
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
     * 微信分享
     *
     * @param type     WeiXinFriend: 好友分享,WeiXinCircle：朋友圈分享
     * @param listener 结果监听
     *                 <p>
     *                 WXWebpageObject wxWebpageObject = new WXWebpageObject();//网页地址分享
     *                 wxWebpageObject.webpageUrl = "url";
     *                 <p>
     *                 WXMediaMessage wxMediaMessage = new WXMediaMessage(wxWebpageObject);
     *                 wxMediaMessage.title = "标题";
     *                 wxMediaMessage.description = "描述";
     *                 wxMediaMessage.setThumbImage(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher));//不能超过32k
     *                 </p>
     */
    public void share(@ShareType int type, WeChatMediaMessage weChatMediaMessage, WeChatResultListener listener) {
        init();
        mWeChatResultListener = listener;
        if (mIWXAPI == null)
            return;
        if (!checkWXAppInstalled())
            return;
        if (listener != null)
            WXEntryActivity.setWeChatResultListener(listener);
        if (weChatMediaMessage != null) {
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            if (type == WECHAT_FRIEND)
                req.scene = SendMessageToWX.Req.WXSceneSession;//发送到微信好友(默认)
            else if (type == WECHAT_CIRCLE) {
                if (mIWXAPI.getWXAppSupportAPI() >= 0x21020001)
                    req.scene = SendMessageToWX.Req.WXSceneTimeline;//发送到朋友圈
                else
                    mHandler.sendEmptyMessage(requestCode2);
            }
            req.transaction = String.valueOf(System.currentTimeMillis());//用于唯一标识一个请求
            WXMediaMessage wxMediaMessage = null;
            if (weChatMediaMessage.getWXWebpageObject() != null)
                wxMediaMessage = new WXMediaMessage(weChatMediaMessage.getWXWebpageObject());
            else
                wxMediaMessage = new WXMediaMessage();
            wxMediaMessage.title = weChatMediaMessage.getTitle();
            wxMediaMessage.description = weChatMediaMessage.getDescription();
            wxMediaMessage.setThumbImage(weChatMediaMessage.getThumbImage());
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
    public void pay(WxPayInfoVO info, WeChatResultListener listener) {
        init();
        mWeChatResultListener = listener;
        if (mIWXAPI == null)
            return;
        if (!checkWXAppInstalled())
            return;
        if (listener != null)
            WXEntryActivity.setWeChatResultListener(listener);
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

    public void handleIntent(Intent intent, IWXAPIEventHandler iwxapiEventHandler) {
        if (mIWXAPI == null)
            return;
        mIWXAPI.handleIntent(intent, iwxapiEventHandler);
    }

    /**
     * 检查微信是否安装
     *
     * @return
     */
    private boolean checkWXAppInstalled() {
        if (!mIWXAPI.isWXAppInstalled()) {
            mHandler.sendEmptyMessage(requestCode1);
            return false;
        }
        return true;
    }

    /**
     * 获取access_token
     *
     * @param baseResp
     */
    public void getAccessToken(BaseResp baseResp) {
        RetrofitTtpManage.getInstance().create(WeChatApiService.WeChatBaseUrl,WeChatApiService.class)
                .getAccessToken(WeChatApiService.GET_ACCESS_TOKEN, TtpConstants.WECHAT_APP_ID, TtpConstants.WECHAT_SECRET, ((SendAuth.Resp) baseResp).code, "authorization_code")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeChatAccessTokenVO>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WeChatAccessTokenVO weChatAccessTokenVO) {

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
     *
     * @param weChatAccessTokenVO
     */
    public void refreshAccessToken(WeChatAccessTokenVO weChatAccessTokenVO) {
        RetrofitTtpManage.getInstance().create(WeChatApiService.WeChatBaseUrl,WeChatApiService.class)
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
     *
     * @param weChatAccessTokenVO
     */
    public void checkAccessToken(WeChatAccessTokenVO weChatAccessTokenVO) {
        RetrofitTtpManage.getInstance().create(WeChatApiService.WeChatBaseUrl,WeChatApiService.class)
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
     *
     * @param baseResp
     */
    public void getUserInfo(BaseResp baseResp) {
        RetrofitTtpManage.getInstance().create(WeChatApiService.WeChatBaseUrl, WeChatApiService.class)
                .getAccessToken(WeChatApiService.GET_ACCESS_TOKEN, TtpConstants.WECHAT_APP_ID, TtpConstants.WECHAT_SECRET, ((SendAuth.Resp) baseResp).code, "authorization_code")
                .flatMap(new Function<WeChatAccessTokenVO, ObservableSource<WeChatUserInfoVo>>() {
                    @Override
                    public ObservableSource<WeChatUserInfoVo> apply(WeChatAccessTokenVO weChatAccessTokenVO) {
                        return RetrofitTtpManage.getInstance().create(WeChatApiService.WeChatBaseUrl,WeChatApiService.class)
                                .getUserInfo(WeChatApiService.CHECK_ACCESS_TOKEN, weChatAccessTokenVO.getAccess_token(), weChatAccessTokenVO.getOpenid());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeChatUserInfoVo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WeChatUserInfoVo weChatUserInfoVo) {
                        if (mWeChatTokenResultListener != null)
                            mWeChatTokenResultListener.onSuccess(weChatUserInfoVo);
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

    public void onDestroy() {
        if (mIWXAPI != null)
            mIWXAPI = null;
        if (mWeChatResultListener != null)
            mWeChatResultListener = null;
        if (mWeChatTokenResultListener != null)
            mWeChatTokenResultListener = null;
    }

    /**
     * 实现回调结果
     */
    class WeChatResultListenerIml implements WeChatResultListener {

        @Override
        public void onSuccess(BaseResp resp) {
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
        void onSuccess(WeChatUserInfoVo response);

        void onFailure();
    }
}
