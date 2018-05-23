package com.jn.kiku.common.api;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (WebView)
 * @create by: chenwei
 * @date 2018/5/22 15:51
 */
public interface IWvView {

    /**
     * 初始化WebView
     */
    void initWvView();

    /**
     * 加载富文本
     *
     * @param htmlText
     */
    void loadHtml(String htmlText);

    /**
     * 加载Url
     *
     * @param url
     */
    void loadUrl(String url);

    /**
     * 配置加载信息
     */
    void setWebViewClient();
}
