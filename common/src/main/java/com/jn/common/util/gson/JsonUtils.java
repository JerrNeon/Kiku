package com.jn.common.util.gson;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author：Stevie.Chen Time：2019/8/2
 * Class Comment：JsonUtils
 */
public class JsonUtils {
    private static Gson gson;

    private JsonUtils() {
        if (gson == null) {
            gson = new GsonBuilder().create();
        }
    }

    private static class INSTANCE {
        private static JsonUtils instance = new JsonUtils();
    }

    public static JsonUtils getInstance() {
        return INSTANCE.instance;
    }

    /**
     * 将Java对象转换成json字符串
     */
    public <T> String toJson(T object) {
        return gson.toJson(object);
    }

    /**
     * 将Java对象转换成json字符串
     * 上传头像时Base64字符串转成json时=会转成\u003d，设置disableHtmlEscaping就不会转
     */
    public <T> String toJsonDisableHtml(T object) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(object);
    }

    /**
     * 将json字符串转成JavaBean对象
     */
    public <T> T toObject(String jsonStr, Class<T> tClass) {
        try {
            return gson.fromJson(jsonStr, tClass);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json字符串转成List集合
     *
     * @param type new TypeToken<List<T>>() {}.getType());
     */
    public <T> List<T> toObject(String jsonStr, Type type) {
        try {
            return gson.fromJson(jsonStr, type);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json字符串转成Map集合(无序的)
     */
    public <T, V> Map<T, V> toMap(String jsonStr, Type type) {
        try {
            return gson.fromJson(jsonStr, type);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将对象转成Map集合(无序的)
     */
    public HashMap<String, String> toMap(Object object) {
        try {
            return gson.fromJson(toJson(object), new TypeToken<HashMap<String, String>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * json转List
     *
     * @param jsonStr json字符串
     * @param clazz   类型类对象
     * @param <T>     类型
     * @return List
     */
    public <T> List<T> toList(String jsonStr, Class clazz) {
        try {
            Type type = new ParameterizedTypeListIml(clazz);
            return gson.fromJson(jsonStr, type);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据json字符串和key获取相应的数据
     *
     * @param json json字符串
     * @param key  key
     */
    public String getValueForKey(final Object json, final String key) {
        String result = null;
        if (json != null) {
            try {
                JSONTokener jsonParser = new JSONTokener(json.toString());
                JSONObject status = (JSONObject) jsonParser.nextValue();
                Object obj = status.get(key);
                if (obj != null) {
                    result = obj.toString();
                }
            } catch (Throwable e) {
                result = null;
            }
        }
        return result;
    }

    /**
     * 读取本地Assets中的Json文件
     */
    public String readLocalJson(Context mContext, String jsonFileName) {
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
