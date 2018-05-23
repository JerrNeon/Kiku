package com.jn.example.entiy;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (这里用一句话描述这个类的作用)
 * @create by: chenwei
 * @date 2018/5/16 14:23
 */
public class PageVO implements Parcelable {

    private int page;
    private int num;
    private int total;

    public int getPage() {
        return page;
    }

    public PageVO setPage(int page) {
        this.page = page;
        return this;
    }

    public int getNum() {
        return num;
    }

    public PageVO setNum(int num) {
        this.num = num;
        return this;
    }

    public int getTotal() {
        return total;
    }

    public PageVO setTotal(int total) {
        this.total = total;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.page);
        dest.writeInt(this.num);
        dest.writeInt(this.total);
    }

    public PageVO() {
    }

    protected PageVO(Parcel in) {
        this.page = in.readInt();
        this.num = in.readInt();
        this.total = in.readInt();
    }

    public static final Creator<PageVO> CREATOR = new Creator<PageVO>() {
        @Override
        public PageVO createFromParcel(Parcel source) {
            return new PageVO(source);
        }

        @Override
        public PageVO[] newArray(int size) {
            return new PageVO[size];
        }
    };
}
