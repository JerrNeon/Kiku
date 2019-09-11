package com.jn.kiku.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.jn.common.util.LogUtils;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (webView工具)
 * @create by: chenwei
 * @date 2017/3/30 13:33
 */
public class WebViewUtils {

    /**
     * 初始化系统自带的WebView
     *
     * @param mWebView WebView
     */
    public static void initWebView(WebView mWebView) {
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setVerticalScrollbarOverlay(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setHorizontalScrollbarOverlay(false);
        WebSettings webSettings = mWebView.getSettings();
        //支持javascript
        webSettings.setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webSettings.setSupportZoom(true);
        // 设置出现缩放工具
        webSettings.setBuiltInZoomControls(false);
        //扩大比例的缩放
        webSettings.setUseWideViewPort(true);
        //自适应屏幕
        webSettings.setLoadWithOverviewMode(true);
        //设置默认字体
        //webSettings.setDefaultFontSize(17);
        //提高渲染的优先级
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
    }

    /**
     * 初始化腾讯的WebView
     *
     * @param activity Activity
     * @param webView  WebView
     */
    public static void initTenCentWebView(Activity activity, com.tencent.smtt.sdk.WebView webView) {
        activity.getWindow().setFormat(PixelFormat.TRANSLUCENT);//（这个对宿主没什么影响，建议声明）
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //自适应屏幕
        com.tencent.smtt.sdk.WebSettings webSettings = webView.getSettings();
        //支持javascript
        webSettings.setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webSettings.setSupportZoom(true);
        // 设置出现缩放工具
        webSettings.setBuiltInZoomControls(false);
        //将图片调整到适合webview的大小
        webSettings.setUseWideViewPort(true);
        //自适应屏幕
        webSettings.setLoadWithOverviewMode(true);
        //缓存模式-无缓存
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    /**
     * 初始化腾讯的WebViewX5内核环境
     *
     * @param context Context
     */
    public static void initX5Environment(Context context) {
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                LogUtils.i("TencentWebView: onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                LogUtils.i("TencentWebView: onDownloadFinish");
            }

            @Override
            public void onInstallFinish(int i) {
                LogUtils.i("TencentWebView: onInstallFinish");
            }

            @Override
            public void onDownloadProgress(int i) {
                LogUtils.i("TencentWebView: onDownloadProgress:" + i);
            }
        });
        QbSdk.initX5Environment(context, cb);
    }

    /**
     * 加载富文本
     */
    public static void loadDataWithBaseURL(WebView webView, String htmlText) {
        Document document = Jsoup.parse(htmlText);
        Elements imgElements = document.getElementsByTag("img");//图片标签
        Elements iFrameElements = document.getElementsByTag("iframe");//视频标签
        Elements videoElements = document.getElementsByTag("video");//视频标签
        Elements aElements = document.getElementsByTag("a");//链接标签
        //设置图片宽高
        for (Element element : imgElements) {
            element.attr("width", "100%").attr("height", "auto")
                    .attr("style", "")//防止富文本中的该属性设置了width值会覆盖width属性
                    .attr("onerror", "javascript:this.src='https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557135451684&di=eb2d7915bdde403dcd0e4fd08e1efd63&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F1e3ead27ad747c7c92e659ac5774587a680bb8d25252-mRVFlu_fw658'");//无效图片加载地址
        }
        //设置视频宽高
        for (Element element : iFrameElements) {
            element.attr("width", "100%").attr("height", "auto")
                    .attr("style", "")
                    .attr("allowfullscreen", "true")
                    .attr("webkitallowfullscreen", "true")
                    .attr("mozallowfullscreen", "true");
        }
        for (Element element : videoElements) {
            element.attr("width", "100%").attr("height", "auto");
        }
        //去掉链接
        for (Element element : aElements) {
            element.attr("color", "#000000").attr("text-decoration", "none");
        }
        webView.loadDataWithBaseURL(null, document.toString(), "text/html", "UTF-8", null);
    }

    /**
     * 加载富文本
     */
    public static void loadDataWithBaseURL(com.tencent.smtt.sdk.WebView webView, String htmlText) {
        loadDataWithBaseURL(webView, htmlText, false);
    }

    /**
     * 加载富文本
     */
    public static void loadDataWithBaseURL(com.tencent.smtt.sdk.WebView webView, String htmlText, boolean isChangeStyle) {
        if (isChangeStyle) {
            Document document = Jsoup.parse(htmlText);
            Elements imgElements = document.getElementsByTag("img");//图片标签
            Elements iFrameElements = document.getElementsByTag("iframe");//视频标签
            Elements videoElements = document.getElementsByTag("video");//视频标签
            Elements aElements = document.getElementsByTag("a");//链接标签
            //设置图片宽高
            for (Element element : imgElements) {
                element.attr("width", "100%").attr("height", "auto")
                        .attr("style", "")//防止富文本中的该属性设置了width值会覆盖width属性
                        .attr("onerror", "javascript:this.src='https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557135451684&di=eb2d7915bdde403dcd0e4fd08e1efd63&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F1e3ead27ad747c7c92e659ac5774587a680bb8d25252-mRVFlu_fw658'");//无效图片加载地址
            }
            //设置视频宽高
            for (Element element : iFrameElements) {
                element.attr("width", "100%").attr("height", "auto")
                        .attr("style", "")
                        .attr("allowfullscreen", "true")
                        .attr("webkitallowfullscreen", "true")
                        .attr("mozallowfullscreen", "true");
            }
            for (Element element : videoElements) {
                element.attr("width", "100%").attr("height", "auto");
            }
            //去掉链接
            for (Element element : aElements) {
                element.attr("color", "#000000").attr("text-decoration", "none");
            }
            webView.loadDataWithBaseURL(null, document.toString(), "text/html", "UTF-8", null);
        } else {
            webView.loadDataWithBaseURL(null, htmlText, "text/html", "UTF-8", null);
        }
    }

    /**
     * 加载Url
     */
    public static void loadUrl(WebView webView, String url) {
        webView.loadUrl(url);
    }


    /**
     * 加载Url
     */
    public static void loadUrl(com.tencent.smtt.sdk.WebView webView, String url) {
        webView.loadUrl(url);
    }

    /**
     * 隐藏WebView播放视频全屏时顶部菜单按钮&分享功能
     */
    public static void removeTencentVideoChildView(Activity activity) {
        try {
            ArrayList<View> outView = new ArrayList<>();
            activity.getWindow().getDecorView().findViewsWithText(outView, "about", View.FIND_VIEWS_WITH_TEXT);
            LogUtils.i("outViewCount>>> " + outView.size());
            for (View view : outView) {
                ViewGroup viewParent = (ViewGroup) view.getParent();
                int childCount = viewParent.getChildCount();
                //第0个是返回键，不隐藏
                if (childCount > 0) {
                    for (int i = 1; i < childCount; i++) {
                        viewParent.getChildAt(i).setVisibility(View.GONE);
                    }
                }
                view.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 问题：在不做任何处理前提下 ，浏览网页时点击系统的“Back”键,整个 Browser 会调用 finish()而结束自身
     * 目标：点击返回后，是网页回退而不是推出浏览器
     * 解决方案：在当前Activity中处理并消费掉该 Back 事件
     */
    public static boolean onKeyDown(int keyCode, KeyEvent event, WebView webView) {
        if (webView != null && (keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return false;
    }

    /**
     * 问题：在不做任何处理前提下 ，浏览网页时点击系统的“Back”键,整个 Browser 会调用 finish()而结束自身
     * 目标：点击返回后，是网页回退而不是推出浏览器
     * 解决方案：在当前Activity中处理并消费掉该 Back 事件
     */
    public static boolean onKeyDown(int keyCode, KeyEvent event, com.tencent.smtt.sdk.WebView webView) {
        if (webView != null && (keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return false;
    }

    public static void onResume(WebView webView) {
        if (webView != null) {
            webView.onResume();
        }
    }

    public static void onPause(WebView webView) {
        if (webView != null) {
            webView.onPause();
        }
    }

    public static void onDestroy(WebView webView) {
        if (webView != null) {
            webView.setWebViewClient(null);
            webView.setWebChromeClient(null);
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
        }
    }

    public static void onResume(com.tencent.smtt.sdk.WebView webView) {
        if (webView != null) {
            webView.onResume();
        }
    }

    public static void onPause(com.tencent.smtt.sdk.WebView webView) {
        if (webView != null) {
            webView.onPause();
        }
    }

    public static void onDestroy(com.tencent.smtt.sdk.WebView webView) {
        if (webView != null) {
            webView.setWebViewClient(null);
            webView.setWebChromeClient(null);
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
        }
    }
}
