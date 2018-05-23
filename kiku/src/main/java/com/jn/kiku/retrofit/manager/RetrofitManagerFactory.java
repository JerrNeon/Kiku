package com.jn.kiku.retrofit.manager;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jn.kiku.retrofit.callback.ProgressListener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (Retrofit请求管理工厂)
 * @create by: chenwei
 * @date 2018/5/21 15:56
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
