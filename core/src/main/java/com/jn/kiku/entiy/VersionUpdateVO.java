package com.jn.kiku.entiy;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (版本更新信息)
 * @create by: chenwei
 * @date 2018/5/18 16:02
 */
public class VersionUpdateVO implements Parcelable {

    private String appName;//App名称
    private int appIconResId;//App图标
    private String downLoadUrl;//下载地址
    private int versionNum;//版本号
    private String content;//更新内容
    private int forceUpdate;//是否强制更新 0:不强制  1：强制

    public String getAppName() {
        return appName;
    }

    public VersionUpdateVO setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    public int getAppIconResId() {
        return appIconResId;
    }

    public VersionUpdateVO setAppIconResId(int appIconResId) {
        this.appIconResId = appIconResId;
        return this;
    }

    public String getDownLoadUrl() {
        return downLoadUrl;
    }

    public VersionUpdateVO setDownLoadUrl(String downLoadUrl) {
        this.downLoadUrl = downLoadUrl;
        return this;
    }

    public int getVersionNum() {
        return versionNum;
    }

    public VersionUpdateVO setVersionNum(int versionNum) {
        this.versionNum = versionNum;
        return this;
    }

    public String getContent() {
        return content;
    }

    public VersionUpdateVO setContent(String content) {
        this.content = content;
        return this;
    }

    public int getForceUpdate() {
        return forceUpdate;
    }

    public VersionUpdateVO setForceUpdate(int forceUpdate) {
        this.forceUpdate = forceUpdate;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.appName);
        dest.writeInt(this.appIconResId);
        dest.writeString(this.downLoadUrl);
        dest.writeInt(this.versionNum);
        dest.writeString(this.content);
        dest.writeInt(this.forceUpdate);
    }

    public VersionUpdateVO() {
    }

    protected VersionUpdateVO(Parcel in) {
        this.appName = in.readString();
        this.appIconResId = in.readInt();
        this.downLoadUrl = in.readString();
        this.versionNum = in.readInt();
        this.content = in.readString();
        this.forceUpdate = in.readInt();
    }

    public static final Creator<VersionUpdateVO> CREATOR = new Creator<VersionUpdateVO>() {
        @Override
        public VersionUpdateVO createFromParcel(Parcel source) {
            return new VersionUpdateVO(source);
        }

        @Override
        public VersionUpdateVO[] newArray(int size) {
            return new VersionUpdateVO[size];
        }
    };
}
