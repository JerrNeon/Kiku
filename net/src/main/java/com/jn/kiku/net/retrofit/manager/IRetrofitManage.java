package com.jn.kiku.net.retrofit.manager;

import com.google.gson.Gson;
import com.jn.kiku.net.retrofit.callback.ProgressListener;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Author：Stevie.Chen Time：2019/8/6
 * Class Comment：Retrofit管理
 */
public interface IRetrofitManage {

    int DEFAULT_CONNECTTIMEOUT_TIME = 10;//连接超时时间(单位：秒)
    int DEFAULT_READTIMEOUT_TIME = 10;//读取超时时间(单位：秒)
    int DEFAULT_WRITETIMEOUT_TIME = 10;//写入超时时间(单位：秒)

    /**
     * Retrofit配置
     *
     * @return Retrofit
     */
    Retrofit createRetrofit();

    /**
     * Retrofit配置
     *
     * @return Retrofit.Builder
     */
    Retrofit.Builder createRetrofitBuilder();

    /**
     * Gson配置
     *
     * @return Gson
     */
    Gson createGson();

    /**
     * OkHttpClient配置
     *
     * @return OkHttpClient.Builder
     */
    OkHttpClient.Builder createOkHttpClient();

    /**
     * 创建请求响应日志拦截器
     *
     * @return Interceptor
     */
    Interceptor createHttpLoggingInterceptor();

    /**
     * 创建拦截器
     *
     * @return Interceptor
     */
    Interceptor createInterceptor();

    /**
     * 创建Header拦截器
     *
     * @return Interceptor
     */
    Interceptor createHeaderInterceptor();

    /**
     * 创建拦截器
     */
    Interceptor[] createInterceptors();

    /**
     * 进度监听
     *
     * @return ProgressListener
     */
    ProgressListener getProgressListener();

    /**
     * 获取连接超时时间(单位：秒)
     *
     * @return int
     */
    int getConnectTimeout();

    /**
     * 获取读取超时时间(单位：秒)
     *
     * @return int
     */
    int getReadTimeout();

    /**
     * 获取写入超时时间(单位：秒)
     *
     * @return int
     */
    int getWriteTimeout();
}
