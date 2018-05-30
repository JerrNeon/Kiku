package com.jn.kiku.ttp.common.manage;

import android.support.annotation.NonNull;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (用于第三方信息的请求管理)
 * @create by: chenwei
 * @date 2018/5/29 19:11
 */
public class RetrofitTtpManage {

    private static RetrofitTtpManage instance = null;

    private RetrofitTtpManage() {
    }

    public static synchronized RetrofitTtpManage getInstance() {
        if (instance == null)
            instance = new RetrofitTtpManage();
        return instance;
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
    public <T> T create(@NonNull String BASE_URL, final Class<T> service) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(service);
    }
}
