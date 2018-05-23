package com.jn.kiku.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (这里用一句话描述这个类的作用)
 * @create by: chenwei
 * @date 2017/3/14 16:37
 */
public class PropertyUtils {

    private PropertyUtils() {
        throw new UnsupportedOperationException("u can't instantiate ");
    }

    /**
     * 根据key获取config.properties里面的值
     *
     * @param context
     * @param key
     * @return
     */
    public static String getProperty(Context context, String key) {
        try {
            Properties props = new Properties();
            InputStream input = context.getAssets().open("config.properties");
            if (input != null) {
                props.load(input);
                return props.getProperty(key);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
