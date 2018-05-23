package com.jn.kiku.ttp.share.sina;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (新浪调用地址)
 * @create by: chenwei
 * @date 2017/9/7 17:44
 */
public interface SinaApiService {

    /**
     * 根据用户ID获取用户信息
     * <p>
     * access_token 	true 	string 	采用OAuth授权方式为必填参数，OAuth授权后获得。
     * uid 	false 	int64 	需要查询的用户ID。
     * screen_name 	false 	string 	需要查询的用户昵称。
     * </p>
     */
    String GET_USERINFO = "https://api.weibo.com/2/users/show.json";

    /**
     * 获取新浪微博用户信息
     *
     * @param url          地址
     * @param access_token 调用接口凭证
     * @param uid          普通用户标识，对该公众帐号唯一
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<SinaUserInfoVO> getUserInfo(@Url String url, @Field("access_token") String access_token, @Field("uid") String uid);
}
