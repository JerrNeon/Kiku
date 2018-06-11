package com.jn.kiku.ttp.common.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class JsonUtils {
    private static Gson gson;

    private JsonUtils() {
    }

    public static Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder().create();
        }
        return gson;
    }

    /**
     * 将Java对象转换成json字符串
     *
     * @param object
     * @return
     */
    public static <T> String toJson(T object) {
        return getGson().toJson(object);
    }

    /**
     * 将Java对象转换成json字符串
     * 上传头像时Base64字符串转成json时=会转成\u003d，设置disableHtmlEscaping就不会转
     *
     * @param object
     * @return
     */
    public static <T> String toJsonDisableHtml(T object) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(object);
    }

    /**
     * 将json字符串转成JavaBean对象
     *
     * @param jsonStr
     * @return
     */
    public static <T> T toObject(String jsonStr, Class<T> tClass) {
        try {
            return getGson().fromJson(jsonStr, tClass);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json字符串转成List集合
     *
     * @param jsonStr
     * @param type  new TypeToken<List<T>>() {}.getType());
     * @return
     */
    public static <T> List<T> toObject(String jsonStr, Type type) {
        try {
            return getGson().fromJson(jsonStr, type);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json字符串转成Map集合(无序的)
     *
     * @param jsonStr
     * @param type
     * @return
     */
    public static <T, V> Map<T, V> toMap(String jsonStr, Type type) {
        try {
            return getGson().fromJson(jsonStr, type);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据json字符串和key获取相应的数据
     *
     * @param json json字符串
     * @param name key
     * @return
     */
    public static String getObjectForName(final Object json, final String name) {
        String result = null;
        if (json != null) {
            try {
                JSONTokener jsonParser = new JSONTokener(json.toString());
                JSONObject status = (JSONObject) jsonParser.nextValue();
                Object obj = status.get(name);
                if (obj != null) {
                    result = obj.toString();
                }
                jsonParser = null;
                status = null;
            } catch (Throwable e) {
                result = null;
            }
        }
        return result;
    }

    public static String readLocalJson(Context mContext, String jsonFileName) {
        StringBuilder builder = new StringBuilder();
        try {
            InputStreamReader isr = new InputStreamReader(mContext.getAssets().open(jsonFileName + ".json"), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            br.close();
            isr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

}
