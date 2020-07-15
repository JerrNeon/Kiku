package com.jn.kiku.ttp.share.qq;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (QQ接口地址)
 * @create by: chenwei
 * @date 2017/9/8 13:17
 */
public interface QqApiService {

    String QQBaseUrl = "https://graph.qq.com/";

    /**
     * 获取UNIONID
     */
    String GET_UNIONID = "https://graph.qq.com/oauth2.0/me";

    /**
     * 获取QQ用户信息
     *
     * @param url          地址
     * @param access_token token
     * @param unionid
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<QqUserInfoVO> getUserInfo(@Url String url, @Field("access_token") String access_token, @Field("unionid") String unionid);
}
