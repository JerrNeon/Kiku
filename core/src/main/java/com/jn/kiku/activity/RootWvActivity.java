package com.jn.kiku.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.jn.kiku.R;
import com.jn.kiku.common.api.IWvView;
import com.jn.kiku.utils.WebViewUtils;
import com.jn.kiku.utils.manager.WebViewManager;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

/**
 * Author：Stevie.Chen Time：2019/8/7
 * Class Comment：RootWvActivity - 腾讯的WebView
 */
public class RootWvActivity extends RootTbActivity implements IWvView {

    protected WebView mWebView;//WebView
    protected ProgressBar mProgressBar;//ProgressBar
    private int mWebViewHeight;//WebView height
    private WebViewManager mWebViewManager;//WebViewManager

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
        //直接 new WebView 并传入 application context 代替在 XML 里面声明以防止 activity 引用被滥用，能解决90+%的 WebView 内存泄漏。
        mWebView = new WebView(getApplicationContext());
        LinearLayout linearLayout = findViewById(R.id.ll_wv);
        linearLayout.addView(mWebView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        mProgressBar = findViewById(R.id.pb_wv_common);

        mWebViewManager = new WebViewManager(this, mWebView);
        getLifecycle().addObserver(mWebViewManager);
    }

    @Override
    public void loadHtml(String htmlText) {
        mWebViewManager.loadDataWithBaseURL(htmlText);
    }

    @Override
    public void loadUrl(String url) {
        mWebViewManager.loadUrl(url);
    }

    @Override
    public void setWebViewClient() {
        //progress bar
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView webView, String s) {
                super.onReceivedTitle(webView, s);
                setTitleText(s);
            }

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
    protected void onDestroy() {
        super.onDestroy();
        mWebView = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (WebViewUtils.onKeyDown(keyCode, event, mWebView))
            return true;
        return super.onKeyDown(keyCode, event);
    }
}
