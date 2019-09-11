package com.jn.kiku.ttp.share.wechat;

import android.graphics.Bitmap;

import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (分享信息)
 * @create by: chenwei
 * @date 2018/5/30 15:47
 */
public class WeChatMediaMessage {

    private String title;//分享标题
    private String description;//分享描述
    private Bitmap thumbImage;//分享图片
    private WXWebpageObject WXWebpageObject;//分享链接地址

    public WeChatMediaMessage(com.tencent.mm.opensdk.modelmsg.WXWebpageObject WXWebpageObject) {
        this.WXWebpageObject = WXWebpageObject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getThumbImage() {
        return thumbImage;
    }

    public void setThumbImage(Bitmap thumbImage) {
        this.thumbImage = thumbImage;
    }

    public com.tencent.mm.opensdk.modelmsg.WXWebpageObject getWXWebpageObject() {
        return WXWebpageObject;
    }

    public void setWXWebpageObject(com.tencent.mm.opensdk.modelmsg.WXWebpageObject WXWebpageObject) {
        this.WXWebpageObject = WXWebpageObject;
    }
}
