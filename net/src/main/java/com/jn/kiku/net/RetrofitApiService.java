package com.jn.kiku.net;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Author：Stevie.Chen Time：2019/8/6
 * Class Comment：常用请求
 */
public interface RetrofitApiService {

    /**
     * 上传文件
     *
     * @param url         地址
     * @param requestBody 请求体
     */
    @POST
    Observable<ResponseBody> uploadFile(@Url String url, @Body RequestBody requestBody);

    /**
     * 下载文件(带下载进度监听)
     *
     * @param url 地址
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String url);
}
