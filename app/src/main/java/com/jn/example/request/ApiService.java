package com.jn.example.request;

import com.jn.example.entiy.NewsListVO;
import com.jn.example.entiy.XaResult;
import com.jn.example.entiy.XaResult2;
import com.jn.kiku.entiy.VersionUpdateVO;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (网络请求)
 * @create by: chenwei
 * @date 2018/5/9 13:57
 */
public interface ApiService {

    String BASE_URL = "http://censhcms.hqdemo.cn/news/";

    @FormUrlEncoded
    @POST("Api/Videos/getVideoLists")
    Observable<XaResult<NewsListVO>> getVideoList(
            @Field("page") int pageIndex, @Field("num") int pageSize);

    @FormUrlEncoded
    @POST("http://114.215.84.189:8889/censh/api/versionUpdate/findVersionInfo")
    Observable<XaResult2<VersionUpdateVO>> getVersionUpdateInfo(@Field("type") int type);

}
