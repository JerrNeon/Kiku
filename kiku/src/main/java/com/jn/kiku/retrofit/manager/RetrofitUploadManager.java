package com.jn.kiku.retrofit.manager;

import com.jn.kiku.retrofit.body.UploadRequestBody;
import com.jn.kiku.retrofit.callback.ProgressListener;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (Retrofit上传)
 * @create by: chenwei
 * @date 2018/5/22 10:11
 */
public class RetrofitUploadManager extends RetrofitRequestManager {

    private ProgressListener mProgressListener;

    public RetrofitUploadManager(String base_url, ProgressListener listener) {
        super(base_url);
        mProgressListener = listener;
    }

    @Override
    public Interceptor createInterceptor() {
        return new Interceptor() {

            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                return chain.proceed(originalRequest
                        .newBuilder()
                        .post(new UploadRequestBody(originalRequest.body(), getProgressListener()))
                        .build());
            }
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
