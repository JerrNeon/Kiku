package com.jn.kiku.common.api;

/**
 * Author：Stevie.Chen Time：2019/8/13
 * Class Comment：WebView
 */
public interface IWvView {

    /**
     * 初始化WebView
     */
    void initWvView();

    /**
     * 加载富文本
     */
    void loadHtml(String htmlText);

    /**
     * 加载Url
     */
    void loadUrl(String url);

    /**
     * 配置加载信息
     */
    void setWebViewClient();
}
