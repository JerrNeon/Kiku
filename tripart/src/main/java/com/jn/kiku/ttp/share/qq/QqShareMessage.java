package com.jn.kiku.ttp.share.qq;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (QQ分享实体)
 * @create by: chenwei
 * @date 2018/5/30 16:02
 */
public class QqShareMessage {

    private String appName;//App名称
    private String title;//标题
    private String imageUrl;//图片地址
    private String summary;//描述
    private String targetUrl;//链接地址

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }
}
