package com.jn.kiku.retrofit.manager;

import com.google.gson.Gson;
import com.jn.kiku.retrofit.callback.ProgressListener;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (Retrofit管理)
 * @create by: chenwei
 * @date 2018/5/21 11:25
 */
public interface IRetrofitManage {

    int DEFAULT_CONNECTTIMEOUT_TIME = 20;//连接超时时间(单位：秒)
    int DEFAULT_READTIMEOUT_TIME = 20;//读取超时时间(单位：秒)
    int DEFAULT_WRITETIMEOUT_TIME = 20;//写入超时时间(单位：秒)

    /**
     * Retrofit配置
     *
     * @return
     */
    Retrofit createRetrofit();

    /**
     * Retrofit配置
     *
     * @return
     */
    Retrofit.Builder createRetrofitBuilder();

    /**
     * Gson配置
     *
     * @return
     */
    Gson createGson();

    /**
     * OkHttpClient配置
     *
     * @return
     */
    OkHttpClient.Builder createOkHttpClient();

    /**
     * 创建请求响应日志拦截器
     *
     * @return
     */
    Interceptor createHttpLoggingInterceptor();

    /**
     * 创建拦截器
     *
     * @return
     */
    Interceptor createInterceptor();

    /**
     * 进度监听
     *
     * @return
     */
    ProgressListener getProgressListener();

    /**
     * 获取连接超时时间(单位：秒)
     *
     * @return
     */
    int getConnectTimeout();

    /**
     * 获取读取超时时间(单位：秒)
     *
     * @return
     */
    int getReadTimeout();

    /**
     * 获取写入超时时间(单位：秒)
     *
     * @return
     */
    int getWriteTimeout();
}
