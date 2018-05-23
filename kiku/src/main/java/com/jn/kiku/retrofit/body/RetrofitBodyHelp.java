package com.jn.kiku.retrofit.body;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jn.kiku.utils.gson.JsonUtils;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (Retrofit请求响应体帮助类)
 * @create by: chenwei
 * @date 2018/5/18 14:08
 */
public class RetrofitBodyHelp {

    /**
     * 获取上传多张图片并带有额外参数的RequestBody
     *
     * @param fileParams 文件参数信息
     * @param params     额外参数信息
     * @return RequestBody
     */
    public static RequestBody getUploadImageRequestBody(@NonNull Map<String, File> fileParams, @Nullable Map<String, String> params) {
        MultipartBody.Builder build = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        if (fileParams.size() > 0) {
            for (Map.Entry<String, File> entry : fileParams.entrySet()) {
                build.addFormDataPart(entry.getKey(), entry.getValue().getName(), RequestBody.create(MediaType.parse(MediaTypeConstans.IMAGE), entry.getValue()));
            }
        }
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                build.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }
        return build.build();
    }

    /**
     * 获取Json类型的RequestBody
     *
     * @param object 请求参数对象
     * @return RequestBody
     */
    public static RequestBody getJsonRequestBody(Object object) {
        if (object != null)
            return RequestBody.create(MediaType.parse(MediaTypeConstans.JSON), JsonUtils.toJson(object));
        return null;
    }
}
