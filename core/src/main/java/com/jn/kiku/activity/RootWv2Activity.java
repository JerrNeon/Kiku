package com.jn.kiku.activity;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.jn.kiku.R;
import com.jn.kiku.common.api.IWvView;
import com.jn.kiku.mvp.IBPresenter;
import com.jn.kiku.utils.WebViewUtils;

/**
 * Author：Stevie.Chen Time：2019/8/20
 * Class Comment：RootWvActivity - Android自带的WebView
 */
public class RootWv2Activity<P extends IBPresenter> extends RootTbActivity<P> implements IWvView {

    protected WebView mWebView;//WebView
    protected ProgressBar mProgressBar;//ProgressBar
    private int mWebViewHeight;//WebView height

    @Override
    public int getLayoutResourceId() {
        return R.layout.common_wv2;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWvView();
        setWebViewClient();
    }

    @Override
    public void initWvView() {
        //直接 new WebView 并传入 application context 代替在 XML 里面声明以防止 activity 引用被滥用，能解决90+%的 WebView 内存泄漏。
        mWebView = new WebView(getApplicationContext());
        LinearLayout linearLayout = findViewById(R.id.ll_wv);
        linearLayout.addView(mWebView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        mProgressBar = findViewById(R.id.pb_wv_common);

        WebViewUtils.initWebView(mWebView);
    }

    @Override
    public void loadHtml(String htmlText) {
        WebViewUtils.loadDataWithBaseURL(mWebView, htmlText);
    }

    @Override
    public void loadUrl(String url) {
        WebViewUtils.loadUrl(mWebView, url);
    }

    @Override
    public void setWebViewClient() {
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                logI("shouldOverrideUrlLoading");
                webView.loadUrl(s);
                return true;
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
                logI("onPageStarted");
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                logI("onPageFinished");
            }

            @Override
            public void onReceivedError(WebView webView, int i, String s, String s1) {
                super.onReceivedError(webView, i, s, s1);
                logI("网页加载失败");
            }
        });
        //progress bar
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (mProgressBar != null)
                    mProgressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    logI("加载完成");
                    if (mProgressBar != null)
                        mProgressBar.setVisibility(View.GONE);
                } else {
                    if (mProgressBar != null)
                        mProgressBar.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //video screen back webView bottom will display many white space，this reset webView height on screen switch
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {//LANDSCAPE)
            mWebViewHeight = mWebView.getRootView().getHeight();
            WebViewUtils.removeTencentVideoChildView(mActivity);//hide video top view
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {//PORTRAIT
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mWebView.getRootView().getLayoutParams();
            lp.height = mWebViewHeight;
        }
    }

    @Override
    protected void onResume() {
        WebViewUtils.onResume(mWebView);
        super.onResume();
    }

    @Override
    protected void onPause() {
        WebViewUtils.onPause(mWebView);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        WebViewUtils.onDestroy(mWebView);
        mWebView = null;
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (WebViewUtils.onKeyDown(keyCode, event, mWebView))
            return true;
        return super.onKeyDown(keyCode, event);
    }
}
