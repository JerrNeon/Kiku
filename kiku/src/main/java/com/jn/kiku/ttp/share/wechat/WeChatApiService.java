package com.jn.kiku.ttp.share.wechat;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (微信调用地址)
 * @create by: chenwei
 * @date 2017/3/9 11:43
 */
public interface WeChatApiService {

    String WeChatBaseUrl = "https://api.weixin.qq.com/";

    /**
     * 根据code获取access_token
     * <p>
     * 参数--->appid	:应用唯一标识，在微信开放平台提交应用审核通过后获得
     * 参数--->secret:应用密钥AppSecret，在微信开放平台提交应用审核通过后获得
     * 参数--->code:填写第一步获取的code参数
     * 参数--->grant_type:填authorization_code
     * <p>
     * 正确返回--->access_token:接口调用凭证
     * 正确返回--->expires_in:access_token接口调用凭证超时时间，单位（秒）
     * 正确返回--->refresh_token	:用户刷新access_token
     * 正确返回--->openid:授权用户唯一标识
     * 正确返回--->scope	:用户授权的作用域，使用逗号（,）分隔
     * 错误返回--->"errcode":40029,"errmsg":"invalid code"
     */
    String GET_ACCESS_TOKEN = WeChatBaseUrl + "sns/oauth2/access_token";
    /**
     * 刷新access_token有效期
     * <p>
     * 参数--->appid	:应用唯一标识
     * 参数--->grant_type:填refresh_token
     * 参数--->refresh_token	:填写通过access_token获取到的refresh_token参数
     * <p>
     * 正确返回--->access_token:接口调用凭证
     * 正确返回--->expires_in:access_token接口调用凭证超时时间，单位（秒）
     * 正确返回--->refresh_token	:用户刷新access_token
     * 正确返回--->openid:授权用户唯一标识
     * 正确返回--->scope:用户授权的作用域，使用逗号（,）分隔
     * 错误返回--->"errcode":40030,"errmsg":"invalid refresh_token"
     */
    String REFRESH_ACCESS_TOKEN = WeChatBaseUrl + "sns/oauth2/refresh_token";
    /**
     * 检查access_token有效期
     * <p>
     * 参数--->access_token：调用接口凭证
     * 参数--->openid：普通用户标识，对该公众帐号唯一
     * <p>
     * 返回--->正确："errcode":0,"errmsg":"ok"
     * 返回--->错误："errcode":40003,"errmsg":"invalid openid"
     */
    String CHECK_ACCESS_TOKEN = WeChatBaseUrl + "sns/auth";
    /**
     * 获取用户个人信息
     * <p>
     * 参数--->access_token:调用凭证
     * 参数--->openid:普通用户的标识，对当前开发者帐号唯一
     * 参数--->lang:国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语，默认为zh-CN(可为空)
     * <p>
     * 正确返回--->openid:普通用户的标识，对当前开发者帐号唯一
     * 正确返回--->nickname:普通用户昵称
     * 正确返回--->sex:普通用户性别，1为男性，2为女性
     * 正确返回--->province:普通用户个人资料填写的省份
     * 正确返回--->city:普通用户个人资料填写的城市
     * 正确返回--->country:国家，如中国为CN
     * 正确返回--->headimgurl:用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
     * 正确返回--->privilege	:用户特权信息，json数组，如微信沃卡用户为（chinaunicom）
     * 正确返回--->unionid:用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的。
     * 错误返回--->"errcode":40003,"errmsg":"invalid openid"
     */
    String GET_USER_INFO = WeChatBaseUrl + "sns/userinfo";

    /**
     * 获取token
     *
     * @param url        请求地址
     * @param appid      应用唯一标识，在微信开放平台提交应用审核通过后获得
     * @param secret     应用密钥AppSecret，在微信开放平台提交应用审核通过后获得
     * @param code
     * @param grant_type 填authorization_code
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<WeChatAccessTokenVO> getAccessToken(@Url String url, @Field("appid") String appid,
                                                   @Field("secret") String secret, @Field("code") String code,
                                                   @Field("grant_type") String grant_type);

    /**
     * 刷新token
     *
     * @param url           请求地址
     * @param appid         应用唯一标识，在微信开放平台提交应用审核通过后获得
     * @param refresh_token
     * @param grant_type    refresh_token
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<WeChatAccessTokenVO> refreshAccessToken(@Url String url, @Field("appid") String appid,
                                                       @Field("refresh_token") String refresh_token,
                                                       @Field("grant_type") String grant_type);

    /**
     * 检查token是否可用
     *
     * @param url          请求地址
     * @param access_token 调用接口凭证
     * @param openid       普通用户标识，对该公众帐号唯一
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<WeChatAccessTokenVO> checkAccessToken(@Url String url, @Field("access_token") String access_token,
                                                     @Field("openid") String openid);

    /**
     * 获取用户信息
     *
     * @param url          请求地址
     * @param access_token 调用接口凭证
     * @param openid       普通用户标识，对该公众帐号唯一
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<WeChatUserInfoVo> getUserInfo(@Url String url, @Field("access_token") String access_token,
                                             @Field("openid") String openid);

}
