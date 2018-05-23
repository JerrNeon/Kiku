package com.jn.kiku.retrofit.manager;

import com.jn.kiku.retrofit.body.DownloadResponseBody;
import com.jn.kiku.retrofit.callback.ProgressListener;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (Retrofit下载)
 * @create by: chenwei
 * @date 2018/5/21 11:41
 */
public class RetrofitDownloadManager extends RetrofitRequestManager {

    private ProgressListener mProgressListener;

    public RetrofitDownloadManager(String base_url, ProgressListener listener) {
        super(base_url);
        mProgressListener = listener;
    }

    @Override
    public Interceptor createInterceptor() {
        return new Interceptor() {

            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder()
                        .body(new DownloadResponseBody(originalResponse.body(), getProgressListener()))
                        .build();
            }
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
