package com.jn.kiku.retrofit;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jn.kiku.retrofit.body.RetrofitBodyHelp;
import com.jn.kiku.retrofit.callback.ProgressListener;
import com.jn.kiku.retrofit.manager.IRetrofitManage;
import com.jn.kiku.retrofit.manager.RetrofitManagerFactory;

import java.io.File;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (网络请求管理)
 * @create by: chenwei
 * @date 2018/5/3 17:46
 */
public class RetrofitManage {

    private static RetrofitManage instance = null;
    private Retrofit mRetrofit = null;
    private String mBaseUrl;

    private RetrofitManage() {
    }

    public static synchronized RetrofitManage getInstance() {
        if (instance == null)
            instance = new RetrofitManage();
        return instance;
    }

    /**
     * 初始化Retrofit
     * <p>
     * 请在Application中使用
     * </P>
     *
     * @param BASE_URL 服务器域名地址
     */
    public void initRetrofit(String BASE_URL) {
        mBaseUrl = BASE_URL;
    }

    /**
     * 创建请求服务
     * <p>
     * 用于普通请求
     * </P>
     *
     * @param service
     * @param <T>
     * @return
     */
    public <T> T create(final Class<T> service) {
        if (mBaseUrl == null)
            throw new NullPointerException("BaseUrl is empty,please initRetrofit in Application");
        if (mRetrofit == null) {
            IRetrofitManage iRetrofitManage = RetrofitManagerFactory.create(RetrofitManagerFactory.REQUEST, mBaseUrl, null);
            mRetrofit = iRetrofitManage.createRetrofit();
        }
        return mRetrofit.create(service);
    }

    /**
     * 创建请求服务
     * <p>
     * 用于下载
     * </P>
     *
     * @param service
     * @param <T>
     * @param listener 进度监听器
     * @return
     */
    public <T> T createDownload(final Class<T> service, ProgressListener listener) {
        if (mBaseUrl == null)
            throw new NullPointerException("BaseUrl is empty,please initRetrofit in Application");
        IRetrofitManage iRetrofitManage = RetrofitManagerFactory.create(RetrofitManagerFactory.DOWNLOAD, mBaseUrl, listener);
        Retrofit retrofit = iRetrofitManage.createRetrofit();
        return retrofit.create(service);
    }

    /**
     * 创建请求服务
     * <p>
     * 用于上传
     * </P>
     *
     * @param service
     * @param <T>
     * @param listener 进度监听器
     * @return
     */
    public <T> T createUpload(final Class<T> service, ProgressListener listener) {
        if (mBaseUrl == null)
            throw new NullPointerException("BaseUrl is empty,please initRetrofit in Application");
        IRetrofitManage iRetrofitManage = RetrofitManagerFactory.create(RetrofitManagerFactory.UPLOAD, mBaseUrl, listener);
        Retrofit retrofit = iRetrofitManage.createRetrofit();
        return retrofit.create(service);
    }

    /**
     * 获取下载文件的Observable
     *
     * @param url      下载地址
     * @param listener 进度监听器
     * @return
     */
    public Observable<ResponseBody> getDownloadObservable(@NonNull String url, ProgressListener listener) {
        RetrofitApiService apiService = createDownload(RetrofitApiService.class, listener);
        return apiService.downloadFile(url);
    }

    /**
     * 获取上传单张图片的Observable
     *
     * @param url        上传地址
     * @param fileParams 文件参数信息
     * @return Observable
     */
    public Observable<ResponseBody> getUploadObservable(@NonNull String url, @NonNull Map<String, File> fileParams) {
        return getUploadObservable(url, fileParams, null);
    }

    /**
     * 获取上传多张图片的Observable
     *
     * @param url
     * @param fileParams
     * @param params
     * @return
     */
    public Observable<ResponseBody> getUploadObservable(@NonNull String url, @NonNull Map<String, File> fileParams, @Nullable Map<String, String> params) {
        RequestBody requestBody = RetrofitBodyHelp.getUploadImageRequestBody(fileParams, params);
        RetrofitApiService apiService = create(RetrofitApiService.class);
        return apiService.uploadFile(url, requestBody);
    }
}
