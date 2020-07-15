package com.jn.kiku.net.retrofit.manager;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jn.kiku.net.retrofit.callback.ProgressListener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Author：Stevie.Chen Time：2019/8/6
 * Class Comment：Retrofit请求管理工厂
 */
public class RetrofitManagerFactory {

    public static final int REQUEST = 1;//普通请求
    public static final int DOWNLOAD = 2;//下载
    public static final int UPLOAD = 3;//上传

    @IntDef({REQUEST, DOWNLOAD, UPLOAD})
    @Retention(RetentionPolicy.SOURCE)
    @interface RequestType {
    }

    public static IRetrofitManage create(@RequestType int requestType, @NonNull String BASE_URL, @Nullable ProgressListener listener) {
        IRetrofitManage retrofitManage = null;
        switch (requestType) {
            case REQUEST:
                retrofitManage = new RetrofitRequestManager(BASE_URL);
                break;
            case DOWNLOAD:
                retrofitManage = new RetrofitDownloadManager(BASE_URL, listener);
                break;
            case UPLOAD:
                retrofitManage = new RetrofitUploadManager(BASE_URL, listener);
                break;
            default:
                break;
        }
        return retrofitManage;
    }

}
