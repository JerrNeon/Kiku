package com.jn.kiku.ttp.share.wechat;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (微信AccessToken实体)
 * @create by: chenwei
 * @date 2017/3/9 12:00
 */
public class WeChatAccessTokenVO {

    private String access_token;//接口调用凭证
    private long expires_in;//access_token接口调用凭证超时时间，单位（秒）
    private String refresh_token;//用户刷新access_token
    private String openid;//授权用户唯一标识
    private String scope;//用户授权的作用域，使用逗号（,）分隔
    private String unionid;//当且仅当该移动应用已获得该用户的userinfo授权时，才会出现该字段

    private String errcode;//40029,
    private String errmsg;//"invalid code"

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
