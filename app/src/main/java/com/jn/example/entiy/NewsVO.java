package com.jn.example.entiy;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (这里用一句话描述这个类的作用)
 * @create by: chenwei
 * @date 2018/5/16 14:22
 */
public class NewsVO implements Parcelable {

    private String id;
    private String title;
    private String image;
    private String start;//发布时间
    private String hot;//0,不热门，1热门
    private String top;//0，不置顶，1置顶
    private String cate;
    private String link;

    public String getId() {
        return id;
    }

    public NewsVO setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public NewsVO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getImage() {
        return image;
    }

    public NewsVO setImage(String image) {
        this.image = image;
        return this;
    }

    public String getStart() {
        return start;
    }

    public NewsVO setStart(String start) {
        this.start = start;
        return this;
    }

    public String getHot() {
        return hot;
    }

    public NewsVO setHot(String hot) {
        this.hot = hot;
        return this;
    }

    public String getTop() {
        return top;
    }

    public NewsVO setTop(String top) {
        this.top = top;
        return this;
    }

    public String getCate() {
        return cate;
    }

    public NewsVO setCate(String cate) {
        this.cate = cate;
        return this;
    }

    public String getLink() {
        return link;
    }

    public NewsVO setLink(String link) {
        this.link = link;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.image);
        dest.writeString(this.start);
        dest.writeString(this.hot);
        dest.writeString(this.top);
        dest.writeString(this.cate);
        dest.writeString(this.link);
    }

    public NewsVO() {
    }

    protected NewsVO(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.image = in.readString();
        this.start = in.readString();
        this.hot = in.readString();
        this.top = in.readString();
        this.cate = in.readString();
        this.link = in.readString();
    }

    public static final Parcelable.Creator<NewsVO> CREATOR = new Parcelable.Creator<NewsVO>() {
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
