package com.jn.kiku.utils.manager;

import android.app.Activity;
import android.arch.lifecycle.DefaultLifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.jn.kiku.utils.WebViewUtils;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Author：Stevie.Chen Time：2019/8/7
 * Class Comment：
 */
public class WebViewManager implements DefaultLifecycleObserver {

    private static final int MSG_WHAT = 0x01;
    private static final String CSS_STYLE = "<link rel=\"stylesheet\" type=\"text/css\" href=\"http://mplanetasset.allyes.com/download/webview-reset.css\" >";

    private Activity mActivity;
    private WebView mWebView;
    private boolean isPageFinished;
    private Handler mHandler = new Handler(msg -> {
        if (msg.what == MSG_WHAT) {
            isPageFinished = true;
        }
        return false;
    });

    public WebViewManager(@NonNull Activity activity, @NonNull WebView webView) {
        mActivity = activity;
        mWebView = webView;
    }

    public void loadDataWithBaseURL(String htmlText) {
        if (null == mWebView)
            return;
        WebViewUtils.loadDataWithBaseURL(mWebView, htmlText);
        //webView加载带CSS样式的富文本时有时会出现大面积空白
        WebViewUtils.loadDataWithBaseURL(mWebView, CSS_STYLE + htmlText);
    }

    public void loadUrl(String url) {
        if (null == mWebView)
            return;
        WebViewUtils.loadUrl(mWebView, url);
    }

    private void setWebViewClient() {
        if (null == mWebView)
            return;
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return true;
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                //webView加载有时会出现大面积空白
                mWebView.loadUrl("javascript:App.resize(document.body.getBoundingClientRect().height)");
                super.onPageFinished(webView, s);
                //延迟设置加载完成标志，因为{ExpandWebView}中OnDraw方法中执行时间是在此方法之后
                mHandler.sendEmptyMessageDelayed(MSG_WHAT, 500);
            }

            @Override
            public void onReceivedError(WebView webView, int i, String s, String s1) {
                super.onReceivedError(webView, i, s, s1);
                //网页加载失败
            }
        });
    }

    public boolean isPageFinished() {
        return isPageFinished;
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        WebViewUtils.initTenCentWebView(mActivity, mWebView);
        setWebViewClient();
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        WebViewUtils.onResume(mWebView);
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        WebViewUtils.onPause(mWebView);
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        WebViewUtils.onDestroy(mWebView);
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }
}
