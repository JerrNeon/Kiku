package com.jn.example.entiy;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (这里用一句话描述这个类的作用)
 * @create by: chenwei
 * @date 2018/5/16 14:23
 */
public class NewsListVO implements Parcelable {

    private PageVO page;
    private List<NewsVO> list;

    public PageVO getPage() {
        return page;
    }

    public NewsListVO setPage(PageVO page) {
        this.page = page;
        return this;
    }

    public List<NewsVO> getList() {
        return list;
    }

    public NewsListVO setList(List<NewsVO> list) {
        this.list = list;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.page, flags);
        dest.writeTypedList(this.list);
    }

    public NewsListVO() {
    }

    protected NewsListVO(Parcel in) {
        this.page = in.readParcelable(PageVO.class.getClassLoader());
        this.list = in.createTypedArrayList(NewsVO.CREATOR);
    }

    public static final Creator<NewsListVO> CREATOR = new Creator<NewsListVO>() {
        @Override
        public NewsListVO createFromParcel(Parcel source) {
            return new NewsListVO(source);
        }

        @Override
        public NewsListVO[] newArray(int size) {
            return new NewsListVO[size];
        }
    };
}
