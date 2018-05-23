package com.jn.kiku.utils;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

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
        webSettings.setUseWideViewPort(false);
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
        //扩大比例的缩放
        //webSettings.setUseWideViewPort(false);
        //自适应屏幕
        //webSettings.setLoadWithOverviewMode(true);
    }

    /**
     * 加载富文本
     *
     * @param webView
     * @param htmlText
     */
    public static void loadDataWithBaseURL(com.tencent.smtt.sdk.WebView webView, String htmlText) {
        Document document = Jsoup.parse(htmlText);
        Elements imgElements = document.getElementsByTag("img");//图片标签
        Elements iFrameElements = document.getElementsByTag("iframe");//视频标签
        Elements videoElements = document.getElementsByTag("video");//视频标签
        Elements aElements = document.getElementsByTag("a");//链接标签
        //设置图片宽高
        for (Element element : imgElements) {
            element.attr("width", "100%").attr("height", "auto")
                    .attr("style", "")//防止富文本中的该属性设置了width值会覆盖width属性
                    .attr("onerror", "javascript:this.src='https://m.censh.com/media/default-product.png'");//无效图片加载地址
        }
        //设置视频宽高
        for (Element element : iFrameElements) {
            element.attr("width", "100%").attr("height", "auto");
        }
        for (Element element : videoElements) {
            element.attr("width", "100%").attr("height", "auto");
        }
        //去掉链接
        for (Element element : aElements) {
            element.attr("color", "#000000").attr("text-decoration", "none");
        }
        //"http://www.censh.com"
        webView.loadDataWithBaseURL(null, document.toString(), "text/html", "UTF-8", null);
    }

    /**
     * 加载Url
     * @param webView
     * @param url
     */
    public static void loadUrl(com.tencent.smtt.sdk.WebView webView, String url) {
        webView.loadUrl(url);
    }

    /**
     * 隐藏WebView播放视频全屏时顶部菜单按钮&分享功能
     *
     * @param activity
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
     *
     * @param keyCode
     * @param event
     * @return
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
     *
     * @param keyCode
     * @param event
     * @return
     */
    public static boolean onKeyDown(int keyCode, KeyEvent event, com.tencent.smtt.sdk.WebView webView) {
        if (webView != null && (keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return false;
    }
}
