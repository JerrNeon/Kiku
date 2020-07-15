package com.jn.kiku.net.retrofit.manager;

import com.jn.kiku.net.retrofit.body.UploadRequestBody;
import com.jn.kiku.net.retrofit.callback.ProgressListener;

import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * Author：Stevie.Chen Time：2019/8/6
 * Class Comment：chenwei
 */
public class RetrofitUploadManager extends RetrofitRequestManager {

    private ProgressListener mProgressListener;

    RetrofitUploadManager(String base_url, ProgressListener listener) {
        super(base_url);
        mProgressListener = listener;
    }

    @Override
    public Interceptor createInterceptor() {
        return chain -> {
            Request originalRequest = chain.request();
            return chain.proceed(originalRequest
                    .newBuilder()
                    .post(new UploadRequestBody(originalRequest.body(), getProgressListener()))
                    .build());
        };
    }

    @Override
    public ProgressListener getProgressListener() {
        return mProgressListener;
    }

    @Override
    public int getConnectTimeout() {
        return super.getConnectTimeout() * 2;
    }

    @Override
    public int getReadTimeout() {
        return super.getReadTimeout() * 2;
    }

    @Override
    public int getWriteTimeout() {
        return super.getWriteTimeout() * 2;
    }
}
