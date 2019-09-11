package com.jn.kiku.net;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jn.kiku.net.retrofit.body.RetrofitBodyHelp;
import com.jn.kiku.net.retrofit.callback.ProgressListener;
import com.jn.kiku.net.retrofit.manager.IRetrofitManage;
import com.jn.kiku.net.retrofit.manager.RetrofitManagerFactory;

import java.io.File;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author：Stevie.Chen Time：2019/8/6
 * Class Comment：网络请求管理
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
     * @param service Class<T>
     * @param <T>     T
     * @return T
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
     * 用于普通请求
     * </P>
     *
     * @param service Class<T>
     * @param <T>     T
     * @return T
     */
    public <T> T create(@NonNull String BASE_URL, final Class<T> service) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(service);
    }

    /**
     * 创建请求服务
     * <p>
     * 用于下载
     * </P>
     *
     * @param service  Class<T>
     * @param <T>      T
     * @param listener 进度监听器
     * @return T
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
     * @param service  Class<T>
     * @param <T>      T
     * @param listener 进度监听器
     * @return T
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
     * @return Observable<ResponseBody>
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
     * @return Observable<ResponseBody>
     */
    public Observable<ResponseBody> getUploadObservable(@NonNull String url, @NonNull Map<String, File> fileParams) {
        return getUploadObservable(url, fileParams, null);
    }

    /**
     * 获取上传多张图片的Observable
     *
     * @param url        String
     * @param fileParams Map<String, File>
     * @param params     Map<String, String
     * @return Observable<ResponseBody>
     */
    public Observable<ResponseBody> getUploadObservable(@NonNull String url, @NonNull Map<String, File> fileParams, @Nullable Map<String, String> params) {
        RequestBody requestBody = RetrofitBodyHelp.getFileUploadRequestBody(fileParams, params);
        RetrofitApiService apiService = create(RetrofitApiService.class);
        return apiService.uploadFile(url, requestBody);
    }
}
