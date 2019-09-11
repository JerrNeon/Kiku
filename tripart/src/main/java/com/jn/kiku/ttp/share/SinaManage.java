package com.jn.kiku.ttp.share;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.jn.common.api.ILogToastView;
import com.jn.common.util.ContextUtils;
import com.jn.kiku.net.RetrofitManage;
import com.jn.kiku.ttp.TtpConstants;
import com.jn.kiku.ttp.share.sina.SinaApiService;
import com.jn.kiku.ttp.share.sina.SinaUserInfoVO;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author：Stevie.Chen Time：2019/8/2
 * Class Comment：新浪登录 、 分享管理
 */
public class SinaManage implements WbShareCallback, ILogToastView, DefaultLifecycleObserver {

    private Application mContext;
    private SsoHandler mSsoHandler = null;//授权关键类(登录仅用到此类)
    //封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能
    private Oauth2AccessToken mAccessToken;
    private WbShareHandler mWbShareHandler = null;//分享关键类
    private SinaResultListener mSinaResultListener = null;//登录、分享结果监听
    private SinaUserInfoResultListener mSinaUserInfoResultListener = null;//用户信息监听

    public static SinaManage getInstance() {
        return INSTANCE.instance;
    }

    private static class INSTANCE {
        private static SinaManage instance = new SinaManage();
    }

    private SinaManage() {
        this.mContext = ContextUtils.getInstance().getApplication();
    }

    /**
     * 初始化分享
     */
    private void initShare(Activity activity) {
        if (null == mWbShareHandler)
            mWbShareHandler = new WbShareHandler(activity);
        mWbShareHandler.registerApp();
    }

    /**
     * 初始化登录
     */
    private void initLogin(Activity activity) {
        if (null == mSsoHandler)
            mSsoHandler = new SsoHandler(activity);
    }

    /**
     * 新浪微博登录
     *
     * @param listener 结果监听
     */
    public void login(@NonNull Activity activity, @NonNull SinaResultListener listener) {
        mSinaResultListener = listener;
        initLogin(activity);
        if (mSsoHandler != null)
            mSsoHandler.authorize(new SelfWbAuthListener());
    }

    /**
     * 新浪微博登录
     *
     * @param listener 结果监听
     */
    public void login(@NonNull Activity activity, @NonNull SinaUserInfoResultListener listener) {
        initLogin(activity);
        mSinaUserInfoResultListener = listener;
        if (mSsoHandler != null)
            mSsoHandler.authorize(new SelfWbAuthListener());
    }

    /**
     * 新浪微博退出
     */
    public void logout() {
        AccessTokenKeeper.clear(mContext);
        mAccessToken = new Oauth2AccessToken();
    }

    /**
     * 新浪微博分享
     *
     * @param listener 结果监听
     *                 <p>
     *                 TextObject textObject = new TextObject();
     *                 textObject.title = "";
     *                 textObject.text = "分享内容";
     *                 textObject.actionUrl = "";
     *                 </p>
     */
    public void share(@NonNull Activity activity, TextObject textObject, ImageObject imageObject, @Nullable SinaResultListener listener) {
        mSinaResultListener = listener;
        WeiboMultiMessage message = new WeiboMultiMessage();
        if (textObject != null)
            message.textObject = textObject;
        if (imageObject != null) {
            message.imageObject = imageObject;
        }
        initShare(activity);
        if (mWbShareHandler != null)
            mWbShareHandler.shareMessage(message, false);
    }

    /**
     * 设置APP_KEY、REDIRECT_URL、SCOPE等信息
     */
    private void install() {
        WbSdk.install(mContext, new AuthInfo(mContext, TtpConstants.SINA_APP_KEY, TtpConstants.SINA_REDIRECT_URL, TtpConstants.SINA_SCOPE));
    }

    /**
     * 授权时在Activity中onActivityResult()方法中调用
     */
    public void authorizeCallBack(int requestCode, int resultCode, Intent data) {
        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    /**
     * 分享时在Activity中onNewIntent()方法中调用
     */
    public void onActivityResult(Intent intent) {
        if (mWbShareHandler != null)
            mWbShareHandler.doResultIntent(intent, this);
    }

    /**
     * 登录回调
     */
    private class SelfWbAuthListener implements com.sina.weibo.sdk.auth.WbAuthListener {
        @Override
        public void onSuccess(final Oauth2AccessToken token) {
            mAccessToken = token;
            if (mAccessToken.isSessionValid()) {
                // 保存 Token 到 SharedPreferences
                AccessTokenKeeper.writeAccessToken(mContext, mAccessToken);
                //ToastUtil.showToast(mContext, "登录成功");
                if (mSinaResultListener != null)
                    mSinaResultListener.onSuccess(token);
                getUserInfo(token);
            }
        }

        @Override
        public void cancel() {
            showToast("取消登录");
        }

        @Override
        public void onFailure(WbConnectErrorMessage errorMessage) {
            showToast("登录失败");
            if (mSinaResultListener != null)
                mSinaResultListener.onFailure(errorMessage);
        }
    }


    @Override
    public void onWbShareSuccess() {
        if (mSinaResultListener != null)
            mSinaResultListener.onSuccess(null);
        showToast("分享成功");
    }

    @Override
    public void onWbShareCancel() {
        showToast("取消分享");
    }

    @Override
    public void onWbShareFail() {
        if (mSinaResultListener != null)
            mSinaResultListener.onFailure(null);
        showToast("分享失败");
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo(final Oauth2AccessToken token) {
        RetrofitManage.getInstance()
                .create(SinaApiService.SinaBaseUrl, SinaApiService.class)
                .getUserInfo(SinaApiService.GET_USERINFO, token.getToken(), token.getUid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SinaUserInfoVO>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SinaUserInfoVO sinaUserInfoVO) {
                        sinaUserInfoVO.setUid(token.getUid())
                                .setAccess_token(token.getToken());
                        if (mSinaUserInfoResultListener != null)
                            mSinaUserInfoResultListener.onSuccess(sinaUserInfoVO);
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
        if (mSsoHandler != null)
            mSsoHandler = null;
        if (mAccessToken != null)
            mAccessToken = null;
        if (mWbShareHandler != null)
            mWbShareHandler = null;
        if (mSinaResultListener != null)
            mSinaResultListener = null;
        if (mSinaUserInfoResultListener != null)
            mSinaUserInfoResultListener = null;
    }

    /**
     * 分享、登录结果回调(分享返回的token和errorMessage都为空)
     */
    public interface SinaResultListener {
        void onSuccess(Oauth2AccessToken token);

        void onFailure(WbConnectErrorMessage errorMessage);
    }

    public interface SinaUserInfoResultListener {
        void onSuccess(SinaUserInfoVO response);
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        install();
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
