package com.jn.example;

import android.content.Context;

import androidx.multidex.MultiDex;

import com.jn.common.util.ContextUtils;
import com.jn.kiku.RootApplication;

/**
 * Author：Stevie.Chen Time：2019/9/11
 * Class Comment：
 */
public class BaseApplication extends RootApplication {

    private static BaseApplication instance = null;

    public static synchronized BaseApplication getInstance() {
        return instance;
    }

    @Override
    protected boolean isLogEnable() {
        return BuildConfig.DEBUG;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initActivityManager();
        initRetrofit(BuildConfig.BASE_URL);
        initUtilsManager();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);//突破65536个方法数
    }

    @Override
    protected void initUtilsManager() {
        super.initUtilsManager();
        ContextUtils.getInstance().init(this);
    }
}
