package com.jn.kiku.retrofit;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (常用请求)
 * @create by: chenwei
 * @date 2018/5/18 13:21
 */
public interface RetrofitApiService {

    /**
     * 上传文件
     *
     * @param url         地址
     * @param requestBody 请求体
     * @return
     */
    @POST
    Observable<ResponseBody> uploadFile(@Url String url, @Body RequestBody requestBody);

    /**
     * 下载文件(带下载进度监听)
     *
     * @param url
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String url);
}
