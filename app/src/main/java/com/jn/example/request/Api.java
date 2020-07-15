package com.jn.example.request;

import com.jn.example.entiy.NewsVO;
import com.jn.example.entiy.XaResult;
import com.jn.example.entiy.XaResult2;
import com.jn.kiku.entiy.VersionUpdateVO;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Author：Stevie.Chen Time：2019/9/11
 * Class Comment：网络请求
 */
public interface Api {

    @FormUrlEncoded
    @POST(ApiConfig.GET_NEW_LIST)
    Observable<XaResult<List<NewsVO>>> getNewList(@Field("page") int pageIndex, @Field("count") int pageSize, @Field("type") String type);

    @FormUrlEncoded
    @POST("http://114.215.84.189:8889/censh/api/versionUpdate/findVersionInfo")
    Observable<XaResult2<VersionUpdateVO>> getVersionUpdateInfo(@Field("type") int type);

}
