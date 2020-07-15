package com.jn.kiku.entiy;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (引导页)
 * @create by: chenwei
 * @date 2018/5/17 17:07
 */
public class GuidePageVO implements Parcelable {

    private int position;//位置
    private int imaRes;//本地图片资源
    private String imgUrl;//网络图片资源
    private int imgType;//0：网络图片  1：本地图片
    private boolean isLast;//是否最后一页

    public int getPosition() {
        return position;
    }

    public GuidePageVO setPosition(int position) {
        this.position = position;
        return this;
    }

    public int getImaRes() {
        return imaRes;
    }

    public GuidePageVO setImaRes(int imaRes) {
        this.imaRes = imaRes;
        return this;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public GuidePageVO setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public int getImgType() {
        return imgType;
    }

    public GuidePageVO setImgType(int imgType) {
        this.imgType = imgType;
        return this;
    }

    public boolean isLast() {
        return isLast;
    }

    public GuidePageVO setLast(boolean last) {
        isLast = last;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.position);
        dest.writeInt(this.imaRes);
        dest.writeString(this.imgUrl);
        dest.writeInt(this.imgType);
        dest.writeByte(this.isLast ? (byte) 1 : (byte) 0);
    }

    public GuidePageVO() {
    }

    protected GuidePageVO(Parcel in) {
        this.position = in.readInt();
        this.imaRes = in.readInt();
        this.imgUrl = in.readString();
        this.imgType = in.readInt();
        this.isLast = in.readByte() != 0;
    }

    public static final Creator<GuidePageVO> CREATOR = new Creator<GuidePageVO>() {
        @Override
        public GuidePageVO createFromParcel(Parcel source) {
            return new GuidePageVO(source);
        }

        @Override
        public GuidePageVO[] newArray(int size) {
            return new GuidePageVO[size];
        }
    };
}
