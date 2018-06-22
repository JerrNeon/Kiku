package com.jn.example;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.jn.example.request.ApiService;
import com.jn.kiku.RootApplication;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (这里用一句话描述这个类的作用)
 * @create by: chenwei
 * @date 2018/5/9 14:01
 */
public class BaseApplication extends RootApplication {

    private static BaseApplication instance = null;

    public static synchronized BaseApplication getInstance() {
        return instance;
    }

    @Override
    protected boolean isLOG_DEBUG() {
        return BuildConfig.DEBUG;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initActivityManager();
        initRetrofit(ApiService.BASE_URL);
        initUtilsManager(BuildConfig.APPLICATION_ID);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);//突破65536个方法数
    }
}
