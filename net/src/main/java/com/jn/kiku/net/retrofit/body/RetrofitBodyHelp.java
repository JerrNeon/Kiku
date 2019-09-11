package com.jn.kiku.net.retrofit.body;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jn.common.util.gson.JsonUtils;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Author：Stevie.Chen Time：2019/8/6
 * Class Comment：Retrofit请求响应体帮助类
 */
public class RetrofitBodyHelp {

    /**
     * 获取上传单张图片并带有额外参数的RequestBody
     *
     * @param fileKey 文件Key参数信息
     * @param file    文件参数信息
     * @return RequestBody
     */
    public static MultipartBody.Part getFileUploadRequestBody(@NonNull String fileKey, @NonNull File file) {
        RequestBody requestBody = RequestBody.create(MultipartBody.FORM, file);
        return MultipartBody.Part.createFormData(fileKey, file.getName(), requestBody);
    }

    /**
     * 获取上传多张图片并带有额外参数的RequestBody
     *
     * @param fileParams 文件参数信息
     * @param params     额外参数信息
     * @return RequestBody
     */
    public static RequestBody getFileUploadRequestBody(@NonNull Map<String, File> fileParams, @Nullable Map<String, String> params) {
        MultipartBody.Builder build = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        if (fileParams.size() > 0) {
            for (Map.Entry<String, File> entry : fileParams.entrySet()) {
                RequestBody requestBody = RequestBody.create(MultipartBody.FORM, entry.getValue());
                build.addFormDataPart(entry.getKey(), entry.getValue().getName(), requestBody);
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
            return RequestBody.create(MediaType.parse(MediaTypeConstants.JSON), JsonUtils.getInstance().toJson(object));
        return null;
    }
}
