package com.jn.kiku.net.retrofit.manager;

import com.jn.kiku.net.retrofit.body.DownloadResponseBody;
import com.jn.kiku.net.retrofit.callback.ProgressListener;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Author：Stevie.Chen Time：2019/8/6
 * Class Comment：Retrofit下载
 */
public class RetrofitDownloadManager extends RetrofitRequestManager {

    private ProgressListener mProgressListener;

    RetrofitDownloadManager(String base_url, ProgressListener listener) {
        super(base_url);
        mProgressListener = listener;
    }

    @Override
    public Interceptor createInterceptor() {
        return chain -> {
            Response originalResponse = chain.proceed(chain.request());
            return originalResponse.newBuilder()
                    .body(new DownloadResponseBody(originalResponse.body(), getProgressListener()))
                    .build();
        };
    }

    @Override
    public ProgressListener getProgressListener() {
        return mProgressListener;
    }

    @Override
    public int getConnectTimeout() {
        return super.getConnectTimeout() * 3;
    }

    @Override
    public int getReadTimeout() {
        return super.getReadTimeout() * 3;
    }

    @Override
    public int getWriteTimeout() {
        return super.getWriteTimeout() * 3;
    }
}
