package com.jn.kiku.activity;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.jn.kiku.R;
import com.jn.kiku.common.api.IWvView;
import com.jn.kiku.utils.WebViewUtils;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (RootWvActivity)
 * @create by: chenwei
 * @date 2018/5/22 14:41
 */
public class RootWvActivity extends RootTbActivity implements IWvView {

    protected WebView mWebView;//WebView
    protected ProgressBar mProgressBar;//ProgressBar
    private int mWebViewHeight;//WebView height

    @Override
    public int getLayoutResourceId() {
        return R.layout.common_wv;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWvView();
        setWebViewClient();
    }

    @Override
    public void initWvView() {
        mWebView = findViewById(R.id.wv_common);
        mProgressBar = findViewById(R.id.pb_wv_common);

        WebViewUtils.initTenCentWebView(mActivity, mWebView);
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
                //webView.loadUrl(s);
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
            mWebViewHeight = mWebView.getView().getHeight();
            WebViewUtils.removeTencentVideoChildView(mActivity);//hide video top view
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {//PORTRAIT
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mWebView.getView().getLayoutParams();
            lp.height = mWebViewHeight;
        }
    }

    @Override
    protected void onResume() {
        if (mWebView != null)
            mWebView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (mWebView != null)
            mWebView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (WebViewUtils.onKeyDown(keyCode, event, mWebView))
            return true;
        return super.onKeyDown(keyCode, event);
    }
}
