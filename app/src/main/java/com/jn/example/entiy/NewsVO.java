package com.jn.example.entiy;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author：Stevie.Chen Time：2019/9/11
 * Class Comment：
 */
public class NewsVO implements Parcelable {

    /**
     * sid : 29760268
     * text : 不出意外我要和一个穷小子谈恋爱了，双人100自助餐都吃不起的那种
     * type : video
     * thumbnail : http://wimg.spriteapp.cn/picture/2019/0908/5d74c1a02f701_wpd.jpg
     * video : http://wvideo.spriteapp.cn/video/2019/0908/5d74c1a02f701_wpd.mp4
     * images : null
     * up : 202
     * down : 43
     * forward : 0
     * comment : 48
     * uid : 23129702
     * name : 欧耶
     * header : http://wimg.spriteapp.cn/profile/large/2019/07/04/5d1d752997741_mini.jpg
     * top_comments_content : 他肯定把开房的钱备足了
     * top_comments_voiceuri : 
     * top_comments_uid : 21389170
     * top_comments_name : 百思补刀大队长-鞭尸能手
     * top_comments_header : http://wimg.spriteapp.cn/profile/large/2019/02/13/5c6416d40f960_mini.jpg
     * passtime : 2019-09-10 22:45:02
     */

    private String sid;
    private String text;
    private String type;
    private String thumbnail;
    private String video;
    private String images;
    private String up;
    private String down;
    private String forward;
    private String comment;
    private String uid;
    private String name;
    private String header;
    private String top_comments_content;
    private String top_comments_voiceuri;
    private String top_comments_uid;
    private String top_comments_name;
    private String top_comments_header;
    private String passtime;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getUp() {
        return up;
    }

    public void setUp(String up) {
        this.up = up;
    }

    public String getDown() {
        return down;
    }

    public void setDown(String down) {
        this.down = down;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getTop_comments_content() {
        return top_comments_content;
    }

    public void setTop_comments_content(String top_comments_content) {
        this.top_comments_content = top_comments_content;
    }

    public String getTop_comments_voiceuri() {
        return top_comments_voiceuri;
    }

    public void setTop_comments_voiceuri(String top_comments_voiceuri) {
        this.top_comments_voiceuri = top_comments_voiceuri;
    }

    public String getTop_comments_uid() {
        return top_comments_uid;
    }

    public void setTop_comments_uid(String top_comments_uid) {
        this.top_comments_uid = top_comments_uid;
    }

    public String getTop_comments_name() {
        return top_comments_name;
    }

    public void setTop_comments_name(String top_comments_name) {
        this.top_comments_name = top_comments_name;
    }

    public String getTop_comments_header() {
        return top_comments_header;
    }

    public void setTop_comments_header(String top_comments_header) {
        this.top_comments_header = top_comments_header;
    }

    public String getPasstime() {
        return passtime;
    }

    public void setPasstime(String passtime) {
        this.passtime = passtime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sid);
        dest.writeString(this.text);
        dest.writeString(this.type);
        dest.writeString(this.thumbnail);
        dest.writeString(this.video);
        dest.writeString(this.images);
        dest.writeString(this.up);
        dest.writeString(this.down);
        dest.writeString(this.forward);
        dest.writeString(this.comment);
        dest.writeString(this.uid);
        dest.writeString(this.name);
        dest.writeString(this.header);
        dest.writeString(this.top_comments_content);
        dest.writeString(this.top_comments_voiceuri);
        dest.writeString(this.top_comments_uid);
        dest.writeString(this.top_comments_name);
        dest.writeString(this.top_comments_header);
        dest.writeString(this.passtime);
    }

    public NewsVO() {
    }

    protected NewsVO(Parcel in) {
        this.sid = in.readString();
        this.text = in.readString();
        this.type = in.readString();
        this.thumbnail = in.readString();
        this.video = in.readString();
        this.images = in.readString();
        this.up = in.readString();
        this.down = in.readString();
        this.forward = in.readString();
        this.comment = in.readString();
        this.uid = in.readString();
        this.name = in.readString();
        this.header = in.readString();
        this.top_comments_content = in.readString();
        this.top_comments_voiceuri = in.readString();
        this.top_comments_uid = in.readString();
        this.top_comments_name = in.readString();
        this.top_comments_header = in.readString();
        this.passtime = in.readString();
    }

    public static final Creator<NewsVO> CREATOR = new Creator<NewsVO>() {
        @Override
        public NewsVO createFromParcel(Parcel source) {
            return new NewsVO(source);
        }

        @Override
        public NewsVO[] newArray(int size) {
            return new NewsVO[size];
        }
    };
}
