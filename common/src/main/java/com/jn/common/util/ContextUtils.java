package com.jn.common.util;

import android.app.Application;
import android.content.Context;

/**
 * Author：Stevie.Chen Time：2019/7/18
 * Class Comment：
 */
public class ContextUtils {

    private Application application;

    private ContextUtils() {
    }

    private static class INSTANCE {
        private static ContextUtils instance = new ContextUtils();
    }

    public static ContextUtils getInstance() {
        return INSTANCE.instance;
    }

    public void init(Application application) {
        if (this.application == null)
            this.application = application;
    }

    public Application getApplication() {
        if (application == null)
            throw new RuntimeException("please call init()");
        return application;
    }

    public Context getContext() {
        if (application == null)
            throw new RuntimeException("please call init()");
        return application.getApplicationContext();
    }
}
