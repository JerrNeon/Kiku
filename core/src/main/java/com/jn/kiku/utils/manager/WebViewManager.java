package com.jn.kiku.utils.manager;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.webkit.JavascriptInterface;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.jn.common.util.LogUtils;
import com.jn.kiku.utils.WebViewUtils;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Author：Stevie.Chen Time：2019/8/7
 * Class Comment：
 */
public class WebViewManager implements DefaultLifecycleObserver {

    private static final int MSG_WHAT = 0x01;
    private static final String META = "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
            "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
            "<meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0,viewport-fit=cover\">\n" +
            "<meta name=\"apple-mobile-web-app-capable\" content=\"yes\">\n" +
            "<meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\">\n" +
            "<meta name=\"format-detection\" content=\"telephone=no\">";
    private static final String CSS_STYLE = "<link rel=\"stylesheet\" type=\"text/css\" href=\"http://mplanetasset.allyes.com/download/webview-reset.css\" >";

    private Activity mActivity;
    private WebView mWebView;
    private int loadTimes;//加载次数
    private OnContentChangeListener onContentChangeListener;//WebView高度监听

    private Handler mHandler = new Handler(msg -> {
        if (msg.what == MSG_WHAT) {
            if (onContentChangeListener != null) {
                float value = (float) msg.obj;
                onContentChangeListener.onContentChange((int) value);
            }
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
        WebViewUtils.loadDataWithBaseURL(mWebView, META + CSS_STYLE + htmlText);
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
                //mWebView.loadUrl("javascript:App.resize(document.body.getBoundingClientRect().height)");
                webView.loadUrl("javascript:AndroidFunction.resize(document.body.scrollHeight)");
            }

            @Override
            public void onReceivedError(WebView webView, int i, String s, String s1) {
                super.onReceivedError(webView, i, s, s1);
                //网页加载失败
            }
        });
        mWebView.addJavascriptInterface(this, "AndroidFunction");
    }

    @JavascriptInterface
    public void resize(final float height) {
        if (loadTimes == 0) {
            loadTimes++;
            return;
        }
        float webViewHeight = (height * mActivity.getResources().getDisplayMetrics().density);
        LogUtils.i("WebView Height：" + webViewHeight);
        mHandler.obtainMessage(MSG_WHAT, webViewHeight).sendToTarget();
    }

    public void setOnContentChangeListener(OnContentChangeListener onContentChangeListener) {
        this.onContentChangeListener = onContentChangeListener;
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        WebViewUtils.initTenCentWebView(mActivity, mWebView);
        mWebView.setBackgroundColor(0);
        mWebView.setAlpha(1f);
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

    /**
     * 监听WebView内容高度
     */
    public interface OnContentChangeListener {
        void onContentChange(int height);
    }
}
